/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conwaysgame;

/**
 *
 * @author Utilizador
 */
public class TreeInt {
    
    private int x;
    private int y;
    private int state;

    public TreeInt(int x, int y, int state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public int getX() {
        return x;
    }

    public int getState() {
        return state;
    }

    public int getY() {
        return y;
    }
    
    
    
}
