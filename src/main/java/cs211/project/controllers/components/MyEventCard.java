package cs211.project.controllers.components;

import cs211.project.controllers.MyEventController;
import cs211.project.models.Event;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.JoinEventCollection;
import cs211.project.models.collections.QuestionCollection;
import cs211.project.models.collections.TeamCollection;
import cs211.project.services.Datasource;
import cs211.project.services.EventListFileDatasource;
import cs211.project.services.FXRouter;
import cs211.project.services.TeamListFileDatasource;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class MyEventCard {
    @FXML
    private Label eventDate;
    @FXML
    private ImageView eventImage;
    @FXML
    private Label eventLocation;
    @FXML
    private Label eventTitle;
    @FXML
    private Button eventToggleStatus;
    @FXML
    private Button orderNumber;
    @FXML
    private Label participantEvent;
    private String pathEventImage = "";
    private boolean statusEvent = true;
    private HashMap<String, Object> data;
    private MyEventController myEventController;
    private Datasource<EventCollection> datasourceEvent;
    private Datasource<TeamCollection> datasourceTeam;

    //Route
    @FXML
    public void goToListTeam() {
        try {
            FXRouter.goTo("listTeam", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goToEditEvent() {
        try {
            FXRouter.goTo("editEvent", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goToParticipant(){
        try {
            FXRouter.goTo("eventParticipant", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goToActivityEvent(){
        try {
            FXRouter.goTo("activityEvent", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onHandleDeleteEvent(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("คุณต้องการลบอีเวนต์นี้ใช่หรือไม่ ?");
        alert.setHeaderText("หากลบแล้วจะส่งผลให้ทีมภายใต้อีเวนต์นี้ถูกลบไปด้วย");

        Optional<ButtonType> result = alert.showAndWait();
        ButtonType button = result.orElse(ButtonType.CANCEL);

        if (button == ButtonType.OK) {
            //Delete Event
            datasourceEvent.deleteById((String) data.get("eventId"));
            //Delete Team
            datasourceTeam.deleteById((String) data.get("eventId"));
            myEventController.reload();
        } else {
            alert.close();
        }
    }

    //Toggle
    @FXML
    public void onHandleEventStatus() {
        statusEvent = !statusEvent;
        if(statusEvent){
            eventToggleStatus.setText("เปิด");
            eventToggleStatus.getStyleClass().remove("btn-danger");
            eventToggleStatus.getStyleClass().add("btn-success");
        }else{
            eventToggleStatus.setText("ปิด");
            eventToggleStatus.getStyleClass().remove("btn-success");
            eventToggleStatus.getStyleClass().add("btn-danger");
        }
    }

    //Init
    public MyEventCard(){
        eventDate = new Label();
        eventImage = new ImageView();
        eventLocation = new Label();
        eventTitle = new Label();
        orderNumber = new Button();
        participantEvent = new Label();
        datasourceEvent = new EventListFileDatasource("data/event", "event.csv");
        datasourceTeam = new TeamListFileDatasource("data/team", "team.csv");
    }

    //Getter
    public String getEventDate() {
        return eventDate.getText();
    }

    public String getEventImage() {
        return pathEventImage;
    }

    public String getEventLocation() {
        return eventLocation.getText();
    }

    public String getEventTitle() {
        return eventTitle.getText();
    }

    public String getOrderNumber() {
        return orderNumber.getText();
    }

    public String getParticipantEvent() {
        return participantEvent.getText();
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    //Setter
    public void setMyEventController(MyEventController myEventController) {
        this.myEventController = myEventController;
    }

    public void setEventDate(String eventDate) {
        this.eventDate.setText(eventDate);
    }

    public void setEventImage(String path) {
        Image image = new Image("file:"+"data/image/event/"+path);
        eventImage.setImage(image);
        this.pathEventImage = path;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation.setText(eventLocation);
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle.setText(eventTitle);
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber.setText(orderNumber);
    }

    public void setParticipantEvent(String participantEvent) {
        this.participantEvent.setText(participantEvent);
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
}
