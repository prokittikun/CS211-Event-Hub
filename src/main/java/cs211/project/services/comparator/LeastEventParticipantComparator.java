package cs211.project.services.comparator;

import cs211.project.models.Event;

import java.util.Comparator;

public class LeastEventParticipantComparator implements Comparator<Event> {

    @Override
    public int compare(Event o1, Event o2) {
        double o1ParticipantPercentage = ((double) o1.getParticipantCount() / o1.getMaxParticipant()) * 100;
        double o2ParticipantPercentage = ((double) o2.getParticipantCount() / o2.getMaxParticipant()) * 100;
        if (o1ParticipantPercentage > o2ParticipantPercentage) {
            return 1;
        } else if (o1ParticipantPercentage < o2ParticipantPercentage) {
            return -1;
        }
        return 0;
    }
}
