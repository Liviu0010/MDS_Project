/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking.Requests;

import Database.DatabaseHandler;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class RegisterAccount extends Request {
    private final String username;
    private final String password;
    
    public RegisterAccount(String username, String password) {
        super(RequestType.REGISTER_ACCOUNT);
        this.username = username;
        this.password = password;
    }
    
    @Override
    public void execute(ObjectOutputStream outputStream) {
        try {
            if (DatabaseHandler.getInstance().findName(username))
                outputStream.writeObject(new BooleanResponse(false));
            else {
                outputStream.writeObject(new BooleanResponse(true));
                DatabaseHandler.getInstance().pushPlayer(username, password);
            }
            outputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(LoginAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
