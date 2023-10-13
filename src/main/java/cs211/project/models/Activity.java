package cs211.project.models;

import cs211.project.services.DateTimeService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class Activity {
    private UUID id;
    private String name;
    private String detail;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;

    private String dateTimeStart;
    private String dateTimeEnd;

    private Boolean status;

    public Activity(String id,  String name, String detail, String startDate, String startTime, String endDate, String endTime) {
        this.id = UUID.fromString(id);
        this.name = name;
        this.detail = detail;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.setDateTimeStart();
        this.setDateTimeEnd();
        this.status = false;
    }

    public Activity(HashMap<String, String> activity) {
        this.id = UUID.fromString(activity.get("id"));
        this.name = activity.get("name");
        this.detail = activity.get("detail");
        this.startDate = activity.get("startDate");
        this.startTime = activity.get("startTime");
        this.endDate =  activity.get("endDate");
        this.endTime = activity.get("endTime");
        this.setDateTimeStart();
        this.setDateTimeEnd();
        this.status = Boolean.parseBoolean(activity.get("status"));

    }

    public String getId() {
        return id.toString();
    }


    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
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

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public void setDateTimeStart(){
        this.dateTimeStart = DateTimeService.toString(this.startDate) + " " + this.startTime;
    }

    public void setDateTimeEnd(){
        this.dateTimeEnd = DateTimeService.toString(this.endDate) + " " + this.endTime;
    }

    public void setStatus(Boolean status){
        this.status = status;
    }
    public Boolean getStatus(){
        return this.status;
    }
    public String getDateTimeStart() {
        return dateTimeStart;
    }

    public String getDateTimeEnd() {
        return  dateTimeEnd;
    }

}
