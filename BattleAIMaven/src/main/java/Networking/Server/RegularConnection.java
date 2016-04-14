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
 * MatchConnection handles the connection between a
 * hosted match and the master server. The master server requires the host of 
 * the match to send requests each PACKET_DELAY milliseconds in order
 * to check if the connection is still active. This class starts a thread 
 * the constructor running its own run method in order to read and
 * handle those requests.
 * The connection is deemed inactive if a period of PACKET_DELAY * 2 
 * milliseconds have passed and no request has been received!
 */
public class RegularConnection extends Connection {
    
    private int ticksLeft;
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
    
    private void start() {
        threadRunning = true;
        new Thread(this).start();
    }
    
    private void startConnectionHandler() {
        Timer connectionHandler = new Timer();
        
        TimerTask handleConnections = new TimerTask() {
            @Override
            public void run() {
                if (switchedConnection)
                {
                    connectionHandler.cancel();
                    return;
                }
                
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

                    ticksLeft = Math.max(ticksLeft + 1, 1);
                    
                    Request request = (Request)object;
                    request.execute(outputStream);
                    
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
