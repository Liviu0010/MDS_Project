/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Requests;

import Client.ConnectionHandler;
import Networking.Server.ClientServerDispatcher;
import Networking.Server.Player;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
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
            System.out.println("Sending to master server");
            ConnectionHandler.getInstance().sendToMasterServer(new AddPlayer(username));
        } catch (IOException ex) {
            Logger.getLogger(PlayerConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getUsername() {
        return username;
    }
}
