package Engine;

import Client.ConnectionHandler;
import Compiler.SourceCompiler;
import Editor.Source;
import Engine.GameEntity;
import Engine.Tank;
import Intelligence.IntelligenceTemplate;
import Intelligence.Semaphore;
import Intelligence.TankThread;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IntelligenceControlThread extends Thread{
    private static IntelligenceControlThread instance;
    private ArrayList<TankThread> tankThreads;
    private ArrayList<Semaphore> semaphores;
    private ArrayList<Tank> tankList;
    private boolean running;
    
    public IntelligenceControlThread(ArrayList<Source> surse){
        IntelligenceTemplate playerCode;    //TODO: Compile player code
        tankThreads = new ArrayList<>();
        semaphores = new ArrayList<>();
        
        running = true;
        
        for(int i = 0; i<surse.size(); i++){
            playerCode = (IntelligenceTemplate)SourceCompiler.getInstanceOfSource(surse.get(i));
            semaphores.add(new Semaphore());
            tankThreads.add(new TankThread(playerCode, semaphores.get(i)));
        }
        
    }
    
    @Override
    public void run(){
        
        for(int i = 0; i<tankThreads.size(); i++) {
            tankThreads.get(i).start();
        }
        
        while(running) {
            try {
                this.wait(1000/60);
            } catch (InterruptedException ex) {
                Logger.getLogger(IntelligenceControlThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for(int i = 0; i < tankThreads.size(); i++){
                if(semaphores.get(i).isGreen()) {
                    
                }
            }
            
            for(int i = 0; i < tankThreads.size(); i++){
                if(semaphores.get(i).isGreen()){
                    semaphores.get(i).goRed();
                    semaphores.get(i).notify();
                }
            }
        }
    }
    
    public void stopNicely(){
        running = false;
    }
}
