package cs211.project.services;

import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.TeamCollection;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventListFileDatasource implements Datasource<EventCollection> {
    private final String directoryName;
    private final String fileName;
    private final DataFileManager dataFileManager;

    //Constructor
    public EventListFileDatasource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        checkFileIsExisted();
        dataFileManager = new DataFileManager(directoryName, fileName);
    }

    // ตรวจสอบว่ามีไฟล์ให้อ่านหรือไม่ ถ้าไม่มีให้สร้างไฟล์เปล่า
    private void checkFileIsExisted() {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = directoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public EventCollection readData() {
        EventCollection eventList = new EventCollection();
        for (HashMap<String, String> data : dataFileManager.getData()) {
            eventList.addEvent(new Event(data));
        }
        return eventList;
    }

    @Override
    public void writeData(EventCollection data) {
        ArrayList<HashMap<String, String>> dataToWrite = new ArrayList<>();
        for (Event event : data.getEvents()) {
            dataToWrite.add(event.toHashMap());
        }
        dataFileManager.writeData(dataToWrite);
    }

    @Override
    public void deleteById(String id){
        dataFileManager.deleteById(id);
    }
    @Override
    public void deleteAllByColumnAndValue(String value, String column){
        dataFileManager.deleteByColumnAndValue(value, column);
    }
    @Override
    public void updateColumnById(String id, String targetColumn, String newValue){
        dataFileManager.updateColumnById(id, targetColumn, newValue);
    }

    @Override
    public void updateColumnsById(String id, Map<String, String> newValues){
        dataFileManager.updateColumnsById(id, newValues);
    }

    @Override
    public void deleteByConditions(Map<String, String> conditions){
        dataFileManager.deleteByConditions(conditions);
    }

    @Override
    public EventCollection findById(String id) {
        EventCollection events = new EventCollection();
        for (HashMap<String, String> data : dataFileManager.findById(id)) {
            events.addEvent(new Event(data));
        }
        return events;
    }

    @Override
    public EventCollection findAllByColumnsAndValue(Map<String, String> conditions) {
        EventCollection events = new EventCollection();
        for (HashMap<String, String> item : dataFileManager.findAllByColumnsAndValue(conditions)) {
            events.addEvent(new Event(item));
        }
        return events;
    }

    @Override
    public EventCollection query(String query) {
        EventCollection events = new EventCollection();
        for (HashMap<String, String> item : dataFileManager.query(query)) {
            events.addEvent(new Event(item));
        }
        return events;
    }
}
