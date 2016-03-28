package Engine;

import java.awt.Shape;
import java.awt.geom.*;

public class Bullet extends GameEntity implements TransformInterface {

    Bullet(double xPos, double yPos, double spd, double dmg, double ang) {
        super(xPos, yPos, spd, dmg, ang);
        angle = ang;
        speed = spd;
        damage = dmg;
    }

    public void MoveFront() {
        double s = Math.sin(angle * Math.PI / 180.0);
        double c = Math.cos(angle * Math.PI / 180.0);
        double x = c * speed;
        double y;
        y = s * speed;
    }
    public Shape GetShape() {
        return area;
    }
}
