package Networking.Server;

import Console.ConsoleFrame;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Constants.MasterServerConstants;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerDispatcher implements Runnable {
	
    private static ServerDispatcher serverDispatcher = new ServerDispatcher();
    protected List<Connection> activeConnections =
        Collections.synchronizedList(new LinkedList<Connection>());
    protected boolean isRunning = false;
    protected Thread mainThread;
    protected int port;
    protected final ExecutorService THREAD_POOL;
    
    protected ServerDispatcher() {
        THREAD_POOL = Executors.newCachedThreadPool();
    }
    
    public static ServerDispatcher getInstance() {
        return serverDispatcher;
    }

    public boolean start(int port) {
        if (!isRunning) {
            this.port = port;
            mainThread = new Thread(this);
            isRunning = true;
            mainThread.start();
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
    
    protected void listenForConnections(ServerSocket serverSocket) {
        while (isRunning) {
            try {
                Socket clientSocket = serverSocket.accept();
                addConnection(new RegularConnection(clientSocket));
            } catch (IOException ex) {
                Logger.getLogger(ServerDispatcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    protected void startConnectionCleaner() {
        Timer connectionCleaner = new Timer();

        /* This task checks the activeConnection list each PACKET_DELAY * 2 
          milliseconds and removes every inactive connection. */
        TimerTask removeInactiveConnections = new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < activeConnections.size(); i++)
                    if (!activeConnections.get(i).isActive()) {
                            System.out.println("removing");
                            ConsoleFrame.sendMessage(TimerTask.class.getSimpleName(),
                                    "Removing connection with "+activeConnections.get(i).getClientSocket().getInetAddress());
                            activeConnections.remove(i);
                            i--;
                    }
            }
        };
        connectionCleaner.scheduleAtFixedRate(removeInactiveConnections, 0,
                        MasterServerConstants.PACKET_DELAY * 2);
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
    
    public List<Match> getActiveMatches() throws IOException {
        List<Match> activeMatches = new LinkedList<>();
        
        MatchConnection matchConnection = null;
        for (Connection connection: activeConnections) {
            if (connection.isActive() && connection instanceof MatchConnection) {
                matchConnection = (MatchConnection)connection;
                ConsoleFrame.sendMessage(this.getClass().getSimpleName(),
                        "Sent match: " + matchConnection.getActiveMatch().getTitle()
                        +" to " + connection.getClientSocket().getInetAddress());
                System.out.println("Sent match: " + matchConnection.getActiveMatch().getTitle());
                activeMatches.add(matchConnection.getActiveMatch());
            }
        }
        
        return activeMatches;
    }
    
    public List<String> getLocalConnections(){
        List<String> connections = new LinkedList<>();
        
        for(Connection connection:activeConnections){
            if(connection.isActive()){
                connections.add(connection.getClientSocket().getInetAddress().getHostAddress());
            }
        }
        return connections;
    }
    
    public void addConnection(Connection connection) {
        THREAD_POOL.execute(connection);
        activeConnections.add(connection);
        ConsoleFrame.sendMessage(this.getClass().getSimpleName(), "Added connection with "+connection.getClientSocket().getInetAddress());
    }
}
