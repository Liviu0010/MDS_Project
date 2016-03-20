package ServerMainPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Constants.ServerConstants;

public class ServerDispatcher implements Runnable {
	
    private static ServerDispatcher serverDispatcher = new ServerDispatcher();
    private List<MatchConnection> activeConnections =
        Collections.synchronizedList(new LinkedList<MatchConnection>());
    private boolean isRunning = false;
    private Thread mainThread;

    private ServerDispatcher() {}

    public ServerDispatcher getInstance() {
        return serverDispatcher;
    }

    public boolean start() {
        if (!isRunning) {
            mainThread = new Thread(this);
            mainThread.start();
            isRunning = true;
            return true;
        }

        return false;
    }

    public boolean stop() throws InterruptedException {
        if (isRunning) {
            isRunning = false;
            activeConnections.clear();
            mainThread.join();
            return true;
        }

        return false;
    }

    @Override
    public void run() {
        Timer connectionCleaner = new Timer();
        
        /* This task checks the activeConnection list each PACKET_DELAY * 2 
          milliseconds and removes every inactive connection. */
        TimerTask removeInactiveConnections = new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < activeConnections.size(); i++)
                    if (!activeConnections.get(i).isActive()) {
                            activeConnections.remove(i);
                            i--;
                    }
            }
        };
        connectionCleaner.scheduleAtFixedRate(removeInactiveConnections, 0,
                        ServerConstants.PACKET_DELAY * 2);

        Object object = null;

        while (isRunning) {
            try (ServerSocket serverSocket = new ServerSocket(ServerConstants.PORT)) {
                Socket clientSocket = serverSocket.accept();
                
                /* Once connection is establshed, client should immediately send
                   a request to clarify the intent. If the request is not
                   received in 2 seconds, readObject() should timeout. */
                clientSocket.setSoTimeout(2000);

                ObjectInputStream objectInputStream = 
                        new ObjectInputStream(clientSocket.getInputStream());
                // Read a request from the accepted client.
                object = objectInputStream.readObject(); 
                
                // Handle client request
                if (object instanceof ClientRequest) 
                        processRequest(clientSocket, (ClientRequest)object);
            } catch (SocketTimeoutException e) {
                    e.printStackTrace();
            } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
            }
        }

    }

    private void processRequest(Socket clientSocket, ClientRequest clientRequest) throws IOException {
        if (clientRequest instanceof HostMatchRequest) {
            HostMatchRequest request = (HostMatchRequest)clientRequest;
            // We no longer need readObject() to timeout 
            clientSocket.setSoTimeout(0);
                
            MatchConnection clientConnection = 
                new MatchConnection(clientSocket, request.getMatch());
            
            activeConnections.add(clientConnection);
        } else if (clientRequest instanceof RegularClientRequest) {
            RegularClientRequest request = (RegularClientRequest)clientRequest;
            
            if (request.getRequestType() != RegularRequestType.GET_MATCH_LIST) 
                return;
            
            ObjectOutputStream objectOutputStream = 
                new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.writeObject(activeConnections);
            objectOutputStream.flush();
            objectOutputStream.close();
            clientSocket.close();
        }
    }
}
