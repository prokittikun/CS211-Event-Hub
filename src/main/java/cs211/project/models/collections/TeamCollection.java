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

}
