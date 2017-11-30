/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedalgorithm2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Component extends UnicastRemoteObject implements ProcessInterface {

    private final int ID;
    private int timestamp = 0;

    private List<ProcessInterface> neighborList = new ArrayList<ProcessInterface>();

    private List<MessageBuffer> bufferMessage = new ArrayList<MessageBuffer>();
    private List<String> bufferAck = new ArrayList<String>();
    private List<String> deliveredMessage = new ArrayList<String>();

    //Varables used by status collection system
    private boolean amIRecordingTheChannels = false;
    private boolean isFirstMarker = true; // Do i never recevived a marker, will the next market the first one?
    private List<Integer> isMarkerRecevivedFrom = new ArrayList<Integer>(); // Here i collect all the marker recevived from all the process, if a precess is here i stop collecting it's channel status
    private List<String> stateProcess = new ArrayList<String>(); //List of all messages delivered 
    private List<MessageBuffer> stateChannel = new ArrayList<MessageBuffer>(); //List of all transactions for each comunications channels, i can use this to make a list of channel state

    public Component(int id) throws RemoteException {
        this.ID = id;
    }

    @Override
    public void startStatusLogSequence() throws RemoteException {
        System.out.println("I'm: " + ID + " and I'm starting the status collection system");

        //Save my current status
        for (String message : deliveredMessage) {
            stateProcess.add(message);
        }

        //I'm the starter so i already "recevived" the first marker
        isFirstMarker = false;

        //Send marker to all process connected to me
        broadcastNotToMe("#");

        //Listen on all the input channel associated with this process
        amIRecordingTheChannels = true;
    }

    @Override
    public void receviveMessage(String message, int id_sender, int timestamp) throws RemoteException {
        System.out.println("I'm: " + ID + " and i just recevived: " + message + " with timestamp: " + timestamp + " from: " + id_sender);

        if (message.compareTo("#") == 0) {
            if (isFirstMarker) {
                System.out.println("I'm: " + ID + " and this is my first marker");

                isFirstMarker = false;

                //Save my current status
                for (String mex : deliveredMessage) {
                    stateProcess.add(mex);
                }

                //Save the message incoming channel as empty
                isMarkerRecevivedFrom.add(id_sender);

                //Listen on all the input channel associated with this process (except the process which sent me the marker)
                amIRecordingTheChannels = true;
                System.out.println("I'm: " + ID + " and I started recording the input channels");

                //If there are only two the channel reading stops immidialy because of
                if (isMarkerRecevivedFrom.size() == (neighborList.size() - 1)) {
                    amIRecordingTheChannels = false;
                    System.out.println("I'm: " + ID + " and I stopped recording the input channels (there is only one process which is the starter)");
                    printState();
                }

                Random rand = new Random();
                try {
                    Thread.sleep(rand.nextInt(200) + 10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Component.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Send marker to all process connected to me
                broadcastNotToMe("#");

            } else {
                isMarkerRecevivedFrom.add(id_sender);
                System.out.println("I'm: " + ID + " and I stopped recording the input from the channel linked to process: " + id_sender);

                if (isMarkerRecevivedFrom.size() == (neighborList.size() - 1)) {
                    amIRecordingTheChannels = false;
                    System.out.println("I'm: " + ID + " and I stopped recording the input channels (i got two marker from every process)");
                    printState();
                }
            }
        } else {

            //synchronized (this) {
            //Put the message in a waiting list
            bufferMessage.add(new MessageBuffer(message, id_sender, timestamp));
            if (amIRecordingTheChannels) {
                checkSenderAndSaveMessageOnChannel(message, id_sender);
            }
            // }

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
    }

    private int checkSenderAndSaveMessageOnChannel(String message, int id_sender) {
        if (id_sender == ID) { //There isn't a channel with the process itself 
            return 0;
        }

        for (int idSenderWithDoubleMarker : isMarkerRecevivedFrom) {
            if (idSenderWithDoubleMarker == id_sender) {
                return 0;
            }
        }

        System.out.println("I'm: " + ID + " and I'm saving this message: " + message + " from: " + id_sender);
        stateChannel.add(new MessageBuffer(message, id_sender, 0));
        return 1;
    }

    @Override
    public void receviveAck(String message, int id_sender) throws RemoteException {
        System.out.println("Ack receviced by: " + ID + " and sended by: " + id_sender);

        synchronized (this) {
            bufferAck.add(message);
            if (amIRecordingTheChannels) {
                checkSenderAndSaveMessageOnChannel("ACK:" + message, id_sender);
            }

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
        System.out.println("Message: " + message + " broadcasted by: " + this.toString());
        timestamp++;

        for (ProcessInterface p : neighborList) {
            p.receviveMessage(message, getID(), timestamp);
        }
    }

    public void broadcastNotToMe(String message) throws RemoteException {
        System.out.println("Message: " + message + " broadcasted by: " + this.toString());
        timestamp++;

        for (ProcessInterface p : neighborList) {
            if (p.getID() != getID()) {
                System.out.println("I'm: " + ID + " and I'm sending: " + message + " to: " + p.getID());
                p.receviveMessage(message, getID(), timestamp);
            }
        }
    }

    @Override
    public String toString() {
        return ID + "";
    }

    //TESTING METHODS
    private void printState() {
        System.out.print("I'm: " + ID + " and my process state is: ");
        for (String state : stateProcess) {
            System.out.print(" " + state + " / ");
        }
        if (stateProcess.size() == 0) {
            System.out.print("empty");
        }

        System.out.print(" and the channel state is: ");
        for (MessageBuffer message : stateChannel) {
            System.out.print(" " + ID + "-" + message.id_sender + " message: " + message.message + " /");
        }
        if (stateChannel.size() == 0) {
            System.out.print("empty");
        }

        System.out.println("");

    }

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
