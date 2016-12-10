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
public class Caotico extends Agent {

    private ArrayList<TreeInt> estaveis;

    private static int ROWS = 5;
    private static int COLUMNS = 5;

    private TreeInt[][] activos;
    //private int[][] estaveis;

    protected void setup() {
        estaveis = new ArrayList<>();
        activos = new TreeInt[ROWS][COLUMNS];

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
                        System.out.println(x+","+y+"    state:"+activos[x][y].getState()+"    ger:"+activos[x][y].getGer_stopped()+"           moved:"+activos[x][y].getStabilized());
                    }

                    //board.next_gen(states);
                    //states = new ArrayList<>();
                }
                 myAgent.addBehaviour(new CheckStability());
            }
           
            block();
        }
    }

    public class CheckStability extends OneShotBehaviour {

        public void action() {
            String changes = "";
            AID receiver = new AID();
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            receiver.setLocalName("Campo");
            msg.addReceiver(receiver);

            //msg.setContent("Alive");
            msg.setConversationId("" + System.currentTimeMillis());
            for (int x = 0; x < COLUMNS; x++) {
                for (int y = 0; y < ROWS; y++) {
                    if (activos[x][y].getGer_stopped() == 4) {
                        changes = calc_change(x, y, changes);
                        activos[x][y] = new TreeInt(x, y, 2);
                    } else if (activos[x][y].getState() > 0) {
                        activos[x][y].moved();
                    }

                }
            }
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
