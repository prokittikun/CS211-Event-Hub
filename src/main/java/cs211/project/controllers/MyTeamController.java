package cs211.project.controllers;

import cs211.project.controllers.components.MyTeamCardController;
import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.TeamMember;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.TeamCollection;
import cs211.project.models.collections.TeamMemberCollection;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyTeamController {

    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    @FXML
    private AnchorPane navbar;

    @FXML
    private AnchorPane footer;

    private TeamCollection teamList;

    private TeamMemberCollection teamMemberCollection;

    @FXML
    private VBox myTeamComponent;

    private Datasource<TeamCollection> teamDatasource;

    private Datasource<TeamMemberCollection> teamMemberDatasource;

    private Datasource<EventCollection> eventDatasource;
    private HashMap<String, Object> data;

    private String userId;

    @FXML
    private void initialize() {
        data = FXRouter.getData();
        userId = (String) data.get("userId");
        teamMemberDatasource = new TeamMemberListFileDatasource("data/team", "teamMember.csv");
        teamDatasource = new TeamListFileDatasource("data/team", "team.csv");
        eventDatasource = new EventListFileDatasource("data/event", "event.csv");

//        teamList
        initMyTeam();

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


    private void initMyTeam() {
        executorService.submit(() -> {
            teamMemberCollection = teamMemberDatasource.query("userId = "+this.userId);
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
                    myTeamCardController.setEventImage(event.getImage());
                    myTeamCardController.setEventName(event.getName());
                    myTeamCardController.setEventLocation(event.getLocation());
                    myTeamCardController.setStartDate(event.getStartDate());
                    myTeamCardController.setTeamName(team.getName());
                    TeamMemberCollection teamMemberCollection1 = teamMemberDatasource.query("teamId = " + team.getId());
                    myTeamCardController.setApplicants(String.valueOf(teamMemberCollection1.getTeamMembers().size()) + " / " + team.getMaxMember());
                    myTeamCardController.setOrder(String.valueOf(++i));
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

    public void reloadData() {
        this.myTeamComponent.getChildren().clear();
//        this.teamMemberCollection = n
        initMyTeam();
    }

}
