
package Engine;

import java.awt.geom.*;
import java.math.*;
public class Tank extends GameEntity  implements MovementInterface,TransformInterface{
    protected double  life;
    protected 
    Tank(double xPos, double yPos, double spd, double lfe, double dmg, double ang){
        boundingBox = new Rectangle2D.Double(xPos,yPos,20,20);
        speed = spd;
        life = lfe;
        damage = dmg;
        angle = ang;
    }
    
    Tank(){
        boundingBox = new Rectangle2D.Double(0,0,20,20);
        speed = 10;
        life = 100;
        damage = 20;
        angle = 0;
    }
    /**
     * Gets the speed of the Tank.
     * @return a double value representing the speed of the Tank
     */
    public double GetSpeed(){
        return speed;
    }
    /**
     * Sets the speed of then tank at 'spd'
     * @param spd a double value representing the desired speed for the Tank
     */
    public void SetSpeed(double spd){
        speed = spd;
    }
    /**
     * Gets the damage of the Tank.
     * @return a double value representing the damage of the Tank
     */
    public double GetDamage(){
        return damage;
    }
    /**
     * Sets the damage of then tank at 'dmg'
     * @param dmg a double value representing the desired damage for the Tank
     */
    public void SetDamage(double dmg){
        damage = dmg;
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
     */
    public void SetAngle(double ang){
        angle = ang % 360;
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
    /**
     * Rotates the tank bye 'degrees' reported to his current rotation.
     * @param degrees a double value representing the desired rotation of the Tank.
     */
    @Override
    public void Rotate(double degrees){
        angle = (angle + degrees)%360;
        
    }
    /**
     * Resizes the tank by 'sx' on X axis and 'sy' on Y axis.
     * @param sx a double value representing the desired resize on the X axis
     * @param sy a double value representing the desired resize on the Y axis
     */
    @Override
    public void Resize(double sx, double sy){
        
    }
    /**
     * Moves the Tank Up
     */
    @Override
    public void MoveUp() {
        y--;
    }
    /**
     * Moves the Tank Down
     */
    @Override
    public void MoveDown() {
        y++;
    }
    /**
     * Moves the Tank Left
     */
    @Override
    public void MoveLeft() {
        x--;
    }
    /**
     * Moves the Tank Right
     */
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
        return null;
    }
    
}
