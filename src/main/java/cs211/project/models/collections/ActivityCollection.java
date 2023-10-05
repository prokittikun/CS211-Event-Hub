package cs211.project.models.collections;

import cs211.project.models.Activity;

import java.util.ArrayList;

public class ActivityCollection<T> {
    private ArrayList<T> activities;

    public ActivityCollection() {
        activities = new ArrayList<>();
    }

    public void addNewActivity(T activity){
        activities.add(activity);
    }
    public ArrayList<T> getActivities() {
        return activities;
    }
}
