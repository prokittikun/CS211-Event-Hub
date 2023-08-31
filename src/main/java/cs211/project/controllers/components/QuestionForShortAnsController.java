package cs211.project.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class QuestionForShortAnsController {
    @FXML
    private TextField answer;

    @FXML
    private Label question;

    public String getAnswer() {
        return answer.getText();
    }

    public String getQuestion() {
        return question.getText();
    }

    public void setQuestion(String question) {
        this.question.setText(question);
    }

}
