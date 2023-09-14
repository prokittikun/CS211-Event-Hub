package cs211.project.services;

import cs211.project.models.User;
import cs211.project.models.collections.UserCollection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserFileDatasource implements Datasource<UserCollection> {
    private String directoryName;
    private String fileName;
    private DataFileManager dataFileManager;

    public UserFileDatasource(String directoryName, String fileName) {
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
    public UserCollection readData() {
        UserCollection users = new UserCollection();
        for (HashMap<String, String> data : dataFileManager.getData()) {
            users.addUser(new User(data));
        }
        return users;
    }

    @Override
    public void writeData(UserCollection data) {
        ArrayList<HashMap<String, String>> dataToWrite = new ArrayList<>();
        for (User user : data.getAllUsers()) {
            dataToWrite.add(user.toHashMap());
        }
        dataFileManager.writeData(dataToWrite);
    }

    @Override
    public void deleteById(String id) {
        dataFileManager.deleteById(id);
    }

    @Override
    public void deleteAllByColumnAndValue(String column, String value) {

    }

    @Override
    public void deleteByConditions(Map<String, String> conditions) {

    }

    @Override
    public void updateColumnById(String id, String targetColumn, String newValue) {

    }

    @Override
    public void updateColumnsById(String id, Map<String, String> newValues) {

    }

    @Override
    public UserCollection findById(String id) {
        return null;
    }

    @Override
    public UserCollection findAllByColumnsAndValue(Map<String, String> conditions) {
        return null;
    }

    @Override
    public UserCollection query(String query) {
        return null;
    }
}
