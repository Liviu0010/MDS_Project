
package Engine;
import java.awt.geom.*;
public class Bullet extends GameEntity implements TransformInterface{
    public Bullet(double xPos , double yPos , double ang, double spd,double dmg){
        boundingBox = new Rectangle2D.Double(xPos,yPos,5,5);
        x = xPos;
        y = yPos;
        angle = ang;
        speed = spd;
        damage = dmg;
    }
    public Bullet(){
        boundingBox = new Rectangle2D.Double(0,0,5,5);
        x = y = angle = 0;
        speed = 10;
        damage = 10;
    }
    public void MoveFront(){
        double s = Math.sin(angle * Math.PI/180.0);
        double c = Math.cos(angle * Math.PI/180.0);
        x += c*speed;
        y += s*speed;
    }

    @Override
    public void Rotate(double degrees) {
        angle = (angle+degrees)%360;
    }

    @Override
    public void Resize(double sx, double sy) {
        
    }
    
}
