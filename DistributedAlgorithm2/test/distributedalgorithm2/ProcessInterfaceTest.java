/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedalgorithm2;

import java.rmi.RemoteException;
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
public class ProcessInterfaceTest {
    
    public ProcessInterfaceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of startStatusLogSequence method, of class ProcessInterface.
     */
    @Test
    public void testStartStatusLogSequence() throws Exception {
        System.out.println("startStatusLogSequence");
        ProcessInterface instance = new ProcessInterfaceImpl();
        instance.startStatusLogSequence();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of receviveMessage method, of class ProcessInterface.
     */
    @Test
    public void testReceviveMessage() throws Exception {
        System.out.println("receviveMessage");
        String message = "";
        int id_sender = 0;
        int timestamp = 0;
        ProcessInterface instance = new ProcessInterfaceImpl();
        instance.receviveMessage(message, id_sender, timestamp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of receviveAck method, of class ProcessInterface.
     */
    @Test
    public void testReceviveAck() throws Exception {
        System.out.println("receviveAck");
        String message = "";
        int id_sender = 0;
        ProcessInterface instance = new ProcessInterfaceImpl();
        instance.receviveAck(message, id_sender);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNeighbor method, of class ProcessInterface.
     */
    @Test
    public void testSetNeighbor() throws Exception {
        System.out.println("setNeighbor");
        List<ProcessInterface> list = null;
        ProcessInterface instance = new ProcessInterfaceImpl();
        instance.setNeighbor(list);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of broadcast method, of class ProcessInterface.
     */
    @Test
    public void testBroadcast() throws Exception {
        System.out.println("broadcast");
        String message = "";
        ProcessInterface instance = new ProcessInterfaceImpl();
        instance.broadcast(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class ProcessInterface.
     */
    @Test
    public void testGetID() throws Exception {
        System.out.println("getID");
        ProcessInterface instance = new ProcessInterfaceImpl();
        int expResult = 0;
        int result = instance.getID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDeliveredMessage method, of class ProcessInterface.
     */
    @Test
    public void testGetDeliveredMessage() throws Exception {
        System.out.println("getDeliveredMessage");
        ProcessInterface instance = new ProcessInterfaceImpl();
        List<String> expResult = null;
        List<String> result = instance.getDeliveredMessage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAckNumber method, of class ProcessInterface.
     */
    @Test
    public void testGetAckNumber() throws Exception {
        System.out.println("getAckNumber");
        String message = "";
        ProcessInterface instance = new ProcessInterfaceImpl();
        int expResult = 0;
        int result = instance.getAckNumber(message);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of test method, of class ProcessInterface.
     */
    @Test
    public void testTest() throws Exception {
        System.out.println("test");
        ProcessInterface instance = new ProcessInterfaceImpl();
        instance.test();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class ProcessInterfaceImpl implements ProcessInterface {

        public void startStatusLogSequence() throws RemoteException {
        }

        public void receviveMessage(String message, int id_sender, int timestamp) throws RemoteException {
        }

        public void receviveAck(String message, int id_sender) throws RemoteException {
        }

        public void setNeighbor(List<ProcessInterface> list) throws RemoteException {
        }

        public void broadcast(String message) throws RemoteException {
        }

        public int getID() throws RemoteException {
            return 0;
        }

        public List<String> getDeliveredMessage() throws RemoteException {
            return null;
        }

        public int getAckNumber(String message) throws RemoteException {
            return 0;
        }

        public void test() throws RemoteException {
        }
    }
    
}
