package Editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Alexandra-Pc
 */
public class EditorMenuListener implements MouseListener{

    @Override
    public void mouseEntered(MouseEvent e) {
        Component menuItem = e.getComponent();
        menuItem.setBackground(Color.WHITE);
        menuItem.setForeground(Color.BLACK);
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Component menuItem = e.getComponent();
        menuItem.setBackground(Color.BLACK);
        menuItem.setForeground(Color.WHITE);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
