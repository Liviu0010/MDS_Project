/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Server;

import Console.ConsoleFrame;
import Constants.MasterServerConstants;
import Networking.Requests.HostMatch;
import Networking.Requests.RegisterActivity;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class ClientServerDispatcher extends ServerDispatcher {
    
    private Match activeMatch;
    private static ClientServerDispatcher instance;
    
    private ClientServerDispatcher() {
    }
    
    public static ClientServerDispatcher getInstance() {
        if (instance == null)
            instance = new ClientServerDispatcher();
        
        return instance;
    }
    
    private void startMasterServerNotifier() {
        Timer masterServerNotifier = new Timer();
                
        TimerTask notification = new TimerTask() {
            private Socket masterServerSocket = null;
            private ObjectOutputStream objectOutputStream = null;
            @Override
            public void run() {
                if (masterServerSocket == null)
                    try {
                        masterServerSocket = new Socket(MasterServerConstants.IP, MasterServerConstants.PORT);
                        System.out.println("Connection established");
                       objectOutputStream = 
                            new ObjectOutputStream(masterServerSocket.getOutputStream());
                        objectOutputStream.flush();
                        objectOutputStream.writeObject(new HostMatch(activeMatch));
                        objectOutputStream.flush();
                } catch (IOException ex) {
                    Logger.getLogger(ServerDispatcher.class.getName()).log(Level.SEVERE, null, ex);
                    this.cancel();
                }

                try {
                    System.out.println("Send Acknowledgement");
                    objectOutputStream.writeObject(new RegisterActivity());
                    objectOutputStream.flush();
                } catch (IOException ex) {
                    Logger.getLogger(ServerDispatcher.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        masterServerNotifier.scheduleAtFixedRate(notification, 0, MasterServerConstants.PACKET_DELAY);
    }
    
    public boolean start(int port, Match match) {
        this.activeMatch = match;
        startMasterServerNotifier();
        return this.start(port);
    }
    
    protected void listenForConnections(ServerSocket serverSocket) {
        while (isRunning) {
            try {
                Socket clientSocket = serverSocket.accept();
                activeConnections.add(new PlayerConnection(clientSocket));
            } catch (IOException ex) {
                Logger.getLogger(ServerDispatcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void run() {
        try (ServerSocket serverSocket = 
                    new ServerSocket(port)) {
            startConnectionCleaner();
            listenForConnections(serverSocket);
        } catch (IOException ex) {
            Logger.getLogger(ServerDispatcher.class.getName()).log(Level.SEVERE, null, ex);
            ConsoleFrame.sendMessage(getClass().getName(),
                    "Failed to start master server.\n" + ex.getMessage());
        }
    }
    
    public void broadcast(Object object) {
        for (Connection i: activeConnections)
            try {
                i.getOutputStream().writeObject(object);
            } catch (IOException ex) {
                Logger.getLogger(ClientServerDispatcher.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
