package Engine;

import java.awt.Shape;
import java.awt.geom.*;

/**
 *
 * A bullet class used by tank class to destroy enemies.
 */
public class Bullet extends GameEntity implements TransformInterface{
    final private int width,height;
    private int tankId;
    public Bullet(double xPos , double yPos , double spd, double dmg,double ang,int id){
        super(xPos,yPos,spd,dmg,ang,15,15);                 //spd represents the speed of the bullet, dmg represents the damage that the bullet gives to an
                                                            //enemy tank and ang represents the angle in wich the bullet is rotated
                                                            //id represents the id of the tank the fired the bullet
        width = height = 15;                                                    
        x = xPos;
        y = yPos;
        tankId = id;
    }
    public Bullet(){
        super(0,0,12,11,13,15,15);
        x = y =0;
        width = height = 15;
        tankId = 0;
    }
    public void moveFront(){
        double s = Math.sin(angle * Math.PI/180.0);
        double c = Math.cos(angle * Math.PI/180.0);
        x += c*speed;
        y += s*speed;
        area = new Area(new Rectangle2D.Double(x, y, width, height));
        transformation.rotate(angle);
        area.transform(transformation);
        
    }
    /**
     * We get the id of the tank that fired the bullet .
     * @return a integer value 
     */
    public int getId(){
        return tankId;
    }
    public Shape getShape() {
        return area;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
}
