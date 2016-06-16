package Networking.Server;

import java.io.Serializable;

/**
 * Singleton class representing the currently logged in player.
 */
public class Player implements Serializable {
    
    private static Player instance;
    private String username;
    private boolean loggedIn = false;
    
    private Player() {
    }
    
    public static Player getInstance() {
        if (instance == null)
            instance = new Player();
        return instance;
    }
    
    /**
     * Sets the player username.
     * @param username 
     */
    private void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Gets the player username.
     * @return 
     */
    public String getUsername() {
        if(username == null)
            return "Local";
        return username;
    }
    
    public boolean isLoggedIn(){
        return loggedIn;
    }
    
    /**
     * Set's the Player as logged into a Master-Server with the given username
     * @param username 
     */
    public void login(String username){
        loggedIn = true;
        setUsername(username);
    }
    
    /**
     * Set's the Player as Local
     */
    public void logout(){
        loggedIn = false;
        setUsername("Local");
    }
    
}
