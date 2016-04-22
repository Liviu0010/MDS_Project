/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor;

import Compiler.SourceCompiler;
import Console.ConsoleFrame;
import Networking.Server.Player;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 *
 * @author Dragos-Alexandru
 */
public class EditorCompileListener extends EditorMenuListener{

    private final RSyntaxTextArea sourceArea;
    private String sourceName;
    private final JTextArea compileArea;
    
    
    public EditorCompileListener(String sourceName, RSyntaxTextArea sourceArea, JTextArea compileArea){
        this.sourceArea = sourceArea;
        this.sourceName = sourceName;
        this.compileArea = compileArea;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            CompileWorker worker = new CompileWorker();
            
            worker.execute();
            
            boolean success = worker.get();
            
            compileArea.setText(" Compile result: \n");
            if(success){
                compileArea.append("\nCompilation was successfull");
            }else{
                compileArea.append("\nCompilation failed: \n"+SourceCompiler.getLastError());
            }
        } catch (InterruptedException | ExecutionException ex) {
            ConsoleFrame.sendMessage(this.getClass().getSimpleName(), "Compile worker failed");
            Logger.getLogger(EditorCompileListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    
    private class CompileWorker extends SwingWorker<Boolean, Void>{

        @Override
        protected Boolean doInBackground() throws Exception {
            
            Source source = new Source(sourceArea.getText(),sourceName, Player.getInstance().getUsername());
            
            Object sourceObject = SourceCompiler.getInstanceOfSource(source);
            
            return sourceObject != null;
        }
        
    }
    
}
