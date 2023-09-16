package cs211.project.models;

import java.util.HashMap;
import java.util.UUID;

public class Team {
    UUID id;
    String name;
    UUID eventId;
    UUID leaderId;

    String maxMember;

    public Team(String id, String name, String eventId, String leaderId, String maxMember) {
        this.id = UUID.fromString(id);
        this.name = name;
        this.eventId = UUID.fromString(eventId);
        this.leaderId = UUID.fromString(leaderId);
        this.maxMember = maxMember;
    }

    //constructor overloading hash map
    public Team(HashMap<String, String> team) {
        this.id = UUID.fromString(team.get("id"));
        this.name = team.get("name");
        this.eventId = UUID.fromString(team.get("eventId"));
        this.leaderId = UUID.fromString(team.get("leaderId"));
        this.maxMember = team.get("maxMember");
    }

    //getter

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return name;
    }

    public String getEventId() {
        return eventId.toString();
    }

    public String getLeaderId() {
        return leaderId.toString();
    }

    public String getMaxMember() {
        return maxMember;
    }


    //setter

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEventId(String eventId) {
        this.eventId = UUID.fromString(eventId);
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = UUID.fromString(leaderId);
    }

    public void setMaxMember(String maxMember) {
        this.maxMember = maxMember;
    }

    //toHashMap
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> team = new HashMap<>();
        team.put("id", this.id.toString());
        team.put("name", this.name);
        team.put("eventId", this.eventId.toString());
        team.put("leaderId", this.leaderId.toString());
        team.put("maxMember", this.maxMember);
        return team;
    }



}
