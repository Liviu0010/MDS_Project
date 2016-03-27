/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author Liviu
 */
public class VisualTank extends Engine.Tank implements VisualEntity{
    Image tankSprite, cannonSprite;
    
    public VisualTank(double x, double y, double speed, double life, double damage, double angle, Image tankSprite, Image cannonSprite){
        super(x, y, speed, life, damage, angle);
        this.tankSprite = tankSprite;
        this.cannonSprite = cannonSprite;
    }
    
    @Override
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        AffineTransform at = g2.getTransform();
        
        g2.rotate(Math.toRadians(90), x+10, y+10);
        g2.rotate(Math.toRadians(angle), x+10, y+10);
        g2.drawImage(tankSprite, (int)x, (int)y, null);
        g2.setTransform(at); 
        
        g2.rotate(Math.toRadians(90), cannon.getX()+10, cannon.getY()+10);
        g2.rotate(Math.toRadians(cannon.GetAngle()), cannon.getX()+10, cannon.getY()+10);
        g2.drawImage(cannonSprite, (int)cannon.getX()+8, (int)cannon.getY(), null);
        g2.setTransform(at);
        
    }
}
