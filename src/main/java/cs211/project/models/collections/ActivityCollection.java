package cs211.project.models.collections;

import cs211.project.models.Activity;

import java.util.ArrayList;

public class ActivityCollection {
    private ArrayList<Activity> activities;

    public ActivityCollection() {
        activities = new ArrayList<>();
    }

    public void addNewActivity(Activity activity){
        activities.add(activity);
    }
    public ArrayList<Activity> getActivities() {
        return activities;
    }
}
