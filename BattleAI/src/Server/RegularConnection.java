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
public class RegularConnection extends Connection {
    
    private int ticksLeft;
    
    public RegularConnection(Socket clientSocket, 
            ObjectInputStream inputStream,
            ObjectOutputStream outputStream) throws IOException {
        super(clientSocket, inputStream, outputStream);
        ticksLeft = 0;
    }
    
    public void start() {
        threadRunning = true;
        new Thread(this).start();
    }
    
    private void startConnectionHandler() {
        Timer connectionHandler = new Timer();
        
        TimerTask handleConnections = new TimerTask() {
            @Override
            public void run() {
                if (ticksLeft == 0) {
                    threadRunning = false;
                    activeConnection = false;
                    connectionHandler.cancel();
                    
                    try {
                        clientSocket.shutdownInput();
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

                    if (object instanceof RegularClientRequest) {
                        ticksLeft = Math.max(ticksLeft + 1, 1);

                        RegularClientRequest request = (RegularClientRequest)object;
                        if (request.getRequestType() == RegularRequestType.GET_MATCH_LIST)
                            ServerDispatcher.getInstance().processRequest(false, clientSocket, 
                                    request, inputStream, outputStream);
                    }
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MatchConnection.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(RegularConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
        /*try {
            //clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(RegularConnection.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
}
