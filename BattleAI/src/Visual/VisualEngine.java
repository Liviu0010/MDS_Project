package Visual;

import Constants.VisualEngineConstants;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

//DO NOT TOUCH THIS CLASS UNDER ANY CIRCUMSTANCES
//ONLY WORK WITH THE VEngine CLASS
//Welp, unless you wanna have a bad time, that is
public class VisualEngine extends Application {
    
    private GraphicsContext graphicContext;
    private Stage visualStage;
    private static VisualEngine instance;
    private boolean isRunning;
    private Animator animator;
    
    @Override
    public void start(Stage primaryStage) {
        //SETTING UP THE WINDOW CONTENTS
        Pane pane = new Pane();
        Canvas canvas =new Canvas(VisualEngineConstants.ENGINE_WIDTH, VisualEngineConstants.ENGINE_HEIGHT);
        
        graphicContext = canvas.getGraphicsContext2D();
        
        pane.getChildren().add(canvas);
        
        Scene scene = new Scene(pane, VisualEngineConstants.ENGINE_WIDTH-10, VisualEngineConstants.ENGINE_HEIGHT-10);
        //^it adds 10 pixels on width and height when I set the stage to be non-resizable, for some reason
        //END
        
        
        
        //this is actually the window
        visualStage = new Stage();
        visualStage.setTitle(VisualEngineConstants.ENGINE_TITLE);
        visualStage.setScene(scene);
        Platform.setImplicitExit(false);    //this, so it won't exit the engine when I hide the window
                                            //it will only exit when the game is closed, aka the WHOLE game, not just the match
        visualStage.setOnCloseRequest(this::onClose);
        visualStage.setResizable(false);
        //END
        
        animator = new Animator(canvas.getGraphicsContext2D());  //get the magic ready
        
        graphicContext.setFill(Color.BLACK);
        graphicContext.fillRect(0, 0, 1, 1);
        
        instance = this;
        isRunning = false;
    }
    
    public static VisualEngine getInstance(){
        return instance;
    }
    
    public void showEngine(){
        Platform.runLater(new Runnable(){
           @Override
            public void run(){
                isRunning = true;
                visualStage.show();
                animator.start();    //start the magic
            }
        });
        
    }
    
    public void hideEngine(){
        
        Platform.runLater(new Runnable(){
           @Override
            public void run(){
                isRunning = false;
                visualStage.hide();
                animator.stop();     //no hidden magic
            }
        });
    }
    
    //DO NOT CALL MORE THAN ONCE
    public void exit(){
        instance = null;
        Platform.exit();    //this is why you do not call it more than once
    }
    
    private void onClose(WindowEvent we){
        isRunning = false;
        animator.stop();     //stop the magic before it eats all resources
    }
    
    public boolean isRunning(){
        return isRunning;
    }
    
    public static void main(String args[]){
        launch(args);   //this is also why you don't call exit() more than once
                        //launch can only be called ONCE in a JavaFX application
    }
}
