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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private Label eventStartDate;
    private List<EventCard> eventCardList;
    private HashMap<String, Object> data;
    private UUID eventId;
    private UUID userId;
    private Datasource<EventCollection> eventDatasource;
    private Datasource<JoinEventCollection> joinEventDatasource;
    private Event event;
    private EventCollection eventRecommendCollection;
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @FXML
    private void initialize() {
        data = FXRouter.getData();
        eventId = UUID.fromString((String) data.get("eventId"));
        userId = UUID.fromString((String) data.get("userId"));
        eventDatasource = new EventListFileDatasource("data/event","event.csv");
        joinEventDatasource = new JoinEventListFileDatasource("data/event", "joinEvent.csv");

        event = eventDatasource.query("id = " + eventId.toString()).getEvents().get(0);
        setEventName(event.getName());
        setEventLocation(event.getLocation());
        setEventDetail(event.getDetail());
        setEventStartDate(event.getStartDate());


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

                    JoinEventCollection joinEventCollection = joinEventDatasource.query("eventId = " + event.getId());
                    indexEventCard.setEventParticipant(joinEventCollection.getJoinEvents().size() + "/" + event.getMaxParticipant());
                    indexEventCard.setCurrentParticipant(joinEventCollection.getJoinEvents().size());


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


    @FXML
    void onHandleRegisterEvent(ActionEvent registerEvent) {
        JoinEventCollection newJoinEventCollection = new JoinEventCollection();
        newJoinEventCollection.addJoinEvent(new JoinEvent(UUID.randomUUID().toString(),eventId.toString(),userId.toString(), DateTimeService.getCurrentDate(),"0"));
        joinEventDatasource.writeData(newJoinEventCollection);
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
            FXRouter.goTo("allEvent");
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

    public String getEventParticipant() {
        return eventParticipant.getText();
    }

    public void setEventParticipant(String eventParticipant) {
        this.eventParticipant.setText(eventParticipant);
    }

    public String getEventStartDate() {
        return eventStartDate.getText();
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate.setText(eventStartDate);
    }
}
