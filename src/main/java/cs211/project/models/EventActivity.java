package cs211.project.models;

import java.util.HashMap;
import java.util.UUID;

public class EventActivity extends Activity{

    private UUID eventId;
    public EventActivity(String id, String eventId, String name, String detail, String startDate, String startTime, String endDate, String endTime) {
        super(id, name, detail, startDate, startTime, endDate, endTime);
        this.eventId = UUID.fromString(eventId);
    }

    public EventActivity(HashMap<String, String> activity) {
        super(activity);
        this.eventId = UUID.fromString(activity.get("eventId"));
    }

    public String getEventId() {
        return eventId.toString();
    }

    public void setEventId(String eventId) {
        this.eventId = UUID.fromString(eventId);
    }

    public HashMap<String, String> toHashMap() {
        HashMap<String, String> activity = new HashMap<>();
        activity.put("id", this.getId());
        activity.put("eventId", this.getEventId());
        activity.put("name", this.getName());
        activity.put("detail", this.getDetail());
        activity.put("startDate", this.getStartDate());
        activity.put("startTime", this.getStartTime());
        activity.put("endDate", this.getEndDate());
        activity.put("endTime", this.getEndTime());
        activity.put("status", this.getStatus().toString());
        return activity;
    }
}
