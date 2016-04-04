/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Dragos-Alexandru
 */
public class EditorMenuListener extends MouseAdapter{

    
    
    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
        e.getComponent().setBackground(Color.orange);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
        e.getComponent().setBackground(Color.white);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        e.getComponent().setBackground(Color.blue);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        if(e.getComponent().contains(e.getPoint())){
            e.getComponent().setBackground(Color.orange);
        }else{
            e.getComponent().setBackground(Color.white);
        }
    }
}
