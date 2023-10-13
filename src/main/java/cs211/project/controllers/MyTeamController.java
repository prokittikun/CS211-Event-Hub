package cs211.project.controllers;

import cs211.project.controllers.components.MyTeamCardController;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyTeamController {

    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    @FXML
    private AnchorPane navbar;

    @FXML
    private AnchorPane footer;

    private TeamMemberCollection teamMemberCollection;

    @FXML
    private VBox myTeamComponent;

    @FXML
    private RadioButton inProgressButton;

    @FXML
    private RadioButton allTeamButton;

    @FXML
    private RadioButton finishedButton;
    private Datasource<TeamCollection> teamDatasource;

    private Datasource<TeamMemberCollection> teamMemberDatasource;

    private Datasource<EventCollection> eventDatasource;

    private Datasource<JoinEventCollection> joinEventDatasource;
    private HashMap<String, Object> data;

    private String userId;

    @FXML
    private void initialize() {
        data = FXRouter.getData();
        userId = (String) data.get("userId");
        teamMemberDatasource = new TeamMemberListFileDatasource("data/team", "teamMember.csv");
        teamDatasource = new TeamListFileDatasource("data/team", "team.csv");
        eventDatasource = new EventListFileDatasource("data/event", "event.csv");
        joinEventDatasource = new JoinEventListFileDatasource("data/event", "joinEvent.csv");
        allTeamButton.setSelected(true);
        ToggleGroup toggleGroup1 = new ToggleGroup();
        inProgressButton.setToggleGroup(toggleGroup1);
        allTeamButton.setToggleGroup(toggleGroup1);
        finishedButton.setToggleGroup(toggleGroup1);
        teamMemberCollection = teamMemberDatasource.query("userId = " + this.userId);
//        teamList
        initMyTeam();

        //Navbar
        FXMLLoader navbarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/navbar.fxml"));
        //Footer
        FXMLLoader footerComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/footer.fxml"));
        try {
            //Navbar
            AnchorPane navbarComponent = navbarComponentLoader.load();
            //get controller
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


    private void initMyTeam() {
        executorService.submit(() -> {
            int i = 0;
            for (TeamMember teamMember : teamMemberCollection.getTeamMembers()) {
                try {
                    Team team = teamDatasource.query("id = " + teamMember.getTeamId()).getTeams().get(0);
                    System.out.println(team.getName());
                    Event event = eventDatasource.query("id = " + team.getEventId()).getEvents().get(0);
                    System.out.println(event.getName());

                    FXMLLoader myTeamCardLoader = new FXMLLoader();
                    myTeamCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/my-team-card-component.fxml"));
                    AnchorPane teamComponent = myTeamCardLoader.load();
                    MyTeamCardController myTeamCardController = myTeamCardLoader.getController();
                    myTeamCardController.setTeamId(team.getId());
                    myTeamCardController.setUserId(this.userId);
                    myTeamCardController.setEventId(event.getId());
                    JoinEventCollection joinEventCollection = joinEventDatasource.query("eventId = " + event.getId());
                    myTeamCardController.setApplicants(joinEventCollection.getJoinEvents().size() + "/" + event.getMaxParticipant());
                    myTeamCardController.setEventImage(event.getImage());
                    myTeamCardController.setEventName(event.getName());
                    myTeamCardController.setEventLocation(event.getLocation());
                    myTeamCardController.setStartDate(DateTimeService.toString(event.getStartDate()));
                    myTeamCardController.setTeamName(team.getName());
                    TeamMemberCollection teamMemberCollection1 = teamMemberDatasource.query("teamId = " + team.getId());
                    myTeamCardController.setTeamApplicants(String.valueOf(teamMemberCollection1.getTeamMembers().size()) + " / " + team.getMaxMember());
                    myTeamCardController.setOrder(String.valueOf(++i));
                    LocalDateTime endDateTimeOfEvent = LocalDateTime.parse(event.getEndDate() + "T" + event.getEndTime());
                    LocalDateTime currentDateTime = LocalDateTime.parse(DateTimeService.getCurrentDate() + "T" + DateTimeService.getCurrentTime());
                    if (endDateTimeOfEvent.isBefore(currentDateTime)) {
                        myTeamCardController.setActivityIsEnd();
                    }
                    myTeamCardController.setParentController(this);

                    javafx.application.Platform.runLater(() -> {
                        myTeamComponent.getChildren().add(teamComponent);
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    void onHandleAllTeamButton(ActionEvent event) {
        myTeamComponent.getChildren().clear();
        teamMemberCollection = teamMemberDatasource.query("userId = "+this.userId);
        initMyTeam();
    }

    @FXML
    void onHandleFinishedButton(ActionEvent event) {
        teamMemberCollection = teamMemberDatasource.query("userId = "+this.userId);
        ArrayList<TeamMember> teamMembers = new ArrayList<>();
        for (TeamMember teamMember: teamMemberCollection.getTeamMembers()){
            Team team = teamDatasource.query("id = " + teamMember.getTeamId()).getTeams().get(0);
            Event event1 = eventDatasource.query("id = " + team.getEventId()).getEvents().get(0);
            LocalDateTime endDateTimeOfEvent = LocalDateTime.parse(event1.getEndDate() + "T" + event1.getEndTime());
            LocalDateTime currentDateTime = LocalDateTime.parse(DateTimeService.getCurrentDate() + "T" + DateTimeService.getCurrentTime());
            if (endDateTimeOfEvent.isBefore(currentDateTime)) {
                teamMembers.add(teamMember);
            }
        }
        myTeamComponent.getChildren().clear();
        teamMemberCollection.setTeamMembers(teamMembers);
        initMyTeam();

    }

    @FXML
    void onHandleInProgressButton(ActionEvent event) {
        teamMemberCollection = teamMemberDatasource.query("userId = "+this.userId);
        ArrayList<TeamMember> teamMembers = new ArrayList<>();
        for (TeamMember teamMember: teamMemberCollection.getTeamMembers()){
            Team team = teamDatasource.query("id = " + teamMember.getTeamId()).getTeams().get(0);
            Event event1 = eventDatasource.query("id = " + team.getEventId()).getEvents().get(0);
            LocalDateTime endDateTimeOfEvent = LocalDateTime.parse(event1.getEndDate() + "T" + event1.getEndTime());
            LocalDateTime currentDateTime = LocalDateTime.parse(DateTimeService.getCurrentDate() + "T" + DateTimeService.getCurrentTime());
            if (endDateTimeOfEvent.isAfter(currentDateTime)) {
                teamMembers.add(teamMember);
            }
        }
        myTeamComponent.getChildren().clear();
        teamMemberCollection.setTeamMembers(teamMembers);
        initMyTeam();

    }

    public void reloadData() {
        this.myTeamComponent.getChildren().clear();
        teamMemberCollection = teamMemberDatasource.query("userId = "+this.userId);
        initMyTeam();
    }

}
