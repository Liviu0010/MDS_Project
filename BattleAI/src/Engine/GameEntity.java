
package Engine;
import java.awt.geom.*;

public abstract class GameEntity {
    protected Rectangle2D.Double boundingBox;
    protected double x, y , angle, speed , damage ;
   /**
     * Gets the speed of the entity.
     * @return a double value representing the speed of the entity
     */
    public double GetSpeed(){
        return speed;
    }
    /**
     * Sets the speed of then entity at 'spd' if 'spd'>0.
     * @param spd a double value representing the desired speed for the entity
     */
    public void SetSpeed(double spd){
        speed = (spd > 0)? spd: speed;
    }
    /**
     * Gets the damage of the entity.
     * @return a double value representing the damage of the entity
     */
    public double GetDamage(){
        return damage;
    }
    /**
     * Sets the damage of then entity at 'dmg' if 'dmg' >0.
     * @param dmg a double value representing the desired damage for the entity
     */
    public void SetDamage(double dmg){
        damage = (dmg >0 )? dmg : damage;
    }
    /**
     * Gets the angle of the current rotation. 
     * @return a double value representing the angle of the current rotation
     */
    public double GetAngle(){
        return angle;
    }
    /**
     * Sets the angle of the current rotation at  'ang' % 360.
     * @param ang a double value representing the desired angle of the entity 
     */
    public void SetAngle(double ang){
        angle = ang % 360;
    }
    /**
     * Checks for collision .
     * @param obj representing the object that we check collision for
     * @return if the objects collide then the intersection object it returned otherwise null is returned
     */
    public Rectangle2D.Double collision(GameEntity obj){
        return null;
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
}
