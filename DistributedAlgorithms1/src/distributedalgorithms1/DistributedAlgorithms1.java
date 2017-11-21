/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedalgorithms1;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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

        try {
            java.rmi.registry.LocateRegistry.createRegistry(1100);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.setProperty(("java.rmi.server.hostname"), "127.0.0.1");

        Remote r1 = new Process();
        Naming.rebind("rmi://localhost:1100/p1", r1);

        Remote r2 = new Process();
        Naming.rebind("rmi://localhost:1100/p1", r2);

        Remote r3 = new Process();
        Naming.rebind("rmi://localhost:1100/p1", r3);

        ProcessInterface p1 = (ProcessInterface) Naming.lookup("rmi://localhost:1100/p1");
        ProcessInterface p2 = (ProcessInterface) Naming.lookup("rmi://localhost:1100/p2");
        ProcessInterface p3 = (ProcessInterface) Naming.lookup("rmi://localhost:1100/p3");

        List<ProcessInterface> list = new ArrayList<ProcessInterface>();
        list.add(p1);
        list.add(p2);
        list.add(p3);

    }
    
}
