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

        //Collect ALL the input message recevived form a process (in order of receviving) and check if the status process and channel are correctly collected
        //- you can't have a  process status not empty if you haven't delivered a message(recevived message + all the ack for it)\
        //- the channel status is a collection of message for a given channel between two #(marker), so if there is any status channel check if that comunication happen between two process and between two markers
        
        // This test can be easly implemented buy some for and if by doing a positional analisys on the input message recevived.
    
    
    }
}
