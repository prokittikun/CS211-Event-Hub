package cs211.project.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class Event {
    private final UUID id;
    private UUID userId;
    private String name;
    private String detail;
    private String location;
    private String openDate;
    private String openTime;
    private String closeDate;
    private String closeTime;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private int maxParticipant;
    private String image;
    private boolean status;
    private String progressStatus;
    private int participantCount;

    private LocalDateTime createAt;

    //Constructor
    public Event(String userId, String name, String detail,
                 String location, String openDate, String openTime, String closeDate, String closeTime, String startDate, String startTime,
                 String endDate, String endTime, int maxParticipant,
                 String image) {

        this.id = UUID.randomUUID();
        this.userId = UUID.fromString(userId);
        this.name = name;
        this.detail = detail;
        this.location = location;
        this.openDate = openDate;
        this.openTime = openTime;
        this.closeDate = closeDate;
        this.closeTime = closeTime;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.maxParticipant = maxParticipant;
        this.image = image;
        this.status = true;
        this.participantCount = 0;
        this.createAt = LocalDateTime.now();
    }

    //Constructor (HashMap)
    public Event(HashMap<String, String> data) {
        this.id = UUID.fromString(data.get("id").trim());
        this.userId = UUID.fromString(data.get("userId").trim());
        this.name = data.get("name").trim();
        this.detail = data.get("detail").trim();
        this.location = data.get("location").trim();
        this.openDate = data.get("openDate").trim();
        this.openTime = data.get("openTime").trim();
        this.closeDate = data.get("closeDate").trim();
        this.closeTime = data.get("closeTime").trim();
        this.startDate = data.get("startDate").trim();
        this.startTime = data.get("startTime").trim();
        this.endDate = data.get("endDate").trim();
        this.endTime = data.get("endTime").trim();
        this.maxParticipant = Integer.parseInt(data.get("maxParticipant").trim());
        this.image = data.get("image").trim();
        this.status = Boolean.parseBoolean(data.get("status").trim());
        this.createAt = LocalDateTime.parse(data.get("createAt").trim());

    }

    public void updateEventStatus() {
        LocalDateTime startDateTime = LocalDateTime.parse(startDate + "T" + startTime);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate + "T" + endTime);
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(startDateTime)) {
            progressStatus = "ยังไม่เริ่ม";
        } else if (now.isAfter(startDateTime) && now.isBefore(endDateTime)) {
            progressStatus = "กำลังดำเนินการ";
        } else if (now.isAfter(endDateTime)) {
            progressStatus = "เสร็จสิ้น";
        }
    }


    //Getter
    public String getStatus() {
        updateEventStatus();
        return progressStatus;
    }

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

    public String getOpenDate() {
        return openDate;
    }

    public String getOpenTime() {
        return openTime;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public String getCloseTime() {
        return closeTime;
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

    public int getParticipantCount() {
        return participantCount;
    }

    public String getImage() {
        return image;
    }

    public boolean isStatus() {
        return status;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
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

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
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

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
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

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    //Methods (For WriteFile)
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> data = new HashMap<>();
        data.put("id", id.toString());
        data.put("userId", userId.toString());
        data.put("image", image);
        data.put("name", name);
        data.put("detail", detail);
        data.put("location", location);
        data.put("openDate", openDate);
        data.put("openTime", openTime);
        data.put("closeDate", closeDate);
        data.put("closeTime", closeTime);
        data.put("startDate", startDate);
        data.put("startTime", startTime);
        data.put("endDate", endDate);
        data.put("endTime", endTime);
        data.put("maxParticipant", String.valueOf(maxParticipant));
        data.put("status", String.valueOf(status));
        data.put("createAt", createAt.toString());
        return data;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", detail='" + detail + '\'' +
                ", location='" + location + '\'' +
                ", startDate='" + startDate + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endDate='" + endDate + '\'' +
                ", endTime='" + endTime + '\'' +
                ", maxParticipant=" + maxParticipant +
                ", image='" + image + '\'' +
                ", status=" + status +
                '}';
    }

}