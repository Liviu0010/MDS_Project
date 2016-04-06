package Engine;

import Constants.VisualConstants;
import Visual.VisualPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
/**
 * A Cannon class used by an instance of Tank class to launch instances of Bullet class.
 */
final public class Cannon extends GameEntity implements TransformInterface, Drawable{
    Image cannonSprite;
    
    /**
     *
     * @param id
     * @param xPos
     * @param yPos
     * @param spead
     * @param damage
     * @param angle
     * @param cannonSprite
     */
    public Cannon(int id, double xPos, double yPos, double spead, double damage, double angle, Image cannonSprite){
        //in the case of the cannon spd will represend the speed of moving on the board and dmg the damage that it
        super(id,xPos,yPos,spead,damage,angle);
        //gives to the tank that it hit's and ang will represent the 
        //current angle of the bullet which will be the same
        this.cannonSprite = cannonSprite;
        xPos -= VisualConstants.CANNON_WIDTH;
        yPos -= VisualConstants.CANNON_HEIGHT;
        width = VisualConstants.CANNON_WIDTH;
        height = VisualConstants.CANNON_HEIGHT;
    }
    public Cannon(){
        super();
        width = VisualConstants.CANNON_WIDTH;
        height = VisualConstants.CANNON_HEIGHT;
     }
    public void moveFront(){
        double s = Math.sin(angle * Math.PI/180.0);
        double c = Math.cos(angle * Math.PI/180.0);
        setX(getX()+c*speed);
        setY(getY()+s*speed);
    }
    @Override
    public Shape getShape() {
        return null;
    }
    
    public Point getForwardPoint(Point origin, double angle){
        double s = Math.sin(angle * Math.PI/180.0);
        double c = Math.cos(angle * Math.PI/180.0);
        return new Point((int)(origin.x+c), (int)(origin.y+s));
    }
    
    /**
     * Shoots a bullet.
     *
     * @return a Bullet object representing a bullet shoot by the tank.
     */
    public Bullet fire() {
        Point firePoint = getForwardPoint(new Point((int)getX(), (int)getY()), angle);
        
        return new Bullet(id, firePoint.x+VisualConstants.TANK_WIDTH/2-VisualConstants.CANNON_WIDTH/2, firePoint.y+VisualConstants.TANK_HEIGHT/2-VisualConstants.CANNON_WIDTH/2, speed+1, damage, angle, VisualPanel.bulletSprite);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        AffineTransform at = g2.getTransform();
        
        g2.rotate(Math.toRadians(90), getX()+10, getY()+10);
        g2.rotate(Math.toRadians(getAngle()), getX()+10, getY()+10);
        g2.drawImage(cannonSprite, (int)getX()+8, (int)getY(), null);
        g2.setTransform(at);
    }
    
    
}
