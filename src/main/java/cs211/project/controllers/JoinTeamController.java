package cs211.project.controllers;

import cs211.project.controllers.components.EventCard;
import cs211.project.controllers.components.JoinTeamCard;
import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.JoinEventCollection;
import cs211.project.models.collections.TeamCollection;
import cs211.project.models.collections.TeamMemberCollection;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        eventId = UUID.fromString((String) data.get("eventId"));
        eventDatasource = new EventListFileDatasource("data/event", "event.csv");
        teamDatasource = new TeamListFileDatasource("data/team", "team.csv");
        joinEventDatasource = new JoinEventListFileDatasource("data/event", "joinEvent.csv");
        teamMemberDatasource = new TeamMemberListFileDatasource("data/team", "teamMember.csv");

        Event event = eventDatasource.query("id = " + this.eventId.toString()).getEvents().get(0);
        eventName.setText(event.getName());

        initJoinTeam();

//        joinTeamCardList = new ArrayList<>(JoinTeamCardList());
//        for (JoinTeamCard teamCard : joinTeamCardList) {
//            try {
//                FXMLLoader joinTeamCardLoader = new FXMLLoader();
//                joinTeamCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/join-team-card.fxml"));
//                AnchorPane joinTeamCardComponent = joinTeamCardLoader.load();
//                JoinTeamCard joinTeamCard = joinTeamCardLoader.getController();
//                joinTeamCard.setEventDate(teamCard.getEventDate());
//                joinTeamCard.setEventImage(teamCard.getEventImage());
//                joinTeamCard.setEventLocation(teamCard.getEventLocation());
//                joinTeamCard.setEventName(teamCard.getEventName());
//                joinTeamCard.setEventParticipant(teamCard.getEventParticipant());
//                joinTeamCard.setTeamName(teamCard.getTeamName());
//                joinTeamCard.setTeamOrder(teamCard.getTeamOrder());
//                joinTeamVbox.getChildren().add(joinTeamCardComponent);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
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

    void initJoinTeam() {
        teamCollection = teamDatasource.query("eventId = " + eventId.toString());
        System.out.println(teamCollection.getTeams().size());
        executorService.submit(() -> {
            int i = 0;
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

                    TeamMemberCollection teamMemberCollection = teamMemberDatasource.query("teamId = " + team.getId());
                    joinTeamCard.setTeamParticipant(teamMemberCollection.getTeamMembers().size() + "/" + team.getMaxMember());

                    joinTeamCard.setTeamName(team.getName());
                    joinTeamCard.setTeamOrder(++i + "");
                    joinTeamCard.setTeamId(team.getId());
                    joinTeamCard.setUserId((String) data.get("userId"));

                    javafx.application.Platform.runLater(() -> {
                        joinTeamVbox.getChildren().add(joinTeamCardComponent);
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
//    private List<JoinTeamCard> JoinTeamCardList(){
//        List<JoinTeamCard> joinTeamCardList = new ArrayList<>();
//        JoinTeamCard joinTeamCard;
//        for (int i = 0; i < 10; i++){
//            joinTeamCard = new JoinTeamCard();
//            joinTeamCard.setEventDate(i + " ม.ค. 2566");
//            joinTeamCard.setEventImage("https://picsum.photos/200");
//            joinTeamCard.setEventLocation("ศูนย์การประชุมแห่งชาติสิริกิติ์");
//            joinTeamCard.setEventName("สัปดาห์หนังสือแห่งชาติ ครั้งที่ 51");
//            joinTeamCard.setEventParticipant("250/500");
//            joinTeamCard.setTeamName("Team " + i);
//            joinTeamCard.setTeamOrder(i+1 + "");
//            joinTeamCardList.add(joinTeamCard);
//        }
//        return joinTeamCardList;
//    }
}
