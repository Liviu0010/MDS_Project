package Main;

import Console.ConsoleFrame;
import Constants.MasterServerConstants;
import Interface.MainFrame;
import Server.ServerDispatcher;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main implements ApplicationState{
    
    public static ConsoleFrame console;
    static MainFrame mainFrame;
        
    public static void main(String[] args){
        System.out.println("---------------------------------------");
        System.out.println("            Started BattleAI           ");
        System.out.println("---------------------------------------\n");
        ConsoleFrame.showConsole = false;
        ConsoleFrame.sendMessage(Main.class.getName(),"Deciding how to start application...");
        final boolean showConsole;
        final boolean isServer;

        if(args.length > 0){
            switch (args[0]) {
                case MASTER_SERVER_CONSOLE:
                {
                    ConsoleFrame.showConsole = true;
                    ConsoleFrame.sendMessage(Main.class.getName(),"Start as server with visible console");
                    isServer = true;
                    showConsole = true;
                }
                break;
                case MASTER_SERVER_NO_CONSOLE:
                {
                    ConsoleFrame.showConsole = false;
                    ConsoleFrame.sendMessage(Main.class.getName(),"Start as server without visible console");
                    isServer = true;
                    showConsole = false;
                }break;
                case CLIENT_CONSOLE:
                {
                    ConsoleFrame.showConsole = true;
                    ConsoleFrame.sendMessage(Main.class.getName(),"Start as client with visible console");
                    isServer = false;
                    showConsole = true;
                }
                break;
                default:
                {
                    ConsoleFrame.showConsole = false;
                    ConsoleFrame.sendMessage(Main.class.getName(),"Start as client without visible console");
                    isServer = false;
                    showConsole = false;
                }
            }
        }else{
            ConsoleFrame.showConsole = false;
            ConsoleFrame.sendMessage(Main.class.getName(),"Start as client without visible console");
            showConsole = false;
            isServer = false;
        }
        
        //Starting main interface if is not server
        if(!isServer){
            EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    ConsoleFrame.sendMessage(Main.class.getName(),"Initializing MainFrame");
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
                    ConsoleFrame.sendMessage(Main.class.getName(),"Initializing ConsoleFrame");
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
            ConsoleFrame.sendMessage(Main.class.getCanonicalName(), "Initializing Database");
            Database.DatabaseHandler.getInstance();
            ConsoleFrame.sendMessage(Main.class.getCanonicalName(), "Database ready");
            ConsoleFrame.sendMessage(Main.class.getName(),"Initializing ServerDispatcher");
            ServerDispatcher.getInstance().start(MasterServerConstants.PORT, true, null);
            ConsoleFrame.sendMessage(Main.class.getName(),"ServerDispatcher ready");
        }
    }
}
