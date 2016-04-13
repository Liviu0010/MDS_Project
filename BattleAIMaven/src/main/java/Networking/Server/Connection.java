package Networking.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import java.io.ObjectOutputStream;

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
    
    protected Socket clientSocket;
    protected ObjectOutputStream outputStream;
    protected ObjectInputStream inputStream;
    protected Thread clientThread;
    protected volatile boolean threadRunning;
    protected boolean activeConnection;
    
    /**
     * @param clientSocket The client socket associated with the connection.
     * @param match The active match object to be used for the connection.
     */
    public Connection(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        outputStream.flush();
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
        activeConnection = true;
    }
    
    public Connection(Socket clientSocket, ObjectInputStream inputStream,
            ObjectOutputStream outputStream) {
        this.clientSocket = clientSocket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        activeConnection = true;
    }
    
    /**
     * @return Returns the client socket associated with the connection.
     */
    public Socket getClientSocket() {
        return clientSocket;
    }
    
    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }
    
    public ObjectInputStream getInputStream() {
        return inputStream;
    }
    
    /**
     * @return Returns a boolean value indicating whether the connection
     * is active.
     */
    public boolean isActive() {
        return activeConnection;
    }
    
    @Override
    public void run() {
    }
}
