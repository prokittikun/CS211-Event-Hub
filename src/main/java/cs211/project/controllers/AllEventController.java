package cs211.project.controllers;

import cs211.project.controllers.components.EventCard;
import cs211.project.models.Event;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.JoinEventCollection;
import cs211.project.services.Datasource;
import cs211.project.services.EventListFileDatasource;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinEventListFileDatasource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AllEventController {
    @FXML
    private AnchorPane navbar;
    @FXML
    private AnchorPane footer;
    @FXML
    private GridPane showAllEvent;
    private List<EventCard> eventCardList;
    private Datasource<EventCollection> eventDatasource;
    private Datasource<JoinEventCollection> joinEventDatasource;
    private EventCollection eventCollection;
    private HashMap<String, Object> data;
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @FXML
    private void initialize() {
        data = FXRouter.getData();
        eventDatasource = new EventListFileDatasource("data/event", "event.csv");
        joinEventDatasource = new JoinEventListFileDatasource("data/event", "joinEvent.csv");
        eventCollection = new EventCollection();

        initAllEvent();
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
//    private void initAllEvent(){
//        int column = 0;
//        int row = 1;
//        executorService.submit(() -> {
//            for (Event event : eventCollection.getEvents()) {
//                try {
//                    FXMLLoader eventCardLoader = new FXMLLoader();
//                    eventCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/event-card.fxml"));
//                    AnchorPane eventComponent = eventCardLoader.load();
//                    EventCard allEventCard = eventCardLoader.getController();
//                    allEventCard.setEventImage(event.getImage());
//                    allEventCard.setEventName(event.getName());
//                    allEventCard.setEventDate(event.getStartDate());
//                    allEventCard.setEventLocation(event.getLocation());
//
//                    JoinEventCollection joinEventCollection = joinEventDatasource.query("eventId = " + event.getId());
//                    allEventCard.setEventParticipant(joinEventCollection.getJoinEvents().size() + "/" + event.getMaxParticipant());
//                    allEventCard.setCurrentParticipant(joinEventCollection.getJoinEvents().size());
//
//                    if (column == 5) {
//                        column = 0;
//                        ++row;
//                    }
//
//                    HashMap<String, Object> data = new HashMap<>();
//                    data.put("userId", this.data.get("userId"));
//                    data.put("eventId", event.getId());
//                    allEventCard.setData(data);
//                    javafx.application.Platform.runLater(() -> {
//                        showAllEvent.add(eventComponent, column++, row);
//                        GridPane.setMargin(eventComponent, new Insets(5));
//                    });
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//    }
//}
    private void initAllEvent(){
        eventCardList = new ArrayList<>(EventCardList());
        int column = 0;
        int row = 1;
        try {
            for (EventCard eventCard : eventCardList) {
                FXMLLoader eventCardLoader = new FXMLLoader();
                eventCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/event-card.fxml"));
                AnchorPane eventComponent = eventCardLoader.load();
                EventCard allEventCard = eventCardLoader.getController();
                allEventCard.setEventImage(eventCard.getEventImage());
                allEventCard.setEventName(eventCard.getEventName());
                allEventCard.setEventDate(eventCard.getEventDate());
                allEventCard.setEventLocation(eventCard.getEventLocation());
                allEventCard.setEventParticipant(eventCard.getEventParticipant());
                if (column == 5) {
                    column = 0;
                    ++row;
                }

                showAllEvent.add(eventComponent, column++, row);
                GridPane.setMargin(eventComponent, new Insets(5));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private List<EventCard> EventCardList(){
        List<EventCard> eventCardList = new ArrayList<>();
        EventCard eventCard;
        for (int i = 0; i < 15; i++){
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
}
