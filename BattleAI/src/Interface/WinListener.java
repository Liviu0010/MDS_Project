/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Visual.VEngine;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author Liviu
 */
public class WinListener implements WindowListener{

    @Override
    public void windowOpened(WindowEvent e) {
        
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        if(VEngine.initialized())
            VEngine.getInstance().exit();   //telling the engine to close with the window
    }

    @Override
    public void windowClosed(WindowEvent e) {
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        
    }
    
}
