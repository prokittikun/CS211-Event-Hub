package cs211.project.controllers.components;

import cs211.project.controllers.MyTeamController;
import cs211.project.controllers.RegisterEventController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class QuestionForUploadFileController {

    @FXML
    private Label question;
    @FXML
    private Label order;

    private RegisterEventController registerEventController;
    private File file;
    @FXML
    private ImageView imageView;
    @FXML
    private Label errorLabelUpload;

    @FXML
    public void onHandleUploadButton(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser
                .getExtensionFilters()
                .add(
                        new FileChooser.ExtensionFilter(
                                "images PNG JPG",
                                "*.png",
                                "*.jpg",
                                "*.jpeg"
                        )
                );
        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) event.getSource();
        file = chooser.showOpenDialog(source.getScene().getWindow());
        // SET IMAGE TO IMAGEVIEW
        if (file != null) {
            try {
                Image image = new Image(new FileInputStream(file));
                imageView.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public void setParentController(RegisterEventController parentController) {
        this.registerEventController = parentController;
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
    public String getErrorLabelUpload() {
        return errorLabelUpload.getText();
    }

    public void setErrorLabelUpload(String errorLabelUpload) {
        this.errorLabelUpload.setText(errorLabelUpload);
    }
}
