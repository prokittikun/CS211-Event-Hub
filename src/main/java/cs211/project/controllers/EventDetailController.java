package cs211.project.controllers;

import cs211.project.controllers.components.EventCard;
import cs211.project.models.Event;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.JoinEventCollection;
import cs211.project.services.Datasource;
import cs211.project.services.EventListFileDatasource;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinEventListFileDatasource;
import cs211.project.models.JoinEvent;
import cs211.project.services.*;
import cs211.project.services.alert.ToastAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventDetailController {
    @FXML
    private AnchorPane navbar;
    @FXML
    private AnchorPane footer;
    @FXML
    private HBox eventCardHbox;
    @FXML
    private Text eventDetail;

    @FXML
    private ImageView eventImage;

    @FXML
    private Label eventLocation;

    @FXML
    private Label eventName;

    @FXML
    private Label eventParticipant;

    @FXML
    private Label eventStartDateTime;

    @FXML
    private Label eventEndDateTime;

    @FXML
    private Button registerEventButton;

    @FXML
    private Button joinTeamButton;

    private List<EventCard> eventCardList;
    private HashMap<String, Object> data;
    private UUID eventId;
    private UUID userId;
    private Datasource<EventCollection> eventDatasource;
    private Datasource<JoinEventCollection> joinEventDatasource;
    private Event event;
    private EventCollection eventRecommendCollection;

    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private boolean isJoinEvent;

    private String previousPage;

    @FXML
    private void initialize() {
        data = FXRouter.getData();
        eventId = UUID.fromString((String) data.get("eventId"));
        userId = UUID.fromString((String) data.get("userId"));
        previousPage = (String) data.get("previousPage");
        eventDatasource = new EventListFileDatasource("data/event","event.csv");
        joinEventDatasource = new JoinEventListFileDatasource("data/event", "joinEvent.csv");
        event = eventDatasource.query("id = " + eventId.toString()).getEvents().get(0);
        checkIsJoinEvent();
        initEventDetail();


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

        eventRecommendCollection = eventDatasource.readData();
        executorService.submit(() -> {
            for (Event event : eventRecommendCollection.getRandomNEvent(eventId.toString(),5)) {
                try {
                    FXMLLoader eventCardLoader = new FXMLLoader();
                    eventCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/event-card.fxml"));
                    AnchorPane eventComponent = eventCardLoader.load();
                    EventCard indexEventCard = eventCardLoader.getController();
                    indexEventCard.setEventImage(event.getImage());
                    indexEventCard.setEventName(event.getName());
                    indexEventCard.setEventDate(event.getStartDate());
                    indexEventCard.setEventLocation(event.getLocation());

                    JoinEventCollection joinEventCollection2 = joinEventDatasource.query("eventId = " + event.getId());
                    indexEventCard.setEventParticipant(joinEventCollection2.getJoinEvents().size() + "/" + event.getMaxParticipant());
                    indexEventCard.setCurrentParticipant(joinEventCollection2.getJoinEvents().size());


                    HashMap<String, Object> data = new HashMap<>();
                    data.put("userId", this.data.get("userId"));
                    data.put("eventId", event.getId());
                    indexEventCard.setData(data);
                    javafx.application.Platform.runLater(() -> {
                        eventCardHbox.getChildren().add(eventComponent);
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void initEventDetail(){
        setEventName(event.getName());
        setEventLocation(event.getLocation());
        setEventDetail(event.getDetail());
        setEventStartDateTime(DateTimeService.toString(event.getStartDate())+" "+event.getStartTime());
        setEventEndDateTime(DateTimeService.toString(event.getEndDate())+" "+event.getEndTime());
        setEventImage(event.getImage());

        JoinEventCollection joinEventCollection = joinEventDatasource.query("eventId = " + event.getId());
        setEventParticipant(joinEventCollection.getJoinEvents().size() + "/" + event.getMaxParticipant());
    }

    private void checkIsJoinEvent() {
        JoinEventCollection joinEventCollection = joinEventDatasource.query("eventId = " + event.getId() + " AND userId = " + userId.toString());
        if (joinEventCollection.getJoinEvents().size() == 1 || event.getUserId().equals(userId.toString())) {
            registerEventButton.setText("ดูตารางกิจกรรม");
            joinTeamButton.setVisible(false);
            isJoinEvent = true;
        }else {
            isJoinEvent = false;
        }
    }

    @FXML
    void onHandleRegisterEvent(ActionEvent registerEvent) {
        //check event is full

        if (isJoinEvent) {

            try {
                HashMap<String, Object> data = new HashMap<>();
                data.put("userId", this.data.get("userId"));
                data.put("eventId", event.getId());
                data.put("previousPage", "eventDetail");
                FXRouter.goTo("eventActivity", data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            JoinEventCollection joinEventCollection = joinEventDatasource.query("eventId = " + event.getId());
            if (event.getMaxParticipant() <= joinEventCollection.getJoinEvents().size()) {
                ToastAlert.show("ไม่สามารถลงทะเบียนกิจกรรมได้ เนื่องจากจำนวนผู้เข้าร่วมกิจกรรมเต็มแล้ว", ToastAlert.AlertType.ERROR);
                return;
            }
            JoinEventCollection newJoinEventCollection = new JoinEventCollection();
            newJoinEventCollection.addJoinEvent(new JoinEvent(UUID.randomUUID().toString(),eventId.toString(),userId.toString(), DateTimeService.getCurrentDate(),"0"));
            joinEventDatasource.writeData(newJoinEventCollection);
            checkIsJoinEvent();
            initEventDetail();
        }
    }

    @FXML
    void onHandleJoinTeam(ActionEvent joinTeam) {
        try {
            FXRouter.goTo("joinTeam", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleGoToPreviousPage(ActionEvent event) {
        try {
            FXRouter.goTo(this.previousPage, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getEventDetail() {
        return eventDetail.getText();
    }

    public void setEventDetail(String eventDetail) {
        this.eventDetail.setText(eventDetail);
    }

    public String getEventImage() {
        return eventImage.getImage().getUrl();
    }

    public void setEventImage(String eventImage) {
        Image image = new Image("file:data" + File.separator + "image" + File.separator + "event" + File.separator + eventImage);
        this.eventImage.setImage(image);
    }

    public String getEventLocation() {
        return eventLocation.getText();
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation.setText(eventLocation);
    }

    public String getEventName() {
        return eventName.getText();
    }

    public void setEventName(String eventName) {
        this.eventName.setText(eventName);
    }

    public String getEventParticipant(

    ) {
        return eventParticipant.getText();
    }

    public void setEventParticipant(String eventParticipant) {
        this.eventParticipant.setText(eventParticipant);
    }

    public String getEventStartDateTime() {
        return eventStartDateTime.getText();
    }

    public void setEventStartDateTime(String eventStartDate) {
        this.eventStartDateTime.setText(eventStartDate);
    }

    public String getEventEndDateTime() {
        return eventEndDateTime.getText();
    }

    public void setEventEndDateTime(String eventEndDate) {
        this.eventEndDateTime.setText(eventEndDate);
    }
}
