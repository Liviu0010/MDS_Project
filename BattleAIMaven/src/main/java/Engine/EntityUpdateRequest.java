package Engine;

import Networking.Requests.Request;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class EntityUpdateRequest extends Request {
    ArrayList<GameEntity> gameEntities;
    
    public EntityUpdateRequest(int type, ArrayList<GameEntity> entities) {
        super(type);
        gameEntities = entities;
    }
    
    @Override
    public void execute(ObjectOutputStream outputStream) {
        
    }
    
    public ArrayList<GameEntity> getEntityList(){
        return gameEntities;
    }
}
