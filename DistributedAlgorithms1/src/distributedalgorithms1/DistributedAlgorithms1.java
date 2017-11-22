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
//Ciao
    
    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException, InterruptedException {
        
        //Setting up RMI
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1100);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.setProperty(("java.rmi.server.hostname"), "127.0.0.1");
        
        
        //Setting up network of process
        Naming.rebind("rmi://localhost:1100/p1", new Process("p1"));
        Naming.rebind("rmi://localhost:1100/p2", new Process("p2"));
        Naming.rebind("rmi://localhost:1100/p3", new Process("p3"));
        
        List<ProcessInterface> list = new ArrayList<ProcessInterface>();
        list.add((ProcessInterface) Naming.lookup("rmi://localhost:1100/p1"));
        list.add((ProcessInterface) Naming.lookup("rmi://localhost:1100/p2"));
        list.add((ProcessInterface) Naming.lookup("rmi://localhost:1100/p3"));
        
        for (ProcessInterface p : list) {
            p.setNeighbor(list);
        }
        
        
        
        //Start comunication
        list.get(1).broadcast("Ciao");
        list.get(2).broadcast("ciao2");
        System.out.println(" main end");
        
    }  
}
