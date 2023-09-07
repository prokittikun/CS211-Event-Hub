package cs211.project.models.collections;
import cs211.project.models.Event;
import java.util.HashMap;
import java.util.UUID;

public class EventCollection {
    private HashMap<UUID, Event> eventHashMap;

    //Constructor
    public EventCollection() {
        this.eventHashMap = new HashMap<UUID, Event>();
    }

    //Getter
    public HashMap<UUID, Event> getEventHashMap() {
        return eventHashMap;
    }

    //Setter
    public void setEventHashMap(HashMap<UUID, Event> eventHashMap) {
        this.eventHashMap = eventHashMap;
    }
}
