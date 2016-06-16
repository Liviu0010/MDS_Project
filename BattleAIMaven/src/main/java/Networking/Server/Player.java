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
    public void setUsername(String username) {
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
    
    public void login(){
        loggedIn = true;
    }
    
}
