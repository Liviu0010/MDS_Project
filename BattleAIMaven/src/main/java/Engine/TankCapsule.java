package Engine;

import Engine.Tank;

public class TankCapsule {
    private Tank tank;
    
    public TankCapsule(){
        synchronized(GameEntity.entityList) {
            tank = (Tank)GameEntity.entityList.get(GameEntity.currentIndex++);
        }
    }
    
    public void moveFront(){
        tank.moveFront();
    }
    
    public void rotateRight(){
        tank.rotate(5);
    }
    
    public void rotateLeft(){
        tank.rotate(-5);
    }
    
    public void rotateCannonLeft(){
        tank.rotateCannon(-10);
    }
    
    public void rotateCannonRight(){
        tank.rotateCannon(10);
    }
}
