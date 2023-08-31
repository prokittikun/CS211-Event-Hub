package cs211.project.models.collections;

import cs211.project.models.Question;

import java.util.ArrayList;

public class QuestionCollection {
    private ArrayList<Question> questions;
    public QuestionCollection() {
        questions = new ArrayList<>();
    }
    public void addNewQuestion(Question question){ questions.add(question); }
    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
