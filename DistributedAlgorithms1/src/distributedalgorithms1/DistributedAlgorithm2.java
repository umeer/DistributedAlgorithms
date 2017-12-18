package distributedalgorithms1;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 *  In this program there is implemented the total global ordering message, you have to run "DistributedAlghorithm2" and "DistributedAlgorithm3" first and than "DistributedAlghorithm1".
 *  A process start the the broadcasting by sending a message to all the process, a process which get the message send to all it's neighbor the ACK and wait for the ACK from
 *  all it's neighbor, a message is considered delivered only when all the ACK for it has been received from all the neighbor.
 * 
 */

public class DistributedAlgorithm2 {

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        //Setting up RMI
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1102);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.setProperty(("java.rmi.server.hostname"), "127.0.0.1");

        Naming.rebind("rmi://localhost:1102/p2", new Process(2));

        System.out.println("Process p2 ready ");
    }

}
