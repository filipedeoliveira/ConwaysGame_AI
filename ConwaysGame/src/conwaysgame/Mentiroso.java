/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conwaysgame;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;

/**
 *
 * @author Utilizador
 */
public class Mentiroso extends Agent {

    protected void setup() {
        this.addBehaviour(new ReceiveMessages());

    }

    public class ReceiveMessages extends CyclicBehaviour {

        public void action() {
            ACLMessage msg = receive();
            String answer = "";
            if (msg != null) {
                System.out.println("entrei"+msg.getContent());
                //System.out.println("recebi mensagem");
                if (msg.getContent().length() > 0) {
                    String[] parts = msg.getContent().split(",");
                    int i = 0;

                    for (i = 0; i < parts.length; i = i + 3) {
                        int x = Integer.parseInt(parts[i]);
                        int y = Integer.parseInt(parts[i + 1]);
                        int vizinhos = Integer.parseInt(parts[i + 2]);
                        //System.out.println(x+","+y+","+vizinhos);
                        if (vizinhos == 3) {
                            answer = answer + x + "," + y + "," + "2,";
                        }
                        if(vizinhos==4){
                            answer = answer + x + "," + y + "," + "1,";
                        
                        }
                    }
                    if (answer.length() > 0) {
                        answer = answer.substring(0, answer.length() - 1);
                    }
                }
                AID receiver = new AID();
                ACLMessage msg_answer = new ACLMessage(ACLMessage.INFORM);
                receiver.setLocalName("Campo");
                msg_answer.addReceiver(receiver);
                msg_answer.setConversationId("" + System.currentTimeMillis());
                msg_answer.setContent(answer);
                myAgent.send(msg_answer);
            }

            block();
        }
    }
}
