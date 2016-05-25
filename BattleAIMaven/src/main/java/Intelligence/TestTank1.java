package Intelligence;

import Console.ConsoleFrame;

/*
    Test class
*/
public class TestTank1 extends IntelligenceTemplate {
    @Override
    public void run(){
        for(int i = 0; i<5; i++)
            moveFront();
        rotate(3);
    }
    
    @Override
    public void hitArenaWall(){
        ConsoleFrame.sendMessage("TestTank1", "Oy vey, hit arena wall!!"+System.currentTimeMillis());
    }
}
