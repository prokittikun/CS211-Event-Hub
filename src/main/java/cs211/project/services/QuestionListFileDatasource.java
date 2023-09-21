package cs211.project.services;

import cs211.project.models.Question;
import cs211.project.models.collections.QuestionCollection;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuestionListFileDatasource implements Datasource<QuestionCollection> {
    private final String directoryName;
    private final String fileName;
    private final DataFileManager dataFileManager;

    public QuestionListFileDatasource(String directoryName, String fileName) {
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
    public QuestionCollection readData() {
        QuestionCollection questionList = new QuestionCollection();
        for (HashMap<String, String> data : dataFileManager.getData()) {
            questionList.addNewQuestion(new Question(data));
        }
        return questionList;
    }
    @Override
    public void writeData(QuestionCollection data) {
        ArrayList<HashMap<String, String>> dataToWrite = new ArrayList<>();
        for (Question question : data.getQuestions()) {
            dataToWrite.add(question.toHashMap());
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
    public QuestionCollection findById(String id) {
        QuestionCollection questionList = new QuestionCollection();
        for (HashMap<String, String> data : dataFileManager.findById(id)) {
            questionList.addNewQuestion(new Question(data));
        }
        return questionList;
    }

    @Override
    public QuestionCollection findAllByColumnsAndValue(Map<String, String> conditions) {
        QuestionCollection questionList = new QuestionCollection();
        for (HashMap<String, String> item : dataFileManager.findAllByColumnsAndValue(conditions)) {
            questionList.addNewQuestion(new Question(item));
        }
        return questionList;
    }

    @Override
    public QuestionCollection query(String query) {
        QuestionCollection questionList = new QuestionCollection();
        for (HashMap<String, String> item : dataFileManager.query(query)) {
            questionList.addNewQuestion(new Question(item));
        }
        return questionList;
    }

    @Override
    public QuestionCollection queryWithOffsetAndLimit(String query, int offset, int limit) {
        return null;
    }

}
