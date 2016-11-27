

package conwaysgame;

import jade.core.Agent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.Random;

public class NormalCell extends Agent{

    private int state;
    private int neighbours;
    

    protected void setup(){
        this.state=0;
        this.neighbours=0;
        //como controlar comportamentos conforme estado???
        //isto dos ifs funciona? penso que nao porque pelo que percebi com o addbehaviour esta acção
        // é adicionada permanentemente ao agente e vai tar a fazela enquanto tiver vivo mas nao tenho a certeza
        this.addBehaviour(new WarnAlive());
        this.addBehaviour(new ReceiveMessages());
        /*if(state==1)
            addBehaviour(new WarnAlive());
        addBehaviour(new ReceiveAliveWarns());*/
    }
    
    public class WarnAlive extends SimpleBehaviour {

    private boolean finished = false;
    
    public int onEnd(){
           return 0;
       }


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
        
        finished=true;

        block();
    }

    @Override
    public boolean done() {
        return finished;
    }
    
    
}
    
    public class ReceiveMessages extends CyclicBehaviour {

    public void action(){
        ACLMessage msg= receive();
        if(msg!=null){
            switch(msg.getContent()){
                case "Alive":
                    neighbours++;
                    break;
                case "NewGen":
                    if(neighbours<2 && state==1)state=0;
                    if(neighbours>3 && state==1)state=0;
                    if(neighbours==3 && state==0)state=1;
                    neighbours=0;
                    break;
                default:
            }
        block();
    }

}
    
    
    
    
}
}
