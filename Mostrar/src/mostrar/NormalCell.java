package mostrar;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class NormalCell extends Agent {

    private int current_state;
    private int next_state;
    private int neighbours;
    private SimpleBehaviour s1;
    private SimpleBehaviour s2;
    private int part1;
    private int part2;
    private boolean finished = false;
    private boolean finished2=false;

    protected void setup() {
        this.current_state = 0;
        this.next_state = 0;
        this.neighbours = 0;
        String[] nameparts = this.getLocalName().split(",");
        part1 = Integer.parseInt(nameparts[0]);
        part2 = Integer.parseInt(nameparts[1]);
        s1 = new WarnAlive();
        s2=new SendState();
        this.addBehaviour(new ReceiveMessages());
    }

    public class WarnAlive extends SimpleBehaviour {

        public int onEnd() {
            return 0;
        }

        public void action() {
            //System.out.println("entrei aqui");
            if (current_state == 1) {
                
                //finished2=false;
                AID receiver;
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.setContent("Alive");
                //enviar mensagem para cada uma das celulas da periferia

                receiver = new AID();
                msg.setConversationId("" + System.currentTimeMillis());
                receiver.setLocalName((part1 - 1) + "," + (part2 - 1));
                msg.addReceiver(receiver);
                //send(msg);

                receiver = new AID();
                msg.setConversationId("" + System.currentTimeMillis());
                receiver.setLocalName((part1 - 1) + "," + (part2));
                msg.addReceiver(receiver);
                //send(msg);

                receiver = new AID();
                msg.setConversationId("" + System.currentTimeMillis());
                receiver.setLocalName((part1 - 1) + "," + (part2 + 1));
                msg.addReceiver(receiver);
                //send(msg);

                receiver = new AID();
                msg.setConversationId("" + System.currentTimeMillis());
                receiver.setLocalName((part1) + "," + (part2 - 1));
                msg.addReceiver(receiver);
                //send(msg);

                receiver = new AID();
                msg.setConversationId("" + System.currentTimeMillis());
                receiver.setLocalName((part1) + "," + (part2 + 1));
                msg.addReceiver(receiver);
                //send(msg);

                receiver = new AID();
                msg.setConversationId("" + System.currentTimeMillis());
                receiver.setLocalName((part1 + 1) + "," + (part2 - 1));
                msg.addReceiver(receiver);
                //send(msg);

                receiver = new AID();
                msg.setConversationId("" + System.currentTimeMillis());
                receiver.setLocalName((part1 + 1) + "," + (part2));
                msg.addReceiver(receiver);
                //send(msg);

                receiver = new AID();
                msg.setConversationId("" + System.currentTimeMillis());
                receiver.setLocalName((part1 + 1) + "," + (part2 + 1));
                msg.addReceiver(receiver);
                send(msg);

            }
            finished = true;

            block();
        }

        @Override
        public boolean done() {
            return finished;
        }

    }
    
    public class SendState extends SimpleBehaviour {

        public int onEnd() {
            return 0;
        }

        public void action() {
            //if (next_state != current_state) {
            
                AID receiver = new AID();
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.setConversationId("" + System.currentTimeMillis()+part1+","+part2);
                receiver.setLocalName("Coordenator");
                msg.addReceiver(receiver);
                current_state = next_state;
                msg.setContent(part1 + "," + part2 + "," + current_state);
                System.out.println(part1+","+part2+"           prepara para enviar");
                send(msg);
                System.out.println(part1+","+part2+"           enviar");
            //}
        
            //finished=false;
            finished2 = true;

            block();
        }

        @Override
        public boolean done() {
            return finished2;
        }



    }


    public class ReceiveMessages extends CyclicBehaviour {

        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                //System.out.println(myAgent.getLocalName()+"                 "+msg.getContent());
                switch (msg.getContent()) {
                    case "Alive":
                        /*if (myAgent.getLocalName().equals("2,3")) {
                            System.out.println(msg.getSender().getName());
                        }*/
                        neighbours++;
                        break;
                    case "NewGen":
                        /*if (myAgent.getLocalName().equals("2,3")) {
                            System.out.println("NewGen");
                        }
                        if (myAgent.getLocalName().equals("2,3")) {
                            System.out.println(myAgent.getLocalName() + "           " + neighbours + "                antes");
                        }*/
                        /*if (myAgent.getLocalName().equals("2,3")) {
                            System.out.println(myAgent.getLocalName() + "           " + neighbours + "                depois");
                        }*/
                        if (neighbours < 2 && current_state == 1) {
                            next_state = 0;
                        }
                        if (neighbours > 3 && current_state == 1) {
                            next_state = 0;
                        }
                        if (neighbours == 3 && current_state == 0) {
                            next_state = 1;
                        }
                        neighbours = 0;
                        
                        
                        s2.action();//answerBack();
                        s1.action();
                        //s1.action();

                        break;
                    default:
                        current_state = Integer.parseInt(msg.getContent());
                        s1.action();
                        break;
                }
                
            }
            block();

        }

        private void answerBack() {
            if (next_state != current_state) {
                AID receiver = new AID();
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.setConversationId("" + System.currentTimeMillis());
                receiver.setLocalName("Coordenator");
                msg.addReceiver(receiver);
                current_state = next_state;
                msg.setContent(part1 + "," + part2 + "," + current_state);
                send(msg);
            }
        }

    }
}
