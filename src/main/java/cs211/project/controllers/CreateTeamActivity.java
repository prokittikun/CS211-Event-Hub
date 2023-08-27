package cs211.project.controllers;

import cs211.project.services.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class CreateTeamActivity {
    @FXML
    private Circle activityCreatorImage;

    @FXML
    private Label activityCreatorName;

    @FXML
    private TableColumn<?, ?> dateColumn;

    @FXML
    private Label eventDate;

    @FXML
    private ImageView eventImage;

    @FXML
    private Label eventLocation;

    @FXML
    private Label eventName;

    @FXML
    private Label eventParticipant;

    @FXML
    private TextField inputActivityDateEnd;

    @FXML
    private TextField inputActivityDateStart;

    @FXML
    private TextArea inputActivityDetail;

    @FXML
    private TextField inputActivityName;

    @FXML
    private TextField inputActivityTimeEnd;

    @FXML
    private TextField inputActivityTimeStart;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableView<?> scheduleTable;

    @FXML
    private Label teamName;

    @FXML
    private TableColumn<?, ?> timestampColumn;

    @FXML
    private Label titlePage;

    @FXML
    private AnchorPane navbar;

    @FXML
    private AnchorPane footer;

    @FXML
    private void initialize() {
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

    @FXML
    void onHandleBackToPreviousPage(ActionEvent event) {
        try {
            FXRouter.goTo("teamManagement");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleSaveButton(ActionEvent event) {

    }

}
