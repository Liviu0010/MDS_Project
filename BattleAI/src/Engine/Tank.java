package Engine;

import java.awt.Shape;
import java.awt.geom.*;
import java.math.*;

public class Tank extends GameEntity implements MovementInterface, TransformInterface {

    protected double life;
    protected Cannon cannon;
    protected Bullet bullet;

    Tank(double xPos, double yPos, double spd, double dmg, double ang, double lfe) {
        super(xPos, yPos, spd, dmg, ang);
        cannon = new Cannon(xPos, yPos, spd, dmg, ang);
        life = lfe;
    }

    Tank() {
        life = 100;
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
    public void MoveUp() {
        transformation.translate(0, -1);
        area.transform(transformation);
        transformation.setToIdentity();
    }

    @Override
    public void MoveDown() {
        transformation.translate(0, 10);
        area.transform(transformation);
        transformation.setToIdentity();
    }

    @Override
    public void MoveLeft() {
        transformation.translate(-1, 0);
        area.transform(transformation);
        transformation.setToIdentity();
    }

    @Override
    public void MoveRight() {
        transformation.translate(1, 0);
        area.transform(transformation);
        transformation.setToIdentity();
    }

    /**
     * Move the tank forward reported to it's current orientation angle.
     */
    public void MoveFront() {
        double s = Math.sin(angle * Math.PI / 180.0);
        double c = Math.cos(angle * Math.PI / 180.0);
        double x = c * speed;
        double y = s * speed;
    }

    /**
     * Shoots a bullet.
     *
     * @return a Bullet object representing a bullet shoot by the tank.
     */
    public Bullet Shoot() {
        return new Bullet(area.getBounds().x, area.getBounds().y, angle, speed, damage);
    }

    @Override
    public Shape GetShape() {
        return area;
    }

}
