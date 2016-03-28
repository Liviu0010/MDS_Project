/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Constants.MasterServerConstants;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class MatchConnection extends Connection {
    
    private Match activeMatch;
    
    public MatchConnection(Socket clientSocket, 
            Match activeMatch,
            ObjectInputStream inputStream,
            ObjectOutputStream outputStream) throws IOException {
        super(clientSocket, inputStream, outputStream);
        this.activeMatch = activeMatch;
    }
    
    public void start() {
        threadRunning = true;
        new Thread(this).start();
    }
    
    public Match getActiveMatch() {
        return activeMatch;
    }
    
    private void startConnectionHandler() {
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
                if (!threadRunning) {
                    connectionHandler.cancel();
                    return;
                }
                
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
                        System.out.println("Closed input");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        connectionHandler.scheduleAtFixedRate(handleConnections, 0, 
                MasterServerConstants.PACKET_DELAY * 2);
    }
    
    @Override
    public void run() {
        startConnectionHandler();
        
        Object object = null;

        while (threadRunning) {
            try {
                if (!clientSocket.isInputShutdown()) {
                    object = inputStream.readObject();
                    System.out.print("Received....");
                    if (object instanceof RegularClientRequest)
                        System.out.println("Regular client request");
                    if (object instanceof ClientRequest) {
                        System.out.println("Received client request");
                        // Request has been received so the connection is active
                        activeConnection = true;

                        // Update the activeMatch in case of such request
                        if (object instanceof HostMatchRequest) 
                            activeMatch = ((HostMatchRequest)object).getMatch();
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(MatchConnection.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MatchConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
        /*try {
            //clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(MatchConnection.class.getName()).log(Level.SEVERE, null, ex);
        }*/
       
    }
}
