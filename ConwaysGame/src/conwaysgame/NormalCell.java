

package conwaysgame;

import java.core.Agent;

public class NormalCell extends Agent{

    protected int state=0;
    protected int neighbours=0;

    protected void setup(){
        //como controlar comportamentos conforme estado???
        //isto dos ifs funciona? penso que nao porque pelo que percebi com o addbehaviour esta acção
        // é adicionada permanentemente ao agente e vai tar a fazela enquanto tiver vivo mas nao tenho a certeza
        if(state==1)
            addBehaviour(new WarnAlive());
        addBehaviour(new ReceiveAliveWarns());
    }
}
