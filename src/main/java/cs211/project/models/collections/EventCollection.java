package cs211.project.models.collections;
import cs211.project.models.Event;
import cs211.project.models.JoinEvent;
import cs211.project.services.EventDateDifference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public void addEvent (Event event){
        this.events.add(event);
    }

    public ArrayList<Event> filterByUserId(String userId){
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

    public ArrayList<Event> getRandomNEvent(String currentEventId, int n) {
        ArrayList<Event> filteredEvents = new ArrayList<>();
        Set<Event> selectedEvents = new HashSet<>();
        ArrayList<Event> eventsToExclude = new ArrayList<>();
        String eventIdToExclude = currentEventId;

        Event eventToExclude = findEventById(eventIdToExclude);
        if (eventToExclude != null) {
            eventsToExclude.add(eventToExclude);
        }

        Random random = new Random();

        while (selectedEvents.size() < n && selectedEvents.size() < events.size()) {
            Event randomEvent = events.get(random.nextInt(events.size()));
            if (!eventsToExclude.contains(randomEvent)) {
                selectedEvents.add(randomEvent);
            }
        }

        filteredEvents.addAll(selectedEvents);

        return filteredEvents;
    }

    public ArrayList<Event> getPopularEvent(JoinEventCollection joinEventCollection) {
        System.out.println("test");
        HashMap<String, Double> filteredEvents = new HashMap<>();
        for (Event event : events) {
            int participants = joinEventCollection.filterByEventId(event.getId()).size();
            double participantPercentage = ((double) participants / event.getMaxParticipant()) * 100;
            filteredEvents.put(event.getId(), participantPercentage);
        }

        List<Map.Entry<String, Double>> entryList = new ArrayList<>(filteredEvents.entrySet());

        Collections.sort(entryList, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> entry1, Map.Entry<String, Double> entry2) {
                return Double.compare(entry2.getValue(), entry1.getValue());
            }
        });
        System.out.println("entryList"+ entryList);

        ArrayList<Event> sortedEvents = new ArrayList<>();

        int count = 0;
        for (Map.Entry<String, Double> entry : entryList) {
            if (count >= 2) {
                break;
            }

            String eventId = entry.getKey();
            Event event = findEventById(eventId);
            if (event != null) {
                sortedEvents.add(event);
                count++;
            }
        }
        return sortedEvents;
    }

        public ArrayList<Event> getClosestEvents() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();

        List<EventDateDifference> eventDateDifferences = new ArrayList<>();

        for (Event event : events) {
            try {
                Date startDate = dateFormat.parse(event.getStartDate());
                long dateDifference = Math.abs(startDate.getTime() - currentDate.getTime());
                eventDateDifferences.add(new EventDateDifference(event, dateDifference));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        Collections.sort(eventDateDifferences, (e1, e2) -> Long.compare(e1.dateDifference, e2.dateDifference));

        ArrayList<Event> closestEvents = new ArrayList<>();

        int count = 0;
        for (EventDateDifference eventDateDifference : eventDateDifferences) {
            closestEvents.add(eventDateDifference.event);
            count++;
            if (count >= 2) {
                break;
            }
        }

        return closestEvents;
    }

    public ArrayList<Event> getLatestEvents() {
        ArrayList<Event> filteredEvents = new ArrayList<>();
        for (int i = events.size() - 5; i < events.size(); i++) {
            Event event = events.get(i);
            if (event == null) {
                return null;
            }
            filteredEvents.add(event);
        }
        return filteredEvents;
    }


}
