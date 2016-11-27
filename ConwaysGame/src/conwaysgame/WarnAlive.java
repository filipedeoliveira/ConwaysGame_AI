package conwaysgame;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class WarnAlive extends SimpleBehaviour {

    boolean finished = false;


    public void action() {
        AID receiver = new AID();
        String[] nameparts=myAgent.getLocalName().split(",");
        int part1=Integer.parseInt(nameparts[0]);
        int part2=Integer.parseInt(nameparts[1]);

        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

        msg.setContent("Alive");
        msg.setConversationId("" + System.currentTimeMillis());//para que serve isto. aind aé preciso???


        //enviar mensagem para cada uma das celulas da periferia
        receiver.setLocalName((part1-1)+","+(part2-1));
        msg.addReceiver(receiver);
        myAgent.send(msg);

        receiver.setLocalName((part1-1)+","+(part2));
        msg.addReceiver(receiver);
        myAgent.send(msg);

        receiver.setLocalName((part1-1)+","+(part2+1));
        msg.addReceiver(receiver);
        myAgent.send(msg);

        receiver.setLocalName((part1)+","+(part2-1));
        msg.addReceiver(receiver);
        myAgent.send(msg);

        receiver.setLocalName((part1)+","+(part2+1));
        msg.addReceiver(receiver);
        myAgent.send(msg);

        receiver.setLocalName((part1+1)+","+(part2-1));
        msg.addReceiver(receiver);
        myAgent.send(msg);

        receiver.setLocalName((part1+1)+","+(part2));
        msg.addReceiver(receiver);
        myAgent.send(msg);

        receiver.setLocalName((part1+1)+","+(part2+1));
        msg.addReceiver(receiver);
        myAgent.send(msg);

        block();
    }

    @Override
    public boolean done() {
        //verificar se isto ainda é preciso
        return finished;
    }
}
