package Main;

import Console.ConsoleFrame;
import Interface.MainFrame;
import Server.ServerDispatcher;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main implements ApplicationState{
    
    public static ConsoleFrame console;
    static MainFrame mainFrame;
        
    public static void main(String[] args){
        
        final boolean showConsole;
        final boolean isServer;

        if(args.length > 0){
            switch (args[0]) {
                case MASTER_SERVER_CONSOLE:
                {
                    isServer = true;
                    showConsole = true;
                }
                break;
                case MASTER_SERVER_NO_CONSOLE:{
                    isServer = true;
                    showConsole = false;
                }break;
                case CLIENT_CONSOLE:
                {
                    isServer = false;
                    showConsole = true;
                }
                break;
                default:{
                    isServer = false;
                    showConsole = false;
                }
            }
        }else{
            showConsole = false;
            isServer = false;
        }
        
        //Starting main interface if is not server
        if(!isServer){
            EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    mainFrame = MainFrame.getInstance();
                    mainFrame.setLocationRelativeTo(null);
                    mainFrame.setVisible(true);
                }
            });
        }
        
        //Starting console if wanted
        if(showConsole){
            EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    console = ConsoleFrame.getInstance();
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
        
        if(isServer){
            ServerDispatcher.getInstance().start();
        }
    }
}
