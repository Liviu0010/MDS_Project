
package Engine;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
 
/* 
 * This is like the FontDemo applet in volume 1, except that it 
 * uses the Java 2D APIs to define and render the graphics and text.
 */
 
public class DummyEngineClass extends JApplet {
    final static int maxCharHeight = 15;
    final static int minFontSize = 6;
    
 
    final static Color bg = Color.white;
    final static Color fg = Color.black;
    final static BasicStroke stroke = new BasicStroke(2.0f);
    final static BasicStroke wideStroke = new BasicStroke(8.0f);
 
    final static float dash1[] = {10.0f};
    final static BasicStroke dashed = new BasicStroke(1.0f, 
                                                      BasicStroke.CAP_BUTT, 
                                                      BasicStroke.JOIN_MITER, 
                                                      10.0f, dash1, 0.0f);
    
    Bullet bullet = new Bullet(0,0,10,10,0);
    Bullet bullet2 = new Bullet(100,100,15,15,90);
    Tank tank = new Tank();
    Shape shape ,shape2;
    @Override
    public void init() {
        //Initialize drawing colors
        setBackground(bg);
        setForeground(fg);
        //bullet.Rotate(90);
        tank.moveDown();
        tank.moveDown();
        tank.moveDown();
        tank.moveDown();
        tank.moveDown();
        
        shape2 = bullet2.getShape();
        shape = bullet.getShape();
    }
 
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(stroke);
        
        g2.draw(shape);
        //g2.draw(shape);  
    }
 
    public static void main(String s[]) {
        JFrame f = new JFrame("ShapesDemo2D");
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        JApplet applet = new DummyEngineClass();
        f.getContentPane().add("Center", applet);
        applet.init();
        f.pack();
        f.setSize(new Dimension(1000,700));
        f.setVisible(true);
    }
 
}