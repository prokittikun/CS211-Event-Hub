package cs211.project.services;

import cs211.project.models.Answer;
import cs211.project.models.collections.AnswerCollection;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnswerListFileDatasource implements Datasource<AnswerCollection> {
    private final String directoryName;
    private final String fileName;
    private final DataFileManager dataFileManager;

    public AnswerListFileDatasource(String directoryName, String fileName) {
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
    public AnswerCollection readData() {
        AnswerCollection answerList = new AnswerCollection();
        for (HashMap<String, String> data : dataFileManager.getData()) {
            answerList.addAnswer(new Answer(data));
        }
        return answerList;
    }
    @Override
    public void writeData(AnswerCollection data) {
        ArrayList<HashMap<String, String>> dataToWrite = new ArrayList<>();
        for (Answer answer : data.getAnswers()) {
            dataToWrite.add(answer.toHashMap());
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
    public void deleteByConditions(Map<String, String> conditions) {
        dataFileManager.deleteByConditions(conditions);
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
    public AnswerCollection findById(String id) {
        AnswerCollection answerList = new AnswerCollection();
        for (HashMap<String, String> data : dataFileManager.findById(id)) {
            answerList.addAnswer(new Answer(data));
        }
        return answerList;
    }

    @Override
    public AnswerCollection findAllByColumnsAndValue(Map<String, String> conditions) {
        AnswerCollection answerList = new AnswerCollection();
        for (HashMap<String, String> item : dataFileManager.findAllByColumnsAndValue(conditions)) {
            answerList.addAnswer(new Answer(item));
        }
        return answerList;
    }

    @Override
    public AnswerCollection query(String query) {
        AnswerCollection answerList = new AnswerCollection();
        for (HashMap<String, String> item : dataFileManager.query(query)) {
            answerList.addAnswer(new Answer(item));
        }
        return answerList;
    }

    @Override
    public AnswerCollection queryWithOffsetAndLimit(String query, int offset, int limit) {
        return null;
    }

}
