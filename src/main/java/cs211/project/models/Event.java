package cs211.project.models;
import java.util.HashMap;
import java.util.UUID;

public class Event {
    private final UUID id;
    private UUID userId;
    private String name;
    private String detail;
    private String location;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private int maxParticipant;
    private String image;
    private boolean status;

    //Constructor
    public Event(String userId, String name, String detail, String location, String startDate, String startTime, String endDate, String endTime, int maxParticipant, String image) {
        this.id = UUID.randomUUID();
        this.userId = UUID.fromString(userId);
        this.name = name;
        this.detail = detail;
        this.location = location;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.maxParticipant = maxParticipant;
        this.image = image;
        this.status = true;
    }

    //Constructor (HashMap)
    public Event(HashMap<String, String> data){
        this.id = UUID.fromString(data.get("id").trim());
        this.userId = UUID.fromString(data.get("userId").trim());
        this.name = data.get("name").trim();
        this.detail = data.get("detail").trim();
        this.location = data.get("location").trim();
        this.startDate = data.get("startDate").trim();
        this.startTime = data.get("startTime").trim();
        this.endDate = data.get("endDate").trim();
        this.endTime = data.get("endTime").trim();
        this.maxParticipant = Integer.parseInt(data.get("maxParticipant").trim());
        this.image = data.get("image").trim();
        this.status = Boolean.parseBoolean(data.get("status").trim());
    }


    //Getter
    public String getId() {
        return id.toString();
    }

    public String getUserId() {
        return userId.toString();
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
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

    public int getMaxParticipant() {
        return maxParticipant;
    }

    public String getImage() {
        return image;
    }

    public boolean isStatus() {
        return status;
    }


    //Setter
    public void setUserId(String userId) {
        this.userId = UUID.fromString(userId);
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public void setMaxParticipant(int maxParticipant) {
        this.maxParticipant = maxParticipant;
    }

    public void setPathImagePreview(String image) {
        this.image = image;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    //Methods (For WriteFile)
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> data = new HashMap<>();
        data.put("id", id.toString());
        data.put("userId", userId.toString());
        data.put("image", image);
        data.put("name", name);
        data.put("location", location);
        data.put("startDate", startDate);
        data.put("startTime", startTime);
        data.put("endDate", endDate);
        data.put("endTime", endTime);
        data.put("maxParticipant", String.valueOf(maxParticipant));
        data.put("status", String.valueOf(status));
        return data;
    }

//    @Override
//    public int compareTo(Object o) {
//        Event event = (Event) o;
//        if (event.getMaxParticipant() >= this.getMaxParticipant()) {
//            return 1;
//        } else {
//            return -1;
//        }
//    }
}