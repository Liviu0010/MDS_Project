package MainPackage;

import InterfacePackage.MainFrame;
import java.awt.EventQueue;


public class Main implements Runnable{

	public static void main(String[] args) {
            Main main = new Main();
            EventQueue.invokeLater(main);
	}

    @Override
    public void run() {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}
