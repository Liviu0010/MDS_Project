/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Server;

import Constants.MasterServerConstants;
import Networking.Requests.AddPlayer;
import Networking.Requests.HostMatch;
import Networking.Requests.PlayerConnect;
import Networking.Requests.Request;
import Networking.Requests.RequestType;
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
            ObjectInputStream inputStream,
            ObjectOutputStream outputStream,
            Match activeMatch)  {
        super(clientSocket, inputStream, outputStream);
        this.activeMatch = activeMatch;
        this.start();
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
                   
                    activeConnection = true;
                    
                    Request request = (Request)object;
                    request.execute(outputStream);
                    
                    if (request.getType() == RequestType.ADD_PLAYER) {
                        AddPlayer player = (AddPlayer)request;
                        activeMatch.addPlayer(player.getUsername());
                    }
                    
                    Thread.sleep(MasterServerConstants.PACKET_DELAY);
                }

            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(MatchConnection.class.getName()).log(Level.SEVERE, null, ex);
                threadRunning = false;
                activeConnection = false;
            } catch (InterruptedException ex) {
                Logger.getLogger(MatchConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
