package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import Constants.MasterServerConstants;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 * MatchConnection handles the connection between a
 * hosted match and the master server. The master server requires the host of 
 * the match to send requests each PACKET_DELAY milliseconds in order
 * to check if the connection is still active. This class starts a thread 
 * the constructor running its own run method in order to read and
 * handle those requests.
 * The connection is deemed inactive if a period of PACKET_DELAY * 2 
 * milliseconds have passed and no request has been received!
 */
public class MatchConnection implements Runnable {
	
    private Match activeMatch;
    private Socket clientSocket;
    private Thread clientThread;
    private volatile boolean threadRunning;
    private boolean activeConnection;
    
    /**
     * @param clientSocket The client socket associated with the connection.
     * @param match The active match object to be used for the connection.
     */
    public MatchConnection(Socket clientSocket, Match match) {
        this.clientSocket = clientSocket;
        activeMatch = match;
        activeConnection = true;
        clientThread = new Thread(this);
        clientThread.start();
        threadRunning = true;
    }
    
    /**
     * @return Returns the match associated with the connection.
     */
    public Match getActiveMatch() {
        return activeMatch;
    }
    
    /**
     * @return Returns the client socket associated with the connection.
     */
    public Socket getClientSocket() {
        return clientSocket;
    }
    
    /**
     * @return Returns a boolean value indicating whether the connection
     * is active.
     */
    public boolean isActive() {
        return activeConnection;
    }
    
    /**
     * This method is used when starting a thread in the constructor.
     * Its purpose is to read requests each PACKET_DELAY milliseconds, to start 
     * a timer which will monitor whether a request has been received in the 
     * last PACKET_DELAY * 2 milliseconds and handle the connection accordingly.
     */
    @Override
    public void run() {
        Object object = null;
        
        Timer connectionHandler = new Timer();
        
        /* This task runs every PACKET_DELAY * 2 milliseconds. If 
        activeConnection is true, then a request has been received in the last 
        PACKET_DELAY * 2 milliseconds and we set activeConnection to false. 
        activeConnections becomes true after a request has been received. If 
        activeConnection is false when this task is executed, it means a request
        has not been received in the last PACKET_DELAY * 2 milliseconds and 
        the connection is inactive for good.
        */
        TimerTask handleConnections = new TimerTask() {
            @Override
            public void run() {
                if (activeConnection)
                    activeConnection = false;
                else {
                    // Shut down the thread
                    threadRunning = false;
                    try {
                        /* Close the input stream of the socket. This also 
                        forces readObject() to exit if it's still waiting for 
                        an object to be read from the stream.
                        */
                        clientSocket.shutdownInput();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        connectionHandler.scheduleAtFixedRate(handleConnections, 0, 
                MasterServerConstants.PACKET_DELAY * 2);

        while (threadRunning) {
            try (ObjectInputStream inputStream = 
                    new ObjectInputStream(clientSocket.getInputStream())) {
                Thread.sleep(MasterServerConstants.PACKET_DELAY);
                
                if (!clientSocket.isInputShutdown()) {
                    object = inputStream.readObject();

                    if (object instanceof ClientRequest) {
                        // Request has been receive so the connection is active
                        activeConnection = true;
                        
                        // Update the activeMatch in case of such request
                        if (object instanceof HostMatchRequest) 
                            activeMatch = ((HostMatchRequest)object).getMatch();
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        connectionHandler.cancel();
        try {
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(MatchConnection.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }
}
