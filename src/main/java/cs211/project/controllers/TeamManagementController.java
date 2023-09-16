package cs211.project.controllers;

import cs211.project.models.TeamMember;
import cs211.project.models.User;
import cs211.project.models.collections.TeamMemberCollection;
import cs211.project.models.collections.UserCollection;
import cs211.project.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cs211.project.controllers.components.TeamMemberCard;

public class TeamManagementController {

    private UUID teamId;
    @FXML
    private AnchorPane navbar;

    @FXML
    private AnchorPane footer;

    @FXML
    private VBox memberVbox;

    private List<TeamMemberCard> teamMemberCardList;

    private Datasource<TeamMemberCollection> teamMemberDatasource;

    private Datasource<UserCollection> userDatasource;

    private TeamMemberCollection teamMemberCollection;

    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @FXML
    private void initialize() {

        initTeamMember();
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

    void initTeamMember() {
        executorService.submit(() -> {

            teamId = UUID.fromString("c623dc8c-5175-11ee-be56-0242ac120002");
            teamMemberDatasource = new TeamMemberListFileDatasource("data/team", "teamMember.csv");
            userDatasource = new UserListFileDatasource("data", "user.csv");
            teamMemberCollection = teamMemberDatasource.query("teamId = " + this.teamId.toString());

//        teamMemberCardList = new ArrayList<>(TeamMemberCardList());
            for (TeamMember member : teamMemberCollection.getTeamMembers()) {
                try {
                    User user = userDatasource.query("id = " + member.getUserId()).getAllUsers().get(0);
                    FXMLLoader teamMemberCardLoader = new FXMLLoader();
                    teamMemberCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/team-member-card.fxml"));
                    AnchorPane teamMemberCardComponent = teamMemberCardLoader.load();
                    TeamMemberCard teamMemberCard = teamMemberCardLoader.getController();
                    teamMemberCard.setImage(user.getAvatar());
                    teamMemberCard.setName(user.getFirstName() + " " + user.getLastName());
                    teamMemberCard.setRole(member.getIsLeader());

                    javafx.application.Platform.runLater(() -> {
                        memberVbox.getChildren().add(teamMemberCardComponent);
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private List<TeamMemberCard> TeamMemberCardList() {
        List<TeamMemberCard> teamMemberCardList = new ArrayList<>();
        TeamMemberCard teamMemberCard;
        for (int i = 0; i < 10; i++) {
            teamMemberCard = new TeamMemberCard();
            teamMemberCard.setImage("https://picsum.photos/200");
            teamMemberCard.setName("Kittikun" + i);
            teamMemberCard.setRole("Member");
            teamMemberCardList.add(teamMemberCard);
        }
        return teamMemberCardList;
    }

    @FXML
    void onHandleAddActivity(ActionEvent event) {
        try {
            FXRouter.goTo("createActivity");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleOpenChat(ActionEvent event) {
        try {
            HashMap<String, Object> data = new HashMap<>();
            data.put("teamId", teamId.toString());
            FXRouter.goTo("chat", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
