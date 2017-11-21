/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedalgorithms1;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Thinkpad
 */
public interface ProcessInterface extends Remote {

    public void receviveMessage(String message, long timestamp) throws RemoteException;

    public void setNeighbor( List<ProcessInterface> list) throws RemoteException;
    
    public void broadcast() throws RemoteException;

    public void test() throws RemoteException;

}
