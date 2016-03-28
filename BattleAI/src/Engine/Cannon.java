package Engine;

import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class Cannon extends GameEntity implements TransformInterface {

    Cannon(double xPos, double yPos, double spd, double dmg, double ang) {
        super(xPos, yPos, spd, dmg, ang);
    }

    @Override
    public Shape GetShape() {
        return area;
    }
}
