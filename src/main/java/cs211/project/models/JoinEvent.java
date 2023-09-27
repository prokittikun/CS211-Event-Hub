package cs211.project.models;

import cs211.project.models.collections.JoinEventCollection;

import java.util.HashMap;
import java.util.UUID;

public class JoinEvent {
    private UUID id;
    private String eventId;
    private String userId;
    private String joinTime;
    private String status;

    //Constructor
    public JoinEvent(String id, String eventId, String userId, String joinTime, String status) {
        this.id = UUID.fromString(id);
        this.eventId = eventId;
        this.userId = userId;
        this.joinTime = joinTime;
        this.status = status;
    }

    //Constructor (HashMap)
    public JoinEvent(HashMap<String,String> data) {
        this.id = UUID.fromString(data.get("id").trim());
        this.eventId = data.get("eventId").trim();
        this.userId = data.get("userId").trim();
        this.joinTime = data.get("joinTime").trim();
        this.status = data.get("status").trim();
    }

    //Getter
    public String getId() {
        return id.toString();
    }

    public String getEventId() {
        return eventId;
    }

    public String getUserId() {
        return userId;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public String getStatus() {
        return status;
    }

    //Setter
    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //Methods
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> data = new HashMap<>();
        data.put("id", this.id.toString());
        data.put("eventId", this.eventId);
        data.put("userId", this.userId);
        data.put("joinTime", this.joinTime);
        data.put("status", this.status);
        return data;
    }
}
