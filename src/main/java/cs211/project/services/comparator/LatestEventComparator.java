package cs211.project.services.comparator;

import cs211.project.models.Event;

import java.util.Comparator;

public class LatestEventComparator implements Comparator<Event> {

        @Override
        public int compare(Event o1, Event o2) {
            return o2.getCreateAt().compareTo(o1.getCreateAt());
        }
}
