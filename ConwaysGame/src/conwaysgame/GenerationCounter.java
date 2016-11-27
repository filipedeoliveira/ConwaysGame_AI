package conwaysgame;

import jade.core.Agent;
//Talvez venha a não existir
public class GenerationCounter extends Agent{

    protected int gen=1;

    protected void setup(){
        addBehaviour(new CountdownBehaviour());
    }
}
