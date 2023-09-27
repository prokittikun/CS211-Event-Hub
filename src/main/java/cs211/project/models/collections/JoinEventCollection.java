package cs211.project.models.collections;
import cs211.project.models.JoinEvent;
import java.util.ArrayList;
import java.util.UUID;

public class JoinEventCollection {
    private ArrayList<JoinEvent> joinEvents;

    //Constructor
    public JoinEventCollection() {
        joinEvents = new ArrayList<>();
    }

    //Getter
    public ArrayList<JoinEvent> getJoinEvents() {
        return joinEvents;
    }

    //Setter
    public void setJoinEvents(ArrayList<JoinEvent> joinEvents) {
        this.joinEvents = joinEvents;
    }

    //Method
    public void addJoinEvent (JoinEvent joinEvent){
        this.joinEvents.add(joinEvent);
    }

    public ArrayList<JoinEvent> filterByEventId(String eventId){
        ArrayList<JoinEvent> filteredJoinEvents = new ArrayList<>();
        for (JoinEvent joinEvent : joinEvents) {
            if (joinEvent.getEventId().equals(eventId)) {
                filteredJoinEvents.add(joinEvent);
            }
        }
        return filteredJoinEvents;
    }
}
