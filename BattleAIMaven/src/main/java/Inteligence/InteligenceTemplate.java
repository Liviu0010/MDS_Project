package Inteligence;

import Engine.Inteligence;
import Engine.Tank; 

public class InteligenceTemplate implements Inteligence{ 

    public void run(){
        
        while(true){
            
        }
        
    }

    @Override 
    public void gotHitByBullet() {} 

    @Override 
    public void hitArenaWall() {} 

    @Override 
    public void hitEnemyTank() {} 

    @Override 
    public void detectedEnemyTank(Tank enemy) {} 

}