/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual;

import java.util.Vector;
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
    private JPanel panel;
    private boolean running;
    private final int framerate = Constants.VisualEngineConstants.FRAME_RATE;
    private Vector<VisualEntity> visualEntities;
    
    public Animator(JPanel panel, Vector<VisualEntity> visualEntities){
        this.panel = panel;
        this.visualEntities = visualEntities;
        running = true;
    }
    
    @Override
    public void run(){
        Engine.Tank tank;
        Engine.Bullet bullet;
        
        while(running){
            
            //DO ENTITY UPDATES
            
            for(int i = 0; i < visualEntities.size(); i++) {
                if(visualEntities.get(i) instanceof Engine.Tank){
                    tank = (Engine.Tank) visualEntities.get(i);
                    tank.Rotate(1);
                    tank.rotateCannon(1);
                    tank.MoveFront();
                }
            }
            
            //
             
            panel.repaint();    //done with updates, start painting
            
            try{
                Thread.sleep(1000/framerate);
            }
            catch(InterruptedException iex){
                iex.printStackTrace();
            }
        }
    }
    /**
     * Stops the animation in the game engine.
     * If you want to start the animation again, you'll 
     * have to create a new Animator object.
     */
    
    public void stopAnimation(){
        running = false;
    }
}
