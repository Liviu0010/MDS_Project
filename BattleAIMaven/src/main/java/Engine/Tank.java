package Engine;

import java.io.Serializable;

import Constants.EngineConstants;
import Constants.VisualConstants;
import Visual.VisualPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.*;

public class Tank extends GameEntity implements Serializable,MovementInterface, TransformInterface, Drawable {    
    transient protected Image tankSprite;
    protected double life;
    private double energy = 100;
    protected Cannon cannon;
    private final int tank_id;
    protected transient TankCapsule tankCapsule;
    private String name, author;
    
    //the id of the tank will be the current number of instanced tank classes
    private static int staticId;
    /**
     * This block executes once when the class is loaded
     */
    static{
        staticId = 0;
    }
    
    public Tank(double xPos, double yPos,String playerName) {
        super(staticId,xPos, yPos);
        tankSprite  = VisualPanel.tankSprite;
        this.tank_id = staticId++;
        this.life = 100;
        this.author = playerName;
        width = (int)VisualConstants.TANK_WIDTH;
        height = (int)VisualConstants.TANK_HEIGHT;
        cannon = new Cannon(staticId, xPos, yPos, this);
        damage = EngineConstants.DAMAGE;
        angle = EngineConstants.ANGLE;
        speed = EngineConstants.TANK_SPEED;
        life = EngineConstants.LIFE;
    }
    
    public Tank(String name, String author) {
        super(0,0,0);
        Rectangle tankRect =  new Rectangle();
        Rectangle otherTank = new Rectangle();
        
        this.name = name;
        this.author = author;
        
        x = (int)(Math.random()*1000%VisualConstants.ENGINE_WIDTH);
        y = (int)(Math.random()*1000%VisualConstants.ENGINE_HEIGHT);
        
        tankRect.x = (int) x;
        tankRect.y = (int) y;
        
        synchronized (this) {
            for (int i = 0; i < GameEntity.entityList.size(); i++) {
                Tank tank = (Tank) GameEntity.entityList.get(i);
                
                otherTank.x = (int)tank.getX();
                otherTank.y = (int)tank.getY();
                
                //if (Math.sqrt((tank.getX() - xPos) * (tank.getX() - xPos) + (tank.getY() - yPos) * (tank.getY() - yPos)) < 30) {
                if(!isInsideArena() || tankRect.intersects(otherTank)){
                    x = (int) (Math.random() * 1000) % VisualConstants.ENGINE_WIDTH;
                    y = (int) (Math.random() * 1000) % VisualConstants.ENGINE_HEIGHT;
                    i = 0;
                }
            }
            
            //cannon = new Cannon(staticId, x, y);
        }
        
        
        //super(staticId,xPos, yPos);
        //this.x = xPos;
        //this.y = yPos;
        
        tankSprite  = VisualPanel.tankSprite;
        this.tank_id = staticId++;
        this.life = 100;
        width = (int)VisualConstants.TANK_WIDTH;
        height = (int)VisualConstants.TANK_HEIGHT;
        cannon = new Cannon(staticId, x, y, this);
        damage = EngineConstants.DAMAGE;
        angle = EngineConstants.ANGLE;
        speed = EngineConstants.TANK_SPEED;
        life = EngineConstants.LIFE;
            
        synchronized(this){
            GameEntity.entityList.add(this);
        }
    }
    
    public Point getCenter(){
        Point midway = Cannon.getForwardPoint(new Point((int)this.x, (int)this.y), angle, Constants.VisualConstants.TANK_WIDTH/2);
        Point center1 = Cannon.getForwardPoint(midway, angle-90, Constants.VisualConstants.TANK_HEIGHT/2);
        Point center2 = Cannon.getForwardPoint(midway, angle-270, Constants.VisualConstants.TANK_HEIGHT/2);
        
        if(center1.x > this.x && center1.x < this.x+Constants.VisualConstants.TANK_WIDTH &&
                center1.y > this.y && center1.y < this.x+Constants.VisualConstants.TANK_HEIGHT)
            return center1;
        else
            return center2;
    }
    
    /**
     *  Gets the id of the tan
     * @return a integer value representing the id of the tank.
     */
    public int getId(){
        return tank_id;
    }
    /**
     * Gets the life of the Tank.
     *
     * @return
     */
    public double getLife() {
        return life;
    }

    /**
     * Sets the life of the Tank at 'lfe', if the 'lfe' argument is not lower or
     * equal then 0.
     *
     * @param lfe a double value representing a Tanks life
     */
    public void setLife(double lfe) {
        if (lfe >= 0) {
            life = lfe;
        }
    }
    @Override
    public void rotate(double degrees){
        angle = (angle + degrees)%360;
        rotateCannon(degrees);
    }
    /**
     *  Rotates the cannon of the tank by 'degrees' reported to the cannon's rotation angle
     * @param degrees a double value representing the rotation value
     */
    public void rotateCannon(double degrees){
        cannon.rotate(degrees);
    }
    
    @Override
    public void moveUp() {
        setY(getY()-1);
    }

    @Override
    public void moveDown() {
        setY(getY()+1);
    }

    @Override
    public void moveLeft() {
        setX(getX()-1);
    }

