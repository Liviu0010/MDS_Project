package Constants;

import javax.swing.JOptionPane;

/**
 *
 * @author Dragos-Alexandru
 */
public interface MasterServerConstants {
    String MASTER_USERNAME = "BattleAIMS";
    String IP = JOptionPane.showInputDialog(null, "Master Server IP: ", "MS - IP", JOptionPane.INFORMATION_MESSAGE);
    int PORT = 60010;
    long PACKET_DELAY = 3000; // expressed in milliseconds
}
