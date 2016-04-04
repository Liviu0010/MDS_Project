/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor;

import Console.ConsoleFrame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Dragos-Alexandru
 */
public final class SourceManager {
    
    private static final String sourceFolderPath = "source/";
    private static final String inteligentFolderPath = "";
    private static List<Source> sources = new ArrayList<>();
    private static SourceManager instance;
    
    private static final String inteligenceTemplate = ""
            + "package AI;\n" +
                "\n" +
                "import Engine.Tank;\n" +
                "\n" +
                "public class AI implements Inteligence{\n" +
                "\n" +
                "    public AI(){}\n" +
                "    \n" +
                "    @Override\n" +
                "    public void gotHitByBullet() {}\n" +
                "\n" +
                "    @Override\n" +
                "    public void hitArenaWall() {}\n" +
                "\n" +
                "    @Override\n" +
                "    public void hitEnemyTank() {}\n" +
                "\n" +
                "    @Override\n" +
                "    public void detectedEnemyTank(Tank enemy) {}\n" +
                "    \n" +
                "}";
    
    private SourceManager() throws IOException{
        
        File sourceFolder = new File(sourceFolderPath);
        ConsoleFrame.sendMessage(this.getClass().getSimpleName(), "Searching for source folder at "+sourceFolder.getAbsolutePath());
        
        if(!sourceFolder.exists()){
            ConsoleFrame.sendMessage(this.getClass().getSimpleName(), "Creating source folder");
            sourceFolder.mkdir();
            ConsoleFrame.sendMessage(this.getClass().getSimpleName(), "Testing read/write permissions source folder");
            if(!sourceFolder.canRead()){
                throw new IOException("Can't read from designated folder!");
            }
            if(!sourceFolder.canWrite()){
                throw new IOException("Can't write to designated folder!");
            }
            ConsoleFrame.sendMessage(this.getClass().getSimpleName(), "Creating source index file");
            File sourceIndex = new File(sourceFolderPath+"/sourcesIndex.txt");
            sourceIndex.createNewFile();
            
            try (FileOutputStream fOutput = new FileOutputStream(sourceIndex); 
                    ObjectOutputStream oOutput = new ObjectOutputStream(fOutput)) {
                sources.add(new Source("TEST", "Test1", "Dragos"));
                sources.add(new Source("TEST", "Test2", "Dragos"));
                sources.add(new Source("TEST", "Test3", "Dragos"));
                oOutput.writeObject(sources);
            }
            
        }else{
            ConsoleFrame.sendMessage(this.getClass().getSimpleName(), "Source folder exists");
            ConsoleFrame.sendMessage(this.getClass().getSimpleName(), "Searching for source index file");
            File sourceIndex = new File(sourceFolderPath+"/sourcesIndex.txt");
            if(!sourceIndex.exists()){
                ConsoleFrame.sendMessage(this.getClass().getSimpleName(), "Creating source index file");
                sourceIndex.createNewFile();

                try (FileOutputStream fOutput = new FileOutputStream(sourceIndex); 
                        ObjectOutputStream oOutput = new ObjectOutputStream(fOutput)) {
                    ConsoleFrame.sendMessage(this.getClass().getSimpleName(), 
                            "Writing empty source list to source index file");
                sources.add(new Source("TEST", "Test1", "Dragos"));
                sources.add(new Source("TEST", "Test2", "Dragos"));
                sources.add(new Source("TEST", "Test3", "Dragos"));
                    oOutput.writeObject(sources);
                }
            }else{
                ConsoleFrame.sendMessage(this.getClass().getSimpleName(), "Source index file exists");
                try (FileInputStream fInput = new FileInputStream(sourceIndex);
                        ObjectInputStream oInput = new ObjectInputStream(fInput)){
                    ConsoleFrame.sendMessage(this.getClass().getSimpleName(), "Reading from source index file");
                    sources = (ArrayList<Source>)oInput.readObject();
                    
                } catch (ClassNotFoundException ex) {
                    ConsoleFrame.sendMessage(this.getClass().getSimpleName(), "Couldn't read from source index");
                }
            }
        }
        if(!sources.isEmpty()){
            ConsoleFrame.sendMessage(this.getClass().getSimpleName(), "Found this sources: ");
            for(Source sourceAux:sources){
                ConsoleFrame.sendMessage(this.getClass().getSimpleName(), sourceAux.toString());
            }
        }
        
        ConsoleFrame.sendMessage(this.getClass().getSimpleName(), "SourceManager is set to go");
    }
    
    public static SourceManager getInstance(){
        if(instance == null){
            try {
                ConsoleFrame.sendMessage(SourceManager.class.getSimpleName(), "Started SourceManager");
                instance = new SourceManager();
            } catch (IOException ex) {
                ConsoleFrame.sendMessage(SourceManager.class.getSimpleName(), ex.getMessage());
                System.exit(-1);
            }
        }
        return instance;
    }
    
    public void moveFileToSourceFolder(File file){
        File newSource = new File(sourceFolderPath+file.getName());
        try {
            newSource.createNewFile();
            try (FileReader fileReader = new FileReader(file); 
                    BufferedReader reader = new BufferedReader(fileReader); 
                    FileWriter fileWriter = new FileWriter(newSource); 
                    BufferedWriter writer = new BufferedWriter(fileWriter)) {
                
                String stringAux;
                stringAux = reader.readLine();
                while(stringAux != null){
                    writer.write(stringAux);
                }
            }
        } catch (IOException ex) {
            ConsoleFrame.sendMessage(this.getClass().getName(), "Failed to create file "+file.getName());
            JOptionPane.showMessageDialog(null, ex.getMessage(), 
                    "Failed to create file "+file.getName(), 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * This method does not work properly, it needs to get the actual ai source
     * now it gets a string hard coded in this class
     * @return inteligenceTemplate
     */
    public String getInteligenceTemplate(){
        File inteligent = new File(inteligentFolderPath);
        //System.out.println(inteligent.getAbsolutePath());
        
        return inteligenceTemplate;
    }
    
    public static void main(String[] args){
        SourceManager sourceManager = SourceManager.getInstance();
        
    }
    
    
}
