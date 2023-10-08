package cs211.project.models;

import java.util.HashMap;
import java.util.UUID;

public class Chat {
    private UUID id;
    private UUID userId;

    private UUID activityId;
    private String message;

    private UUID teamId;
    private String timestamp;

    public Chat(String senderUserId, String message, String teamId, String activityId, String timestamp) {
        this.id = UUID.randomUUID();
        this.userId = UUID.fromString(senderUserId);
        this.message = message;
        this.teamId = UUID.fromString(teamId);
        this.activityId = UUID.fromString(activityId);
        this.timestamp = timestamp;
    }

    public Chat(HashMap<String, String> chat) {
        this.id = UUID.fromString(chat.get("id"));
        this.userId = UUID.fromString(chat.get("userId"));
        this.message = chat.get("message");
        this.teamId = UUID.fromString(chat.get("teamId"));
        this.activityId = UUID.fromString(chat.get("activityId"));
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

    public void setActivityId(String activityId) {
        this.activityId = UUID.fromString(activityId);
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

    public String getId() {
        return id.toString();
    }
    public String getTeamId() {
        return teamId.toString();
    }
    public String getActivityId() {
        return activityId.toString();
    }

    public HashMap<String, String> toHashMap() {
        HashMap<String, String> chat = new HashMap<>();
        chat.put("id", String.valueOf(this.id));
        chat.put("userId", String.valueOf(this.userId));
        chat.put("message", this.message);
        chat.put("teamId", String.valueOf(this.teamId));
        chat.put("activityId", String.valueOf(this.activityId));
        chat.put("timestamp", this.timestamp);

        return chat;
    }
}
