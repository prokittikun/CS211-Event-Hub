package cs211.project.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.UUID;

public class QuestionForShortAnsController {
    private UUID questionId;
    @FXML
    private TextField textFieldShortAnswer;

    @FXML
    private Label question;
    @FXML
    private Label order;

    @FXML
    private Label errorLabelAnswer;

    public String getQuestionId() {
        return questionId.toString();
    }

    public void setQuestionId(String questionId) {
        this.questionId = UUID.fromString(questionId);
    }

    public String getAnswer() {
        return textFieldShortAnswer.getText();
    }

    public String getQuestion() {
        return question.getText();
    }

    public void setQuestion(String question) {
        this.question.setText(question);
    }
    public void setOrder(String order) {
        this.order.setText(order);
    }
    public String getErrorLabelAnswer() {
        return errorLabelAnswer.getText();
    }

    public void setErrorLabelAnswer(String errorLabelAnswer) {
        this.errorLabelAnswer.setText(errorLabelAnswer);
    }

}
