package cs211.project.models;

import java.util.ArrayList;
import java.util.List;

public class User extends Person {
    private List<Event> events;
    private String username;

    public User(String firstName, String lastName, String username) {
        super(firstName, lastName);
        this.username = username;
        this.events = new ArrayList<>();
    }

    public boolean createEvent(String name, String date) {
        Event newEvent = new Event(name, date);
        events.add(newEvent);
        return true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Event> getEvents() {
        return events;
    }

}