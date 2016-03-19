package Visual;

//You will only interact with THIS class

import Interface.MainFrame;

public class VisualEngineWrapper {
    
    private boolean engineActive;
    private static VisualEngineWrapper instance;
    private VisualEngine visualEngine;
    private MainFrame rootFrame;
    
    private VisualEngineWrapper(MainFrame rootFrame){
        
        this.rootFrame = rootFrame;
        
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                VisualEngine.main(new String[1]); 
            }
        });
        
        thread.start();  //the visual engine will run in this thread
        
        
        while(visualEngine == null)   //just wait for the engine to be initialised in the other thread before returning the instance
        {
            try{
            Thread.sleep(1);
            }
            catch(InterruptedException iex){
                System.out.println(iex.getMessage());
            }
            
            visualEngine = VisualEngine.getInstance();
        }
    }
    
    public static VisualEngineWrapper getInstance(MainFrame rootFrame){
        if(instance == null)
            instance = new VisualEngineWrapper(rootFrame);
        
        return instance;
    }
    
    public static boolean initialized(){
        return instance != null;
    }
    
    public void showEngine(){
        if(instance == null)
            throw new EngineExitedException();
        
        instance.visualEngine.showEngine();
    }
    
    public void hideEngine(){
        instance.visualEngine.hideEngine();
    }
    
    public boolean isRunning(){
        return instance == null ? false : instance.visualEngine.isRunning();
    }
    
    //DO NOT CALL MORE THAN ONCE
    public void exit(){
        if(instance == null)
            return;
        instance.visualEngine.exit();
        instance.visualEngine = null;
        instance = null;
    }
}
