package distributedalgorithms1;

public class MessageBuffer {

    public String message;
    public int id_sender;
    public int timestamp;

    public MessageBuffer(String message, int id_sender, int timestamp) {
        this.message = message;
        this.id_sender = id_sender;
        this.timestamp = timestamp;
    }

}
