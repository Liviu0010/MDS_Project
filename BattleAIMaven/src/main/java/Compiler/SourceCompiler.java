package Compiler;

import Console.ConsoleFrame;
import Editor.Source;
import Editor.SourceManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;
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
public abstract class SourceCompiler {
    
    private final static JavaCompiler COMPILER = ToolProvider.getSystemJavaCompiler();
    private static URLClassLoader CLASS_LOADER;
    
    private static String lastError = "";
    
    public static File compileSource(Source source){
        File sourceFile = SourceManager.getInstance().createSourceFile(source);
        ByteArrayOutputStream errorOStream = new ByteArrayOutputStream();
        int fail = COMPILER.run(null, null, errorOStream, sourceFile.getPath());
        if(fail != 0){
            SourceManager.getInstance().deleteSourceFile(sourceFile);
            ByteArrayInputStream errorIStream = new ByteArrayInputStream(errorOStream.toByteArray());
            Scanner s = new Scanner(errorIStream);
            lastError = "";
            while(s.hasNextLine()){
                lastError += "\n" + s.nextLine();
            }
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
                ConsoleFrame.sendMessage(SourceCompiler.class.getSimpleName(), "Created instance of "+sourceInstance.getClass().getSimpleName());
            } catch (MalformedURLException ex) {
                ConsoleFrame.sendMessage(SourceCompiler.class.getSimpleName(), "Failed to create new URLClassLoader");
            } catch (ClassNotFoundException ex) {
                ConsoleFrame.sendMessage(SourceCompiler.class.getSimpleName(), "Failed to get class of compiled source");
            } catch (InstantiationException | IllegalAccessException ex) {
                ConsoleFrame.sendMessage(SourceCompiler.class.getSimpleName(), "Failed to get instantiate source class");
            }finally{
                SourceManager.getInstance().deleteSourceFile(sourceFile);
            }
        }
        
        return sourceInstance;
        
    }
    
    public static String getLastError(){
        return lastError;
    }
    
}
