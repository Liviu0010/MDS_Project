package Visual;

import java.util.Vector;

/**
 *  This class is temporary. It exists for testing purposes.
 *  It just makes all the tanks fire at a random interval.
 * 
 * @author Liviu
 */
public class TankFiringThread extends Thread{
    Vector<VisualEntity> visualEntities;
    boolean running;
    
    public TankFiringThread(Vector<VisualEntity> visualEntities){
        this.visualEntities = visualEntities;
        running = true;
    }
    
    @Override
    public void run(){
        Engine.Tank tank;
        VisualBullet bullet;
        Engine.Bullet b;
        
        while(running){
            for(int i = 0; i < visualEntities.size(); i++) {
                if(visualEntities.get(i) instanceof VisualTank){
                    tank = (Engine.Tank)visualEntities.get(i);
                    b = tank.Shoot();
                    bullet = new VisualBullet(b.getX(), b.getY(), b.GetAngle(), b.GetSpeed(), b.GetDamage(), VisualPanel.bulletSprite);
                    visualEntities.add(bullet);
                }
            }
            
            try {
                Thread.sleep((long)(Math.random()*500));    //random firing time (500 ms gets 20-30 bullets on the screen)
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void stopFiring(){
        running = false;
    }
}
