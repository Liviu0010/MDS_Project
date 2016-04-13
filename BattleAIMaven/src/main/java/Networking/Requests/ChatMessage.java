/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Requests;

import Networking.Server.ClientServerDispatcher;
import Networking.Server.Match;
import Networking.Server.ServerDispatcher;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class ChatMessage extends Request {

    private String message;

    public ChatMessage(String message) {
        super(RequestType.CHAT_MESSAGE);
        this.message = message;
    }
    
    @Override
    public void execute(ObjectOutputStream outputStream) {
        ClientServerDispatcher.getInstance().broadcast(this);
    }
    
}
