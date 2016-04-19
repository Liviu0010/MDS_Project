package Networking.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import Networking.Requests.HostMatch;
import Networking.Requests.Request;
import Networking.Requests.RequestType;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 * <pre>RegularConnection handles the interaction between clients who do not currently
 * host a match and the master server. These interactions involve registering,
 * logging in, requesting the match list etc. This type of connection is not 
 * continuous, meaning that it closes itself after a period of time.
 * More specifically the connection starts with 30 seconds left until closure.
 * If a request is made, then it adds 30 seconds to the timer up to a maximum
 * of 60 seconds left.
 * If a HostMatch request is made, this type of connection closes immediately
 * and a MatchConnection using current socket and existing streams is created.
 * </pre>
 */
public class RegularConnection extends Connection {
    
    // variable used to count how many stacks of 30 seconds are left
    private int ticksLeft;
    
    /* variable used to indicate whether this connection has been switched to
    a MatchConnection */
    private boolean switchedConnection;
    
    /**
     * @param clientSocket The client socket associated with the connection.
     * @param match The active match object to be used for the connection.
     */
    public RegularConnection(Socket clientSocket) throws IOException {
        super(clientSocket);
        this.start();
        switchedConnection = false;
    }
    
    /**
     * Starts the main thread
     */
    private void start() {
        threadRunning = true;
        new Thread(this).start();
    }
    
    /**
     * Starts the connection handler used to count how many time this
     * connection has left and close it once that time expires.
     */
    private void startConnectionHandler() {
        Timer connectionHandler = new Timer();
        
        TimerTask handleConnections = new TimerTask() {
            @Override
            public void run() {
                // if switched, close this handle
                if (switchedConnection)
                {
                    connectionHandler.cancel();
                    return;
                }
                
                // 0 seconds remaining and no ticks left so close the connection
                if (ticksLeft == 0) {
                    threadRunning = false;
                    activeConnection = false;
                    connectionHandler.cancel();
                    
                    try {
                        clientSocket.close();
                    } catch (IOException ex) {
                        Logger.getLogger(RegularConnection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                    ticksLeft--;
            }
        };
        
        connectionHandler.scheduleAtFixedRate(handleConnections, 30 * 1000, 
                30 * 1000);
    }
    
    @Override
    public void run() {
        startConnectionHandler();
        
        Object object = null;
        
        while (threadRunning) {
            try {

                if (!clientSocket.isInputShutdown()) {
                    object = inputStream.readObject();
                    
                    /* no more than 1 tick is allowed (maximum 60 seconds time 
                    until it closes) */
                    ticksLeft = Math.max(ticksLeft + 1, 1);
                    
                    Request request = (Request)object;
                    request.execute(outputStream);
                    
                    // switch the connection if HostMatch request is made
                    if (request.getType() == RequestType.HOST_MATCH) {
                        Match match = ((HostMatch)request).getMatch();
                        switchedConnection = true;
                        ServerDispatcher.getInstance().addConnection(new MatchConnection(clientSocket, inputStream, outputStream, match));
                        threadRunning = false;
                        activeConnection = false;
                    }
                }
            } catch (ClassNotFoundException | IOException ex) {
                Logger.getLogger(MatchConnection.class.getName()).log(Level.SEVERE, null, ex);
                threadRunning = false;
                activeConnection = false;
            }
        }
    }
}
