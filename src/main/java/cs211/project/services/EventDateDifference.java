package cs211.project.services;

import cs211.project.models.Event;

public class EventDateDifference {
    public Event event;
    public long dateDifference;

    public EventDateDifference(Event event, long dateDifference) {
        this.event = event;
        this.dateDifference = dateDifference;
    }
}