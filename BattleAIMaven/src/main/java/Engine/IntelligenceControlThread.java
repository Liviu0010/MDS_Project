package Engine;

import Client.ConnectionHandler;
import Compiler.SourceCompiler;
import Editor.Source;
import Engine.GameEntity;
import Intelligence.IntelligenceTemplate;
import Intelligence.Semaphore;
import Intelligence.TankThread;
import Networking.Requests.RequestType;
import Visual.VisualEngine;
import java.io.IOException;
import java.util.ArrayList;

public class IntelligenceControlThread extends Thread{
    private static IntelligenceControlThread instance;
    private ArrayList<TankThread> tankThreads;
    private ArrayList<Semaphore> semaphores;
    private boolean running;
    
    public IntelligenceControlThread(ArrayList<Source> surse){
        IntelligenceTemplate playerCode;
        tankThreads = new ArrayList<>();
        semaphores = new ArrayList<>();
        
        running = true;
        
        for(int i = 0; i<surse.size(); i++){
            
            synchronized(GameEntity.entityList){
                new Tank(); //adds it to entityList
            }
            
            playerCode = (IntelligenceTemplate)SourceCompiler.getInstanceOfSource(surse.get(i));
            semaphores.add(new Semaphore());
            tankThreads.add(new TankThread(playerCode, semaphores.get(i)));
        }
        
    }
    
    //testing
    public IntelligenceControlThread(int numberOfTanks){
        GameEntity.entityList.clear();
        GameEntity.currentIndex = 0;
        
        VisualEngine.getInstance().setEntityList(GameEntity.entityList);
        IntelligenceTemplate playerCode;// = new IntelligenceTemplate();
        tankThreads = new ArrayList<>();
        semaphores = new ArrayList<>();
        
        running = true;
        
        for(int i = 0; i<numberOfTanks; i++){
            
            synchronized(GameEntity.entityList){
                new Tank(); //adds it to entityList
            }
            if(i == 0)
                playerCode = new Intelligence.TestTank1();
            else if(i==1)
                playerCode = new Intelligence.TestTank2();
            else if(i == 2)
                playerCode = new Intelligence.TestTank3();
            else
                playerCode = new IntelligenceTemplate();
            
            semaphores.add(new Semaphore());
            tankThreads.add(new TankThread(playerCode, semaphores.get(i)));
        }
        
        System.out.println("size = "+GameEntity.entityList.size());
        
    }
    //END testing
    
    @Override
    public void run(){
        
        for(int i = 0; i<tankThreads.size(); i++) {
            tankThreads.get(i).start();
        }
        
        while(running) {  
            //OFF FOR NOW
            /*synchronized (GameEntity.entityList) {
                for (int i = 0; i < tankThreads.size(); i++) {
                    if (semaphores.get(i).isGreen()) {
                        EntityUpdateRequest eur = new EntityUpdateRequest(RequestType.ENTITIY_UPDATE, GameEntity.entityList);
                        try {
                            ConnectionHandler.getInstance().sendToMatch(eur);
                        } catch (IOException ex) {
                            Console.ConsoleFrame.sendMessage("IntelligenceControlThread", ex.getMessage());
                        }
                    }
                }
            }*/
            
            for(int i = 0; i < tankThreads.size(); i++){
                synchronized (semaphores.get(i)) {
                    if (semaphores.get(i).isGreen()) {
                        semaphores.get(i).goRed();
                        semaphores.get(i).notify();
                    }
                }
            }
            
            try {
                this.sleep(1000/60);
            } catch (InterruptedException ex) {
                Console.ConsoleFrame.sendMessage("IntelligenceControlThread", ex.getMessage());
            }
        }
    }
    
    public void stopNicely(){
        running = false;
        
        for(int i = 0; i < tankThreads.size(); i++)
            tankThreads.get(i).stopNicely();
    }
}
