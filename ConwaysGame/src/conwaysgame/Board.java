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
import java.util.Random;

/**
 *
 * @author Utilizador
 */
public class Board extends Agent {

    private static int ROWS = 5;
    private static int COLUMNS = 5;

    private int[][] field;
    private int[][] next_field;

    private String states;

    protected void setup() {

        //CRIAR E INICIAR CAMPO
        field = new int[ROWS][COLUMNS];
        next_field = new int[ROWS][COLUMNS];

        for (int x = 0; x < ROWS; x++) {
            for (int y = 0; y < COLUMNS; y++) {
                field[x][y] = 0;
                next_field[x][y] = 0;
            }
        }
        states = "";
        this.addBehaviour(new ReceiveMessages());
        //this.addBehaviour(new ReceiveAliveWarns(this));
    }

    public class ReceiveMessages extends CyclicBehaviour {

        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                switch (msg.getContent()) {
                    case "NewGen"://mensagem a indicar nova geração
                        //System.out.println("Recebi new gen");
                        myAgent.addBehaviour(new CalcNextGen());
                        break;
                    default:// atualização de estado
                        receive_state_list(msg.getContent());
                        if (msg.getSender().getLocalName().equals("Caotico")) {
                            myAgent.addBehaviour(new SendStateList());
                        }
                        break;
                }
            }
            else
                block();
        }

        private void receive_state_list(String content) {
            if (content.length() > 0) {
                String[] parts = content.split(",");
                int i = 0;
                for (i = 0; i < parts.length; i = i + 3) {
                    int x = Integer.parseInt(parts[i]);
                    int y = Integer.parseInt(parts[i + 1]);
                    int state = Integer.parseInt(parts[i + 2]);
                    field[x][y] = state;
                    states = states + "," + x + "," + y + "," + state;
                }
            }
        }
    }

    public class CalcNextGen extends OneShotBehaviour {

        public void action() {

            int x, y, neighbours;

            //CASOS ESPECIAIS DA MATRIZ
            //CANTOS
            top_left_corner_value(0, 0);
            top_right_corner_value(COLUMNS - 1, 0);
            bottom_left_corner_value(0, ROWS - 1);
            bottom_right_corner_value(COLUMNS - 1, ROWS - 1);

            //BORDAS
            top_edge();
            bottom_edge();
            left_edge();
            right_edge();

            //RESTO DA MATRIZ
            for (x = 1; x < COLUMNS - 1; x++) {
                for (y = 1; y < ROWS - 1; y++) {
                    normal_next_value(x, y);
                }
            }

            //Atualizar e enviar o campo atual
            //update_and_send_field();
            update_and_send_actives();

            block();
        }

        private void top_edge() {
            //BORDA SUPERIOR y=0
            int x, y, neighbours;
            y = 0;
            for (x = 1; x < COLUMNS - 1; x++) {
                neighbours = 0;
                if (field[x + 1][y] > 0) {
                    neighbours++;
                }
                if (field[x + 1][y + 1] > 0) {
                    neighbours++;
                }
                if (field[x][y + 1] > 0) {
                    neighbours++;
                }
                if (field[x - 1][y + 1] > 0) {
                    neighbours++;
                }
                if (field[x - 1][y] > 0) {
                    neighbours++;
                }
                calc_next_value(x, y, neighbours);
            }
        }

        private void bottom_edge() {
            int x, y, neighbours;
            //borda inferior y=ROWS-1
            y = ROWS - 1;
            for (x = 1; x < COLUMNS - 1; x++) {
                neighbours = 0;
                if (field[x + 1][y] > 0) {
                    neighbours++;
                }
                if (field[x + 1][y - 1] > 0) {
                    neighbours++;
                }
                if (field[x][y - 1] > 0) {
                    neighbours++;
                }
                if (field[x - 1][y - 1] > 0) {
                    neighbours++;
                }
                if (field[x - 1][y] > 0) {
                    neighbours++;
                }
                calc_next_value(x, y, neighbours);
            }
        }

        private void left_edge() {
            int x, y, neighbours;
            //borda esquerda x=0
            x = 0;
            for (y = 1; y < ROWS - 1; y++) {
                neighbours = 0;
                if (field[x + 1][y] > 0) {
                    neighbours++;
                }
                if (field[x + 1][y - 1] > 0) {
                    neighbours++;
                }
                if (field[x][y - 1] > 0) {
                    neighbours++;
                }
                if (field[x + 1][y + 1] > 0) {
                    neighbours++;
                }
                if (field[x][y + 1] > 0) {
                    neighbours++;
                }
                calc_next_value(x, y, neighbours);
            }
        }

        private void right_edge() {
            int x, y, neighbours;
            //borda direita x=COLUMNS-1
            x = COLUMNS - 1;
            for (y = 1; y < ROWS - 1; y++) {
                neighbours = 0;
                if (field[x - 1][y] > 0) {
                    neighbours++;
                }
                if (field[x - 1][y - 1] > 0) {
                    neighbours++;
                }
                if (field[x][y - 1] > 0) {
                    neighbours++;
                }
                if (field[x - 1][y + 1] > 0) {
                    neighbours++;
                }
                if (field[x][y + 1] > 0) {
                    neighbours++;
                }
                calc_next_value(x, y, neighbours);
            }
        }

        private void normal_next_value(int x, int y) {
            int neighbours;
            neighbours = 0;
            if (field[x + 1][y] > 0) {
                neighbours++;
            }
            if (field[x + 1][y + 1] > 0) {
                neighbours++;
            }
            if (field[x][y + 1] > 0) {
                neighbours++;
            }
            if (field[x - 1][y + 1] > 0) {
                neighbours++;
            }
            if (field[x - 1][y] > 0) {
                neighbours++;
            }
            if (field[x - 1][y - 1] > 0) {
                neighbours++;
            }
            if (field[x][y - 1] > 0) {
                neighbours++;
            }
            if (field[x + 1][y - 1] > 0) {
                neighbours++;
            }
            calc_next_value(x, y, neighbours);

        }

        private void update_and_send_field() {
            int x, y;
            String states = "";
            AID receiver = new AID();
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            receiver.setLocalName("Interface");
            msg.addReceiver(receiver);

            //msg.setContent("Alive");
            msg.setConversationId("" + System.currentTimeMillis());
            for (x = 0; x < COLUMNS; x++) {
                for (y = 0; y < ROWS; y++) {
                    field[x][y] = next_field[x][y];
                    states = states + x + "," + y + "," + field[x][y] + ",";
                    //System.out.println(x + "," + y + "," + field[x][y]);
                    next_field[x][y] = 0;
                }
            }
            if(states.length()>0){
            states = states.substring(0, states.length() - 1);
            }
            msg.setContent(states);
            //System.out.println(states);
            myAgent.send(msg);
            //System.out.println("terminei");

        }

        private void update_and_send_actives() {
            int x, y;
            String actives = "";
            states = "";
            AID receiver = new AID();
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            receiver.setLocalName("Caotico");
            msg.addReceiver(receiver);

            //msg.setContent("Alive");
            msg.setConversationId("" + System.currentTimeMillis());
            for (x = 0; x < COLUMNS; x++) {
                for (y = 0; y < ROWS; y++) {
                    field[x][y] = next_field[x][y];
                    states = states + x + "," + y + "," + field[x][y] + ",";
                    if (field[x][y] > 0) {
                        actives = actives + x + "," + y + "," + field[x][y] + ",";
                    }
                    //System.out.println(x + "," + y + "," + field[x][y]);
                    next_field[x][y] = 0;
                }
            }
            if (actives.length() > 0) {
                actives = actives.substring(0, actives.length() - 1);
            }
            if (states.length() > 0) {
                states = states.substring(0, states.length() - 1);
            }
            msg.setContent(actives);
            //System.out.println(states);
            myAgent.send(msg);
            //System.out.println("terminei");

        }

        //método que calcula o valor da p´roxima geração com base nos vizinhos
        private void calc_next_value(int x, int y, int neighbours) {
            if (neighbours == 3) {
                System.out.println(x + "," + y);
            }
            if (neighbours < 2 && field[x][y] > 0) {
                next_field[x][y] = 0;
            }
            if (neighbours > 3 && field[x][y] > 0) {
                next_field[x][y] = 0;
            }
            if (neighbours == 3 && field[x][y] == 0) {
                next_field[x][y] = 1;
            }
            if ((neighbours == 2 || neighbours == 3) && field[x][y] > 0) {
                next_field[x][y] = 1;
            }

            //return 0;
        }

        private void top_left_corner_value(int x, int y) {
            int neighbours = 0;
            if (field[x + 1][y] > 0) {
                neighbours++;
            }
            if (field[x + 1][y + 1] > 0) {
                neighbours++;
            }
            if (field[x][y + 1] > 0) {
                neighbours++;
            }
            calc_next_value(x, y, neighbours);
            //return 0;
        }

        private void top_right_corner_value(int x, int y) {
            int neighbours = 0;
            if (field[x][y + 1] > 0) {
                neighbours++;
            }
            if (field[x - 1][y + 1] > 0) {
                neighbours++;
            }
            if (field[x - 1][y] > 0) {
                neighbours++;
            }
            calc_next_value(x, y, neighbours);
        }

        private void bottom_left_corner_value(int x, int y) {
            int neighbours = 0;
            if (field[x + 1][y] > 0) {
                neighbours++;
            }
            if (field[x + 1][y - 1] > 0) {
                neighbours++;
            }
            if (field[x][y - 1] > 0) {
                neighbours++;
            }
            calc_next_value(x, y, neighbours);
        }

        private void bottom_right_corner_value(int x, int y) {
            int neighbours = 0;
            if (field[x - 1][y] > 0) {
                neighbours++;
            }
            if (field[x - 1][y - 1] > 0) {
                neighbours++;
            }
            if (field[x][y - 1] > 0) {
                neighbours++;
            }
            calc_next_value(x, y, neighbours);
        }

    }

    public class SendStateList extends OneShotBehaviour {

        public void action() {

            AID receiver = new AID();
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            receiver.setLocalName("Interface");
            msg.addReceiver(receiver);

            //msg.setContent("Alive");
            msg.setConversationId("" + System.currentTimeMillis());
            //states = states.substring(0, states.length() - 1);
            msg.setContent(states);
            //System.out.println(states);
            myAgent.send(msg);
            //System.out.println("terminei");

        }
    }
}
