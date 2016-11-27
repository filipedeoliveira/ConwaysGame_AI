package conwaysgame;

import jade.core.Agent;
import conwaysgame.CountdownBehaviour;

//Talvez venha a não existir
public class GenerationCounter extends Agent{

    protected int gen=1;

    protected void setup(){
        addBehaviour(new CountdownBehaviour());
    }
}
