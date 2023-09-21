package cs211.project.controllers;

import cs211.project.controllers.components.EventCard;
import cs211.project.controllers.components.WhiteEventCard;
import cs211.project.services.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.layout.HBox;

public class IndexController {
    @FXML
    private AnchorPane navbar;
    @FXML
    private AnchorPane footer;
    @FXML
    private HBox eventCardHbox;
    @FXML
    private HBox popularEventCardHbox;
    @FXML
    private HBox comingEventCardHbox;
    private List<EventCard> eventCardList;
    private List<WhiteEventCard> popularEventCardList;
    private List<WhiteEventCard> comingEventCardList;
    private HashMap<String, Object> data;

    @FXML
    void onHandleGoToAllEvent(ActionEvent allEvent) {
        try {
            FXRouter.goTo("allEvent");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void initialize() {
        data = FXRouter.getData();

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
        popularEventCardList = new ArrayList<>(PopularEventCardList());
        for (WhiteEventCard popularEventCard : popularEventCardList) {
            try {
                FXMLLoader popularEventCardLoader = new FXMLLoader();
                popularEventCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/white-event-card.fxml"));
                AnchorPane eventComponent = popularEventCardLoader.load();
                WhiteEventCard indexPopularEventCard = popularEventCardLoader.getController();
                indexPopularEventCard.setEventImage(popularEventCard.getEventImage());
                indexPopularEventCard.setEventName(popularEventCard.getEventName());
                indexPopularEventCard.setEventDate(popularEventCard.getEventDate());
                indexPopularEventCard.setEventLocation(popularEventCard.getEventLocation());
                indexPopularEventCard.setEventParticipant(popularEventCard.getEventParticipant());
                popularEventCardHbox.getChildren().add(eventComponent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        comingEventCardList = new ArrayList<>(ComingEventCardList());
        for (WhiteEventCard comingEventCard : comingEventCardList) {
            try {
                FXMLLoader comingEventCardLoader = new FXMLLoader();
                comingEventCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/white-event-card.fxml"));
                AnchorPane eventComponent = comingEventCardLoader.load();
                WhiteEventCard indexComingEventCard = comingEventCardLoader.getController();
                indexComingEventCard.setEventImage(comingEventCard.getEventImage());
                indexComingEventCard.setEventName(comingEventCard.getEventName());
                indexComingEventCard.setEventDate(comingEventCard.getEventDate());
                indexComingEventCard.setEventLocation(comingEventCard.getEventLocation());
                indexComingEventCard.setEventParticipant(comingEventCard.getEventParticipant());
                comingEventCardHbox.getChildren().add(eventComponent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
    private List<WhiteEventCard> PopularEventCardList(){
        List<WhiteEventCard> popularEventCardList = new ArrayList<>();
        WhiteEventCard popularEventCard;
        for (int i = 0; i < 2; i++){
            popularEventCard = new WhiteEventCard();
            popularEventCard.setEventImage("https://picsum.photos/200");
            popularEventCard.setEventName("สัปดาห์หนังสือแห่งชาติ ครั้งที่ 51");
            popularEventCard.setEventDate(i + " ม.ค. 2566");
            popularEventCard.setEventLocation("ศูนย์การประชุมแห่งชาติสิริกิติ์");
            popularEventCard.setEventParticipant("250/500");
            popularEventCardList.add(popularEventCard);
        }
        return popularEventCardList;
    }
    private List<WhiteEventCard> ComingEventCardList(){
        List<WhiteEventCard> comingEventCardList = new ArrayList<>();
        WhiteEventCard comingEventCard;
        for (int i = 0; i < 2; i++){
            comingEventCard = new WhiteEventCard();
            comingEventCard.setEventImage("https://picsum.photos/200");
            comingEventCard.setEventName("สัปดาห์หนังสือแห่งชาติ ครั้งที่ 51");
            comingEventCard.setEventDate(i + " ม.ค. 2566");
            comingEventCard.setEventLocation("ศูนย์การประชุมแห่งชาติสิริกิติ์");
            comingEventCard.setEventParticipant("250/500");
            comingEventCardList.add(comingEventCard);
        }
        return comingEventCardList;
    }

}
