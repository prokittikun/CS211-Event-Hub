package cs211.project.models.collections;

import cs211.project.models.TeamActivity;

import java.util.ArrayList;

public class TeamActivityCollection extends ActivityCollection<TeamActivity>{
    public TeamActivityCollection() {
        super();
    }

    public void addNewActivity(TeamActivity activity){
        super.addNewActivity(activity);
    }

    public ArrayList<TeamActivity> getActivities() {
        return super.getActivities();
    }


}
