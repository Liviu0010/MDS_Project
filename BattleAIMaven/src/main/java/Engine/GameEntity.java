package Engine;

import Constants.VisualConstants;
import java.awt.Shape;
import java.awt.geom.*;
import java.util.Random;
/**
 * Game Entity class provides a framework for Bullet,Cannon and Tank classes
 * the methods implemented in this class should work for normal 'X'-'Y' Cartesian  coordinate system so they would probably need to be altered to work with
 * origin in the top left
 */
public abstract class GameEntity implements TransformInterface {
    
    protected int id;
    //use Area so we can transform using affinetransformer
    private Area area;
    //transformation applied to area
    private AffineTransform transformation;
    protected double x,y;
    private double scaleX, scaleY;
    protected double  angle, speed, damage;
    protected double width, height;

    /**
     * Constructs an entity at position 'xPos' 'yPos' with speed of 'spd',
     * damage of 'dmg' and rotation of 'ang'.
     *
     * @param id
     * @param xPos a double value representing position on the 'X' axis
     * @param yPos a double value representing position on the 'Y' axis
     * @param speed a double value representing the speed of the entity
     * @param damage a double value representing the damage of the entity
     * @param angle a double value representing the current angle of the entity
     */
    public GameEntity(int id, double xPos, double yPos, double speed, double damage, double angle) {
        this.id = id;
        this.damage = damage;
        this.speed = speed;
        this.angle = angle;
        this.scaleX = 1;
        this.scaleY = 1;
        x = xPos;
        y = yPos;
        transformation = new AffineTransform();
        transformation.setToIdentity();
        
    }

    public GameEntity() {
        transformation = new AffineTransform();
        transformation.setToIdentity();
        damage = speed = 10;
        angle = 0;
        x = y = new Random().nextInt(600)+100;
    }
    
    /**
     * Gets the speed of the entity.
     *
     * @return a double value representing the speed of the entity
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets the speed of then entity at 'spd' if 'spd'>0.
     *
     * @param spd a double value representing the desired speed for the entity
     */
    public void setSpeed(double spd) {
        speed = (spd > 0) ? spd : speed;
    }

    /**
     * Gets the damage of the entity.
     *
     * @return a double value representing the damage of the entity
     */
    public double getDamage() {
        return damage;
    }

    /**
     * Sets the damage of then entity at 'dmg' if 'dmg' >0.
     *
     * @param dmg a double value representing the desired damage for the entity
     */
    public void setDamage(double dmg) {
        damage = (dmg > 0) ? dmg : damage;
    }

    /**
     * Gets the angle of the current rotation.
     *
     * @return a double value representing the angle of the current rotation
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Sets the angle of the current rotation at 'ang' % 360.
     *
     * @param ang a double value representing the desired angle of the entity
     */
    public void setAngle(double ang) {
        angle = ang % 360;
    }

    /**
     * Checks for collision .
     *
     * @param obj representing the object that we check collision for
     * @return if the objects collide then the intersection object it returned
     * otherwise null is returned
     */
    public boolean collision(GameEntity obj) {
        return (area.intersects(obj.area.getBounds2D()));
    }
    
    /**
     *  Get the x coordinate.
     * @return a double value.
     */
    public double getX() {
        return x;
    }
    
    /**
     *  Get the y coordinate.
     * @return a double value. 
     */
    public double getY() {
        return y;
    }
    
    /**
     *  Set the x coordinate.
     * @param x
     */
    public void setX(double x){
        this.x = x;
    }
    
    /**
     *  Set the y coordinate.
     * @param y
     */
    public void setY(double y){
        this.y = y;
    }
    
    @Override
    public void rotate(double degrees) {
        angle = (angle+degrees)%360;
    }
    
    @Override
    public void resize(double sx, double sy) {
        scaleX = sx;
        scaleY = sy;
    }
    /**
     *  Get the shape of the object for visual drawing.
     * @return  a Shape object representing the shape of the entity.
     */
    public abstract Shape getShape();

    public AffineTransform getTransformation(){
        transformation.setToIdentity();
        //transformation.concatenate(AffineTransform.getRotateInstance(angle, x+width/2, y+height/2));
        transformation.concatenate(AffineTransform.getTranslateInstance(x+width/2, y+height/2));
        //transformation.concatenate(AffineTransform.getScaleInstance(width, height));
        return transformation;
    }
    
    
    @Override
    public String toString() {
        return "GameEntity{" + "id=" + id + ", transformation=" + transformation + ", x=" + x + ", y=" + y + ", angle=" + angle + ", speed=" + speed + ", damage=" + damage + '}';
    }
    
    

}
