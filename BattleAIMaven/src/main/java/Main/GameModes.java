/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author Dragos-Alexandru
 */
public enum GameModes {
    SINGLEPLAYER(0), MULTIPLAYER_HOST(1),
    MULTIPLAYER_CLIENT(2);
    int value;
    private GameModes(int value){
        this.value = value;
    }
}
