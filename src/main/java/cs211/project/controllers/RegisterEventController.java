package cs211.project.controllers;

import cs211.project.controllers.components.QuestionForShortAnsController;
import cs211.project.controllers.components.QuestionForUploadFileController;
import cs211.project.models.Answer;
import cs211.project.models.Event;
import cs211.project.models.Question;
import cs211.project.models.collections.AnswerCollection;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.QuestionCollection;
import cs211.project.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.HashMap;

public class RegisterEventController {
    @FXML
    private AnchorPane navbar;
    @FXML
    private AnchorPane footer;
    private QuestionCollection questionCollection;
    @FXML
    private VBox showQuestion;
    @FXML
    private TextField textFieldShortAnswer;
    @FXML
    private Label errorLabelAnswer;
    @FXML
    private Label errorLabelUpload;
//    @FXML
//    private VBox showConfirmText;
//    @FXML
    private Label confirmText;
    private Datasource<QuestionCollection> questionDatasource;
    private Datasource<AnswerCollection> answerDatasource;
    private Datasource<EventCollection> eventDatasource;
    private HashMap<String, Object> data;
    private File file;

    @FXML
    private ImageView imageView;
    @FXML
    private void initialize() {
        data = FXRouter.getData();
        questionDatasource = new QuestionListFileDatasource("data/event","question.csv");
        answerDatasource = new AnswerListFileDatasource("data/event", "answer.csv");
//        questionCollection = questionDatasource.query("eventId = 3778a5c2-5070-11ee-be56-0242ac120002");
        
        initQuestion();

        //Navbar
        FXMLLoader navbarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/navbar.fxml"));
        //Footer
        FXMLLoader footerComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/footer.fxml"));
        try {
            //Navbar
            AnchorPane navbarComponent = navbarComponentLoader.load();
            NavbarController navbarController = navbarComponentLoader.getController();
            navbarController.setData(data);
            navbar.getChildren().add(navbarComponent);

            //Footer
            AnchorPane footerComponent = footerComponentLoader.load();
            footer.getChildren().add(footerComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Get Data When Edit
//        if(data.get("eventId") != null){
//            String eventId = (String) data.get("eventId");
//            QuestionCollection questionCollection = questionDatasource.query("id = " + eventId);
//            Question question = questionCollection.getQuestions().get(0);
//            textFieldShortAnswer.setText(question.getAnswer());
//            try {
//                Image image = new Image(new FileInputStream("data/image/fileForRegisterEvent"+event.getImage()));
//                imageView.setImage(image);
//                //Set Image File
//                file = new File(event.getImage());
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private void initQuestion(){
//        questionCollection = questionDatasource.query("eventId = 3778a5c2-5070-11ee-be56-0242ac120002");
//        Event event = eventDatasource.query("id = " + questionCollection.getEventId()).getEvents().get(0);
//        confirmText.setText("คุณยืนยันที่จะลงทะเบียนงานอีเวนต์ " + event.getName() + " ซึ่งจัดขึ้นตั้งแต่วันที่ " + event.getStartDate() + " ถึงวันที่ " + event.getEndDate());
        int i = 0;
        for (Question question : questionCollection.getQuestions()) {
            try {
                FXMLLoader questionLoader = new FXMLLoader();
                AnchorPane questionComponent;
//                Event event = eventDatasource.query("id = " + question.getEventId()).getEvents().get(0);
//                confirmText.setText("คุณยืนยันที่จะลงทะเบียนงานอีเวนต์ " + event.getName() + " ซึ่งจัดขึ้นตั้งแต่วันที่ " + event.getStartDate() + " ถึงวันที่ " + event.getEndDate());
                ++i;
                if(question.getType().equals("upload")) {
                    questionLoader.setLocation(getClass().getResource("/cs211/project/views/components/question-for-upload-file-component.fxml"));
                    questionComponent = questionLoader.load();
                    QuestionForUploadFileController questionForUploadFileController = questionLoader.getController();
                    questionForUploadFileController.setQuestion(question.getQuestion());
                    questionForUploadFileController.setOrder("คำถามที่ " + i );
                    questionForUploadFileController.setErrorLabelUpload("");
                } else {
                    questionLoader.setLocation(getClass().getResource("/cs211/project/views/components/question-for-short-ans-component.fxml"));
                    questionComponent = questionLoader.load();
                    QuestionForShortAnsController questionForShortAnsController = questionLoader.getController();
                    questionForShortAnsController.setQuestion(question.getQuestion());
                    questionForShortAnsController.setOrder("คำถามที่ " + i );
                    questionForShortAnsController.setErrorLabelAnswer("");
                }
//                showConfirmText.getChildren().add(confirmText);
                showQuestion.getChildren().add(questionComponent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
// back button
    @FXML
    void onHandleGoToEventDetail(ActionEvent event) {
        try {
            FXRouter.goTo("eventDetail");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onHandleRegisterEvent() {
        boolean isValid = true;
        if (textFieldShortAnswer.getText().isEmpty()) {
            errorLabelAnswer.setText("Answer is required");
            isValid = false;
        } else {
            errorLabelAnswer.setText("");
        }

        if (imageView.getImage() == null) {
            errorLabelUpload.setText("File is required");
            isValid = false;
        } else {
            errorLabelUpload.setText("");
        }

        if (isValid) {
            String shortAnswer = textFieldShortAnswer.getText();
            String userId = (String) data.get("userId");
            String timestamp = DateTimeService.getCurrentDateTime();
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("data/image/fileForRegisterEvent");
                if (!destDir.exists()) destDir.mkdirs();
                // RENAME FILE
                String[] fileSplit = file.getName().split("\\.");
                String filename =
                        LocalDate.now() +
                                "_" +
                                System.currentTimeMillis() +
                                "." +
                                fileSplit[fileSplit.length - 1];
                Path target = FileSystems
                        .getDefault()
                        .getPath(
                                destDir.getAbsolutePath() +
                                        System.getProperty("file.separator") +
                                        filename
                        );
                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                Files.copy(
                        file.toPath(),
                        target,
                        StandardCopyOption.REPLACE_EXISTING
                );

                for (Question question : questionCollection.getQuestions()) {
                    AnswerCollection answerCollection = new AnswerCollection();
                    try {
                        if (question.getType().equals("upload")) {
                            Answer answer = new Answer(
                                    data.get("userId").toString(),
                                    shortAnswer,
                                    data.get("questionId").toString(),
                                    timestamp
                            );
                            answerCollection.addAnswer(answer);
                        } else {
                            Answer answer = new Answer(
                                    data.get("userId").toString(),
                                    filename,
                                    data.get("questionId").toString(),
                                    timestamp
                            );
                            answerCollection.addAnswer(answer);
                        }
                        answerDatasource.writeData(answerCollection);
                        //Collection
//                        AnswerCollection answerCollection = new AnswerCollection();
//                        answerCollection.addAnswer(answer);
//                        answerDatasource.writeData(answerCollection);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
//                }else{ //Edit
//                    Answer answer = new Answer(
//                            data.get("userId").toString(),
//                            shortAnswer,
//                            data.get("questionId").toString(),
//                            timestamp
//                    );
//                }
                //Go to My Event
                try {
                    FXRouter.goTo("eventDetail", data);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

