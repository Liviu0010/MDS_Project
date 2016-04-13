package Engine;

import Constants.VisualConstants;
import Visual.VisualPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
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
    
    public Point getForwardPoint(Point origin, double angle, double forwardDistance){
        double s = Math.sin(Math.toRadians(angle))*forwardDistance;
        double c = Math.cos(Math.toRadians(angle))*forwardDistance;
        return new Point((int)(origin.x+c), (int)(origin.y+s));
    }
    
    /**
     * Shoots a bullet.
     *
     * @return a Bullet object representing a bullet shoot by the tank.
     */
    public Bullet fire() {
        Point firePoint = getForwardPoint(getForwardPoint(
                getForwardPoint(new Point((int)x, (int)y), angle+270, VisualConstants.CANNON_WIDTH/2), 45, VisualConstants.TANK_WIDTH/2*Math.sqrt(2)), angle,
                VisualConstants.CANNON_HEIGHT);
        
        return new Bullet(id, firePoint.x, 
                firePoint.y, speed+1, 
                damage, angle, VisualPanel.bulletSprite);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        //calculate dem origin point
        Point tankCenter = getForwardPoint(new Point((int) x, (int) y), 45, VisualConstants.TANK_WIDTH / 2 * Math.sqrt(2));
        Point cannonStart = getForwardPoint(tankCenter, 0, -VisualConstants.CANNON_WIDTH/2);
        //end
        
        AffineTransform at = g2.getTransform();
        
        g2.rotate(Math.toRadians(angle + 270), tankCenter.x, tankCenter.y);
        
        
        //draw le reidahr
        //get reidahr far points
        Point r1 = getForwardPoint(cannonStart, VisualConstants.RADAR_SIZE/2+90, VisualConstants.ENGINE_WIDTH);
        Point r2 = getForwardPoint(cannonStart, -VisualConstants.RADAR_SIZE/2+90, VisualConstants.ENGINE_WIDTH);
        //end points
        
        Stroke s = g2.getStroke();
        
        g2.setStroke(new BasicStroke(0.5f));
        
        g2.setColor(Color.BLACK);
        g2.drawLine(tankCenter.x, tankCenter.y, r1.x , r1.y);
        g2.drawLine(tankCenter.x, tankCenter.y, r2.x, r2.y);
        
        g2.setStroke(s);
        //end reidahr
        
        
        g2.drawImage(cannonSprite, (int) cannonStart.x, (int) cannonStart.y, null);     //draw cannon
         
        
        g2.setTransform(at);
    }
    
    
}
