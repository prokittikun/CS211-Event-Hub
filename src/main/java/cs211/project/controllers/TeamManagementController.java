package cs211.project.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs211.project.controllers.components.TeamMemberCard;

public class TeamManagementController {
    @FXML
    private AnchorPane navbar;

    @FXML
    private AnchorPane footer;

    @FXML
    private VBox memberVbox;

    private List<TeamMemberCard> teamMemberCardList;

    public TeamManagementController() {
    }

    @FXML
    private void initialize() {
        teamMemberCardList = new ArrayList<>(TeamMemberCardList());
        for (TeamMemberCard memberCard : teamMemberCardList) {
            try {
                FXMLLoader teamMemberCardLoader = new FXMLLoader();
                teamMemberCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/team-member-card.fxml"));
                AnchorPane teamMemberCardComponent = teamMemberCardLoader.load();
                TeamMemberCard teamMemberCard = teamMemberCardLoader.getController();
                teamMemberCard.setImage(memberCard.getImage());
                teamMemberCard.setName(memberCard.getName());
                teamMemberCard.setRole(memberCard.getRole());
                memberVbox.getChildren().add(teamMemberCardComponent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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

    private List<TeamMemberCard> TeamMemberCardList(){
        List<TeamMemberCard> teamMemberCardList = new ArrayList<>();
        TeamMemberCard teamMemberCard;
        for (int i = 0; i < 10; i++){
            teamMemberCard = new TeamMemberCard();
            teamMemberCard.setImage("https://picsum.photos/200");
            teamMemberCard.setName("Kittikun"+ i);
            teamMemberCard.setRole("Member");
            teamMemberCardList.add(teamMemberCard);
        }
        return teamMemberCardList;
    }
}
