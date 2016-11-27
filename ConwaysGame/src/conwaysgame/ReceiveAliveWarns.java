package conwaysgame;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;



public class WarnAlive extends CyclicBehaviour {

    public void action(){
        ACLMessage msg= receive();
        if(msg!=null){
            if(msg.getContent()=="NewGen"){
                if(myAgent.neighbours<2 && myAgent.state=1)myAgent.state=0;
                if(myAgent.neighbours>3 && myAgent.state=1)myAgent.state=0;
                if(myAgent.neighbours=3 && myAgent.state=0)myAgent.state=1;
                myAgent.neighbours=0;
            }
            else{
                myAgent.neighbours++;
            }
        }
        block();
    }

}
