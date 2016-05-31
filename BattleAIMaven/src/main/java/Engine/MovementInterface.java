package Engine;

import java.io.Serializable;

public interface MovementInterface extends Serializable {

    /**
     * Moves the entity up.
     */
    public void moveUp();

    /**
     * Moves the entity down.
     */
    public void moveDown();

    /**
     * Moves the entity left
     */
    public void moveLeft();

    /**
     * Moves the entity right.
     */
    public void moveRight();
}
