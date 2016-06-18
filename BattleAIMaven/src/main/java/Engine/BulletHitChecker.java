package Engine;

import Console.ConsoleFrame;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class BulletHitChecker extends Thread {
    private boolean running;
    private static BulletHitChecker instance;
    private static boolean good2go;
    
    private BulletHitChecker(){
        running = true;
        good2go = true;
    }
    
    public static BulletHitChecker getInstance(){
        if(good2go == false)
            instance = new BulletHitChecker();
        
        return instance;
    }
    
    @Override
    public void run(){
        Tank t;
        Bullet b;
        Rectangle2D tankRect, bulletRect;
        ArrayList<GameEntity> entities = GameEntity.entityList;
        
        while(running){
            synchronized(entities){
                //if there are N tanks in the game, the first N objects in the
                //entities array will certainly be the tanks
                //ergo no O(n^2) for you m8
                for(int i = 0; i < IntelligenceControlThread.getNumberOfTanks(); i++){
                    if(entities.get(i) instanceof Tank){
                        t = (Tank) entities.get(i);
                        
                        if (t.inTheGame()) {
                            tankRect = new Rectangle((int) t.getX(), (int) t.getY(), (int) Constants.VisualConstants.TANK_WIDTH, (int) Constants.VisualConstants.TANK_HEIGHT);

                            for (int j = IntelligenceControlThread.getNumberOfTanks(); j < entities.size(); j++) {
                                if (entities.get(j) instanceof Bullet) {
                                    b = (Bullet) entities.get(j);
                                    bulletRect = new Rectangle((int) b.getX(), (int) b.getY(), (int) Constants.VisualConstants.BULLET_WIDTH, (int) Constants.VisualConstants.BULLET_HEIGHT);

                                    if (tankRect.intersects(bulletRect) && b.owner != t) {
                                        entities.remove(b);
                                        t.hitByBullet();
                                    }

                                }
                            }
                        }
                    }
                }
            }
            
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException ex) {
                ConsoleFrame.sendMessage("BulletHitChecker", ex.getMessage());
            }
        }
    }
    
    public void stopNicely(){
        good2go = running = false;
    }
}
