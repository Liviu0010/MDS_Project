package Compiler;

import Console.ConsoleFrame;
import Editor.Source;
import Editor.SourceManager;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dragos-Alexandru
 */
public abstract class Compiler {
    
    private final static JavaCompiler COMPILER = ToolProvider.getSystemJavaCompiler();
    private static URLClassLoader CLASS_LOADER;
    
    private static File compileSource(Source source){
        File sourceFile = SourceManager.getInstance().createSourceFile(source);
        
        int fail = COMPILER.run(null, null, null, sourceFile.getPath());
        if(fail != 0){
            SourceManager.getInstance().deleteSourceFile(sourceFile);
            return null;
        }
        return sourceFile;
    }
    
    public static Object getInstanceOfSource(Source source){
        Object sourceInstance = null;
        File sourceFile = compileSource(source);
        if(sourceFile != null){
            try {
                CLASS_LOADER = URLClassLoader.newInstance(new URL[] { sourceFile.toURI().toURL() });
                Class<?> sourceClass = Class.forName("User_Sources."+source.getName(),true,CLASS_LOADER);
                sourceInstance = sourceClass.newInstance();
                ConsoleFrame.sendMessage(Compiler.class.getSimpleName(), "Created instance of "+sourceInstance.getClass().getSimpleName());
            } catch (MalformedURLException ex) {
                Logger.getLogger(Compiler.class.getName()).log(Level.SEVERE, null, ex);
                ConsoleFrame.sendMessage(Compiler.class.getSimpleName(), "Failed to create new URLClassLoader");
            } catch (ClassNotFoundException ex) {
                ConsoleFrame.sendMessage(Compiler.class.getSimpleName(), "Failed to get class of compiled source");
                Logger.getLogger(Compiler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException | IllegalAccessException ex) {
                ConsoleFrame.sendMessage(Compiler.class.getSimpleName(), "Failed to get instantiate source class");
                Logger.getLogger(Compiler.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                SourceManager.getInstance().deleteSourceFile(sourceFile);
            }
        }
        
        return sourceInstance;
        
    }
    
}
