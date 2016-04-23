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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.BorderUIResource;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 * The Editor class represent a frame in witch the user can write
 * his code for an robot
 */
public final class Editor extends JFrame {
    private final MainFrame frame;
    
    private final JMenuBar menuBar;
    
    private final JMenuItem newButton;
    private final JMenuItem openButton;
    private final JMenuItem saveButton;
    private final JMenuItem compileButton;
    
    private String inteligenceTemplate;
    private final RSyntaxTextArea sourceArea;
    private final JScrollPane scrollSourceArea;
    private final JTextArea compileResult;
    private final JScrollPane scrollCompileResult;
    private final JPanel sourcePanel;
    
    
    private final String sourceName;
    
    /**
     * 
     * @param mainFrame
     */
    public Editor(MainFrame mainFrame){
        super("editor");
        
        this.frame = mainFrame;
        inteligenceTemplate = SourceManager.getInstance().getIntelligenceTemplate();
        
        sourceName = JOptionPane.showInputDialog("Source name: ", "LocalSource");
        
        inteligenceTemplate = inteligenceTemplate.replaceFirst("<name>", sourceName);
        
        sourceArea = new RSyntaxTextArea();
        sourceArea.setDocument(new RSyntaxDocument (RSyntaxTextArea.SYNTAX_STYLE_JAVA));
        sourceArea.setDocument(new RSyntaxDocument(RSyntaxTextArea.SYNTAX_STYLE_JAVA));
        sourceArea.append(inteligenceTemplate);
        sourceArea.setSyntaxEditingStyle(RSyntaxTextArea.SYNTAX_STYLE_JAVA);
        sourceArea.setAutoIndentEnabled(true);
        sourceArea.setCodeFoldingEnabled(true);
        sourceArea.setMarkOccurrences(true);
        sourceArea.setActiveLineRange(3, 10);
        sourceArea.setBorder(new BorderUIResource.LineBorderUIResource(Color.white, 4));
        
        scrollSourceArea = new JScrollPane();
        scrollSourceArea.setViewportView(sourceArea);
        
        compileResult = new JTextArea();
        compileResult.setEditable(false);
        compileResult.setBorder(new BorderUIResource.LineBorderUIResource(Color.black));
        compileResult.setLineWrap(true);
        compileResult.setBackground(Color.black);
        compileResult.setForeground(Color.GREEN);
        
        scrollCompileResult = new JScrollPane();
        scrollCompileResult.setViewportView(compileResult);
        scrollCompileResult.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        sourcePanel = new JPanel(new GridLayout(2, 1));
        
        EditorMenuListener editorMenuListener = new EditorMenuListener();
        
        newButton = new JMenuItem("New");
        newButton.setBackground(Color.BLACK);
        newButton.setForeground(Color.WHITE);
        newButton.addMouseListener(editorMenuListener);
        
        openButton = new JMenuItem("Open");
        openButton.setBackground(Color.BLACK);
        openButton.setForeground(Color.WHITE);
        openButton.addMouseListener(editorMenuListener);
        
        saveButton = new JMenuItem("Save");
        saveButton.setBackground(Color.BLACK);
        saveButton.setForeground(Color.WHITE);
        saveButton.addMouseListener(editorMenuListener);
        
        compileButton = new JMenuItem("Compile");
        compileButton.setBackground(Color.BLACK);
        compileButton.setForeground(Color.WHITE);
        compileButton.addMouseListener(new EditorCompileListener(sourceName, sourceArea, compileResult));
        
        menuBar = new JMenuBar();
        menuBar.setLayout(new GridLayout(1,4));
        menuBar.setBackground(Color.BLACK);

        menuBar.add(newButton);
        menuBar.add(openButton);
        menuBar.add(saveButton);
        menuBar.add(compileButton);
        
        start();
    }
    
    /**
     * Starts the editor
     * I put this here because all those functions cannot be called from the
     * constructor
     */
    public final void start(){
        
        this.setSize(800, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                super.windowClosing(e);
                frame.setVisible(true);
        }
        });
        
        this.setVisible(true);
        this.add(menuBar, BorderLayout.NORTH);
        sourcePanel.add(scrollSourceArea);
        sourcePanel.add(scrollCompileResult);
        this.add(sourcePanel, BorderLayout.CENTER);
    }
}
