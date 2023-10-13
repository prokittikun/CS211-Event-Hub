package cs211.project.models;

import cs211.project.models.collections.JoinEventCollection;

import java.util.HashMap;
import java.util.UUID;

public class JoinEvent {
    private UUID id;
    private String eventId;
    private String userId;
    private String joinTime;

    //Constructor
    public JoinEvent(String id, String eventId, String userId, String joinTime) {
        this.id = UUID.fromString(id);
        this.eventId = eventId;
        this.userId = userId;
        this.joinTime = joinTime;
    }

    //Constructor (HashMap)
    public JoinEvent(HashMap<String,String> data) {
        this.id = UUID.fromString(data.get("id").trim());
        this.eventId = data.get("eventId").trim();
        this.userId = data.get("userId").trim();
        this.joinTime = data.get("joinTime").trim();
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

    //Methods
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> data = new HashMap<>();
        data.put("id", this.id.toString());
        data.put("eventId", this.eventId);
        data.put("userId", this.userId);
        data.put("joinTime", this.joinTime);
        return data;
    }
}
