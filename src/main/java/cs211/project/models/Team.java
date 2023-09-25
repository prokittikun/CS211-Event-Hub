package cs211.project.models;

import java.util.HashMap;
import java.util.UUID;

public class Team {
    private UUID id;
    private String name;
    private UUID eventId;
    private UUID leaderId;
    private String maxMember;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;

    public Team(String id, String name, String eventId, String leaderId, String maxMember
            , String startDate, String endDate, String startTime, String endTime) {
        this.id = UUID.fromString(id);
        this.name = name;
        this.eventId = UUID.fromString(eventId);
        this.leaderId = UUID.fromString(leaderId);
        this.maxMember = maxMember;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    //constructor overloading hash map
    public Team(HashMap<String, String> team) {
        this.id = UUID.fromString(team.get("id"));
        this.name = team.get("name");
        this.eventId = UUID.fromString(team.get("eventId"));
        this.leaderId = UUID.fromString(team.get("leaderId"));
        this.maxMember = team.get("maxMember");
        this.startDate = team.get("startDate");
        this.endDate = team.get("endDate");
        this.startTime = team.get("startTime");
        this.endTime = team.get("endTime");
    }

    //Getter
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

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    //Setter
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

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    //toHashMap
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> team = new HashMap<>();
        team.put("id", this.id.toString());
        team.put("name", this.name);
        team.put("eventId", this.eventId.toString());
        team.put("leaderId", this.leaderId.toString());
        team.put("maxMember", this.maxMember);
        team.put("startDate", this.startDate);
        team.put("endDate", this.endDate);
        team.put("startTime", this.startTime);
        team.put("endTime", this.endTime);
        return team;
    }
}
