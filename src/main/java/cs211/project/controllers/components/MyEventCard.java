package cs211.project.controllers.components;

import cs211.project.controllers.MyEventController;
import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.collections.*;
import cs211.project.services.*;
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

    private Datasource<TeamMemberCollection> datasourceTeamMember;

    private Datasource<JoinEventCollection> datasourceJoinEvent;

    //Init
    public MyEventCard() {
        eventDate = new Label();
        eventImage = new ImageView();
        eventLocation = new Label();
        eventTitle = new Label();
        orderNumber = new Button();
        participantEvent = new Label();
    }

    //Init
    @FXML
    public void initialize() {
        datasourceEvent = new EventListFileDatasource("data/event", "event.csv");
        datasourceTeam = new TeamListFileDatasource("data/team", "team.csv");
        datasourceTeamMember = new TeamMemberListFileDatasource("data/team", "teamMember.csv");
        datasourceJoinEvent = new JoinEventListFileDatasource("data/event", "joinEvent.csv");
    }

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
    public void goToParticipant() {
        try {
            FXRouter.goTo("eventParticipant", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goToActivityEvent() {
        try {
            data.put("previousPage", "myEvent");
            FXRouter.goTo("eventActivity", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onHandleDeleteEvent() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("คุณต้องการลบอิเวนต์นี้ใช่หรือไม่ ?");
        alert.setHeaderText("หากลบแล้วจะส่งผลให้ทีมภายใต้อิเวนต์นี้ถูกลบไปด้วย");

        Optional<ButtonType> result = alert.showAndWait();
        ButtonType button = result.orElse(ButtonType.CANCEL);

        if (button == ButtonType.OK) {
            //Delete TeamMember
            TeamCollection teamCollection = datasourceTeam.query("eventId = " + data.get("eventId").toString());
            for (Team team : teamCollection.getTeams()) {
                datasourceTeamMember.deleteAllByColumnAndValue("teamId", team.getId());
                //delete Team
                datasourceTeam.deleteById(team.getId());
            }
            //Delete JoinEvent
            datasourceJoinEvent.deleteAllByColumnAndValue("eventId", (String) data.get("eventId"));
            //Delete Event
            datasourceEvent.deleteById((String) data.get("eventId"));
            myEventController.reload();
        } else {
            alert.close();
        }
    }

    //Toggle
    @FXML
    public void onHandleEventStatus() {
        statusEvent = !statusEvent;
        datasourceEvent.updateColumnById(data.get("eventId").toString(), "status", String.valueOf(statusEvent));
        setStatusEvent(statusEvent);
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
        Image image = new Image("file:" + "data/image/event/" + path);
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

    public void setStatusEvent(boolean statusEvent) {
        this.statusEvent = statusEvent;
        if (statusEvent) {
            eventToggleStatus.setText("เปิด");
            eventToggleStatus.getStyleClass().remove("btn-danger");
            eventToggleStatus.getStyleClass().add("btn-success");
        } else {
            eventToggleStatus.setText("ปิด");
            eventToggleStatus.getStyleClass().remove("btn-success");
            eventToggleStatus.getStyleClass().add("btn-danger");
        }
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
}
