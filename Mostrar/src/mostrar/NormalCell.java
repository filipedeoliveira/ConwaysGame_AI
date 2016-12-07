package mostrar;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
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

    public class WarnAlive extends OneShotBehaviour {

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

       
    }
    
    public class SendState extends OneShotBehaviour {

        public int onEnd() {
            return 0;
        }

        public void action() {
            /*if(part1==2 && part2==2){
                System.out.println(""+neighbours);
                System.out.println(""+current_state);
                System.out.println(""+next_state);
            }*/
                AID receiver = new AID();
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.setConversationId("" + System.currentTimeMillis()+part1+","+part2);
                receiver.setLocalName("Coordenator");
                msg.addReceiver(receiver);
                current_state = next_state;
                if(part1==2 && part2==2){
                System.out.println(current_state);
                }
                msg.setContent(part1 + "," + part2 + "," + current_state);
                send(msg);
                
            block();
        }

        



    }


    public class ReceiveMessages extends CyclicBehaviour {

        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                
                switch (msg.getContent()) {
                    case "Alive":
                        
                        neighbours++;
                        break;
                    case "NewGen":
                        /*if(myAgent.getLocalName().equals("2,2")){
                            System.out.println("Nova");
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
                        
                        
                        //s2.action();//answerBack();
                        myAgent.addBehaviour(new SendState());
                        myAgent.addBehaviour(new WarnAlive());
                        
                        //s1.action();

                        break;
                    default:
                        current_state = Integer.parseInt(msg.getContent());
                        next_state=current_state;
                        myAgent.addBehaviour(new WarnAlive());
                        break;
                }
                
            }
            block();

        }

        

    }
}
