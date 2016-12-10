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
    
    private int stabilized;
    private int ger_stopped;

    public TreeInt(int x, int y, int state) {
        this.x = x;
        this.y = y;
        this.state = state;
        this.stabilized=5;
        this.ger_stopped=0;
    }

   
    
    

    public int getGer_stopped() {
        return ger_stopped;
    }

    public int getStabilized() {
        return stabilized;
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

    public void increment_gen(int state) {
        this.state=state;
        ger_stopped++;
        stabilized=5;
    }
    
    public void moved(){
        stabilized--;
        if(stabilized==0){
            ger_stopped=0;
        }
    }
    
    
    
}
