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
