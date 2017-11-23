/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import distributedalgorithms1.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thinkpad
 */
public class Tests {

    public Tests() {

    }

    @Before
    public void setUp() throws RemoteException, NotBoundException, MalformedURLException {

    }

    @Test
    public void testCreation() throws Exception {
        distributedalgorithms1.Process p1 = new distributedalgorithms1.Process("p1");
        assertTrue(p1.toString().compareTo("p1") == 0);
    }

    @Test
    public void testBroadcast() throws Exception {
        //Setting up RMI
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1100);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.setProperty(("java.rmi.server.hostname"), "127.0.0.1");

        //Setting up network of process
        Naming.rebind("rmi://localhost:1100/p1", new distributedalgorithms1.Process("p1"));
        Naming.rebind("rmi://localhost:1100/p2", new distributedalgorithms1.Process("p2"));
        Naming.rebind("rmi://localhost:1100/p3", new distributedalgorithms1.Process("p3"));

        List<ProcessInterface> processList = new ArrayList<ProcessInterface>();
        processList.add((ProcessInterface) Naming.lookup("rmi://localhost:1100/p1"));
        processList.add((ProcessInterface) Naming.lookup("rmi://localhost:1100/p2"));
        processList.add((ProcessInterface) Naming.lookup("rmi://localhost:1100/p3"));

        for (ProcessInterface p : processList) {
            p.setNeighbor(processList);
        }

        assertTrue(processList.get(0).getID().compareTo("p1") == 0);

        String testMessage = "helloworld";
        
        processList.get(0).broadcast(testMessage);
        
        Thread.sleep(1500);
        
        int numAck = processList.get(1).getAckNumber(testMessage);
        assertTrue(numAck == processList.size());
        
        assertTrue(processList.get(0).getDeliveredMessage().compareTo(testMessage) == 0);

        

    }
}
