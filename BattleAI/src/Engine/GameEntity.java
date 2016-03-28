package Engine;

import java.awt.Shape;
import java.awt.geom.*;

public abstract class GameEntity implements TransformInterface {

    protected Area area;
    protected Rectangle2D rec;
    protected AffineTransform transformation;
    ;
    protected double  angle, speed, damage;

    /**
     * Constructs an entity at position 'xPos' 'yPos' with speed of 'spd',
     * damage of 'dmg' and rotation of 'ang'.
     *
     * @param xPos a double value representing position on the 'X' axis
     * @param yPos a double value representing position on the 'Y' axis
     * @param spd a double value representing the speed of the entity
     * @param dmg a double value representing the damage of the entity
     * @param ang a double value representing the current angle of the entity
     */
    public GameEntity(double xPos, double yPos, double spd, double dmg, double ang) {
        
        this.transformation = new AffineTransform();
        transformation.setToRotation(ang, xPos, yPos);
        area = new Area(new Rectangle2D.Double(xPos,yPos,100,100));
        area.transform(transformation);
        transformation.setToIdentity();
        damage = dmg;
        speed = spd;
        angle = ang;
    }

    public GameEntity() {
        transformation = new AffineTransform();
        area = new Area(new Rectangle2D.Double(10,10,100,100));
        damage = speed = 10;
        angle = 0;
    }

    /**
     * Gets the speed of the entity.
     *
     * @return a double value representing the speed of the entity
     */
    public double GetSpeed() {
        return speed;
    }

    /**
     * Sets the speed of then entity at 'spd' if 'spd'>0.
     *
     * @param spd a double value representing the desired speed for the entity
     */
    public void SetSpeed(double spd) {
        speed = (spd > 0) ? spd : speed;
    }

    /**
     * Gets the damage of the entity.
     *
     * @return a double value representing the damage of the entity
     */
    public double GetDamage() {
        return damage;
    }

    /**
     * Sets the damage of then entity at 'dmg' if 'dmg' >0.
     *
     * @param dmg a double value representing the desired damage for the entity
     */
    public void SetDamage(double dmg) {
        damage = (dmg > 0) ? dmg : damage;
    }

    /**
     * Gets the angle of the current rotation.
     *
     * @return a double value representing the angle of the current rotation
     */
    public double GetAngle() {
        return angle;
    }

    /**
     * Sets the angle of the current rotation at 'ang' % 360.
     *
     * @param ang a double value representing the desired angle of the entity
     */
    public void SetAngle(double ang) {
        angle = ang % 360;
        transformation.setToRotation(ang);
        area.transform(transformation);
        transformation.setToIdentity();
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

    @Override
    public void Rotate(double degrees) {
        transformation = new AffineTransform();
        rec = area.getBounds2D();
        transformation.rotate(degrees, (rec.getX()+ rec.getWidth())/2, (rec.getY()+ rec.getHeight())/2);
        area.transform(transformation);
    }

    @Override
    public void Resize(double sx, double sy) {
        transformation = new AffineTransform();
        transformation.scale(sx, sy);
        area.transform(transformation);
    }
    public abstract Shape GetShape();

}
