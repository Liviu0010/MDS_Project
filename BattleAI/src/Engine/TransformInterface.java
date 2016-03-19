
package Engine;

public interface TransformInterface {
    /**
     * Rotate the entity by 'degrees' reported to it's current rotation.
     * @param degrees a double value representing the desired rotation
     */
    public void Rotate(double degrees);
    /**
     * Resizes the entity's size by 'sx' on X axis and 'sy' on Y axis.
     * @param sx a double value representing the desired resize on the X axis
     * @param sy a double value representing the desired resize on the Y axis
     */
    public void Resize(double sx, double sy);
}
