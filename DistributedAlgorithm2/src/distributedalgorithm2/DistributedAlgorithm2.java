/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedalgorithm2;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thinkpad
 */
public class DistributedAlgorithm2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException, InterruptedException {
        final int NUMBER_OF_COMPONENT = 3;

        //Setting up RMI
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1101);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.setProperty(("java.rmi.server.hostname"), "127.0.0.1");

        List<ProcessInterface> processList = new ArrayList<ProcessInterface>();
        for (int i = 1; i < NUMBER_OF_COMPONENT + 1; i++) {
            //Creation of process
            Naming.rebind("rmi://localhost:1101/" + (i), new Component(i));

            //Linking of process
            processList.add((ProcessInterface) Naming.lookup("rmi://localhost:1101/" + (i)));
        }

//        //Setting up network of process
//        Naming.rebind("rmi://localhost:1101/p1", new Component(1));
//        //Naming.rebind("rmi://localhost:1100/p2", new Process("p2"));
//        //Naming.rebind("rmi://localhost:1100/p3", new Process("p3"));
//
//        List<ProcessInterface> processList = new ArrayList<ProcessInterface>();
//        processList.add((ProcessInterface) Naming.lookup("rmi://localhost:1101/p1"));
//        processList.add((ProcessInterface) Naming.lookup("rmi://localhost:1102/p2"));
//        processList.add((ProcessInterface) Naming.lookup("rmi://localhost:1103/p3"));
        for (ProcessInterface p : processList) {
            p.setNeighbor(processList);
            //System.out.println("Hi i'm process: " + p.getID());
        }

//        //Start comunication
//        processList.get(0).broadcast("Ciao");
//        processList.get(0).broadcast("Ciao2");
//        processList.get(0).broadcast("Ciao3");
        //Thread.sleep(3000);
        //List<String> deliveredMessage = processList.get(0).getDeliveredMessage();
//        for (String message : deliveredMessage) {
//            System.out.println("I recevive: " + message);
//        }
        Random rand = new Random();

        new Thread(new Runnable() {
            public void run() {
                try {

                    processList.get(1).broadcast("Ciao mamma");
                } catch (RemoteException ex) {
                    Logger.getLogger(DistributedAlgorithm2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
        
        

        new Thread(new Runnable() {
            public void run() {
                try {

                    Thread.sleep(rand.nextInt(200) + 10);
                    processList.get(0).startStatusLogSequence();

                } catch (RemoteException ex) {
                    Logger.getLogger(DistributedAlgorithm2.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DistributedAlgorithm2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();

        System.out.println(" main end ");
        //System.exit(0);

    }

}
