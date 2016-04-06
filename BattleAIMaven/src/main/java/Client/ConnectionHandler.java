package Client;

import Constants.MasterServerConstants;
import Server.ClientRequest;
import Server.Match;
import Server.ServerDispatcher;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionHandler {
    
    private static ConnectionHandler instance = new ConnectionHandler();
    
    private Socket masterServerSocket;
    private Socket matchSocket;
    private ObjectInputStream masterServerInputStream;
    private ObjectOutputStream masterServerOutputStream;
    private boolean isHost;
    
    private ConnectionHandler() {
        masterServerSocket = null;
        matchSocket = null;
        isHost = false;
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
            if (ServerDispatcher.getInstance().start(activeMatch.getPort(), false, activeMatch))
                isHost = true;
        }
        
        return isHost;
    }
         
    public void sendToMasterServer(ClientRequest request) throws IOException {
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
    
}
