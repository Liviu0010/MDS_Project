package Visual;

import Console.ConsoleFrame;
import Engine.Bullet;
import Engine.Tank;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;

/**
 * The separate thread which is responsible
 * with updating game entitites. It calls the 
 * repaint() function from JPanel.
 * 
 * @author Liviu
 * 
 */
public class Animator extends Thread{
    private final JPanel panel;
    private boolean running;
    private final int framerate = Constants.VisualConstants.FRAME_RATE;
    private final List<Tank> tanks;
    private final List<Bullet> bullets;
    private final TankFiringThread firingThread;
    
    public Animator(JPanel panel, List<Tank> tanks, List<Bullet> bullets){
        this.panel = panel;
        this.tanks = tanks;
        this.bullets = bullets;
        firingThread = new TankFiringThread(tanks,bullets);
        running = true;
    }
    
    @Override
    public void run(){
        firingThread.start();
        
        while(running){
            
            //DO ENTITY UPDATES
            for (Tank tankAux : tanks) {
                tankAux.rotateCannon(-0.1);
                tankAux.rotate(0.1);
            }
            synchronized(bullets){
                List<Bullet> bulletsToBeRemoved = Collections.synchronizedList(new LinkedList<Bullet>());
                for(Bullet bulletAux: bullets){
                    if(bulletAux.getX() > Constants.VisualConstants.ENGINE_WIDTH || bulletAux.getX() < 0 ||
                            bulletAux.getY() > Constants.VisualConstants.ENGINE_HEIGHT || bulletAux.getY() < 0){
                        //if the bullet is no longer on the screen, it's removed
                        bulletsToBeRemoved.add(bulletAux);
                    }
                    else{
                        bulletAux.moveFront();
                    }
                }
                for(Bullet bulletAux:bulletsToBeRemoved){
                    bullets.remove(bulletAux);
                }
            }
             
            panel.repaint();    //done with updates, start painting
            
            try{
                Thread.sleep(1000/framerate);
            }
            catch(InterruptedException iex){
                ConsoleFrame.sendMessage("Animator", "Thread interrupted");
            }
        }
    }
    /**
     * Stops the animation in the game engine.
     * If you want to start the animation again, you'll 
     * have to create a new Animator object.
     */
    
    public void stopAnimation(){
        firingThread.stopFiring();
        running = false;
    }
}
