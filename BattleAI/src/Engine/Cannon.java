
package Engine;

import java.awt.geom.Rectangle2D;

public class Cannon extends GameEntity implements TransformInterface{
    public Cannon(double xPos, double yPos,double ang, double spd, double dmg){
        x = xPos;
        y = yPos;
        angle = ang;
        speed = spd;
        damage = dmg;
    }
    public Cannon(){
         boundingBox = new Rectangle2D.Double(100,100,10,10);
         x = y = 100;
         angle = 0;
         speed = 10;
         damage = 10;
     }
     
    @Override
    public void Rotate(double degrees) {
        angle = (angle+degrees)%360;
    }

    @Override
    public void Resize(double sx, double sy) {
        
    }
    
    
}
