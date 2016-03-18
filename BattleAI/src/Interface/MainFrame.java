/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Constants.FrameConstants;
import Visual.VEngine;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Dragos-Alexandru
 */
public class MainFrame extends JFrame implements FrameConstants{
    
    /**
     * Frame-ul principal al programului
     */
    public MainFrame(){
        super("BattleAI");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        
        this.addWindowListener(new WinListener());  //need to tell the engine the window is closing
        
        this.add(new MainMenuPanel(this));
    }
    
    public void changePanel(JPanel panel){
        this.getContentPane().removeAll();
        this.getContentPane().add(panel);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }
}
