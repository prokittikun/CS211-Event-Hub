package cs211.project.models.collections;
import cs211.project.models.Event;
import java.util.ArrayList;

public class EventCollection {
    private ArrayList<Event> events;

    //Constructor
    public EventCollection() {
        events = new ArrayList<>();
    }

    //Getter
    public ArrayList<Event> getEvents() {
        return events;
    }

    //Setter
    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    //Method
    public void addEvent (Event event){
        this.events.add(event);
    }
}
