/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Requests;

import Client.ConnectionHandler;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This request is sent when attempting to connect to a match.
 */
public class PlayerConnect extends Request {

    private String username;
    
    public PlayerConnect(String username) {
        super(RequestType.PLAYER_CONNECT);
        this.username = username;
    }

    @Override
    public void execute(ObjectOutputStream outputStream) {
        try {
            // Ask master server to update match
            ConnectionHandler.getInstance().sendToMasterServer(new AddPlayer(username));
        } catch (IOException ex) {
            Logger.getLogger(PlayerConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getUsername() {
        return username;
    }
}
