/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conwaysgame;

/**
 *
 * @author bass
 */

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
        
public class MobileAgent extends Agent{
    
    private static int ROWS = 5;
    private static int COLUMNS = 5;
    
    private int[] location;
    private int lives;
    private boolean born;

    private TreeInt[][] activos;
    //private int[][] estaveis;

    protected void setup() {
        activos = new TreeInt[ROWS][COLUMNS];
        
        location=new int[2];
        location[0]=1;
        location[1]=1;
        lives=3;
        born=false;

        for (int x = 0; x < COLUMNS; x++) {
            for (int y = 0; y < ROWS; y++) {
                activos[x][y] = new TreeInt(x, y, 0);
            }
        }
        this.addBehaviour(new ReceiveMessages());

    }

    public class ReceiveMessages extends CyclicBehaviour {

        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                //System.out.println("recebi mensagem");
                if (msg.getContent().length() > 0) {
                    String[] parts = msg.getContent().split(",");
                    int i = 0;

                    for (i = 0; i < parts.length; i = i + 3) {
                        int x = Integer.parseInt(parts[i]);
                        int y = Integer.parseInt(parts[i + 1]);
                        int state = Integer.parseInt(parts[i + 2]);
                        activos[x][y].increment_gen(state);
                        //System.out.println(x+","+y+"    state:"+activos[x][y].getState()+"    ger:"+activos[x][y].getGer_stopped()+"           moved:"+activos[x][y].getStabilized());
                    }

                    //board.next_gen(states);
                    //states = new ArrayList<>();
                }
                 myAgent.addBehaviour(new CheckSurroundings());
            }
           
            block();
        }
    }

    public class CheckSurroundings extends OneShotBehaviour {
        
        int[] jump=new int[2];

        public void action() {
            
            String changes = "";
            AID receiver = new AID();
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            receiver.setLocalName("Campo");
            msg.addReceiver(receiver);
            System.out.println(changes);
            //msg.setContent("Alive");
            msg.setConversationId("" + System.currentTimeMillis());
            
            if(activos[location[0]][location[1]].getState()==3){
                /*if((neighbours<2 || neighbours>3) && lives>0){
                    changes=calc_change(location[0], location[1], jump[0], jump[1]);
                    activos[]
                }*/
            }else{
                if(activos[location[0]][location[1]].getState()==0 && !born){
                    changes=location[0]+","+location[1]+","+"3,";
                    born=true;
                    activos[location[0]][location[1]]=new TreeInt(location[0],location[1],3);
                }
            }
            
            System.out.println(changes);
            
            
            if (changes.length() > 0) {
                changes = changes.substring(0, changes.length() - 1);
            }
            msg.setContent(changes);
            //System.out.println(states);
            myAgent.send(msg);
            block();
        }

        private String calc_change(int x, int y, String changes) {
            if (x + 1 < COLUMNS && activos[x + 1][y].getState() == 0) {
                return changes + "" + (x + 1) + "," + y + ",2,";
            }
            if (x + 1 < COLUMNS && y + 1 < ROWS && activos[x + 1][y + 1].getState() == 0) {
                return changes + "" + (x + 1) + "," + (y + 1) + ",2,";
            }
            if (y + 1 < ROWS && activos[x][y + 1].getState() == 0) {
                return changes + "" + x + "," + (y + 1) + ",2,";
            }
            if (y + 1 < ROWS && x - 1 > 0 && activos[x - 1][y + 1].getState() == 0) {
                return changes + "" + (x - 1) + "," + (y + 1) + ",2,";
            }
            if (x - 1 > 0 && activos[x - 1][y].getState() == 0) {
                return changes + "" + (x - 1) + "," + y + ",2,";
            }
            if (x - 1 > 0 && y - 1 > 0 && activos[x - 1][y - 1].getState() == 0) {
                return changes + "" + (x - 1) + "," + (y - 1) + ",2,";
            }
            if (y - 1 > 0 && activos[x][y - 1].getState() == 0) {
                return changes + "" + x + "," + (y - 1) + ",2,";
            }
            if (y - 1 > 0 && x + 1 < COLUMNS && activos[x + 1][y - 1].getState() == 0) {
                return changes + "" + (x + 1) + "," + (y - 1) + ",2,";
            }
            return changes;
        }
    }
    
}
