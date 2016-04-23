package Intelligence;

import java.util.ArrayList;

public class IntelligenceControlThread extends Thread{
    private static IntelligenceControlThread instance;
    private ArrayList<IntelligenceTemplate> intelligences;
    private ArrayList<Semaphore> semaphores;
    private boolean running;
    
    public IntelligenceControlThread(){
        running = true;
        
        
    }
    
    public static IntelligenceControlThread getInstance(){
        if(instance == null)
            instance = new IntelligenceControlThread();
        return instance;
    }
    
    @Override
    public void run(){
        
        while(running) {
            
        }
    }
    
    public void stopNicely(){
        running = false;
    }
}
