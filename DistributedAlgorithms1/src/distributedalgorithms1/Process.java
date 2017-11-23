package distributedalgorithms1;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Process extends UnicastRemoteObject implements ProcessInterface {

    private final int ID;
    private List<ProcessInterface> neighborList = new ArrayList<ProcessInterface>();
    private List<MessageBuffer> bufferMessage = new ArrayList<MessageBuffer>();
    private List<MessageBuffer> bufferAck = new ArrayList<MessageBuffer>();
    private String deliveredMessage = "";

    public Process(int id) throws RemoteException {
        this.ID = id;
    }

    @Override
    public void receviveMessage(String message, long timestamp) throws RemoteException {
        System.out.println("I'm: " + this.toString() + " and i just recevived: " + message + " with timestamp: " + timestamp);

        synchronized (this) {
            //Put the message in a waiting list
            bufferMessage.add(new MessageBuffer(message, timestamp));
        }

        //Send ack to all process
        long timestampAck = System.currentTimeMillis() / 1000l;
        for (ProcessInterface p : neighborList) {
            p.receviveAck(message, timestampAck);
        }
    }

    @Override
    public void receviveAck(String message, long timestamp) throws RemoteException {
        System.out.println("Ack receviced by: " + this.toString());

        synchronized (this) {
            bufferAck.add(new MessageBuffer(message, timestamp));
        }
        checkMessageDelivery();
    }

    private void checkMessageDelivery() throws RemoteException {
        long maxTimestamp = 0;

        for (MessageBuffer message : bufferMessage) {
            if (message.timestamp > maxTimestamp) {
                maxTimestamp = message.timestamp;
            }
        }

        for (int i = 0; i < bufferMessage.size(); i++) {
            //All the ack has been recived and is the oldest message, if yes deliver the message
            if (bufferMessage.get(i).timestamp == maxTimestamp) {
                if (getAckNumber(bufferMessage.get(i).message) == neighborList.size()) {
                    System.out.println("Message delivered by: " + this.toString());
                    deliveredMessage = bufferMessage.get(i).message;
                    bufferMessage.remove(i);
                    //Extra TODO remove the ack
                }
            }
        }
    }

    @Override
    public int getAckNumber(String message) throws RemoteException {
        int counter = 0;
        for (MessageBuffer ack : bufferAck) {
            if (ack.message.compareTo(message) == 0) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public void setNeighbor(List<ProcessInterface> list) throws RemoteException {
        this.neighborList = list;
    }

    @Override
    public void broadcast(String message) throws RemoteException {
        System.out.println("Message broadcasted by: " + this.toString());
        Random rand = new Random();

        long timestamp = System.currentTimeMillis() / 1000l;
        for (ProcessInterface p : neighborList) {

            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(rand.nextInt(200) + 10);
                        p.receviveMessage(message, timestamp);
                    } catch (RemoteException ex) {
                        Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();

        }
    }

    @Override
    public String toString() {
        return ID + "";
    }

    //TESTING METHODS
    @Override
    public void test() {
        System.out.println("Hi i'm: " + this.toString());
    }

    @Override
    public int getID() throws RemoteException {
        return ID;
    }

    @Override
    public String getDeliveredMessage() throws RemoteException {
        return deliveredMessage;
    }

}
