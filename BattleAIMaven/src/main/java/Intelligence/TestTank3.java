/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Intelligence;

/**
 *
 * @author Liviu
 */
public class TestTank3 extends Intelligence.IntelligenceTemplate{
    @Override
    public void run(){
        rotateCannonLeft();
        rotateCannonLeft();
        rotateRight();
    }
}
