package Visual;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import Engine.*;

/**
 *
 * @author Liviu
 */
public class Animator extends AnimationTimer{
    
    private final GraphicsContext graphicContext;
    private long elapsedTime, oldTime, fpsCounter, fps;
    private final Image tankSprite, cannonSprite, bulletSprite;
    private int bodyRotation, cannonRotation, tankX=300, tankY=300;
    Tank tank, tank2, tank3;
    
    
    public Animator(GraphicsContext gc){
        this.graphicContext = gc;
        
        tank = new Tank(300, 300, 1, 100, 10, 0);
        tank2 =  new Tank(400, 400, 1, 100, 10, 0);
        tank3 = new Tank(200, 200, 2, 100, 10, 0);
        
        tankSprite = new Image(Constants.VisualEngineConstants.TANK_BODY_SPRITE_PATH);
        cannonSprite = new Image(Constants.VisualEngineConstants.TANK_CANNON_SPRITE_PATH);
        bulletSprite = new Image(Constants.VisualEngineConstants.BULLET_SPRITE_PATH);
        
        
        
    }
    
    @Override
    public void handle(long newTime){  //timeNano == current time value
        
        tank.Rotate(1);
        tank.MoveFront();
        
        tank2.Rotate(-1);
        tank2.MoveFront();
        
        tank3.Rotate(1);
        tank3.MoveFront();
        
        graphicContext.clearRect(0, 0, graphicContext.getCanvas().getWidth(), graphicContext.getCanvas().getHeight());
        graphicContext.setFill(Color.AZURE);
        graphicContext.fillRect(0,0, graphicContext.getCanvas().getWidth(), graphicContext.getCanvas().getHeight());
        
        drawTankSprite((int) tank.GetAngle(), (int)tank.GetAngle(), (int)tank.getX(), (int)tank.getY());
        drawTankSprite((int) tank2.GetAngle(), (int)tank2.GetAngle(), (int)tank2.getX(), (int)tank2.getY());
        drawTankSprite((int) tank3.GetAngle(), (int)tank3.GetAngle(), (int)tank3.getX(), (int)tank3.getY());
        
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
    
    private void drawTankSprite(int bodyRotation, int cannonRotation, int tankX, int tankY){
        
        
        //WORKAROUND FOR DIFFERENCE IN ORIGINAL ORIENTATION
        graphicContext.save();
        graphicContext.transform(new Affine(new Rotate(90,tankX+tankSprite.getWidth()/2, tankY+tankSprite.getHeight()/2)));
        
        //END
        
        //DRAWING TANK BODY
        graphicContext.save();
        graphicContext.transform(new Affine(new Rotate(bodyRotation, tankX+tankSprite.getWidth()/2, tankY+tankSprite.getHeight()/2)));
        graphicContext.drawImage(tankSprite, tankX, tankY);
        graphicContext.restore();
        //END
        
        //DRAWING TANK CANNON
        graphicContext.save();
        graphicContext.transform(new Affine(new Rotate(cannonRotation, tankX+tankSprite.getWidth()/2, tankY+tankSprite.getHeight()/2)));
        graphicContext.drawImage(cannonSprite, tankX+tankSprite.getWidth()/2-cannonSprite.getWidth()/2, tankY+tankSprite.getHeight()/2-cannonSprite.getHeight());
        graphicContext.restore();
        //END
        
        
        graphicContext.restore();
    }
    
}
