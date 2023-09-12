package cs211.project.services;

import cs211.project.models.Event;
import cs211.project.models.JoinEvent;
import cs211.project.models.Team;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.JoinEventCollection;
import cs211.project.models.collections.TeamCollection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JoinEventListFileDatasource implements Datasource<JoinEventCollection> {
    private final String directoryName;
    private final String fileName;
    private final DataFileManager dataFileManager;

    //Constructor
    public JoinEventListFileDatasource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        checkFileIsExisted();
        dataFileManager = new DataFileManager(directoryName, fileName);
    }

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
    public JoinEventCollection readData() {
        JoinEventCollection eventList = new JoinEventCollection();
        for (HashMap<String, String> data : dataFileManager.getData()) {
            eventList.addJoinEvent(new JoinEvent(data));
        }
        return eventList;
    }

    @Override
    public void writeData(JoinEventCollection data) {
        ArrayList<HashMap<String, String>> dataToWrite = new ArrayList<>();
        for (JoinEvent joinEvent : data.getJoinEvents()) {
            dataToWrite.add(joinEvent.toHashMap());
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
    public JoinEventCollection findById(String id) {
        JoinEventCollection joinList = new JoinEventCollection();
        for (HashMap<String, String> data : dataFileManager.findById(id)) {
            joinList.addJoinEvent(new JoinEvent(data));
        }
        return joinList;
    }

    @Override
    public JoinEventCollection findAllByColumnsAndValue(Map<String, String> conditions) {
        JoinEventCollection joinList = new JoinEventCollection();
        for (HashMap<String, String> item : dataFileManager.findAllByColumnsAndValue(conditions)) {
            joinList.addJoinEvent(new JoinEvent(item));
        }
        return joinList;
    }

    @Override
    public JoinEventCollection query(String query) {
        JoinEventCollection joinList = new JoinEventCollection();
        for (HashMap<String, String> item : dataFileManager.query(query)) {
            joinList.addJoinEvent(new JoinEvent(item));
        }
        return joinList;
    }
}
