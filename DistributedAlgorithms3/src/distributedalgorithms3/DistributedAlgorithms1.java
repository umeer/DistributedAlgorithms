package distributedalgorithms3;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * In this program there is implemented the Afek and Gafni async election
 * system, you have to run "DistributedAlghorithm1" and "DistributedAlgorithm2"
 * first and than "DistributedAlghorithm3". You can select how many process
 * there are in the network form the "DistributedAlghorithm3" file. The process
 * can be candidate or ordinary, the ordinary process wont compete in the
 * election race. You can set this value during the process creation.
 *
 */
public class DistributedAlgorithms1 {

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        //Setting up RMI
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1101);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.setProperty(("java.rmi.server.hostname"), "127.0.0.1");

        Naming.rebind("rmi://localhost:1101/p1", new Process(1, TypeOfProcess.CANDIDATE));

        System.out.println("Process p1 ready ");
    }

}
