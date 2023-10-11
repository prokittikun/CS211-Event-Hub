package cs211.project.models.collections;

import cs211.project.models.TeamMember;

import java.util.ArrayList;

public class TeamMemberCollection {
    private ArrayList<TeamMember> teamMembers;

    public TeamMemberCollection() {
        this.teamMembers = new ArrayList<>();
    }

    public TeamMemberCollection(ArrayList<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public void setTeamMembers(ArrayList<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public void addTeamMember(TeamMember teamMember) {
        this.teamMembers.add(teamMember);
    }

    public ArrayList<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public TeamMember findTeamMemberById(String userId) {
        for (TeamMember teamMember : teamMembers) {
            if (teamMember.getUserId().equals(userId)) {
                return teamMember;
            }
        }
        return null;
    }




}
