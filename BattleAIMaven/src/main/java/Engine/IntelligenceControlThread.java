package Engine;

import Compiler.SourceCompiler;
import Console.ConsoleFrame;
import Source.Source;
import Intelligence.IntelligenceTemplate;
import Intelligence.Semaphore;
import Intelligence.TankThread;
import Enums.GameModes;
import Networking.Server.PacketManager;
import Visual.VisualEngine;
import java.util.ArrayList;
import java.util.List;

public class IntelligenceControlThread extends Thread{
    private static IntelligenceControlThread instance;
    private final BulletUpdater bulletUpdater;
    private final ArrayList<TankThread> tankThreads;
    private final ArrayList<Semaphore> semaphores;
    private boolean running;
    private static int numberOfTanks;
    
    public IntelligenceControlThread(List<Source> surse){
        numberOfTanks = surse.size();
        GameEntity.currentIndex = 0;
        
        IntelligenceTemplate playerCode;// = new IntelligenceTemplate();
        tankThreads = new ArrayList<>();
        semaphores = new ArrayList<>();
        
        running = true;
        
        for(int i = 0; i<surse.size(); i++){
            
            synchronized(GameEntity.entityList){
                Tank tanc = new Tank(surse.get(i).getName(), surse.get(i).getAuthor()); //adds it to entityList
                GameEntity.entityList.add(tanc);
            }
            playerCode = (IntelligenceTemplate) SourceCompiler.getInstanceOfSource(surse.get(i));
            
            semaphores.add(new Semaphore());
            tankThreads.add(new TankThread(playerCode, semaphores.get(i)));
        }
        
        if(VisualEngine.getInstance().getMatchMode() == GameModes.SINGLEPLAYER ||
                VisualEngine.getInstance().getMatchMode() == GameModes.MULTIPLAYER_HOST)
            VisualEngine.getInstance().updateEntityList(GameEntity.entityList);
        
        bulletUpdater = new BulletUpdater();
        
        ConsoleFrame.sendMessage("IntelligenceControlThread","size = "+GameEntity.entityList.size());
    }
    //END testing
    
    
    @Override
    public void run(){
        int ingame;
        
        BulletHitChecker.getInstance().start();
        
        for(int i = 0; i<tankThreads.size(); i++) {
            tankThreads.get(i).start();
        }
        
        bulletUpdater.start();
        
        while(running) {
            ingame = 0;
            
            synchronized (GameEntity.entityList) {
                    PacketManager.getInstance().addFrame(GameEntity.entityList);    //send the current frame     
                    }
        
            for(int i = 0; i < tankThreads.size(); i++){
                synchronized (semaphores.get(i)) {
                    if (semaphores.get(i).isGreen()) {
                        if (((Tank) GameEntity.entityList.get(i)).inTheGame()) {
                        //to ensure that the enemy is always detected
                        ((Tank)GameEntity.entityList.get(i)).janitor();
                        //end
                        ingame++;
                        semaphores.get(i).goRed();
                        semaphores.get(i).notify();
                    }
                        else{
                            tankThreads.get(i).stopNicely();
                        }
                    }
                }
            }
            
            if(ingame == 1){
                this.gameOver();
            }
            
            try {
                this.sleep(1000/60);
            } catch (InterruptedException ex) {
                Console.ConsoleFrame.sendMessage("IntelligenceControlThread", ex.getMessage());
            }
        }
    }
    
    public void gameOver(){
        this.stopNicely();
        VisualEngine.getInstance().closeWindow(); 
    }
    
    public static int getNumberOfTanks(){
        return numberOfTanks;
    }
    
    /**
     * To be called from a TankThread that wants to remove itself from the game.
     * @param t 
     */
    public void removeFromGame(int index){
        synchronized(GameEntity.entityList){
            ((Tank)GameEntity.entityList.get(index)).setLife(0);
        }
        System.out.print("Removed from game");
    }
    
    public void stopNicely(){
        synchronized(GameEntity.entityList){
            GameEntity.entityList.clear();
        }
        
        running = false;
        
        bulletUpdater.stopNicely();
        BulletHitChecker.getInstance().stopNicely();
        
        try {
            bulletUpdater.join();
            BulletHitChecker.getInstance().stopNicely();
            BulletHitChecker.getInstance().join();
        } catch (InterruptedException ex) { 
            ConsoleFrame.sendMessage("IntelligeneControlThread", "InterruptedException during stopNicely()");
        }
        for(int i = 0; i < tankThreads.size(); i++){
            try {
                tankThreads.get(i).stopNicely();
                tankThreads.get(i).join();
            } catch (InterruptedException ex) {
                ConsoleFrame.sendMessage("IntelligeneControlThread", "InterruptedException during stopNicely()");
            }
        }
    }
}
