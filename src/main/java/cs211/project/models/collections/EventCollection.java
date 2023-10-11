package cs211.project.models.collections;

import cs211.project.models.Event;
import cs211.project.models.JoinEvent;
import cs211.project.services.EventDateDifference;
import cs211.project.services.comparator.ClosestEventComparator;
import cs211.project.services.comparator.LatestEventComparator;
import cs211.project.services.comparator.MostEventParticipantComparator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

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
    public void addEvent(Event event) {
        this.events.add(event);
    }

    public ArrayList<Event> filterByUserId(String userId) {
        ArrayList<Event> filteredEvents = new ArrayList<>();
        for (Event event : events) {
            if (event.getUserId().equals(userId)) {
                filteredEvents.add(event);
            }
        }
        return filteredEvents;
    }

    public Event findEventById(String eventId) {
        for (Event event : events) {
            if (event.getId().equals(eventId)) {
                return event;
            }
        }
        return null;
    }

    public ArrayList<Event> getRandomEvent(int n) {
        Set<Event> selectedEvents = new HashSet<>();


        Random random = new Random();

        while (selectedEvents.size() < n && selectedEvents.size() < events.size()) {
            Event randomEvent = events.get(random.nextInt(events.size()));
                selectedEvents.add(randomEvent);
        }
        ArrayList<Event> events = new ArrayList<>(selectedEvents);
        return events;
    }

    public ArrayList<Event> getPopularEvent() {

        this.sortByComparator(new MostEventParticipantComparator());
        ArrayList<Event> popular = new ArrayList<>();

        int count = 0;
        for (Event event : events) {
            if (count == 2) {
                break;
            }
            popular.add(event);
            count++;

        }

        return popular;
    }

    public ArrayList<Event> getClosestEvents() {
        this.sortByComparator(new ClosestEventComparator());
        ArrayList<Event> closestEvents = new ArrayList<>();

        int count = 0;
        for (Event event : events) {
            if (count == 2) {
                break;
            }
            closestEvents.add(event);
            count++;

        }

        return closestEvents;
    }

    public ArrayList<Event> getLatestEvents() {
        this.sortByComparator(new LatestEventComparator());
        ArrayList<Event> filteredEvents = new ArrayList<>();
        int count = 0;
        for (Event event : events) {
            if (count == 5 || event == null) {
                break;
            }
            filteredEvents.add(event);
            count++;

        }
        return filteredEvents;
    }

    public ArrayList<Event> sortByBeforeEndDate() {
        ArrayList<Event> filteredEvents = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (Event event : events) {
            LocalDateTime eventEndDate = LocalDateTime.parse(event.getEndDate()+"T"+event.getEndTime());
            if (eventEndDate.isAfter(now)) {
                filteredEvents.add(event);
            }
        }
        return filteredEvents;
    }
    public ArrayList<Event> sortByComparator(Comparator<Event> comparator) {
        Collections.sort(events, comparator);
        return events;
    }

}
