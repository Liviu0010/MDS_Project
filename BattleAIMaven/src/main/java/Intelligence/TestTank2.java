package Intelligence;

import Console.ConsoleFrame;
import java.awt.Point;

/*
    Test class
*/
public class TestTank2 extends Intelligence.IntelligenceTemplate{
    @Override
    public void run(){
        rotateCannon(8);
        try {
            Thread.sleep(60);
        } catch (InterruptedException ex) {
            ConsoleFrame.sendMessage("TestTank2", "Thread interrupted");
        }
    }
    
    @Override
    public void detectedEnemyTank(double enemyAngle){
        fire();
        rotateCannon(enemyAngle);
    }
}
