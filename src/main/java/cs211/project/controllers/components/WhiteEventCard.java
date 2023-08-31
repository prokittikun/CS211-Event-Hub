package cs211.project.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class WhiteEventCard {

    @FXML
    private Label eventDate;

    @FXML
    private Rectangle eventImage;
    @FXML
    private String imagePath;

    @FXML
    private Label eventLocation;

    @FXML
    private Label eventName;

    @FXML
    private Label eventParticipant;

    public WhiteEventCard() {
        this.eventDate = new Label();
        this.eventImage = new Rectangle();
        this.eventLocation = new Label();
        this.eventName = new Label();
        this.eventParticipant = new Label();
    }

    @FXML
    private void initialize() {}

    public String getEventDate() { return eventDate.getText(); }

    public String getEventImage() { return imagePath; }

    public String getEventLocation() { return eventLocation.getText(); }

    public String getEventName() { return eventName.getText(); }

    public String getEventParticipant() { return eventParticipant.getText(); }

    public void setEventDate(String eventDate) { this.eventDate.setText(eventDate); }

    public void setEventImage(String eventImage) {
        Image randomImage = new Image(eventImage);
        imagePath = eventImage;
        this.eventImage.setFill(new ImagePattern(randomImage));
    }

    public void setEventLocation(String eventLocation) { this.eventLocation.setText(eventLocation); }

    public void setEventName(String eventName) { this.eventName.setText(eventName); }

    public void setEventParticipant(String eventParticipant) { this.eventParticipant.setText(eventParticipant); }
}
