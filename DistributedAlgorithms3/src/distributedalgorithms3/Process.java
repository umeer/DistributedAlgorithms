package distributedalgorithms3;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Process extends UnicastRemoteObject implements ProcessInterface {

    private final int realID;
    private final TypeOfProcess type;

    private List<ProcessInterface> neighborList = new ArrayList<>();

    private int id;
    private int level = 0;

    private int potentialFather = 0;
    private int father = -1;
    private boolean killed = false;
    private List<ProcessInterface> rivals = new ArrayList<>();

    Random rand = new Random();

    public Process(int id, TypeOfProcess type) throws RemoteException {
        this.realID = id;
        this.id = id;
        this.type = type;
    }

    @Override
    public void receviveMessage(int linkRealID, int linkID, int linkLevel) throws RemoteException {

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(rand.nextInt(200) + 10);

                    System.out.println(myInfo() + " and i just recevived a message from: " + linkRealID + " with id: " + linkID + " with level: " + linkLevel);

                    if (type == TypeOfProcess.CANDIDATE) {

                        if (compareLevelAndID(linkID, linkLevel) == 0 && killed == false) {
                            try {
                                rivals.remove(findRivalsIndex(linkRealID));
                                level++;
                            } catch (Exception e) {
                            }
                            if (rivals.size() > 0 && !killed) {
                                System.out.println(myInfo() + " and i just sent a message to: " + rivals.get(0).getID() + " with id: " + id + " with level: " + level);
                                rivals.get(0).receviveMessage(realID, id, level);
                            }
                        } else if (compareLevelAndID(linkID, linkLevel) < 0) {
                            System.out.println(myInfo() + " and i just sent a message to: " + linkRealID + " with id: " + linkID + " with level: " + linkLevel);
                            neighborList.get(findNeighborIndex(linkRealID)).receviveMessage(realID, linkID, linkLevel);

                            killed = true;
                            System.out.println(" I just got killed: " + realID + " Level: " + level);

                        } else if (!killed) { //In case the other is smaller, this can happen in case other is a candidate testing me and is small
                            System.out.println(myInfo() + " and i just sent a message to: " + linkRealID + " with id: " + id + " with level: " + level);
                            neighborList.get(findNeighborIndex(linkRealID)).receviveMessage(realID, id, level);
                        }

                        if (rivals.isEmpty()) {
                            if (!killed) {
                                System.out.println("I'm the president. ID: " + realID + " Level: " + level);
                            } else {
                                System.out.println("I'm not the president. ID: " + realID + " Level: " + level);
                            }
                        }

                    } else if (type == TypeOfProcess.ORDINARY) { //This is the ordinary process

                        if (compareLevelAndID(linkID, linkLevel) > 0) { //In case a candidate ask me but i'm big, but i don't care to be elected so i let him thinking that he own me
                            if (father == -1) {
                                System.out.println(myInfo() + " and i just sent a message to: " + linkRealID + " with id: " + linkID + " with level: " + linkLevel);
                                neighborList.get(findNeighborIndex(linkRealID)).receviveMessage(realID, linkID, linkLevel);

                            } else {
                                System.out.println(myInfo() + " and i just sent a message to: " + linkRealID + " with id: " + id + " with level: " + level);
                                neighborList.get(findNeighborIndex(linkRealID)).receviveMessage(realID, id, level);

                            }
                        } else if (compareLevelAndID(linkID, linkLevel) < 0) {
                            potentialFather = linkID;
                            id = linkID;
                            level = linkLevel;
                            if (father == -1) {
                                father = potentialFather;
                            }
                            System.out.println(myInfo() + " and i just sent a message to: " + father + " with id: " + id + " with level: " + level);
                            neighborList.get(findNeighborIndex(father)).receviveMessage(realID, id, level);
                        } else { //In case they are equal
                            father = potentialFather;
                            System.out.println(myInfo() + " and i just sent a message to: " + father + " with id: " + id + " with level: " + level);
                            neighborList.get(findNeighborIndex(father)).receviveMessage(realID, id, level);
                        }

                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    private int compareLevelAndID(int linkID, int linkLevel) {
        if (id > linkID) {
            return 1;
        } else if (id < linkID) {
            return -1;
        } else {
            return 0;
        }
    }

    private int findNeighborIndex(int linkRealID) throws RemoteException {
        int i = 0;
        for (ProcessInterface p : neighborList) {
            if (p.getID() == linkRealID) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private int findRivalsIndex(int linkRealID) throws RemoteException {
        int i = 0;
        for (ProcessInterface p : rivals) {
            if (p.getID() == linkRealID) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    public void startElection() throws RemoteException {
        if (type == TypeOfProcess.CANDIDATE) {
            if (neighborList.size() > 0) {
                if (rivals.size() != 0) {

                    System.out.println("I'm: " + id + " and i'm ready to start the election procedure ");
                    id = realID;
                    level = 0;
//                rivals = neighborList;
                    System.out.println(myInfo() + " and i just sent a message to: " + rivals.get(0).getID() + " with id: " + id + " with level: " + level);
                    rivals.get(0).receviveMessage(realID, id, level);
                } else {
                    System.out.println("I win even before starting");
                }
            } else {
                System.out.println("There are no neighbor to compete with.");
            }
        } else {
            System.out.println("This is a ordinary process, try with a candidate one");
        }
    }

    @Override
    public void setNeighbor(List<ProcessInterface> list) throws RemoteException {
        this.neighborList = list;
        //remove my self out of this list

        neighborList.remove(findNeighborIndex(realID));
        for (ProcessInterface process : neighborList) {
            rivals.add(process);
        }
    }

    public String myInfo() {
        return "Hi i'm: " + realID + " with id: " + id + " and level: " + level;
    }

    //TESTING METHODS
    @Override
    public void test() {
        System.out.println(this.toString());
    }

    @Override
    public int getID() throws RemoteException {
        return realID;
    }

}

enum TypeOfProcess {
    CANDIDATE, ORDINARY
}
