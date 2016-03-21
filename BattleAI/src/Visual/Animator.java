package Visual;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 *
 * @author Liviu
 */
public class Animator extends AnimationTimer{
    
    private final GraphicsContext graphicContext;
    private long elapsedTime, oldTime, fpsCounter, fps;
    
    
    public Animator(GraphicsContext gc){
        this.graphicContext = gc;
    }
    
    @Override
    public void handle(long newTime){  //timeNano == current time value
        graphicContext.clearRect(0, 0, graphicContext.getCanvas().getWidth(), graphicContext.getCanvas().getHeight());
        graphicContext.setFill(Color.AZURE);
        graphicContext.fillRect(0,0, graphicContext.getCanvas().getWidth(), graphicContext.getCanvas().getHeight());
        showFPS(newTime);
    }
    
    private void showFPS(long newTime){
        
        elapsedTime += newTime - oldTime;
        oldTime = newTime;
        fpsCounter++;
        
        if(elapsedTime >= 1e9){
            if(fpsCounter > 60 && elapsedTime > 1e9){
                fps = 60;
            }else{
                fps = fpsCounter;
            }
            elapsedTime = 0;
            fpsCounter = 0;
        }
        
        Paint c = graphicContext.getStroke();
        graphicContext.setStroke(Color.BLACK);
        graphicContext.strokeText(fps+" FPS", 0, 10);
        graphicContext.setStroke(c);
    }
    
}
