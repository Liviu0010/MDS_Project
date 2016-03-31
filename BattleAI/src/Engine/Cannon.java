package Engine;

import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
/**
 * A Cannon class used by an instance of Tank class to launch instances of Bullet class.
 */
final public class Cannon extends GameEntity implements TransformInterface{
    final private int width,height;
    public Cannon(double xPos, double yPos,double spd, double dmg, double ang){
        super(xPos,yPos,spd,dmg,ang,20,20);//in the case of the cannon spd will represend the speed of moving on the board and dmg the damage that it
                                           //gives to the tank that it hit's and ang will represent the current angle of the bullet wich will be the same
                                           //as the cannon , the cannon has a default width and height of 20
        width = height = 50;
        x = xPos;
        y = yPos;
    }
    public Cannon(){
        super(0,0,2,3,20,20,20);
         x = y = 100;
         width = height = 50;
         angle = 0;
         speed = 10;
         damage = 10;
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
    @Override
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
