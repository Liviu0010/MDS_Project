package Networking.Requests;

import Engine.GameEntity;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class EntityUpdateRequest extends Request {
    ArrayList<GameEntity> gameEntities;
    
    public EntityUpdateRequest(ArrayList<GameEntity> entities) {
        super(RequestType.ENTITIY_UPDATE);
        gameEntities = entities;
    }
    
    @Override
    public void execute(ObjectOutputStream outputStream) {
        
    }
}
