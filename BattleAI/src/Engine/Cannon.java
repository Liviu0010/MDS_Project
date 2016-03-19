
package Engine;

import java.awt.geom.Rectangle2D;

public class Cannon extends GameEntity implements TransformInterface{
     Cannon(double xPos, double yPos,double ang, double spd, double dmg){
        x = xPos;
        y = yPos;
        angle = ang;
        speed = spd;
        damage = dmg;
    }
     Cannon(){
         boundingBox = new Rectangle2D.Double(100,100,10,10);
         x = y = 100;
         angle = 0;
         speed = 10;
         damage = 10;
     }

    @Override
    public void Rotate(double degrees) {
        
    }

    @Override
    public void Resize(double sx, double sy) {
        
    }
    
    
}
