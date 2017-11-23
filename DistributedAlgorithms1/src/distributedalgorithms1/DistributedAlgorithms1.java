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
 *
 * @author Thinkpad
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
        //processList.get(2).broadcast("Ciao2");
        System.out.println(" main end ");

    }
}
