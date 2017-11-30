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
     * Test of startStatusLogSequence method, of class Component.
     */
    @Test
    public void testStartStatusLogSequence() throws Exception {
        System.out.println("startStatusLogSequence");
        Component instance = null;
        instance.startStatusLogSequence();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of receviveMessage method, of class Component.
     */
    @Test
    public void testReceviveMessage() throws Exception {
        System.out.println("receviveMessage");
        String message = "";
        int id_sender = 0;
        int timestamp = 0;
        Component instance = null;
        instance.receviveMessage(message, id_sender, timestamp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of receviveAck method, of class Component.
     */
    @Test
    public void testReceviveAck() throws Exception {
        System.out.println("receviveAck");
        String message = "";
        int id_sender = 0;
        Component instance = null;
        instance.receviveAck(message, id_sender);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAckNumber method, of class Component.
     */
    @Test
    public void testGetAckNumber() throws Exception {
        System.out.println("getAckNumber");
        String message = "";
        Component instance = null;
        int expResult = 0;
        int result = instance.getAckNumber(message);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNeighbor method, of class Component.
     */
    @Test
    public void testSetNeighbor() throws Exception {
        System.out.println("setNeighbor");
        List<ProcessInterface> list = null;
        Component instance = null;
        instance.setNeighbor(list);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of broadcast method, of class Component.
     */
    @Test
    public void testBroadcast() throws Exception {
        System.out.println("broadcast");
        String message = "";
        Component instance = null;
        instance.broadcast(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of broadcastNotToMe method, of class Component.
     */
    @Test
    public void testBroadcastNotToMe() throws Exception {
        System.out.println("broadcastNotToMe");
        String message = "";
        Component instance = null;
        instance.broadcastNotToMe(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Component.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Component instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of test method, of class Component.
     */
    @Test
    public void testTest() {
        System.out.println("test");
        Component instance = null;
        instance.test();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class Component.
     */
    @Test
    public void testGetID() throws Exception {
        System.out.println("getID");
        Component instance = null;
        int expResult = 0;
        int result = instance.getID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDeliveredMessage method, of class Component.
     */
    @Test
    public void testGetDeliveredMessage() throws Exception {
        System.out.println("getDeliveredMessage");
        Component instance = null;
        List<String> expResult = null;
        List<String> result = instance.getDeliveredMessage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
