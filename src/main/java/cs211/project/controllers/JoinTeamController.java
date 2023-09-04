package cs211.project.controllers;

import cs211.project.controllers.components.JoinTeamCard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import cs211.project.controllers.components.JoinTeamCard;
import javafx.scene.shape.Rectangle;

public class JoinTeamController {
    @FXML
    private AnchorPane navbar;
    @FXML
    private AnchorPane footer;
    @FXML
    private VBox joinTeamVbox;

    private List<JoinTeamCard> joinTeamCardList;

    @FXML
    private void initialize() {
        joinTeamCardList = new ArrayList<>(JoinTeamCardList());
        for (JoinTeamCard teamCard : joinTeamCardList) {
            try {
                FXMLLoader joinTeamCardLoader = new FXMLLoader();
                joinTeamCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/join-team-card.fxml"));
                AnchorPane joinTeamCardComponent = joinTeamCardLoader.load();
                JoinTeamCard joinTeamCard = joinTeamCardLoader.getController();
                joinTeamCard.setEventDate(teamCard.getEventDate());
                joinTeamCard.setEventImage(teamCard.getEventImage());
                joinTeamCard.setEventLocation(teamCard.getEventLocation());
                joinTeamCard.setEventName(teamCard.getEventName());
                joinTeamCard.setEventParticipant(teamCard.getEventParticipant());
                joinTeamCard.setTeamName(teamCard.getTeamName());
                joinTeamCard.setTeamOrder(teamCard.getTeamOrder());
                joinTeamVbox.getChildren().add(joinTeamCardComponent);
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
    private List<JoinTeamCard> JoinTeamCardList(){
        List<JoinTeamCard> joinTeamCardList = new ArrayList<>();
        JoinTeamCard joinTeamCard;
        for (int i = 0; i < 10; i++){
            joinTeamCard = new JoinTeamCard();
            joinTeamCard.setEventDate(i + " ม.ค. 2566");
            joinTeamCard.setEventImage("https://picsum.photos/200");
            joinTeamCard.setEventLocation("ศูนย์การประชุมแห่งชาติสิริกิติ์");
            joinTeamCard.setEventName("สัปดาห์หนังสือแห่งชาติ ครั้งที่ 51");
            joinTeamCard.setEventParticipant("250/500");
            joinTeamCard.setTeamName("Team " + i);
            joinTeamCard.setTeamOrder(i+1 + "");
            joinTeamCardList.add(joinTeamCard);
        }
        return joinTeamCardList;
    }
}
