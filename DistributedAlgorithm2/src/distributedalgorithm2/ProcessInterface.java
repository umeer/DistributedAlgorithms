package distributedalgorithm2;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Thinkpad
 */
public interface ProcessInterface extends Remote {

    public void startStatusLogSequence() throws RemoteException;

    public void receviveMessage(String message, int id_sender, int timestamp) throws RemoteException;

    public void receviveAck(String message, int id_sender) throws RemoteException;

    public void setNeighbor(List<ProcessInterface> list) throws RemoteException;

    public void broadcast(String message) throws RemoteException;

    //Testing ones
    public int getID() throws RemoteException;

    public List<String> getDeliveredMessage() throws RemoteException;

    public int getAckNumber(String message) throws RemoteException;

    public void test() throws RemoteException;

}
