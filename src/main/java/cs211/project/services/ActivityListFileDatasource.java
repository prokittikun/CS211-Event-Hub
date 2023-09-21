package cs211.project.services;

import cs211.project.models.Activity;
import cs211.project.models.collections.ActivityCollection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityListFileDatasource implements Datasource<ActivityCollection>{
    private String directoryName;
    private String fileName;
    private DataFileManager dataFileManager;

    //Constructor เพื่อกำหนดที่อยู่ file csv ที่จะอ่าน
    public ActivityListFileDatasource(String directoryName, String fileName) {
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
    public ActivityCollection readData() {
        ActivityCollection activityList = new ActivityCollection();
        for (HashMap<String, String> data : dataFileManager.getData()) {
            activityList.addNewActivity(new Activity(data));
        }
        return activityList;
    }

    //parameter type อย่าลืมแก้นะจ้ะ
    @Override
    public void writeData(ActivityCollection data) {
        ArrayList<HashMap<String, String>> dataToWrite = new ArrayList<>();
        for (Activity activity : data.getActivities()) {
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
    public ActivityCollection findById(String id) {
        ActivityCollection activityList = new ActivityCollection();
        for (HashMap<String, String> data : dataFileManager.findById(id)) {
            activityList.addNewActivity(new Activity(data));
        }
        return activityList;
    }

    @Override
    public ActivityCollection findAllByColumnsAndValue(Map<String, String> conditions) {
        ActivityCollection activityList = new ActivityCollection();
        for (HashMap<String, String> item : dataFileManager.findAllByColumnsAndValue(conditions)) {
            activityList.addNewActivity(new Activity(item));
        }
        return activityList;
    }

    @Override
    public ActivityCollection query(String query) {
        ActivityCollection activityList = new ActivityCollection();
        for (HashMap<String, String> item : dataFileManager.query(query)) {
            activityList.addNewActivity(new Activity(item));
        }
        return activityList;
    }

    @Override
    public ActivityCollection queryWithOffsetAndLimit(String query, int offset, int limit) {
        ActivityCollection activityList = new ActivityCollection();
        for (HashMap<String, String> item : dataFileManager.queryWithOffsetAndLimit(query, offset, limit)) {
            activityList.addNewActivity(new Activity(item));
        }
        return activityList;
    }
}
