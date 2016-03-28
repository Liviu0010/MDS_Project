package Visual;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Liviu
 */
public class VisualBullet extends Engine.Bullet implements VisualEntity{
    Image sprite;
    
    public VisualBullet(double x , double y, double angle, double speed,double damage, Image sprite){
        super(x, y, angle, speed, damage);
        this.sprite = sprite;
    }
    
    
    @Override
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        AffineTransform at = g2.getTransform();
        
        g2.rotate(Math.toRadians(angle), x, y);
        //g2.drawImage(sprite, (int)x, (int)y,null);    //disabled for now
                                                       //8-9 FPS at ~1300 objects
        g2.setColor(Color.red);
        g2.fillRect((int)x, (int)y, 5, 5);  //drawing rectangles is less costly than drawing images
                                            // 60 FPS at ~1300 objects
                                            //6-9 FPS at ~18000 objects
        
        g2.setTransform(at);
    }
}
