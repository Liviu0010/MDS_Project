package Engine;

import java.awt.Shape;
import java.awt.geom.*;
import java.math.*;

public class Tank extends GameEntity implements MovementInterface, TransformInterface {

    protected double life;
    protected Cannon cannon;
    protected Bullet bullet;        
    private static int id;          //the id of the tank will be the current number of instanced tank classes
    final private int width,height; //the width and height of the tank are constant so we make then final
    /**
     * This block executes once when the class is loaded
     */
    static{
        id = 0;
    }
    
    public Tank(double xPos, double yPos, double spd, double dmg, double ang, double lfe) {
        super(xPos, yPos, spd, dmg, ang,100,100);                       
        cannon = new Cannon(xPos, yPos, spd, dmg, ang);
        width = height = 100;
        life = lfe;
        id++;
    }

    public Tank() {
        super(0,0,10,10,10,100,100);
        width = height = 100;
        life = 100;
    }
    /**
     *  Gets the id of the tan
     * @return a integer value representing the id of the tank.
     */
    public int getId(){
        return id;
    }
    /**
     * Gets the life of the Tank.
     *
     * @return
     */
    public double GetLife() {
        return life;
    }

    /**
     * Sets the life of the Tank at 'lfe', if the 'lfe' argument is not lower or
     * equal then 0.
     *
     * @param lfe a double value representing a Tanks life
     */
    public void SetLife(double lfe) {
        if (lfe >= 0) {
            life = lfe;
        }
    }
    @Override
    public void rotate(double degrees){
        angle = (angle + degrees)%360;
        cannon.rotate(degrees);
        
    }
    /**
     *  Rotates the cannon of the tank by 'degrees' reported to the cannon's rotation angle
     * @param degrees a double value representing the rotation value
     */
    public void rotateCannon(double degrees){
        cannon.rotate(degrees);
    }
    
    @Override
    public void resize(double sx, double sy){
        
    }
    @Override
    public void moveUp() {
        transformation.translate(0, -1);
        area.transform(transformation);
        transformation.setToIdentity();
    }

    @Override
    public void moveDown() {
        transformation.translate(0, 10);
        area.transform(transformation);
        transformation.setToIdentity();
    }

    @Override
    public void moveLeft() {
        transformation.translate(-1, 0);
        area.transform(transformation);
        transformation.setToIdentity();
    }

    @Override
    public void moveRight() {
        transformation.translate(1, 0);
        area.transform(transformation);
        transformation.setToIdentity();
    }

    /**
     * Move the tank forward reported to it's current orientation angle.
     */
    public void moveFront(){
        double s = Math.sin(angle * Math.PI/180.0);
        double c = Math.cos(angle * Math.PI/180.0);
        x += c*speed;
        y += s*speed;
        area = new Area(new Rectangle2D.Double(x, y, width, height));       //we first move front the Tank then the Cannon
        transformation.rotate(angle);
        area.transform(transformation); 
        double cangle = cannon.getAngle();                                  //we store the angle of the cannon in cangle
        cannon.setAngle(angle);                                             //se the cannon rotaton to the tank rotation    
        cannon.moveFront();                                                 //then move the cannon front
        cannon.setAngle(cangle);                                            //then we restore the cannon to it's former angle    
    }

    /**
     * Shoots a bullet.
     *
     * @return a Bullet object representing a bullet shoot by the tank.
     */
    public Bullet fire() {
        return new Bullet(area.getBounds().x, area.getBounds().y, angle, speed, damage,id);
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
