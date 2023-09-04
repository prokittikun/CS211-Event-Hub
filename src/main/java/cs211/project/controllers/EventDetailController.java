package cs211.project.controllers;

import cs211.project.controllers.components.EventCard;
import cs211.project.services.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventDetailController {
    @FXML
    private AnchorPane navbar;
    @FXML
    private AnchorPane footer;
    @FXML
    private HBox eventCardHbox;
    private List<EventCard> eventCardList;
    @FXML
    private void initialize() {
        eventCardList = new ArrayList<>(EventCardList());
        for (EventCard eventCard : eventCardList) {
            try {
                FXMLLoader eventCardLoader = new FXMLLoader();
                eventCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/event-card.fxml"));
                AnchorPane eventComponent = eventCardLoader.load();
                EventCard indexEventCard = eventCardLoader.getController();
                indexEventCard.setEventImage(eventCard.getEventImage());
                indexEventCard.setEventName(eventCard.getEventName());
                indexEventCard.setEventDate(eventCard.getEventDate());
                indexEventCard.setEventLocation(eventCard.getEventLocation());
                indexEventCard.setEventParticipant(eventCard.getEventParticipant());
                eventCardHbox.getChildren().add(eventComponent);
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
    private List<EventCard> EventCardList(){
        List<EventCard> eventCardList = new ArrayList<>();
        EventCard eventCard;
        for (int i = 0; i < 5; i++){
            eventCard = new EventCard();
            eventCard.setEventImage("https://picsum.photos/200");
            eventCard.setEventName("สัปดาห์หนังสือแห่งชาติ ครั้งที่ 51");
            eventCard.setEventDate(i+1 + " ม.ค. 2566");
            eventCard.setEventLocation("ศูนย์การประชุมแห่งชาติสิริกิติ์");
            eventCard.setEventParticipant("250/500");
            eventCardList.add(eventCard);
        }
        return eventCardList;
    }
    @FXML
    void onHandleRegisterEvent(ActionEvent registerEvent) {
        try {
            FXRouter.goTo("registerEvent");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void onHandleJoinTeam(ActionEvent joinTeam) {
        try {
            FXRouter.goTo("joinTeam");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleGoToPreviousPage(ActionEvent event) {
        try {
            FXRouter.goTo("allEvent");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
