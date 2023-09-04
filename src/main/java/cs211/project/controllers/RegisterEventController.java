package cs211.project.controllers;

import cs211.project.controllers.components.LeftChatLayoutController;
import cs211.project.controllers.components.QuestionForShortAnsController;
import cs211.project.controllers.components.QuestionForUploadFileController;
import cs211.project.controllers.components.RightChatLayoutController;
import cs211.project.models.Chat;
import cs211.project.models.Question;
import cs211.project.models.collections.QuestionCollection;
import cs211.project.services.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class RegisterEventController {
    @FXML
    private AnchorPane navbar;
    @FXML
    private AnchorPane footer;
    private QuestionCollection questionList;
    @FXML
    private VBox showQuestion;

    @FXML
    private void initialize() {
        questionList = new QuestionCollection();
        Question question = new Question("กรุณาอัพโหลด Portfolio", "upload");
        questionList.addNewQuestion(question);
        question = new Question("มีโรคประจำตัวหรือไม่ คืออะไร", "shortAns");
        questionList.addNewQuestion(question);
        question = new Question("อยู่จังหวัดอะไร", "shortAns");
        questionList.addNewQuestion(question);
        question = new Question("อายุเท่าไหร่", "shortAns");
        questionList.addNewQuestion(question);
        initQuestion();


        //Navbar
        FXMLLoader navbarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/navbar.fxml"));
        //Footer
        FXMLLoader footerComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/footer.fxml"));
        try {
            //Navbar
            AnchorPane navbarComponent = navbarComponentLoader.load();
            navbar.getChildren().add(navbarComponent);

            //Footer
            AnchorPane footerComponent = footerComponentLoader.load();
            footer.getChildren().add(footerComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void initQuestion(){
        for (Question question : questionList.getQuestions()) {
            try {
                FXMLLoader questionLoader = new FXMLLoader();
                AnchorPane questionComponent;
                if(question.getType().equals("upload")) {
                    questionLoader.setLocation(getClass().getResource("/cs211/project/views/components/question-for-upload-file-component.fxml"));
                    questionComponent = questionLoader.load();
                    QuestionForUploadFileController questionForUploadFileController = questionLoader.getController();
                    questionForUploadFileController.setQuestion(question.getQuestion());
                }else{
                    questionLoader.setLocation(getClass().getResource("/cs211/project/views/components/question-for-short-ans-component.fxml"));
                    questionComponent = questionLoader.load();
                    QuestionForShortAnsController questionForShortAnsController = questionLoader.getController();
                    questionForShortAnsController.setQuestion(question.getQuestion());
                }

                showQuestion.getChildren().add(questionComponent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void onHandleGoToEventDetail(ActionEvent event) {
        try {
            FXRouter.goTo("eventDetail");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

