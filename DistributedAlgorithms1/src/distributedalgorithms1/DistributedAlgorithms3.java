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
 *  In this program there is implemented the total global ordering message, you have to run "DistributedAlghorithm2" and "DistributedAlgorithm3" first and than "DistributedAlghorithm1".
 *  A process start the the broadcasting by sending a message to all the process, a process which get the message send to all it's neighbor the ACK and wait for the ACK from
 *  all it's neighbor, a message is considered delivered only when all the ACK for it has been received from all the neighbor.
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

        Naming.rebind("rmi://localhost:1103/p3", new Process(3));

        System.out.println("Process p3 ready ");
    }

}
