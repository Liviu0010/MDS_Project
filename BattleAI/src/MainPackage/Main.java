package MainPackage;

import ConsolePackage.ConsoleFrame;
import InterfacePackage.MainFrame;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main{
    
    static ConsoleFrame console;
    static MainFrame mainFrame;
        
    public static void main(String[] args) {
        
        boolean showConsole = false;
        
        if(args.length > 0){
                if(args[0].equals("-c")){
                showConsole = true;
            }
        }
        
        //Starting main interface
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainFrame = new MainFrame();
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setVisible(true);
            }
        });
        
        
        //Starting console if wanted
        if(showConsole){
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    console = new ConsoleFrame();
                    console.setLocation(100, 100);
                    console.setVisible(true);
                }
            });
            while(console == null){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
