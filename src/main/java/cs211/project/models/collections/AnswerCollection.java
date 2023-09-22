package cs211.project.models.collections;

import cs211.project.models.Answer;

import java.util.ArrayList;

public class AnswerCollection {
    private ArrayList<Answer> answers;
    public AnswerCollection() {
        answers = new ArrayList<>();
    }

    public void addAnswer(Answer answer) { answers.add(answer); }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }
}
