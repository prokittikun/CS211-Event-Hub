package cs211.project.services;

import cs211.project.models.TeamActivity;
import cs211.project.models.collections.TeamActivityCollection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeamActivityListFileDatasource implements Datasource<TeamActivityCollection>{
    private String directoryName;
    private String fileName;
    private DataFileManager dataFileManager;

    //Constructor เพื่อกำหนดที่อยู่ file csv ที่จะอ่าน
    public TeamActivityListFileDatasource(String directoryName, String fileName) {
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

    //return type อย่าลืมแก้นะจ้ะ
    @Override
    public TeamActivityCollection readData() {
        TeamActivityCollection activityList = new TeamActivityCollection();
        for (HashMap<String, String> data : dataFileManager.getData()) {
            activityList.addNewActivity(new TeamActivity(data));
        }
        return activityList;
    }

    //parameter type อย่าลืมแก้นะจ้ะ
    @Override
    public void writeData(TeamActivityCollection data) {
        ArrayList<HashMap<String, String>> dataToWrite = new ArrayList<>();
        for (TeamActivity activity : data.getActivities()) {
            dataToWrite.add(activity.toHashMap());
        }
        dataFileManager.writeData(dataToWrite);
    }

    @Override
    public void deleteById(String id){
        dataFileManager.deleteById(id);
    }
    @Override
    public void deleteAllByColumnAndValue(String column, String value){
        dataFileManager.deleteByColumnAndValue(value, column);
    }
    @Override
    public void updateColumnById(String id, String targetColumn, String newValue){
        dataFileManager.updateColumnById(id, targetColumn, newValue);
    }
    @Override
    public void deleteByConditions(Map<String, String> conditions){
        dataFileManager.deleteByConditions(conditions);
    }

    @Override
    public void updateColumnsById(String id, Map<String, String> newValues){
        dataFileManager.updateColumnsById(id, newValues);
    }

    @Override
    public TeamActivityCollection findById(String id) {
        TeamActivityCollection activityList = new TeamActivityCollection();
        for (HashMap<String, String> data : dataFileManager.findById(id)) {
            activityList.addNewActivity(new TeamActivity(data));
        }
        return activityList;
    }

    @Override
    public TeamActivityCollection findAllByColumnsAndValue(Map<String, String> conditions) {
        TeamActivityCollection activityList = new TeamActivityCollection();
        for (HashMap<String, String> item : dataFileManager.findAllByColumnsAndValue(conditions)) {
            activityList.addNewActivity(new TeamActivity(item));
        }
        return activityList;
    }

    @Override
    public TeamActivityCollection query(String query) {
        TeamActivityCollection activityList = new TeamActivityCollection();
        for (HashMap<String, String> item : dataFileManager.query(query)) {
            activityList.addNewActivity(new TeamActivity(item));
        }
        return activityList;
    }

    @Override
    public TeamActivityCollection queryWithOffsetAndLimit(String query, int offset, int limit) {
        TeamActivityCollection activityList = new TeamActivityCollection();
        for (HashMap<String, String> item : dataFileManager.queryWithOffsetAndLimit(query, offset, limit)) {
            activityList.addNewActivity(new TeamActivity(item));
        }
        return activityList;
    }
}
