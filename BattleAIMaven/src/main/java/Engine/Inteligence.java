package Engine;

/**
 *
 * @author Dragos-Alexandru
 */
public interface Inteligence {

    public void gotHitByBullet();

    public void hitArenaWall();

    public void hitEnemyTank();

    public void detectedEnemyTank(TankCapsule enemy);

}
