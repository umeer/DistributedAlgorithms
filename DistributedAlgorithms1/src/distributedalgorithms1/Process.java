/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedalgorithms1;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Process extends UnicastRemoteObject implements ProcessInterface {

    private List<ProcessInterface> list = new ArrayList<ProcessInterface>();

    public Process() throws RemoteException {

    }

    @Override
    public void receviveMessage(String message, long timestamp) {
        System.out.print("Sono: " + this.toString() + " e ho ricevuto: " + message + " at: " + timestamp);

    }

    @Override
    public void setNeighbor(List<ProcessInterface> list) throws RemoteException {
        this.list = list;
    }
    
    @Override
    public void broadcast() throws RemoteException {

        
    }
    

    @Override
    public void test() {
        System.out.print("Ciao come ti chiami io sono: " + this.toString());
    }

//    private static Process instance = null;
//
//    protected Process()  {
//        // Exists only to defeat instantiation.
//    }
//
//    public static Process getInstance() {
//        if (instance == null) {
//            instance = new Process();
//        }
//        return instance;
//    }

    
}
