package cs211.project.models.collections;

import cs211.project.models.EventActivity;

import java.util.ArrayList;

public class EventActivityCollection extends ActivityCollection<EventActivity>{
    public EventActivityCollection() {
        super();
    }

    public void addNewActivity(EventActivity activity){
        super.addNewActivity(activity);
    }

    public ArrayList<EventActivity> getActivities() {
        return super.getActivities();
    }
}
