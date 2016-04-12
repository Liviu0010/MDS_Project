/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Console.ConsoleFrame;
import Constants.MasterServerConstants;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

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
    
    @Override
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
        connectionHandler.scheduleAtFixedRate(handleConnections, MasterServerConstants.PACKET_DELAY * 2, 
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
                   
                    
                    if (object instanceof ClientRequest) {
                        // Request has been received so the connection is active
                        activeConnection = true;

                        // Update the activeMatch in case of such request
                        if (object instanceof HostMatchRequest) 
                            activeMatch = ((HostMatchRequest)object).getMatch();
                    }
                    
                    Thread.sleep(MasterServerConstants.PACKET_DELAY);
                }

            } catch (IOException | ClassNotFoundException ex) {
                ConsoleFrame.sendMessage(MatchConnection.class.getSimpleName(), "Failed to read message or client disconnected");
                threadRunning = false;
                activeConnection = false;
            } catch (InterruptedException ex) {
                ConsoleFrame.sendMessage(MatchConnection.class.getSimpleName(), "Sleeping thread interrupted");
            }
        }
            
        /*try {
            //clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(MatchConnection.class.getName()).log(Level.SEVERE, null, ex);
        }*/
       
    }
}
