package ServerMainPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Constants.ServerConstants;

public class ServerDispatcher implements Runnable {
	
	private static ServerDispatcher serverDispatcher = new ServerDispatcher();
	private static List<MatchConnection> activeConnections =
			Collections.synchronizedList(new LinkedList<MatchConnection>());
	
	private ServerDispatcher() {}
	
	public ServerDispatcher getInstance() {
		return serverDispatcher;
	}
	
	@Override
	public void run() {
		Timer connectionCleaner = new Timer();
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
		
		try (ServerSocket serverSocket = new ServerSocket(ServerConstants.PORT)) {
			Socket clientSocket = serverSocket.accept();
			MatchConnection clientConnection = new MatchConnection(clientSocket);
			activeConnections.add(clientConnection);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
