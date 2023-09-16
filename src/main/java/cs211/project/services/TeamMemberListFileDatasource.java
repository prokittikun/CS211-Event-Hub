package cs211.project.services;

import cs211.project.models.TeamMember;
import cs211.project.models.collections.TeamMemberCollection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeamMemberListFIleDatasource implements Datasource<TeamMemberCollection>{
    private String directoryName;
    private String fileName;
    private DataFileManager dataFileManager;

    //Constructor เพื่อกำหนดที่อยู่ file csv ที่จะอ่าน
    public TeamMemberListFIleDatasource(String directoryName, String fileName) {
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
    public TeamMemberCollection readData() {
        TeamMemberCollection teamMemberList = new TeamMemberCollection();
        for (HashMap<String, String> data : dataFileManager.getData()) {
            teamMemberList.addTeamMember(new TeamMember(data));
        }
        return teamMemberList;
    }

    //parameter type อย่าลืมแก้นะจ้ะ
    @Override
    public void writeData(TeamMemberCollection data) {
        ArrayList<HashMap<String, String>> dataToWrite = new ArrayList<>();
        for (TeamMember teamMember : data.getTeamMembers()) {
            dataToWrite.add(teamMember.toHashMap());
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
    public TeamMemberCollection findById(String id) {
        TeamMemberCollection teamMemberList = new TeamMemberCollection();
        for (HashMap<String, String> data : dataFileManager.findById(id)) {
            teamMemberList.addTeamMember(new TeamMember(data));
        }
        return teamMemberList;
    }

    @Override
    public TeamMemberCollection findAllByColumnsAndValue(Map<String, String> conditions) {
        TeamMemberCollection teamMemberList = new TeamMemberCollection();
        for (HashMap<String, String> item : dataFileManager.findAllByColumnsAndValue(conditions)) {
            teamMemberList.addTeamMember(new TeamMember(item));
        }
        return teamMemberList;
    }

    @Override
    public TeamMemberCollection query(String query) {
        TeamMemberCollection teamMemberList = new TeamMemberCollection();
        for (HashMap<String, String> item : dataFileManager.query(query)) {
            teamMemberList.addTeamMember(new TeamMember(item));
        }
        return teamMemberList;
    }
}
