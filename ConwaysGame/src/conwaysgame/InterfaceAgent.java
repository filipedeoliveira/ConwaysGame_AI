/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Utilizador
 */
public class InterfaceAgent extends GuiAgent{
    protected Frame board;
    
    private ArrayList<TreeInt> states;
    
    protected void setup(){
        board= new Frame(this);
        states=new ArrayList<>();
        this.addBehaviour(new ReceiveMessages());
        //board.setVisible(true);
    }
    
    protected void onGuiEvent(GuiEvent ev){
        int command=ev.getType();
        if(command==1){
            String content=(String)ev.getSource();
            String[] nameparts=content.split(",");
            int x=Integer.parseInt(nameparts[0]);
            int y=Integer.parseInt(nameparts[1]);
            int z=Integer.parseInt(nameparts[2]);
            AID receiver=new AID();
            receiver.setLocalName("Campo");
            long time =System.currentTimeMillis();
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setContent(content);
            msg.setConversationId(""+time);
            msg.addReceiver(receiver);
            send(msg);
        }
        if(command==2){
            String content=(String)ev.getSource();
            AID receiver=new AID();
            receiver.setLocalName("Campo");
            long time =System.currentTimeMillis();
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setContent("NewGen");
            msg.setConversationId(""+time);
            msg.addReceiver(receiver);
            send(msg);
        }
    }
    
    
    public class ReceiveMessages extends CyclicBehaviour {

        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                System.out.println("recebi mensagem");
                String[] parts = msg.getContent().split(",");
                int i=0;
                for(i=0;i<parts.length;i=i+3){
                    //System.out.println("entrei");
                    TreeInt cell= new TreeInt(Integer.parseInt(parts[i]), Integer.parseInt(parts[i+1]), Integer.parseInt(parts[i+2]));
                    states.add(cell);
                }
                board.next_gen(states);
                states=new ArrayList<>();
            }
            block();
        }
    }
}
