package Client;

import Constants.MasterServerConstants;
import Networking.Server.Match;
import Networking.Requests.Request;
import Networking.Server.ClientServerDispatcher;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionHandler {
    
    private static ConnectionHandler instance = new ConnectionHandler();
    
    private Socket masterServerSocket;
    private ObjectInputStream masterServerInputStream;
    private ObjectOutputStream masterServerOutputStream;
    
    private Socket matchSocket;
    private ObjectInputStream matchInputStream;
    private ObjectOutputStream matchOutputStream;
    
    private boolean isHost;
    
    private ConnectionHandler() {
        masterServerSocket = null;
        matchSocket = null;
        isHost = false;
        
        matchSocket = null;
    };
    
    public static ConnectionHandler getInstance() {
        return instance;
    }
    
    private void connectToMasterServer() throws IOException {
        masterServerSocket = new Socket(MasterServerConstants.IP, 
                MasterServerConstants.PORT);
        masterServerOutputStream = new ObjectOutputStream(masterServerSocket.getOutputStream());
        masterServerOutputStream.flush();
        masterServerInputStream = new ObjectInputStream(masterServerSocket.getInputStream());
        
    }
    
    public boolean hostMatch(Match activeMatch) {
        if (!isHost) {
            if (ClientServerDispatcher.getInstance().start(activeMatch.getPort(), activeMatch))
                isHost = true;
        }
        
        return isHost;
    }
         
    public void sendToMasterServer(Request request) throws IOException {
        if (masterServerSocket == null)
            connectToMasterServer();
        try {
            masterServerOutputStream.writeObject(request);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            connectToMasterServer();
            masterServerOutputStream.writeObject(request);
        }
        
        masterServerOutputStream.flush();
    }
    
    public Object readFromMasterServer() throws IOException, ClassNotFoundException {
        if (masterServerSocket == null)
            connectToMasterServer();
        Object result = null;
        try {
            result = masterServerInputStream.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            connectToMasterServer();
            result = masterServerInputStream.readObject();
        }
        return result;
    }
    
    public void connectToMatch(Match match) throws IOException {
        matchSocket = new Socket(match.getIP(), match.getPort());
        matchOutputStream = new ObjectOutputStream(matchSocket.getOutputStream());
        matchOutputStream.flush();
        matchInputStream = new ObjectInputStream(matchSocket.getInputStream());
    }
    
    public Object readFromMatch() throws IOException, ClassNotFoundException {
        Object object = matchInputStream.readObject();
        return object;
    }
    
    public void sendToMatch(Request request) throws IOException {
        matchOutputStream.writeObject(request);
    }
    
}
