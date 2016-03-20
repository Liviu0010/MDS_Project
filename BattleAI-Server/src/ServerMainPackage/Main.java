package ServerMainPackage;

import ConsolePackage.ConsoleFrame;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    
    public static ConsoleFrame console;
    
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
            
            Main main = new Main();
            main.start();
        }
    }
    
    public void start(){
        Thread th = new Thread(new Speaker("A", console));
        Thread th2 = new Thread(new Speaker("B", console));
        
        th.start();
        th2.start();
        
        
    }
    
    class Speaker implements Runnable{
        
        ConsoleFrame console;
        String name;
        public Speaker(String name, ConsoleFrame console){
            this.console = console;
            this.name = name;
        }
        
        @Override
        public void run() {
            for(int i =0;i<30;i++){
                Main.console.sendMessage(name, i+"");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
}
