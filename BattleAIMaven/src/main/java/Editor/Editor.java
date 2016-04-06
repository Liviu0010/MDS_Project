/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor;

import Interface.MainFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.plaf.BorderUIResource;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 *
 * @author Dragos-Alexandru
 */
public final class Editor extends JFrame{
    
    JMenuBar menuBar;
    JMenuItem newButton;
    JMenuItem openButton;
    JMenuItem saveButton;
    JMenuItem compileButton;
    
    RSyntaxTextArea sourceArea;
    
    String inteligenceTemplate;
    
    MainFrame mainFrame;
    
    public Editor(MainFrame rootFrame){
        super("Editor");
        
        this.mainFrame = rootFrame;
        inteligenceTemplate = SourceManager.getInstance().getInteligenceTemplate();
        
        menuBar = new JMenuBar();
        menuBar.setLayout(new GridLayout(1, 4));
        
        newButton = new JMenuItem("NEW");
        openButton = new JMenuItem("OPEN");
        saveButton = new JMenuItem("SAVE");
        compileButton = new JMenuItem("Test Compile");
        
        newButton.setBackground(Color.WHITE);
        newButton.addMouseListener(new EditorMenuListener());
        openButton.setBackground(Color.WHITE);
        openButton.addMouseListener(new EditorMenuListener());
        saveButton.setBackground(Color.WHITE);
        saveButton.addMouseListener(new EditorMenuListener());
        compileButton.setBackground(Color.WHITE);
        compileButton.addMouseListener(new EditorMenuListener());
        
        menuBar.add(newButton);
        menuBar.add(openButton);
        menuBar.add(saveButton);
        menuBar.add(compileButton);
        
        sourceArea = new RSyntaxTextArea();
        sourceArea.setDocument(new RSyntaxDocument(RSyntaxTextArea.SYNTAX_STYLE_JAVA));
        sourceArea.append(inteligenceTemplate);
        sourceArea.setSyntaxEditingStyle(RSyntaxTextArea.SYNTAX_STYLE_JAVA);
        sourceArea.setAutoIndentEnabled(true);
        sourceArea.setCodeFoldingEnabled(true);
        sourceArea.setMarkOccurrences(true);
        sourceArea.setActiveLineRange(3, 10);
        sourceArea.setBorder(new BorderUIResource.LineBorderUIResource(Color.white, 4));
        
    }
    
    public void start(){
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 800);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                mainFrame.setVisible(true);
            }
        });
        
        this.add(menuBar,BorderLayout.NORTH);
        this.add(sourceArea,BorderLayout.CENTER);
        this.setVisible(true);
    }
    
}
