package Intelligence;

import Engine.TankCapsule;
import java.awt.Point;

public class IntelligenceTemplate extends TankCapsule{ 
    
    public boolean anaAreMere;
    
    public IntelligenceTemplate(){
        super();
    }
    
    public void run(){
    }

    @Override 
    public void gotHitByBullet() {} 

    @Override 
    public void hitArenaWall() {} 

    @Override 
    public void hitEnemyTank() {} 

    @Override 
    public void detectedEnemyTank(double enemyAngle) {} 

}
