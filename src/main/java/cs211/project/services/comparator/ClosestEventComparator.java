package cs211.project.services.comparator;

import cs211.project.models.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;

public class ClosestEventComparator implements Comparator<Event> {

    @Override
    public int compare(Event o1, Event o2) {
        //parse date
        LocalDate o1Date = LocalDate.parse(o1.getStartDate());
        LocalDate o2Date = LocalDate.parse(o2.getStartDate());
        LocalTime o1Time = LocalTime.parse(o1.getStartTime());
        LocalTime o2Time = LocalTime.parse(o2.getStartTime());
        LocalDateTime o1DateTime = LocalDateTime.of(o1Date, o1Time);
        LocalDateTime o2DateTime = LocalDateTime.of(o2Date, o2Time);
        return o1DateTime.compareTo(o2DateTime);
    }
}
