package cs211.project.models;
import java.util.UUID;

public class Event {
    private UUID uuid;
    private String title;
    private String location;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private int totalParticipant;
    private int maxParticipant;
    private String pathImagePreview;
    private boolean status;

    //Constructor
    public Event(String title, String location, String startDate, String startTime, String endDate, String endTime, int totalParticipant, int maxParticipant, String pathImagePreview) {
        this.uuid = UUID.randomUUID();
        this.title = title;
        this.location = location;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.totalParticipant = totalParticipant;
        this.maxParticipant = maxParticipant;
        this.pathImagePreview = pathImagePreview;
    }

    //Getter
    public UUID getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getTotalParticipant() {
        return totalParticipant;
    }

    public int getMaxParticipant() {
        return maxParticipant;
    }

    public String getPathImagePreview() {
        return pathImagePreview;
    }

    public boolean isStatus() {
        return status;
    }

    //Setter
    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setTotalParticipant(int totalParticipant) {
        this.totalParticipant = totalParticipant;
    }

    public void setMaxParticipant(int maxParticipant) {
        this.maxParticipant = maxParticipant;
    }

    public void setPathImagePreview(String pathImagePreview) {
        this.pathImagePreview = pathImagePreview;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}