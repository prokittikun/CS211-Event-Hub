package cs211.project.services;

import cs211.project.models.Team;
import cs211.project.models.collections.TeamCollection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeamListFileDatasource implements Datasource<TeamCollection> {
    private String directoryName;
    private String fileName;
    private DataFileManager dataFileManager;

    //Constructor เพื่อกำหนดที่อยู่ file csv ที่จะอ่าน
    public TeamListFileDatasource(String directoryName, String fileName) {
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
    public TeamCollection readData() {
        TeamCollection teams = new TeamCollection();
        for (HashMap<String, String> data : dataFileManager.getData()) {
            teams.addTeam(new Team(data));
        }
        return teams;
    }

    //parameter type อย่าลืมแก้นะจ้ะ
    @Override
    public void writeData(TeamCollection data) {
        ArrayList<HashMap<String, String>> dataToWrite = new ArrayList<>();
        for (Team team : data.getTeams()) {
            dataToWrite.add(team.toHashMap());
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
    public TeamCollection findById(String id) {
        TeamCollection teams = new TeamCollection();
        for (HashMap<String, String> data : dataFileManager.findById(id)) {
            teams.addTeam(new Team(data));
        }
        return teams;
    }

    @Override
    public TeamCollection findAllByColumnsAndValue(Map<String, String> conditions) {
        TeamCollection teams = new TeamCollection();
        for (HashMap<String, String> item : dataFileManager.findAllByColumnsAndValue(conditions)) {
            teams.addTeam(new Team(item));
        }
        return teams;
    }

    @Override
    public TeamCollection query(String query) {
        TeamCollection teams = new TeamCollection();
        for (HashMap<String, String> item : dataFileManager.query(query)) {
            teams.addTeam(new Team(item));
        }
        return teams;
    }
}

