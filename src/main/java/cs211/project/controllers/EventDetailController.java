package cs211.project.controllers;

import cs211.project.controllers.components.EventCard;
import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.TeamMember;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.JoinEventCollection;
import cs211.project.models.collections.TeamCollection;
import cs211.project.models.collections.TeamMemberCollection;
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
import java.time.LocalDateTime;
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

    @FXML
    private Label registrationPeriod;
    private List<EventCard> eventCardList;
    private HashMap<String, Object> data;
    private UUID eventId;
    private UUID userId;
    private Datasource<EventCollection> eventDatasource;
    private Datasource<JoinEventCollection> joinEventDatasource;
    private Datasource<TeamMemberCollection> teamMemberDatasource;
    private Datasource<TeamCollection> teamDatasource;

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
        eventDatasource = new EventListFileDatasource("data/event", "event.csv");
        joinEventDatasource = new JoinEventListFileDatasource("data/event", "joinEvent.csv");
        teamDatasource = new TeamListFileDatasource("data/team", "team.csv");
        teamMemberDatasource = new TeamMemberListFileDatasource("data/team", "teamMember.csv");
        event = eventDatasource.query("id = " + eventId.toString()).getEvents().get(0);
        eventRecommendCollection = new EventCollection();
        checkInRegistrationPeriod();
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

        EventCollection eventCollection = eventDatasource.query("status = true AND NOT id = " + eventId.toString());
        eventRecommendCollection.setEvents(eventCollection.sortByBeforeEndDate());
        executorService.submit(() -> {
            for (Event event : eventRecommendCollection.getRandomEvent(5)) {
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
                    data.put("previousPage", this.previousPage);
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

    private void checkInRegistrationPeriod() {
        LocalDateTime currentDateTime = LocalDateTime.parse(DateTimeService.getCurrentDate() + "T" + DateTimeService.getCurrentTime());
        LocalDateTime openDateTime = LocalDateTime.parse(event.getOpenDate() + "T" + event.getOpenTime());
        LocalDateTime closeDateTime = LocalDateTime.parse(event.getCloseDate() + "T" + event.getCloseTime());
        if (currentDateTime.isBefore(openDateTime) || currentDateTime.isAfter(closeDateTime)) {
            registerEventButton.setDisable(true);
            registerEventButton.setText("ไม่อยู่ในช่วงลงทะเบียน");
        }
    }

    private void initEventDetail() {
        setEventName(event.getName());
        setEventLocation(event.getLocation());
        setEventDetail(event.getDetail());
        setEventStartDateTime(DateTimeService.toString(event.getStartDate()) + " " + event.getStartTime());
        setEventEndDateTime(DateTimeService.toString(event.getEndDate()) + " " + event.getEndTime());
        setEventImage(event.getImage());
        setRegistrationPeriod(DateTimeService.toString(event.getOpenDate()) + " " + event.getOpenTime() + " - " + DateTimeService.toString(event.getCloseDate()) + " " + event.getCloseTime());
        JoinEventCollection joinEventCollection = joinEventDatasource.query("eventId = " + event.getId());
        setEventParticipant(joinEventCollection.getJoinEvents().size() + "/" + event.getMaxParticipant());
    }

    private void checkIsJoinEvent() {
        JoinEventCollection joinEventCollection = joinEventDatasource.query("eventId = " + event.getId() + " AND userId = " + userId.toString());
        TeamMemberCollection teamMemberCollection = teamMemberDatasource.query("userId = " + userId.toString());
        boolean isMemberOfTeamInEvent = false;
        for (TeamMember teamMember : teamMemberCollection.getTeamMembers()) {
            Team team = teamDatasource.query("id = " + teamMember.getTeamId()).getTeams().get(0);
            if (team.getEventId().equals(eventId.toString())) {
                isMemberOfTeamInEvent = true;
                break;
            }
        }
        if (joinEventCollection.getJoinEvents().size() == 1 || event.getUserId().equals(userId.toString())) {
            registerEventButton.setText("ดูตารางกิจกรรม");
            registerEventButton.setDisable(false);
            joinTeamButton.setVisible(false);
            isJoinEvent = true;
        } else if (isMemberOfTeamInEvent) {
            registerEventButton.setText("ดูตารางกิจกรรม");
            registerEventButton.setDisable(false);
            isJoinEvent = true;
        } else {
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
                data.put("previousPage", this.previousPage);
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
            newJoinEventCollection.addJoinEvent(new JoinEvent(UUID.randomUUID().toString(), eventId.toString(), userId.toString(), DateTimeService.getCurrentDate()));
            joinEventDatasource.writeData(newJoinEventCollection);
            ToastAlert.show("ลงทะเบียนสำเร็จแล้ว", ToastAlert.AlertType.SUCCESS);
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
            FXRouter.goTo((String) data.get("previousPage"), data);
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

    public void setRegistrationPeriod(String registrationPeriod) {
        this.registrationPeriod.setText(registrationPeriod);
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
