package cs211.project.controllers;

import cs211.project.controllers.components.EventCard;
import cs211.project.controllers.components.JoinTeamCard;
import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.TeamMember;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.JoinEventCollection;
import cs211.project.models.collections.TeamCollection;
import cs211.project.models.collections.TeamMemberCollection;
import cs211.project.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import cs211.project.controllers.components.JoinTeamCard;
import javafx.scene.shape.Rectangle;

public class JoinTeamController {
    @FXML
    private AnchorPane navbar;
    @FXML
    private AnchorPane footer;
    @FXML
    private Label eventName;

    @FXML
    private VBox joinTeamVbox;

    private List<JoinTeamCard> joinTeamCardList;
    private TeamCollection teamCollection;
    private EventCollection eventCollection;
    private TeamListFileDatasource teamDatasource;
    private EventListFileDatasource eventDatasource;
    private JoinEventListFileDatasource joinEventDatasource;
    private TeamMemberListFileDatasource teamMemberDatasource;
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private HashMap<String, Object> data;

    private UUID eventId;

    @FXML
    private void initialize() {
        data = FXRouter.getData();
        System.out.println("data = " + data);

        eventId = UUID.fromString((String) data.get("eventId"));
//        System.out.println("eventId"+eventId);
        eventDatasource = new EventListFileDatasource("data/event", "event.csv");
        teamDatasource = new TeamListFileDatasource("data/team", "team.csv");
        joinEventDatasource = new JoinEventListFileDatasource("data/event", "joinEvent.csv");
        teamMemberDatasource = new TeamMemberListFileDatasource("data/team", "teamMember.csv");

        Event event = eventDatasource.query("id = " + this.eventId.toString()).getEvents().get(0);
        eventName.setText(event.getName());

        initJoinTeam();

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
    }

    public void reload() {
        this.joinTeamVbox.getChildren().clear();
        initJoinTeam();
    }

    void initJoinTeam() {
        teamCollection = teamDatasource.query("eventId = " + eventId.toString());
        System.out.println(teamCollection.getTeams().size());
//        final int[] i = {1};
        executorService.submit(() -> {
            LocalDateTime currentDateTime = LocalDateTime.parse(DateTimeService.getCurrentDate()+"T"+DateTimeService.getCurrentTime());
            for (Team team : teamCollection.getTeams()) {
                try {
                    FXMLLoader joinTeamCardLoader = new FXMLLoader();
                    joinTeamCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/join-team-card.fxml"));
                    AnchorPane joinTeamCardComponent = joinTeamCardLoader.load();

//                    Event event = eventDatasource.query("id = " + team.getEventId()).getEvents().get(0);
                    JoinTeamCard joinTeamCard = joinTeamCardLoader.getController();
//                    joinTeamCard.setEventDate(event.getStartDate());
//                    joinTeamCard.setEventImage(event.getImage());
//                    joinTeamCard.setEventLocation(event.getLocation());
//                    joinTeamCard.setEventName(event.getName());
                    joinTeamCard.setTeamId(team.getId());

                    TeamMemberCollection teamMemberCollection = teamMemberDatasource.query("teamId = " + team.getId());
                    joinTeamCard.setTeamParticipant(teamMemberCollection.getTeamMembers().size() + "/" + team.getMaxMember());

                    joinTeamCard.setTeamName(team.getName());
//                    joinTeamCard.setTeamOrder(Integer.toString(++i[0]));
                    joinTeamCard.setUserId((String) data.get("userId"));
                    joinTeamCard.setJoinTeamController(this);
                    LocalDateTime teamStartDateTime = LocalDateTime.parse(team.getStartDate() + "T" + team.getStartTime());
                    LocalDateTime teamEndDateTime = LocalDateTime.parse(team.getEndDate() + "T" + team.getEndTime());
                    if (currentDateTime.isAfter(teamStartDateTime) && currentDateTime.isBefore(teamEndDateTime)) {
                        joinTeamCard.setJoinTeamButton(false,"เข้าร่วมทีม");
                        System.out.println("In range");
                    }else{
                        joinTeamCard.setJoinTeamButton(true, "ไม่อยู่ระหว่างการรับสมัคร");
                        System.out.println("not in range");
                    }
                    TeamMember isMe = teamMemberCollection.findTeamMemberById((String) data.get("userId"));
                    System.out.println("userId = " + (String) data.get("userId"));
                    if (isMe != null) {
                        joinTeamCard.isJoinedTeam();
                    }
                    joinTeamCard.checkTeamIsFull();
                    joinTeamCard.setDate(DateTimeService.toString(team.getStartDate()) + " " + team.getStartTime(), DateTimeService.toString(team.getEndDate()) + " " + team.getEndTime());
                    javafx.application.Platform.runLater(() -> {
                        joinTeamVbox.getChildren().add(joinTeamCardComponent);
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    @FXML
    void onHandleBackToEventDetail(ActionEvent event) {
        try {
            FXRouter.goTo("eventDetail", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
