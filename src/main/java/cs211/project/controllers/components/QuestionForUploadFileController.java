package cs211.project.controllers.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class QuestionForUploadFileController {

    @FXML
    private Label question;

    @FXML
    private Button uploadFileButton;

    @FXML
    void onHandleUploadFile(ActionEvent event) {

    }

    public String getQuestion() {
        return question.getText();
    }

    public void setQuestion(String question) {
        this.question.setText(question);
    }

}
