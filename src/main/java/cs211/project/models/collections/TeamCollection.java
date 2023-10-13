package cs211.project.models.collections;

import cs211.project.models.Team;

import java.util.ArrayList;

public class TeamCollection {
    private ArrayList<Team> teams;

    public TeamCollection() {
        this.teams = new ArrayList<>();
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void isNameExist(String name) throws Exception {
        for (Team team : teams) {
            if (team.getName().equals(name)) {
                throw new Exception("Team name already exist");
            }
        }
    }

    //Overloading
    public void isNameExits(String name, String id) throws Exception {
        for (Team team : teams) {
            if (team.getName().equals(name) && !team.getId().equals(id)) {
                throw new Exception("Team name already exist");
            }
        }
    }

    public ArrayList<Team> findTeamByEventId(String eventId) {
        ArrayList<Team> teams = new ArrayList<>();
        for (Team team : this.teams) {
            if (team.getEventId().equals(eventId)) {
                teams.add(team);
            }
        }
        return teams;
    }
}
