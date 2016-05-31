package Engine;

import java.io.Serializable;

public interface TransformInterface extends Serializable {

    /**
     * Rotate the entity by 'degrees' reported to it's current rotation.
     *
     * @param degrees a double value representing the desired rotation
     */
    public void rotate(double degrees);
}
