package cs211.project.models;

public class Chat {
    private String sender;
    private String message;

    private String senderImagePath;

    public Chat(String sender, String message, String senderImagePath) {
        this.sender = sender;
        this.message = message;
        this.senderImagePath = senderImagePath;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderImagePath() {
        return senderImagePath;
    }
}
