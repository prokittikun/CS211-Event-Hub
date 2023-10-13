package cs211.project.models;

import java.util.HashMap;
import java.util.UUID;

public class TeamMember {
    private UUID id;
    private UUID userId;
    private UUID teamId;

    public TeamMember(String id, String userId, String teamId) {
        this.id = UUID.fromString(id);
        this.userId = UUID.fromString(userId);
        this.teamId = UUID.fromString(teamId);
    }

    public TeamMember(HashMap<String, String> teamMember) {
        this.id = UUID.fromString(teamMember.get("id"));
        this.userId = UUID.fromString(teamMember.get("userId"));
        this.teamId = UUID.fromString(teamMember.get("teamId"));
    }

    public String getId() {
        return id.toString();
    }

    public String getUserId() {
        return userId.toString();
    }

    public String getTeamId() {
        return teamId.toString();
    }


    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    public void setUserId(String userId) {
        this.userId = UUID.fromString(userId);
    }

    public void setTeamId(String teamId) {
        this.teamId = UUID.fromString(teamId);
    }



    //toHashMap
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> teamMember = new HashMap<>();
        teamMember.put("id", this.id.toString());
        teamMember.put("userId", this.userId.toString());
        teamMember.put("teamId", this.teamId.toString());
        return teamMember;
    }

}
