package Client;

import Constants.MasterServerConstants;
import java.io.IOException;
import java.net.Socket;

public class ConnectionHandler {
    
    private static ConnectionHandler instance = new ConnectionHandler();
    
    private Socket masterServerSocket;
    private Socket matchSocket;
    
    private ConnectionHandler() {
        masterServerSocket = null;
        matchSocket = null;
    };
    
    public static ConnectionHandler getInstance() {
        return instance;
    }
    
    public void connectToMasterServer() throws IOException {
        if (masterServerSocket == null)
            masterServerSocket = new Socket(MasterServerConstants.IP, 
                    MasterServerConstants.PORT);
    }
    
}
