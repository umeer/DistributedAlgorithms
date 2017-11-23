/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedalgorithms1;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 *
 * @author Thinkpad
 */
public class DistributedAlgorithm2 {

    /**
     * @param args the command line arguments
     */
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
