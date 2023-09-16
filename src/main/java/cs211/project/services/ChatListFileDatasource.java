package cs211.project.services;

import cs211.project.models.Chat;
import cs211.project.models.Team;
import cs211.project.models.collections.ChatCollection;
import cs211.project.models.collections.TeamCollection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatListFileDatasource implements Datasource<ChatCollection> {
    private String directoryName;
    private String fileName;
    private DataFileManager dataFileManager;

    //Constructor เพื่อกำหนดที่อยู่ file csv ที่จะอ่าน
    public ChatListFileDatasource(String directoryName, String fileName) {
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
    public ChatCollection readData() {
        ChatCollection chatList = new ChatCollection();
        for (HashMap<String, String> data : dataFileManager.getData()) {
            chatList.addNewChat(new Chat(data));
        }
        return chatList;
    }

    //parameter type อย่าลืมแก้นะจ้ะ
    @Override
    public void writeData(ChatCollection data) {
        ArrayList<HashMap<String, String>> dataToWrite = new ArrayList<>();
        for (Chat chat : data.getChats()) {
            dataToWrite.add(chat.toHashMap());
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
    public ChatCollection findById(String id) {
        ChatCollection chatList = new ChatCollection();
        for (HashMap<String, String> data : dataFileManager.findById(id)) {
            chatList.addNewChat(new Chat(data));
        }
        return chatList;
    }

    @Override
    public ChatCollection findAllByColumnsAndValue(Map<String, String> conditions) {
        ChatCollection chatList = new ChatCollection();
        for (HashMap<String, String> item : dataFileManager.findAllByColumnsAndValue(conditions)) {
            chatList.addNewChat(new Chat(item));
        }
        return chatList;
    }

    @Override
    public ChatCollection query(String query) {
        ChatCollection chatList = new ChatCollection();
        for (HashMap<String, String> item : dataFileManager.query(query)) {
            chatList.addNewChat(new Chat(item));
        }
        return chatList;
    }

    @Override
    public ChatCollection queryWithOffsetAndLimit(String query, int offset, int limit) {
        ChatCollection chatList = new ChatCollection();
        for (HashMap<String, String> item : dataFileManager.queryWithOffsetAndLimit(query, offset, limit)) {
            chatList.addNewChat(new Chat(item));
        }
        return chatList;
    }
}

