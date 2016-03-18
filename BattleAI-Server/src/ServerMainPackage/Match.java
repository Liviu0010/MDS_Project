package ServerMainPackage;

import java.util.AbstractCollection;
import java.util.TreeSet;

public class Match {
	
	private String title;
	private String address;
	private String owner;
	private AbstractCollection<String> players;
	private int maxNumberOfPlayers;
	
	public Match(String name, String address, String owner, int maxNumberOfPlayers) {
		this.title = name;
		this.address = address;
		this.owner = owner;
		this.maxNumberOfPlayers = maxNumberOfPlayers;
		
		players = new TreeSet<String>();
		players.add(owner);
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public int getNumberOfPlayers() {
		return players.size();
	}
	
	public int getMaxNumberOfPlayers() {
		return maxNumberOfPlayers;
	}

	public void setMaxNumberOfPlayers(int maxNumberOfPlayers) {
		this.maxNumberOfPlayers = maxNumberOfPlayers;
	}
}
