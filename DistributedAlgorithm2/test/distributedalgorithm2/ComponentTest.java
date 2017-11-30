/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedalgorithm2;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author umohammad
 */
public class ComponentTest {

    public ComponentTest() {
    }
@Test
    public void testCreation() throws Exception {
        
        distributedalgorithm2.Component p1 = new distributedalgorithm2.Component(1);
        assertTrue(p1.getID() == 1);
        
    }

    @Test
    public void testBroadcast() throws Exception {
//        //Setting up RMI
//        try {
//            java.rmi.registry.LocateRegistry.createRegistry(1101);
//            java.rmi.registry.LocateRegistry.createRegistry(1102);
//            java.rmi.registry.LocateRegistry.createRegistry(1103);
//
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//        System.setProperty(("java.rmi.server.hostname"), "127.0.0.1");
//
//        //Setting up network of process
//        Naming.rebind("rmi://localhost:1101/p1", new distributedalgorithms1.Process(1));
//        Naming.rebind("rmi://localhost:1102/p2", new distributedalgorithms1.Process(2));
//        Naming.rebind("rmi://localhost:1103/p3", new distributedalgorithms1.Process(3));
//
//        List<ProcessInterface> processList = new ArrayList<ProcessInterface>();
//        processList.add((ProcessInterface) Naming.lookup("rmi://localhost:1101/p1"));
//        processList.add((ProcessInterface) Naming.lookup("rmi://localhost:1102/p2"));
//        processList.add((ProcessInterface) Naming.lookup("rmi://localhost:1103/p3"));
//
//        for (ProcessInterface p : processList) {
//            p.setNeighbor(processList);
//        }
//
//        //Start comunication
//        processList.get(0).broadcast("Ciao");
//        processList.get(0).broadcast("Ciao2");
//        processList.get(0).broadcast("Ciao3");
//
//        Thread.sleep(3000);
//
//        List<String> deliveredMessage = new ArrayList<String>();
//        deliveredMessage = processList.get(0).getDeliveredMessage();
//
//        assertTrue(deliveredMessage.get(0).compareTo("Ciao") == 0);
//        assertTrue(deliveredMessage.get(1).compareTo("Ciao2") == 0);
//        assertTrue(deliveredMessage.get(2).compareTo("Ciao3") == 0);

    }
}
