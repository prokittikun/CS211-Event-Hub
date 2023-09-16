package cs211.project.controllers;

import cs211.project.controllers.components.LeftChatLayoutController;
import cs211.project.controllers.components.MyTeamCardController;
import cs211.project.controllers.components.RightChatLayoutController;
import cs211.project.controllers.components.TeamMemberCard;
import cs211.project.models.Chat;
import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.TeamMember;
import cs211.project.models.collections.ChatCollection;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.TeamCollection;
import cs211.project.models.collections.TeamMemberCollection;
import cs211.project.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Window;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.Objects;

public class MyTeamController {
    @FXML
    private AnchorPane navbar;

    @FXML
    private AnchorPane footer;

    private TeamCollection teamList;

    private TeamMemberCollection teamMemberCollection;

    @FXML
    private VBox myTeamComponent;

    private Datasource<TeamCollection> datasourceTeam;

    private Datasource<TeamMemberCollection> datasourceTeamMember;

    private Datasource<EventCollection> datasourceEvent;


    @FXML
    private void initialize() {
        datasourceTeamMember = new TeamMemberListFIleDatasource("data/team", "teamMember.csv");
        datasourceTeam = new TeamListFileDatasource("data/team", "team.csv");
        datasourceEvent = new EventListFileDatasource("data/event", "event.csv");

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


    private void initMyTeam(){
        teamMemberCollection = datasourceTeamMember.query("userId = b1e473a8-5175-11ee-be56-0242ac120002");
        int i = 0;
        for (TeamMember teamMemberCollection : teamMemberCollection.getTeamMembers()) {
            try {
                Team team = datasourceTeam.query("id = " + teamMemberCollection.getTeamId()).getTeams().get(0);
                System.out.println(team.getName());
                Event event = datasourceEvent.query("id = " + team.getEventId()).getEvents().get(0);
                System.out.println(event.getName());

                FXMLLoader myTeamCardLoader = new FXMLLoader();
//                AnchorPane teamComponent;
                myTeamCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/my-team-card-component.fxml"));
                AnchorPane teamComponent = myTeamCardLoader.load();
                MyTeamCardController myTeamCardController = myTeamCardLoader.getController();
                myTeamCardController.setTeamId(team.getId());
                myTeamCardController.setEventImage(event.getImage());
                myTeamCardController.setEventName(event.getName());
                myTeamCardController.setEventLocation(event.getLocation());
                myTeamCardController.setStartDate(event.getStartDate());
                myTeamCardController.setTeamName(team.getName());
                TeamMemberCollection teamMemberCollection1 = datasourceTeamMember.query("teamId = " + team.getId());
                myTeamCardController.setApplicants(String.valueOf(teamMemberCollection1.getTeamMembers().size()) + " / " + team.getMaxMember());
                myTeamCardController.setOrder(String.valueOf(++i));
                myTeamCardController.setParentController(this);




                myTeamComponent.getChildren().add(teamComponent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void reloadData() {
        this.myTeamComponent.getChildren().clear();
//        this.teamMemberCollection = n
        initMyTeam();
    }

}
