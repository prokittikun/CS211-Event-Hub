package cs211.project.controllers;

import cs211.project.controllers.components.ListTeamCard;
import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.JoinEventCollection;
import cs211.project.models.collections.TeamCollection;
import cs211.project.models.collections.UserCollection;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ListTeamController {
    @FXML
    private AnchorPane navbar;
    @FXML
    private AnchorPane footer;
    @FXML
    private VBox teamComponent;
    @FXML
    private Label locationLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label participantLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private ImageView previewImageView;
    private List<ListTeamCard> ListTeamCardList;
    private HashMap<String, Object> data;
    private Datasource<EventCollection> datasourceEvent;
    private Datasource<JoinEventCollection> datasourceJoinEvent;
    private Datasource<TeamCollection> datasourceTeam;
    private Datasource<UserCollection> datasourceUser;
    private EventCollection eventList;
    private JoinEventCollection joinEventList;
    private TeamCollection teamList;
    private UserCollection userList;

    @FXML
    private void initialize() {
        //Get Data
        data = FXRouter.getData();
        System.out.println(data.get("eventId"));
        //Get Event
        datasourceEvent = new EventListFileDatasource("data/event", "event.csv");
        eventList = datasourceEvent.query("id = " + data.get("eventId"));
        Event event = eventList.getEvents().get(0);
        //Filter JoinEvent by eventId
        datasourceJoinEvent = new JoinEventListFileDatasource("data/event", "joinEvent.csv");
        joinEventList = datasourceJoinEvent.query("eventId = " + data.get("eventId"));
        //Filter Team by eventId
        datasourceTeam = new TeamListFileDatasource("data/team", "team.csv");
        teamList = datasourceTeam.query("eventId = " + data.get("eventId"));
        //Get User
        datasourceUser = new UserListFileDatasource("data", "user.csv");

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

        //Header Event
        locationLabel.setText(event.getLocation());
        nameLabel.setText(event.getName());
        participantLabel.setText(joinEventList.getJoinEvents().size()+"/"+event.getMaxParticipant());
        dateLabel.setText(event.getStartDate());
        previewImageView.setImage(new Image("file:data/image/event/"+event.getImage()));

        //Component
        int i = 0;
        for (Team listTeamCardData : teamList.getTeams()) {
            try {
                i++;
                FXMLLoader listTeamCardLoader = new FXMLLoader();
                listTeamCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/list-team-card.fxml"));
                Pane listTeamCardComponent = listTeamCardLoader.load();
                ListTeamCard listTeamCard = listTeamCardLoader.getController();

                //Filter User by userId
                userList = datasourceUser.query("id = " + listTeamCardData.getLeaderId());
                //Set Value in List
                listTeamCard.setHeadTeamLabel(userList.getAllUsers().get(0).getFullName());
                listTeamCard.setHeadTeamImage(userList.getAllUsers().get(0).getAvatar());
                listTeamCard.setTeamLabel(listTeamCardData.getName());
                listTeamCard.setOrderNumber(" "+i);

                //Insert to Component
                teamComponent.getChildren().add(listTeamCardComponent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void goToCreateTeam(){
        try {
            FXRouter.goTo("createTeam");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void backToMyEvent(){
        try {
            FXRouter.goTo("myEvent");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
