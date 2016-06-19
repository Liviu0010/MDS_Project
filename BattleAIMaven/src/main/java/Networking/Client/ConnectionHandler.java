package Networking.Client;

import Console.ConsoleFrame;
import Constants.MasterServerConstants;
import Interface.MainFrame;
import Interface.MultiplayerServerPanel;
import Networking.Requests.PlayerConnect;
import Networking.Requests.RegisterActivity;
import Networking.Server.Match;
import Networking.Requests.Request;
import Networking.Server.ClientServerDispatcher;
import Networking.Server.Player;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles two type of connections, the connection to the master 
 * server and the connection to a match.
 */
public class ConnectionHandler {
    
    private final static ConnectionHandler INSTANCE = new ConnectionHandler();
    
    private final InetSocketAddress masterServerAddress;
    private Socket masterServerSocket;
    private ObjectInputStream masterServerInputStream;
    private ObjectOutputStream masterServerOutputStream;
    
    private Socket matchSocket;
    private ObjectInputStream matchInputStream;
    private ObjectOutputStream matchOutputStream;
    
    private boolean host;
    // this variable indicated where the disconnect was voluntary
    private boolean disconnectedFromMatch;
    
    private final BlockingQueue<Request> gameDataQueue;
           
    public boolean isHost() {
        return host;
    }
    
    private ConnectionHandler() {
        masterServerAddress = 
                new InetSocketAddress(MasterServerConstants.IP, MasterServerConstants.PORT);
        gameDataQueue = new LinkedBlockingQueue<>();
        masterServerSocket = null;
        matchSocket = null;
        host = false;
        disconnectedFromMatch = false;
        matchSocket = null;
    };
    
    public static ConnectionHandler getInstance() {
        return INSTANCE;
    }
    
    private void connectToMasterServer() throws IOException {
        masterServerSocket = new Socket();
        masterServerSocket.connect(masterServerAddress, 4000);
        masterServerSocket.setSoTimeout(5000);
        masterServerOutputStream = new ObjectOutputStream(masterServerSocket.getOutputStream());
        masterServerOutputStream.flush();
        masterServerInputStream = new ObjectInputStream(masterServerSocket.getInputStream());
        
    }
    
    public boolean hostMatch(Match activeMatch) {
        if (!host) {
            if (ClientServerDispatcher.getInstance().start(activeMatch)) {
                try {
                    host = true;
                    connectToMatch(activeMatch);
                } catch (IOException ex) {
                        Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return host;
    }
         
    /**
     * Attempts to send a request to master server.
     * @param request Request send to the master server.
     * @throws IOException 
     */
    public synchronized void sendToMasterServer(Request request) throws IOException {
        if (masterServerSocket == null)
            connectToMasterServer();
        try {
            masterServerOutputStream.writeObject(request);
        } catch (IOException ex) {
            //Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            ConsoleFrame.sendMessage(this.getClass().getSimpleName(), ex.getMessage());
            connectToMasterServer();
            masterServerOutputStream.writeObject(request);
        }
        
        masterServerOutputStream.flush();
    }
    
    /**
     * Attempt to read an object from the master server.
     * @return Object read from master server.
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public Object readFromMasterServer() throws IOException, ClassNotFoundException {
        if (masterServerSocket == null)
            connectToMasterServer();
        Object result = null;
        try {
            result = masterServerInputStream.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            connectToMasterServer();
            try {
                result = masterServerInputStream.readObject();
            } catch (IOException ex2) {
                masterServerSocket = null;
                ConsoleFrame.showError("Connection timed out.");
                throw ex2;
            }
        }
        return result;
    }
    
    public void connectToMatch(Match match) throws IOException {
        InetSocketAddress address = new InetSocketAddress(match.getIP(), match.getPort());
        matchSocket = new Socket();

        matchSocket.connect(address, 3500);
        
        matchOutputStream = new ObjectOutputStream(matchSocket.getOutputStream());
        matchOutputStream.flush();
        matchInputStream = new ObjectInputStream(matchSocket.getInputStream());
        matchOutputStream.writeObject(new PlayerConnect(Player.getInstance().getUsername()));
        matchOutputStream.flush();
       
        
        Timer t = new Timer();
        TimerTask notification = new TimerTask() {
            @Override
            public void run() {
                try {
                    matchOutputStream.writeObject(new RegisterActivity());
                    matchOutputStream.flush();
                } catch (IOException ex) {
                    t.cancel();
                    
                    if (!host && !disconnectedFromMatch) {
                        MainFrame.getInstance()
                                .changePanel(new MultiplayerServerPanel(MainFrame.getInstance()));
                        ConsoleFrame.showError("Connection lost.");
                    } else {
                        host = false;
                        disconnectedFromMatch = false;
                    }
                }
                System.out.println("Send acknowledgement");
            }
        };
        t.scheduleAtFixedRate(notification, 0, MasterServerConstants.PACKET_DELAY);
    }
    
    public void disconnectFromMatch() {
        try {
            matchSocket.close();
            disconnectedFromMatch = true;
            if (host) {
                ClientServerDispatcher.getInstance().stop();
                /* the server does not disappear almost immediately from the 
                   server-browser after it is closed. The workaround is to close
                   the master-server socket when closing the server. The issue
                   should be further investigated */
                masterServerSocket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Object readFromMatch() throws IOException, ClassNotFoundException {
        Object object = matchInputStream.readObject();
        return object;
    }
    
    public void sendToMatch(Request request) throws IOException {
        matchOutputStream.reset();
        matchOutputStream.writeObject(request);
        matchOutputStream.flush();
    }
    
    public void addGameData(Request request) {
        gameDataQueue.add(request);
    }
    
    public Request getGameData() throws InterruptedException {
        return gameDataQueue.take();
    }
    
    public void clearGameData() {
        gameDataQueue.clear();
    }
}
