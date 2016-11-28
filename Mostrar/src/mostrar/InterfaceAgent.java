/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mostrar;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;

/**
 *
 * @author Utilizador
 */
public class InterfaceAgent extends GuiAgent {

    protected Frame board;
    private int ROWS = 5;
    private int COLLUMS = 5;

    private int gen = 0;
    private int period = 2000;
    private TickerBehaviour t1;
    private ArrayList<TreeInt> states;
    private int resp;

    protected void setup() {
        board = new Frame(this);
        states = new ArrayList<>();
        resp = 0;
        //t1=new CountdownBehaviour(this,period);
        //this.addBehaviour(new CountdownBehaviour(this,period));
        //t1 = new CountdownBehaviour(this, period);
        //this.addBehaviour(new ReceiveStates());
        //board.setVisible(true);
    }

    protected void onGuiEvent(GuiEvent ev) {
        int command = ev.getType();
        if (command == 1) {
            String content = (String) ev.getSource();
            String[] nameparts = content.split(",");
            int x = Integer.parseInt(nameparts[0]);
            int y = Integer.parseInt(nameparts[1]);
            int state = Integer.parseInt(nameparts[2]);
            AID receiver = new AID();
            receiver.setLocalName(x + "," + y);
            long time = System.currentTimeMillis();
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setContent("" + state);
            msg.setConversationId("" + time);
            msg.addReceiver(receiver);
            send(msg);
        }
        if (command == 2) {
            String content = (String) ev.getSource();
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setContent("NewGen");
            int i = 0, j = 0;
            for (i = 0; i < ROWS; i++) {
                for (j = 0; j < COLLUMS; j++) {
                    AID receiver = new AID();
                    receiver.setLocalName(i + "," + j);
                    msg.addReceiver(receiver);

                }
            }
            send(msg);
            CyclicBehaviour t2 = new ReceiveStates();
            this.addBehaviour(t2);
        }

    }

    public class ReceiveStates extends CyclicBehaviour {

        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                String[] nameparts = msg.getContent().split(",");
                int x = Integer.parseInt(nameparts[0]);
                int y = Integer.parseInt(nameparts[1]);
                int state = Integer.parseInt(nameparts[2]);
                TreeInt t = new TreeInt(x, y, state);
                resp++;
                
                System.out.println(msg.getSender()+"                   "+resp);
                //board.next_gen_value(x, y, state);
            }else{
                System.out.println("BOOM");
            }
            

            block();

        }

    }

    public class CountdownBehaviour extends TickerBehaviour {

        public CountdownBehaviour(Agent a, long period) {
            super(a, period);
        }

        protected void onTick() {
            //System.out.println("TIck");
            int i = 0, j = 0;
            for (i = 0; i < 10; i++) {
                for (j = 0; j < 10; j++) {
                    AID receiver = new AID();
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.setContent("NewGen");
                    receiver.setLocalName(i + "," + j);
                    msg.addReceiver(receiver);
                    myAgent.send(msg);
                }
            }
            //myAgent.gen++;
            gen++;
        }

    }

}
