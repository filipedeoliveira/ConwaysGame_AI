/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conwaysgame;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ReceiveAliveWarns extends CyclicBehaviour {
    
    NormalCell agent;
    
    public ReceiveAliveWarns(NormalCell a){
        super(a);
        agent=a;
    }

    public void action(){
        ACLMessage msg= myAgent.receive();
        if(msg!=null){
            if(msg.getContent()=="NewGen"){
                if(agent.neighbours<2 && agent.state==1)agent.state=0;
                if(agent.neighbours>3 && agent.state==1)agent.state=0;
                if(agent.neighbours==3 && agent.state==0)agent.state=1;
                agent.neighbours=0;
            }
            else{
                agent.neighbours++;
            }
        }
        block();
    }

}
