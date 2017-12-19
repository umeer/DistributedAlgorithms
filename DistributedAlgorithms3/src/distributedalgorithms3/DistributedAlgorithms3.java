package distributedalgorithms3;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * In this program there is implemented the Afek and Gafni async election
 * system, you have to run "DistributedAlghorithm1" and "DistributedAlgorithm2"
 * first and than "DistributedAlghorithm3". You can select how many process
 * there are in the network form the "DistributedAlghorithm3" file. The process
 * can be candidate or ordinary, the ordinary process wont compete in the
 * election race. You can set this value during the process creation.
 *
 */
public class DistributedAlgorithms3 {

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException, InterruptedException {
        //Setting up RMI
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1103);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.setProperty(("java.rmi.server.hostname"), "127.0.0.1");

        Naming.rebind("rmi://localhost:1103/p3", new Process(3, TypeOfProcess.CANDIDATE));

        List<ProcessInterface> processList = new ArrayList<ProcessInterface>();

        try {
            processList.add((ProcessInterface) Naming.lookup("rmi://localhost:1101/p1"));
            processList.add((ProcessInterface) Naming.lookup("rmi://localhost:1102/p2"));
            processList.add((ProcessInterface) Naming.lookup("rmi://localhost:1103/p3"));

        } catch (Exception e) {
            System.out.println(" Error: Some process are offline, make sure to run ahead the programs DisrbutedAlgorithms1 and DisrbutedAlgorithms2");
            System.exit(0);
        }

        for (ProcessInterface p : processList) {
            p.setNeighbor(processList);
        }

        processList.get(0).startElection();
        processList.get(2).startElection();

        System.out.println(" main end ");
    }

}
