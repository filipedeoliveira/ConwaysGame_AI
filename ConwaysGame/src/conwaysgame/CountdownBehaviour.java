package conwaysgame;

import jade.core.behaviours.TickerBehaviour;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.core.Agent;

public class CountdownBehaviour extends TickerBehaviour {
    
    GenerationCounter agent;
    int gen = 0;

    public CountdownBehaviour(GenerationCounter a, long period) {
        super(a, period);
        agent=a;
    }

    protected void onTick() {
        AID receiver = new AID();
        int i = 0, j = 0;

        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setContent("NewGen");

        for (i = 0; i < 50; i++) {
            for (j = 0; j < 50; j++) {
                receiver.setLocalName(i + "," + j);
                msg.addReceiver(receiver);
                myAgent.send(msg);
            }
        }
        //myAgent.gen++;
        agent.gen++;
    }

}
