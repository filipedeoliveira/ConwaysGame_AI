/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mostrar;

import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

/**
 *
 * @author Filipe Oliveira
 */
public class ConwaysGame {

    /**
     * @param args the command line arguments
     */
     Runtime rt;
     ContainerController container;
     
     private static int ROWS=5;
     private static int COLLUMS=5;

     public void initMainContainer(String host, String port) {
         this.rt = Runtime.instance();
         Profile prof = new ProfileImpl();
         prof.setParameter(Profile.MAIN_HOST, host);
         prof.setParameter(Profile.MAIN_PORT, port);
         prof.setParameter(Profile.MAIN, "true");
         prof.setParameter(Profile.GUI, "true");
         this.container = rt.createMainContainer(prof);
         rt.setCloseVM(true);
     }

     public void startAgentInPlatform(String name, String classpath) {
         try {
             AgentController ac = container.createNewAgent(name, classpath, new Object[0]);
             ac.start();
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

     public static void main(String[] args) {
         // TODO code application logic here
         ConwaysGame mc = new ConwaysGame();
         mc.initMainContainer("127.0.0.1", "1099");
         int i,j;
         mc.startAgentInPlatform("Coordenator", "mostrar.InterfaceAgent");
         //mc.startAgentInPlatform("GenCounter", "conwaysgame.GenerationCounter");
         for(i=0;i<ROWS;i++){
             for(j=0;j<COLLUMS;j++){
                 mc.startAgentInPlatform(i+","+j, "mostrar.NormalCell");
             }
         }
     }

}
