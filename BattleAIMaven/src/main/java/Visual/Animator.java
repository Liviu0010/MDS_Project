package Visual;

import Console.ConsoleFrame;
import Engine.GameEntity;
import Main.GameModes;
import Networking.Client.ConnectionHandler;
import Networking.Requests.EntityUpdateRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The separate thread which is responsible
 * with updating game entitites. It calls the 
 * repaint() function from JPanel.
 * 
 * @author Liviu
 * 
 */
public class Animator extends Thread{
    private final VisualPanel panel;
    private boolean running;
    private final int framerate = Constants.VisualConstants.FRAME_RATE;
    private Thread paintThread;
    volatile boolean updateDone;
    long c = 0;
    
    private GameModes gameMode;
    
    
    public Animator(VisualPanel panel){
        this.panel = panel;
        running = true;
    }
    
    public void setGameMode(GameModes gameMode){
        this.gameMode = gameMode;
    }
    
    private void runMultiplayerHost(){
        while(running){
            if (paintThread == null || !paintThread.isAlive()) {
                paintThread = new Thread(() -> {
                    while (running) {
                        panel.repaint();
                        
                        try {
                            Thread.sleep(1000 / framerate);
                        } catch (InterruptedException ex) {
                            ConsoleFrame.sendMessage("paintThread", "paintThread interrupted");
                        }
                    }
                });

                paintThread.start();

            }
            
            //panel.repaint();  
            
            try{
                Thread.sleep(1000/framerate);
            }
            catch(InterruptedException iex){
                ConsoleFrame.sendMessage("Animator", "Thread interrupted");
            }
        }
    }
    
    private void runMultiplayerClient(){
        while(running){
            
            EntityUpdateRequest newEntities;
            try {
                newEntities = (EntityUpdateRequest) ConnectionHandler.getInstance().readFromMatch();
                panel.entityList = newEntities.gameEntities;
            } catch (IOException | ClassNotFoundException ex) {
                ConsoleFrame.showError("Failed to read from battle stream");
            }
            
            
            if (paintThread == null || !paintThread.isAlive()) {
                paintThread = new Thread(() -> {
                    while (running) {
                        panel.repaint();
                        
                        try {
                            Thread.sleep(1000 / framerate);
                        } catch (InterruptedException ex) {
                            ConsoleFrame.sendMessage("paintThread", "paintThread interrupted");
                        }
                    }
                });

                paintThread.start();

            }
            
            //panel.repaint();  
            
            try{
                Thread.sleep(1000/framerate);
            }
            catch(InterruptedException iex){
                ConsoleFrame.sendMessage("Animator", "Thread interrupted");
            }
        }
    }
    
    private void runSingleplayer(){
        while(running){
            if (paintThread == null || !paintThread.isAlive()) {
                paintThread = new Thread(() -> {
                    while (running) {
                        panel.repaint();
                        
                        try {
                            Thread.sleep(1000 / framerate);
                        } catch (InterruptedException ex) {
                            ConsoleFrame.sendMessage("paintThread", "paintThread interrupted");
                        }
                    }
                });

                paintThread.start();

            }
            
            //panel.repaint();  
            
            try{
                Thread.sleep(1000/framerate);
            }
            catch(InterruptedException iex){
                ConsoleFrame.sendMessage("Animator", "Thread interrupted");
            }
        }
    }
    
    @Override
    public void run(){
        if(gameMode != null){
            switch(gameMode){
                case SINGLEPLAYER:
                    runSingleplayer();
                    break;
                case MULTIPLAYER_HOST:
                    runMultiplayerHost();
                    break;
                case MULTIPLAYER_CLIENT:
                    runMultiplayerClient();
                    break;
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
