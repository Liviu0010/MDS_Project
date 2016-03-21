
package Engine;

import java.awt.geom.*;
public class Tank extends GameEntity  implements MovementInterface,TransformInterface{
    protected double  life;
    protected Cannon cannon;
    protected Bullet bullet;
    public Tank(double xPos, double yPos, double spd, double lfe, double dmg, double ang){
        boundingBox = new Rectangle2D.Double(xPos,yPos,20,20);
        cannon = new Cannon(xPos,yPos,ang,spd,dmg);
        speed = spd;
        life = lfe;
        damage = dmg;
        angle = ang;
        x = xPos;
        y = yPos;
    }
    
    public Tank(){
        boundingBox = new Rectangle2D.Double(200,200,20,20);
        speed = 1;
        life = 100;
        damage = 20;
        angle = 0;
    }
    /**
     * Gets the life of the Tank.
     * @return 
     */
    public double GetLife(){
        return life;
    }
    /**
     * Sets the life of the Tank at 'lfe', if the 'lfe' argument is not lower or equal then 0.
     * @param lfe a double value representing a Tanks life
     */
    public void SetLife(double lfe){
        if (lfe >= 0)
            life = lfe;
    }
    @Override
    public void Rotate(double degrees){
        angle = (angle + degrees)%360;
        
    }
    @Override
    public void Resize(double sx, double sy){
        
    }
    @Override
    public void MoveUp() {
        y--;
    }
    @Override
    public void MoveDown() {
        y++;
    }
    @Override
    public void MoveLeft() {
        x--;
    }
    @Override
    public void MoveRight() {
        x++;
    }
    /**
     * Move the tank forward reported to it's current orientation angle.
     */
    public void MoveFront(){
        double s = Math.sin(angle * Math.PI/180.0);
        double c = Math.cos(angle * Math.PI/180.0);
        x += c*speed;
        y += s*speed;
    }
    /**
     * Shoots a bullet.
     * @return a Bullet object representing a bullet shoot by the tank.
     */
    public Bullet Shoot(){
        return new Bullet(x,y,angle,speed,damage);
    }
    
}
