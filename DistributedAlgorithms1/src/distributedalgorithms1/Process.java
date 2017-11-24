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
    private int timestamp = 0;
    private List<ProcessInterface> neighborList = new ArrayList<ProcessInterface>();
    private List<MessageBuffer> bufferMessage = new ArrayList<MessageBuffer>();
    private List<String> bufferAck = new ArrayList<String>();
    private List<String> deliveredMessage = new ArrayList<String>();

    public Process(int id) throws RemoteException {
        this.ID = id;
    }

    @Override
    public void receviveMessage(String message, int id_sender, int timestamp) throws RemoteException {
        System.out.println("I'm: " + ID + " and i just recevived: " + message + " with timestamp: " + timestamp + " form: " + id_sender);

        synchronized (this) {
            //Put the message in a waiting list
            bufferMessage.add(new MessageBuffer(message, id_sender, timestamp));
        }

        //Send ack to all process
        Random rand = new Random();
        long timestampAck = System.currentTimeMillis() / 1000l;
        for (ProcessInterface p : neighborList) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(rand.nextInt(200) + 10);
                        p.receviveAck(message, ID);

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
    public void receviveAck(String message, int id_sender) throws RemoteException {
        System.out.println("Ack receviced by: " + ID + " and sended by: " + id_sender);

        synchronized (this) {
            bufferAck.add(message);
        }
        checkMessageDelivery();
    }

    private void checkMessageDelivery() throws RemoteException {
        if (bufferMessage.size() > 0) {
            //Find the min absolute timestamp in the buffer
            int minTimestamp = bufferMessage.get(0).timestamp;
            for (MessageBuffer message : bufferMessage) {
                if (message.timestamp < minTimestamp) {
                    minTimestamp = message.timestamp;
                }
            }

            //Check if there are multiple message with the min timestamp
            int countEqualTimestamp = 0;
            int minProcId = 0;
            for (MessageBuffer message : bufferMessage) {
                if (message.timestamp == minTimestamp) {
                    if (minProcId == 0) {
                        minProcId = message.id_sender;
                    } else if (message.id_sender < minProcId) {
                        minProcId = message.id_sender;
                    }
                    countEqualTimestamp++;
                }
            }

            for (MessageBuffer message : bufferMessage) {
                if (message.timestamp == minTimestamp && message.id_sender == minProcId) {
                    if (getAckNumber(message.message) == neighborList.size()) {
                        //Deliver message and cancel buffers
                        System.out.println("Message: " + message.message + " delivered by: " + getID());
                        deliveredMessage.add(message.message);
                        cancelMessageLog(message.message);
                        if (checkIfThereAreOtherMessageReadyToDelivery()) {
                            checkMessageDelivery();
                        }
                        break;
                    }
                }
            }

        }
    }

    private boolean checkIfThereAreOtherMessageReadyToDelivery() throws RemoteException {
        boolean other = false;
        if (bufferMessage.size() > 0) {
            int minTimestamp = bufferMessage.get(0).timestamp;
            for (MessageBuffer message : bufferMessage) {
                if (message.timestamp < minTimestamp) {
                    minTimestamp = message.timestamp;
                }
            }

            for (MessageBuffer message : bufferMessage) {
                if (getAckNumber(message.message) == neighborList.size() && message.timestamp == minTimestamp) {
                    other = true;
                }
            }

        }
        return other;
    }

    private void cancelMessageLog(String message) {
        for (int i = 0; i < bufferMessage.size(); i++) {
            if (bufferMessage.get(i).message.compareTo(message) == 0) {
                bufferMessage.remove(i);
            }
        }

        for (int i = 0; i < bufferAck.size(); i++) {
            if (bufferAck.get(i).compareTo(message) == 0) {
                bufferAck.remove(i);
            }
        }
    }

    @Override
    public int getAckNumber(String message) throws RemoteException {
        int counter = 0;
        for (String ack : bufferAck) {
            if (ack.compareTo(message) == 0) {
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
        timestamp++;

        for (ProcessInterface p : neighborList) {
            p.receviveMessage(message, getID(), timestamp);
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
    public List<String> getDeliveredMessage() throws RemoteException {
        return deliveredMessage;
    }

}
