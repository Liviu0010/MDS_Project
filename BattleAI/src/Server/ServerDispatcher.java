package Server;

import Console.ConsoleFrame;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Constants.MasterServerConstants;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerDispatcher implements Runnable {
	
    private static ServerDispatcher serverDispatcher = new ServerDispatcher();
    private List<Connection> activeConnections =
        Collections.synchronizedList(new LinkedList<Connection>());
    private boolean isRunning = false;
    private Thread mainThread;
    public boolean isMasterServer = false;
    private int port;
    private ServerDispatcher() {}

    public static ServerDispatcher getInstance() {
        return serverDispatcher;
    }

    public boolean start(int port, boolean isMasterServer, Match match) {
        if (!isRunning) {
            this.isMasterServer = isMasterServer;
            this.port = port;
            mainThread = new Thread(this);
            isRunning = true;
            mainThread.start();
            
            
            if (!isMasterServer) {
                Timer masterServerNotifier = new Timer();
                
                TimerTask notification = new TimerTask() {
                    private Socket masterServerSocket = null;
                    private ObjectOutputStream objectOutputStream = null;
                    @Override
                    public void run() {
                        if (masterServerSocket == null)
                            try {
                                masterServerSocket = new Socket(MasterServerConstants.IP, MasterServerConstants.PORT);
                                System.out.println("Connection established");
                               objectOutputStream = 
                                    new ObjectOutputStream(masterServerSocket.getOutputStream());
                                objectOutputStream.flush();
                                objectOutputStream.writeObject(new HostMatchRequest(match));
                                objectOutputStream.flush();
                        } catch (IOException ex) {
                            Logger.getLogger(ServerDispatcher.class.getName()).log(Level.SEVERE, null, ex);
                            this.cancel();
                        }
                        
                        try {
                            System.out.println("Send Acknowledgement");
                            objectOutputStream.writeObject(new RegularClientRequest(RegularRequestType.ACKNOWLEDGE_ACTVITIY));
                            objectOutputStream.flush();
                        } catch (IOException ex) {
                            Logger.getLogger(ServerDispatcher.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };
                
                masterServerNotifier.scheduleAtFixedRate(notification, 0, MasterServerConstants.PACKET_DELAY);
            }
            
            return true;
        }

        return false;
    }

    public boolean stop() throws InterruptedException {
        if (isRunning) {
            isRunning = false;
            activeConnections.clear();
            mainThread.join();
            return true;
        }

        return false;
    }

    @Override
    public void run() {        
        try (ServerSocket serverSocket = 
                    new ServerSocket(port)) {
            Timer connectionCleaner = new Timer();

            /* This task checks the activeConnection list each PACKET_DELAY * 2 
              milliseconds and removes every inactive connection. */
            TimerTask removeInactiveConnections = new TimerTask() {
                @Override
                public void run() {
                    for (int i = 0; i < activeConnections.size(); i++)
                        if (!activeConnections.get(i).isActive()) {
                                System.out.println("removing");
                                activeConnections.remove(i);
                                i--;
                        }
                }
            };
            connectionCleaner.scheduleAtFixedRate(removeInactiveConnections, 0,
                            MasterServerConstants.PACKET_DELAY * 2);

            Object object = null;

            while (isRunning) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                    objectOutputStream.flush();
                    /* Once connection is establshed, client should immediately send
                       a request to clarify the intent. If the request is not
                       received in 2 seconds, readObject() should timeout. */
                    clientSocket.setSoTimeout(90000);
                   
                    ObjectInputStream objectInputStream = 
                            new ObjectInputStream(clientSocket.getInputStream());
                   
                    /* Read a request from the accepted client.
                       The accepted client should immediately clarify the purpose
                       of the connection. */
                    object = objectInputStream.readObject();
                    System.out.println("Received request");
                    // Handle client request
                    if (object instanceof ClientRequest) 
                            processRequest(true, 
                                    clientSocket, 
                                    (ClientRequest)object,
                                    objectInputStream, 
                                    objectOutputStream);
                } catch (IOException ex) {
                    Logger.getLogger(ServerDispatcher.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ServerDispatcher.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ServerDispatcher.class.getName()).log(Level.SEVERE, null, ex);
            ConsoleFrame.sendMessage(getClass().getName(),
                    "Failed to start master server.\n" + ex.getMessage());
        }

    }

    public void processRequest(boolean first, Socket clientSocket, 
            ClientRequest clientRequest,
            ObjectInputStream inputStream, 
            ObjectOutputStream outputStream) throws IOException {
        if (clientRequest instanceof HostMatchRequest) {
            HostMatchRequest request = (HostMatchRequest)clientRequest;
            // We no longer need readObject() to timeout 
            clientSocket.setSoTimeout(0);
                
            Connection clientConnection = 
                new MatchConnection(clientSocket, request.getMatch(), inputStream, outputStream);
            clientConnection.start();
            
            activeConnections.add(clientConnection);
        } else if (clientRequest instanceof RegularClientRequest) {
            RegularClientRequest request = (RegularClientRequest)clientRequest;
            
            if (request.getRequestType() != RegularRequestType.GET_MATCH_LIST) 
                return;
            
            if (first) {
                Connection clientConnection = new RegularConnection(clientSocket, inputStream, outputStream);
                clientConnection.start();
            }
            
            sendActiveMatches(outputStream);
            outputStream.flush();
        }
    }
    
    private void sendActiveMatches(ObjectOutputStream objectOutputStream) throws IOException {
        List<Match> activeMatches = new LinkedList<Match>();
        
        MatchConnection matchConnection = null;
        for (Connection connection: activeConnections) {
            if (connection.isActive() && connection instanceof MatchConnection) {
                matchConnection = (MatchConnection)connection;
                System.out.println("Sent match: " + matchConnection.getActiveMatch().getTitle());
                activeMatches.add(matchConnection.getActiveMatch());
            }
        }
        
        objectOutputStream.writeObject(activeMatches);
    }
}
