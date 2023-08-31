package cs211.project.controllers.components;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

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

    //Route
    @FXML
    public void goToFormEvent() {
        try {
            FXRouter.goTo("createForm");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goToListTeam() {
        try {
            FXRouter.goTo("listTeam");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goToEditEvent() {
        try {
            FXRouter.goTo("editEvent");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goToParticipant(){

    }

    //Toggle
    @FXML
    public void onHandleEventStatus() {
        statusEvent = !statusEvent;
        if(statusEvent){
            eventToggleStatus.setText("Open");
        }else{
            eventToggleStatus.setText("Close");
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

    //Setter
    public void setEventDate(String eventDate) {
        this.eventDate.setText(eventDate);
    }

    public void setEventImage(String path) {
        Image image = new Image(path);
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
}
