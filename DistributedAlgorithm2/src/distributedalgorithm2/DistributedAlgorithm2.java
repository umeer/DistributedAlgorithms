
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
     * In this program there are multiple process(NUMBER_OF_COMPONET) that are connected each other, a process starts a
     * broadcasting a message (total ordering message) and another one starts the status collection algh Chandy-Lamport.
     * The process status are all the message correctly delivered (all the ACK have been received) and the channel status 
     * is the the sequence of message on a channel transmitted between two marker (the ack are considered as message by the algh)
     */


public class DistributedAlgorithm2 {

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException, InterruptedException {
       
        Random rand = new Random();
        final int NUMBER_OF_COMPONENT = 30;

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

        //Acknolege each process of the existance of other process
        for (ProcessInterface p : processList) {
            p.setNeighbor(processList);
        }

        
        
        
        
        
        
        
        

        //Broadcast a message
        new Thread(new Runnable() {
            public void run() {
                try {

                    processList.get(1).broadcast("Ciao mamma");
                } catch (RemoteException ex) {
                    Logger.getLogger(DistributedAlgorithm2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
        
        
        
        

        //Run a status collection procedure
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
        //System.exit(0); //Can be used the system is async

    }

}
