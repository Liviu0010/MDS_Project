/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private GraphicsContext gc;
    private long timeSum, lastTime, fpsCounter, fps;
    
    
    public Animator(GraphicsContext gc){
        this.gc = gc;
    }
    
    @Override
    public void handle(long timeNano){  //timeNano == current time value
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        gc.setFill(Color.AZURE);
        gc.fillRect(0,0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        showFPS(timeNano);
    }
    
    private void showFPS(long timeNano){
        timeSum += timeNano - lastTime;
        lastTime = timeNano;
        fpsCounter++;
        
        if(timeSum >= 1e9){
            fps = fpsCounter > 60 && timeSum > 1e9 ? 60 : fpsCounter;
            timeSum = fpsCounter = 0;
        }
        
        Paint c = gc.getStroke();
        gc.setStroke(Color.BLACK);
        gc.strokeText(fps+" FPS", 0, 10);
        gc.setStroke(c);
    }
    
}
