package Visual;

public class EngineExitedException extends RuntimeException {
    public EngineExitedException(){
        super("The visual engine was already exited.");
    }
    
    public EngineExitedException(String message){
        super(message);
    }
}