    @Override
    public void moveRight() {
        setX(getX()+1);
    }
    
    /**
     * 
     * @param p
     * @return Value which specifies whether the point is inside the arena or not.
     */
    public boolean isInsideArena(Point p){
        return p.x >= 0 && p.y >= 0 && p.x <= VisualConstants.ENGINE_WIDTH-40 && p.y <= VisualConstants.ENGINE_HEIGHT-40;
    }
    
    /**
     * 
     * @return Value which specifies whether the tank is inside the arena or not.
     */
    public final boolean isInsideArena(){
        Point upperLeft,upperRight, lowerLeft, lowerRight;
        upperLeft = new Point((int)x,(int)y);
        upperRight = Cannon.getForwardPoint(upperLeft, angle+90, VisualConstants.TANK_WIDTH);
        lowerLeft = Cannon.getForwardPoint(upperLeft, angle, VisualConstants.TANK_HEIGHT);
        lowerRight = Cannon.getForwardPoint(lowerLeft, angle, VisualConstants.TANK_WIDTH);
        
        return isInsideArena(upperLeft) && isInsideArena(upperRight) && isInsideArena(lowerLeft) && isInsideArena(lowerRight);
    }
    
    /**
     * Move the tank forward reported to it's current orientation angle.
     */
    public void moveFront(){
        double origX = x, origY = y;
        
        double s = Math.sin(angle * Math.PI / 180.0);
        double c = Math.cos(angle * Math.PI / 180.0);
        x += c * speed;
        y += s * speed;
        
        if (!isInsideArena()) {
            x = origX;
            y = origY;
            tankCapsule.hitArenaWall();
        } else {   
            //we store the angle of the cannon in cangle
            double cangle = cannon.getAngle();
            //set the cannon rotaton to the tank rotation  
            cannon.setAngle(angle);
            //then move the cannon front
            cannon.moveFront();
            //then we restore the cannon to it's former angle
            cannon.setAngle(cangle);
        }
        
    }

    /**
     * Shoots a bullet.
     *
     * @return a Bullet object representing a bullet shoot by the tank, or a null object if the tank is unable to fire.
     * 
     */
    public Bullet fire() {
        if(energy < 100)
            return null;
        
        energy = 0;
        return cannon.fire();
    }
    
    public void restoreEnergy(){
        if(energy < 100)
            energy += EngineConstants.ENERGY_RESTORE_RATE;
        
        if(energy > 100)
            energy = 100;
    }
    
    public void setTankCapsule(TankCapsule tankCapsule){
        this.tankCapsule = tankCapsule;
    }
    
    public void hitByBullet(){
        this.life -= 10;
        this.tankCapsule.gotHitByBullet();
    }
    
    public boolean inTheGame(){
        return life > 0;
    }
    
    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        AffineTransform at = g2.getTransform();
        
        g2.rotate(Math.toRadians(90), x+10, y+10);
        g2.rotate(Math.toRadians(angle), x+10, y+10);
        g2.drawImage(VisualPanel.tankSprite, (int)x, (int)y, null);
        g2.setTransform(at); 
        
        cannon.draw(g);
        
        //draw health bar
        g2.setColor(Color.RED);
        g2.fillRect((int)x-2, (int)y-10,(int)VisualConstants.HEALTH_BAR_WIDTH, (int)VisualConstants.HEALTH_BAR_HEIGHT);
        g2.setColor(Color.decode("#0FB81A"));   //this is green
        g2.fillRect((int)x-2, (int)y-10, (int) (life/100*VisualConstants.HEALTH_BAR_WIDTH), (int)VisualConstants.HEALTH_BAR_HEIGHT);
        //end
        
        //draw energy bar
        g2.setColor(Color.decode("#804000"));   //brownish
        g2.fillRect((int)x-2, (int)(y-10-VisualConstants.HEALTH_BAR_HEIGHT), (int)(VisualConstants.HEALTH_BAR_WIDTH), (int)VisualConstants.HEALTH_BAR_HEIGHT);
        g2.setColor(Color.decode("#ff9933"));   //orange
        g2.fillRect((int)x-2, (int)(y-10-VisualConstants.HEALTH_BAR_HEIGHT), (int)(energy/100*VisualConstants.HEALTH_BAR_WIDTH), (int)VisualConstants.HEALTH_BAR_HEIGHT);
        //end
        
        //draw name and author
        g2.setColor(Color.BLACK);
        Font f = g2.getFont();
        g2.setFont(g2.getFont().deriveFont(10f));
        g2.drawString(this.name, (int)(this.x+Constants.VisualConstants.TANK_WIDTH + 3), (int)(this.y+Constants.VisualConstants.TANK_HEIGHT/2-4));
        g2.drawString(this.author, (int)(this.x+Constants.VisualConstants.TANK_WIDTH + 3), (int)(this.y+Constants.VisualConstants.TANK_HEIGHT/2+4));
        g2.setFont(f);
        //end
    }

    @Override
    public String toString() {
        return "Tank{" + " playerName=" + author + ", life=" + life + ", cannon=" +
                cannon + ", width=" + width + ", height=" + height + '}' + " " + super.toString();
    }

}
