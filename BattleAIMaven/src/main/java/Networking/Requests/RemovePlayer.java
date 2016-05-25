package Networking.Requests;

import java.io.ObjectOutputStream;

public class RemovePlayer extends Request {

    private final String username;
    
    public RemovePlayer(String username) {
        super(RequestType.REMOVE_PLAYER);
        this.username = username;
    }
    
    @Override
    public void execute(ObjectOutputStream outputStream) {
    }
    
    public String getUsername() {
        return username;
    }
}
