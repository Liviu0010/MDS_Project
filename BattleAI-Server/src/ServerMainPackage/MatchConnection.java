package ServerMainPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import Constants.ServerConstants;

public class MatchConnection implements Runnable {
	
    private Match activeMatch;
    private Socket clientSocket;
    private Thread clientThread;
    private volatile boolean threadRunning;
    private boolean activeConnection;

    public MatchConnection(Socket clientSocket, Match match) {
        this.clientSocket = clientSocket;
        activeMatch = match;
        threadRunning = true;
        activeConnection = true;
        clientThread = new Thread(this);
        clientThread.start();
    }

    public Match getActiveMatch() {
        return activeMatch;
    }

    public boolean isActive() {
        return activeConnection;
    }

    @Override
    public void run() {
        Object object = null;

        Timer connectionHandler = new Timer();
        TimerTask handleConnections = new TimerTask() {
            @Override
            public void run() {
                if (activeConnection)
                    activeConnection = false;
                else {
                    threadRunning = false;
                    try {
                            clientSocket.shutdownInput();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        connectionHandler.scheduleAtFixedRate(handleConnections, 0, 
                ServerConstants.PACKET_DELAY * 2);

        while (threadRunning) {
            try (ObjectInputStream inputStream = 
                    new ObjectInputStream(clientSocket.getInputStream())) {
                Thread.sleep(ServerConstants.PACKET_DELAY);

                if (!clientSocket.isInputShutdown()) {
                    object = inputStream.readObject();

                    if (object instanceof ClientRequest) {
                        activeConnection = true;
                        
                        if (object instanceof HostMatchRequest) 
                            activeMatch = ((HostMatchRequest)object).getMatch();
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        connectionHandler.cancel();
    }
}
