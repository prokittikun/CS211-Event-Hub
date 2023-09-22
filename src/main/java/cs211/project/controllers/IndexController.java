package cs211.project.controllers;

import cs211.project.controllers.components.EventCard;
import cs211.project.controllers.components.WhiteEventCard;
import cs211.project.models.Event;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.JoinEventCollection;
import cs211.project.services.Datasource;
import cs211.project.services.EventListFileDatasource;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinEventListFileDatasource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private Datasource<EventCollection> eventDatasource;
    private Datasource<JoinEventCollection> joinEventDatasource;
    private EventCollection eventCollection;
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @FXML
    private void initialize() {
        data = FXRouter.getData();
        eventDatasource = new EventListFileDatasource("data/event", "event.csv");
        joinEventDatasource = new JoinEventListFileDatasource("data/event", "joinEvent.csv");
        eventCollection = new EventCollection();
        initPopularEvent();
        initClosestEvent();
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

        eventCollection = eventDatasource.readData();
        executorService.submit(() -> {
            System.out.println(eventCollection.getEvents().size());
            for (Event event : eventCollection.getLatestEvents()) {
                try {
                    FXMLLoader eventCardLoader = new FXMLLoader();
                    eventCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/event-card.fxml"));
                    AnchorPane eventComponent = eventCardLoader.load();
                    EventCard indexEventCard = eventCardLoader.getController();
                    indexEventCard.setEventImage(event.getImage());
                    indexEventCard.setEventName(event.getName());
                    indexEventCard.setEventDate(event.getStartDate());
                    indexEventCard.setEventLocation(event.getLocation());

                    JoinEventCollection joinEventCollection = joinEventDatasource.query("eventId = " + event.getId());
                    indexEventCard.setEventParticipant(joinEventCollection.getJoinEvents().size() + "/" + event.getMaxParticipant());
                    indexEventCard.setCurrentParticipant(joinEventCollection.getJoinEvents().size());

                    HashMap<String, Object> data = new HashMap<>();
                    data.put("userId", this.data.get("userId"));
                    data.put("eventId", event.getId());
                    indexEventCard.setData(data);
                    javafx.application.Platform.runLater(() -> {
                        eventCardHbox.getChildren().add(eventComponent);
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    void initPopularEvent() {
        executorService.submit(() -> {
            EventCollection allEvent;
            allEvent = eventDatasource.readData();
            System.out.println(allEvent.getClosestEvents().size());
            JoinEventCollection joinEventCollection = joinEventDatasource.readData();
            for (Event cloesestEvent : allEvent.getPopularEvent(joinEventCollection)) {
                try {
                    FXMLLoader popularEventCardLoader = new FXMLLoader();
                    popularEventCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/white-event-card.fxml"));
                    AnchorPane eventComponent = popularEventCardLoader.load();
                    WhiteEventCard indexEventCard = popularEventCardLoader.getController();
                    indexEventCard.setEventImage(cloesestEvent.getImage());
                    indexEventCard.setEventName(cloesestEvent.getName());
                    indexEventCard.setEventDate(cloesestEvent.getStartDate());
                    indexEventCard.setEventLocation(cloesestEvent.getLocation());

                    JoinEventCollection joinEventCollectionEachEvent = joinEventDatasource.query("eventId = " + cloesestEvent.getId());
                    indexEventCard.setEventParticipant(joinEventCollectionEachEvent.getJoinEvents().size() + "/" + cloesestEvent.getMaxParticipant());
                    indexEventCard.setCurrentParticipant(joinEventCollectionEachEvent.getJoinEvents().size());

                    HashMap<String, Object> data = new HashMap<>();
                    data.put("userId", this.data.get("userId"));
                    data.put("eventId", cloesestEvent.getId());
                    indexEventCard.setData(data);
                    javafx.application.Platform.runLater(() -> {
                        popularEventCardHbox.getChildren().add(eventComponent);
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    void initClosestEvent() {
        executorService.submit(() -> {
            EventCollection allEvent;
            allEvent = eventDatasource.readData();

            for (Event popularEvent : allEvent.getClosestEvents()) {
                System.out.println("1");
                try {
                    FXMLLoader popularEventCardLoader = new FXMLLoader();
                    popularEventCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/white-event-card.fxml"));
                    AnchorPane eventComponent = popularEventCardLoader.load();
                    WhiteEventCard indexEventCard = popularEventCardLoader.getController();
                    indexEventCard.setEventImage(popularEvent.getImage());
                    indexEventCard.setEventName(popularEvent.getName());
                    indexEventCard.setEventDate(popularEvent.getStartDate());
                    indexEventCard.setEventLocation(popularEvent.getLocation());

                    JoinEventCollection joinEventCollectionEachEvent = joinEventDatasource.query("eventId = " + popularEvent.getId());
                    indexEventCard.setEventParticipant(joinEventCollectionEachEvent.getJoinEvents().size() + "/" + popularEvent.getMaxParticipant());
                    indexEventCard.setCurrentParticipant(joinEventCollectionEachEvent.getJoinEvents().size());

                    HashMap<String, Object> data = new HashMap<>();
                    data.put("userId", this.data.get("userId"));
                    data.put("eventId", popularEvent.getId());
                    indexEventCard.setData(data);
                    javafx.application.Platform.runLater(() -> {
                        comingEventCardHbox.getChildren().add(eventComponent);
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

//        comingEventCardList = new ArrayList<>(ComingEventCardList());
//        for (WhiteEventCard comingEventCard : comingEventCardList) {
//            try {
//                FXMLLoader comingEventCardLoader = new FXMLLoader();
//                comingEventCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/white-event-card.fxml"));
//                AnchorPane eventComponent = comingEventCardLoader.load();
//                WhiteEventCard indexComingEventCard = comingEventCardLoader.getController();
//                indexComingEventCard.setEventImage(comingEventCard.getEventImage());
//                indexComingEventCard.setEventName(comingEventCard.getEventName());
//                indexComingEventCard.setEventDate(comingEventCard.getEventDate());
//                indexComingEventCard.setEventLocation(comingEventCard.getEventLocation());
//                indexComingEventCard.setEventParticipant(comingEventCard.getEventParticipant());
//                comingEventCardHbox.getChildren().add(eventComponent);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

//    private List<EventCard> EventCardList() {
//        List<EventCard> eventCardList = new ArrayList<>();
//        EventCard eventCard;
//        for (int i = 0; i < 5; i++) {
//            eventCard = new EventCard();
//            eventCard.setEventImage("https://picsum.photos/200");
//            eventCard.setEventName("สัปดาห์หนังสือแห่งชาติ ครั้งที่ 51");
//            eventCard.setEventDate(i + 1 + " ม.ค. 2566");
//            eventCard.setEventLocation("ศูนย์การประชุมแห่งชาติสิริกิติ์");
//            eventCard.setEventParticipant("250/500");
//            eventCardList.add(eventCard);
//        }
//        return eventCardList;
//    }
//
//    private List<WhiteEventCard> PopularEventCardList() {
//        List<WhiteEventCard> popularEventCardList = new ArrayList<>();
//        WhiteEventCard popularEventCard;
//        for (int i = 0; i < 2; i++) {
//            popularEventCard = new WhiteEventCard();
//            popularEventCard.setEventImage("https://picsum.photos/200");
//            popularEventCard.setEventName("สัปดาห์หนังสือแห่งชาติ ครั้งที่ 51");
//            popularEventCard.setEventDate(i + " ม.ค. 2566");
//            popularEventCard.setEventLocation("ศูนย์การประชุมแห่งชาติสิริกิติ์");
//            popularEventCard.setEventParticipant("250/500");
//            popularEventCardList.add(popularEventCard);
//        }
//        return popularEventCardList;
//    }
//
//    private List<WhiteEventCard> ComingEventCardList() {
//        List<WhiteEventCard> comingEventCardList = new ArrayList<>();
//        WhiteEventCard comingEventCard;
//        for (int i = 0; i < 2; i++) {
//            comingEventCard = new WhiteEventCard();
//            comingEventCard.setEventImage("https://picsum.photos/200");
//            comingEventCard.setEventName("สัปดาห์หนังสือแห่งชาติ ครั้งที่ 51");
//            comingEventCard.setEventDate(i + " ม.ค. 2566");
//            comingEventCard.setEventLocation("ศูนย์การประชุมแห่งชาติสิริกิติ์");
//            comingEventCard.setEventParticipant("250/500");
//            comingEventCardList.add(comingEventCard);
//        }
//        return comingEventCardList;
//    }

    @FXML
    void onHandleGoToAllEvent(ActionEvent allEvent) {
        try {
            HashMap<String, Object> data = new HashMap<>();

            data.put("userId", "b1e473a8-5175-11ee-be56-0242ac120002");
            FXRouter.goTo("allEvent", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
