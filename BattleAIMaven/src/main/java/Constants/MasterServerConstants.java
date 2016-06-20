package Constants;

import javax.swing.JOptionPane;

/**
 *
 * @author Dragos-Alexandru
 */
public interface MasterServerConstants {
    String MASTER_USERNAME = "BattleAIMS";
    String IP = getIP();
    int PORT = 60010;
    long PACKET_DELAY = 3000; // expressed in milliseconds
    
    public static String getIP(){
        if(System.getenv().get("TRAVIS") != null){
            return "localhost";
        }else{
            return JOptionPane.showInputDialog(null, "Master Server IP: ",
                    "MS - IP", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
}
