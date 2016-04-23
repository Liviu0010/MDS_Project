package Intelligence;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TankThread extends Thread{
    private final IntelligenceTemplate intellT;
    private boolean running;
    private final Semaphore semaphore;
    
    public TankThread(IntelligenceTemplate intellT, Semaphore semaphore) {
        this.intellT = intellT;
        this.semaphore = semaphore;
        running = true;
    }
    
    @Override
    public void run(){
        
        while(running){
            semaphore.goRed();
            intellT.run();
            semaphore.goGreen();
            
            try {
                synchronized(semaphore){
                    while(semaphore.isGreen())
                        semaphore.wait();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(TankThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
    public void stopNicely(){
        running = false;
    }
}
