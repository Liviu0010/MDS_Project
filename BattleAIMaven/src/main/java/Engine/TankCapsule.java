package Engine;

import Console.ConsoleFrame;
import java.awt.Point;

/**
 * Encapsulation for the Tank class so that we can restrict
 * the player's access to it. However, the drawback is that
 * the tank objects have to be separately created in the entityList
 * from the GameEntity interface. Instances of this class will select
 * a Tank object from the entityList in order.
 * 
 */
public class TankCapsule {
    private Tank tank;
    
    public TankCapsule(){
        synchronized(GameEntity.entityList) {
            try{
            tank = (Tank)GameEntity.entityList.get(GameEntity.currentIndex++);
            }
            catch(ArrayIndexOutOfBoundsException ex){
                ConsoleFrame.sendMessage("TankCapsule", "Array out of bounds! No tank found in the gameEntity list!");
            }
            tank.setTankCapsule(this);
        }
    }
    
    public void gotHitByBullet() {} 
    public void hitArenaWall() {} 
    public void hitEnemyTank() {} 
    public void detectedEnemyTank(Point enemyLocation) {} 
    
    public final void moveFront(){
        tank.moveFront();
    }
    
    public final void rotateRight(){
        tank.rotate(5);
    }
    
    public final void rotateLeft(){
        tank.rotate(-5);
    }
    
    public final void rotateCannonLeft(){
        tank.rotateCannon(-5);
    }
    
    public final void rotateCannonRight(){
        tank.rotateCannon(5);
    }
    
    public final void fire(){
            Bullet b = tank.fire();
            
            if (b != null) {
            synchronized (GameEntity.entityList) {
                GameEntity.entityList.add(b);
            }
        }
    }
}
