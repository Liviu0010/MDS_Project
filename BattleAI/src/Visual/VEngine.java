package Visual;

//You will only interact with THIS class
public class VEngine {
    private boolean engineActive;
    private static VEngine instance;
    private VisualEngine ve;
    
    private VEngine(){
        Thread t = new Thread(new Runnable(){
            @Override
            public void run(){
            VisualEngine.main(new String[1]); 
        }
        });
        
        t.start();  //the visual engine will run in this thread
        
        
        while(ve == null)   //just wait for the engine to be initialised in the other thread before returning the instance
        {
            try{
            Thread.sleep(1);
            }
            catch(InterruptedException iex){
                System.out.println(iex.getMessage());
            }
            
            ve = VisualEngine.getInstance();
        }
    }
    
    public static VEngine getInstance(){
        if(instance == null)
            instance = new VEngine();
        
        return instance;
    }
    
    public static boolean initialized(){
        return instance == null ? false : true;
    }
    
    public void showEngine(){
        if(instance == null)
            throw new EngineExitedException();
        
        instance.ve.showEngine();
    }
    
    public void hideEngine(){
        instance.ve.hideEngine();
    }
    
    public boolean isRunning(){
        return instance == null ? false : instance.ve.isRunning();
    }
    
    //DO NOT CALL MORE THAN ONCE
    public void exit(){
        if(instance == null)
            return;
        instance.ve.exit();
        instance.ve = null;
        instance = null;
    }
}
