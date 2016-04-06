package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import Constants.MasterServerConstants;
import java.io.ObjectOutputStream;
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
public abstract class Connection implements Runnable {
	
    //private Match activeMatch;
    protected ObjectOutputStream outputStream;
    protected ObjectInputStream inputStream;
    protected Socket clientSocket;
    protected Thread clientThread;
    protected volatile boolean threadRunning;
    protected boolean activeConnection;
    public abstract void start();
    
    /**
     * @param clientSocket The client socket associated with the connection.
     * @param match The active match object to be used for the connection.
     */
    public Connection(Socket clientSocket,
            ObjectInputStream inputStream,
            ObjectOutputStream outputStream) throws IOException {
        this.clientSocket = clientSocket;
        activeConnection = true;
        threadRunning = false;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
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
    }
}
