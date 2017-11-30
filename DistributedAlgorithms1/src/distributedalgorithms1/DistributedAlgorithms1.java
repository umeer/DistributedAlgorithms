package distributedalgorithms1;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 *  In this program there is implemented the total global ordering message, you have to run "DistributedAlghorithm2" and "DistributedAlgorithm3" first and than this file here.
 *  A process start the the broadcasting by sending a message to all the process, a process which get the message send to all it's neighbor the ACK and wait for the ACK from
 *  all it's neighbor, a message is considered delivered only when all the ACK for it has been received from all the neighbor.
 * 
 */


public class DistributedAlgorithms1 {

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException, InterruptedException {

        //Setting up RMI
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1101);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.setProperty(("java.rmi.server.hostname"), "127.0.0.1");
//        
//        if (System.getSecurityManager() == null) {
//            System.setSecurityManager(new RMISecurityManager());
//        }

        //Setting up network of process
        Naming.rebind("rmi://localhost:1101/p1", new Process(1));
        //Naming.rebind("rmi://localhost:1100/p2", new Process("p2"));
        //Naming.rebind("rmi://localhost:1100/p3", new Process("p3"));

        List<ProcessInterface> processList = new ArrayList<ProcessInterface>();
        processList.add((ProcessInterface) Naming.lookup("rmi://localhost:1101/p1"));
        processList.add((ProcessInterface) Naming.lookup("rmi://localhost:1102/p2"));
        processList.add((ProcessInterface) Naming.lookup("rmi://localhost:1103/p3"));

        for (ProcessInterface p : processList) {
            p.setNeighbor(processList);
        }

        //Start comunication
        processList.get(0).broadcast("Ciao");
        processList.get(0).broadcast("Ciao2");
        processList.get(0).broadcast("Ciao3");

        Thread.sleep(3000);

        List<String> deliveredMessage = processList.get(0).getDeliveredMessage();

        for (String message : deliveredMessage) {
            System.out.println("I recevive: " + message);
        }
        System.out.println(" main end ");

    }
}
