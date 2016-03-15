package ServerMainPackage;

import ConsolePackage.ConsoleFrame;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {

    static ConsoleFrame console;
    
    public static void main(String[] args) {
        
        boolean showConsole = false;
        
        if(args.length > 0){
            if(args[0].equals("-c")){
                showConsole = true;
            }
        }
        
        if(showConsole){
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    console = new ConsoleFrame();
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
