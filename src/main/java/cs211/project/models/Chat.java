package cs211.project.models;

import java.util.HashMap;
import java.util.UUID;

public class Chat {
    private UUID id;
    private UUID userId;
    private String message;

    private UUID teamId;
    private String timestamp;

    public Chat(String senderUserId, String message, String teamId, String timestamp) {
        this.id = UUID.randomUUID();
        this.userId = UUID.fromString(senderUserId);
        this.message = message;
        this.teamId = UUID.fromString(teamId);
        this.timestamp = timestamp;
    }

    public Chat(HashMap<String, String> chat){
        this.id = UUID.fromString(chat.get("id"));
        this.userId = UUID.fromString(chat.get("userId"));
        this.message = chat.get("message");
        this.teamId = UUID.fromString(chat.get("teamId"));
        this.timestamp = chat.get("timestamp");
    }

    public void setSenderUserId(String sender) {
        this.userId = UUID.fromString(sender);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderUserId() {
        return userId.toString();
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public HashMap<String, String> toHashMap() {
        HashMap<String, String> chat = new HashMap<>();
        chat.put("id", String.valueOf(this.id));
        chat.put("userId", String.valueOf(this.userId));
        chat.put("message", this.message);
        chat.put("teamId", String.valueOf(this.teamId));
        chat.put("timestamp", this.timestamp);

        return chat;
    }
}
