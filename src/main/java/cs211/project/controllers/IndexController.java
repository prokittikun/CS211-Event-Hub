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
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.scene.layout.HBox;
import javafx.util.Duration;

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
    private HashMap<String, Object> data;
    private Datasource<EventCollection> eventDatasource;
    private Datasource<JoinEventCollection> joinEventDatasource;
    private EventCollection eventCollection;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image3;

    @FXML
    private ImageView image4;

    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @FXML
    private void initialize() {
        data = FXRouter.getData();
        image1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cs211/project/views/assets/banner/Transmission-Festival-Thailand-2023.jpg"))));
        image2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cs211/project/views/assets/banner/CoLiFes-2023.jpg"))));
        image3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cs211/project/views/assets/banner/Road-to-Sonic-Bang-2023.jpg"))));
        image4.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cs211/project/views/assets/banner/ค่ายวิศว.jpg"))));
        slider();
        data.remove("eventId");
        data.remove("teamId");
        data.remove("previousPage");
        eventDatasource = new EventListFileDatasource("data/event", "event.csv");
        joinEventDatasource = new JoinEventListFileDatasource("data/event", "joinEvent.csv");
        eventCollection = new EventCollection();

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
            System.err.println("navabar :" + e.getMessage());
            throw new RuntimeException(e);
        }

        EventCollection events = eventDatasource.query("status = true");
        eventCollection.setEvents(events.sortByBeforeEndDate());

        initPopularEvent();
        initClosestEvent();
        executorService.submit(() -> {
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
                    data.put("previousPage", "index");
                    indexEventCard.setData(data);
                    javafx.application.Platform.runLater(() -> {
                        eventCardHbox.getChildren().add(eventComponent);
                    });
                } catch (IOException e) {
                    System.err.println("error init:" + e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        });
    }

    void initPopularEvent() {
        EventCollection events = eventDatasource.query("status = true");
        EventCollection allEvent = new EventCollection();
        allEvent.setEvents(events.sortByBeforeEndDate());

        executorService.submit(() -> {

            for (Event cloesestEvent : allEvent.getPopularEvent()) {
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
                    data.put("previousPage", "index");
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
        EventCollection events = eventDatasource.query("status = true");
        EventCollection allEvent = new EventCollection();
        allEvent.setEvents(events.sortByBeforeEndDate());
        executorService.submit(() -> {

            for (Event popularEvent : allEvent.getClosestEvents()) {
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
                    data.put("previousPage", "index");
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


    @FXML
    void onHandleGoToAllEvent(ActionEvent allEvent) {
        try {
            HashMap<String, Object> data = new HashMap<>();
            data.put("userId", (String) this.data.get("userId"));
            FXRouter.goTo("allEvent", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleGoToCreateEvent(ActionEvent createEvent) {
        try {
            HashMap<String, Object> data = new HashMap<>();
            data.put("userId", (String) this.data.get("userId"));
            data.put("previousPage", "index");
            FXRouter.goTo("createEvent", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void slider() {
        new Thread() {
            public void run() {
                int count = 0;
                try {
                    while (true) {
                        switch (count) {
                            case 0:
                                Thread.sleep(5000);
                                TranslateTransition slider1 = new TranslateTransition();
                                slider1.setNode(image1);
                                slider1.setDuration(Duration.seconds(3));
                                slider1.setToX(930);
                                slider1.play();

                                TranslateTransition slider2 = new TranslateTransition();
                                slider2.setNode(image2);
                                slider2.setDuration(Duration.seconds(3));
                                slider2.setToX(930);
                                slider2.play();

                                TranslateTransition slider3 = new TranslateTransition();
                                slider3.setNode(image3);
                                slider3.setDuration(Duration.seconds(3));
                                slider3.setToX(-600);
                                slider3.play();

                                TranslateTransition slider4 = new TranslateTransition();
                                slider4.setNode(image4);
                                slider4.setDuration(Duration.seconds(3));
                                slider4.setToX(-1200);
                                slider4.play();
                                count = 1;
                                break;
                            case 1:
                                Thread.sleep(5000);
                                TranslateTransition slider5 = new TranslateTransition();
                                slider5.setNode(image1);
                                slider5.setDuration(Duration.seconds(3));
                                slider5.setToX(1200);
                                slider5.play();

                                TranslateTransition slider6 = new TranslateTransition();
                                slider6.setNode(image2);
                                slider6.setDuration(Duration.seconds(3));
                                slider6.setToX(1800);
                                slider6.play();

                                TranslateTransition slider7 = new TranslateTransition();
                                slider7.setNode(image3);
                                slider7.setDuration(Duration.seconds(3));
                                slider7.setToX(930);
                                slider7.play();

                                TranslateTransition slider8 = new TranslateTransition();
                                slider8.setNode(image4);
                                slider8.setDuration(Duration.seconds(3));
                                slider8.setToX(-600);
                                slider8.play();
                                count = 2;
                                break;
                            case 2:
                                Thread.sleep(5000);
                                TranslateTransition slider9 = new TranslateTransition();
                                slider9.setNode(image1);
                                slider9.setDuration(Duration.seconds(3));
                                slider9.setToX(1200);
                                slider9.play();

                                TranslateTransition slider10 = new TranslateTransition();
                                slider10.setNode(image2);
                                slider10.setDuration(Duration.seconds(3));
                                slider10.setToX(1800);
                                slider10.play();

                                TranslateTransition slider11 = new TranslateTransition();
                                slider11.setNode(image3);
                                slider11.setDuration(Duration.seconds(3));
                                slider11.setToX(1800);
                                slider11.play();

                                TranslateTransition slider12 = new TranslateTransition();
                                slider12.setNode(image4);
                                slider12.setDuration(Duration.seconds(3));
                                slider12.setToX(930);
                                slider12.play();

                                count = 3;
                                break;
                            case 3:
                                Thread.sleep(5000);
                                TranslateTransition slider13 = new TranslateTransition();
                                slider13.setNode(image1);
                                slider13.setDuration(Duration.seconds(3));
                                slider13.setToX(1200);
                                slider13.play();

                                TranslateTransition slider14 = new TranslateTransition();
                                slider14.setNode(image2);
                                slider14.setDuration(Duration.seconds(3));
                                slider14.setToX(1800);
                                slider14.play();

                                TranslateTransition slider15 = new TranslateTransition();
                                slider15.setNode(image3);
                                slider15.setDuration(Duration.seconds(3));
                                slider15.setToX(930);
                                slider15.play();

                                TranslateTransition slider16 = new TranslateTransition();
                                slider16.setNode(image4);
                                slider16.setDuration(Duration.seconds(3));
                                slider16.setToX(0);
                                slider16.play();

                                count = 4;
                                break;
                            case 4:
                                Thread.sleep(5000);
                                TranslateTransition slider17 = new TranslateTransition();
                                slider17.setNode(image1);
                                slider17.setDuration(Duration.seconds(3));
                                slider17.setToX(1200);
                                slider17.play();

                                TranslateTransition slider18 = new TranslateTransition();
                                slider18.setNode(image2);
                                slider18.setDuration(Duration.seconds(3));
                                slider18.setToX(930);
                                slider18.play();

                                TranslateTransition slider19 = new TranslateTransition();
                                slider19.setNode(image3);
                                slider19.setDuration(Duration.seconds(3));
                                slider19.setToX(0);
                                slider19.play();

                                TranslateTransition slider20 = new TranslateTransition();
                                slider20.setNode(image4);
                                slider20.setDuration(Duration.seconds(3));
                                slider20.setToX(0);
                                slider20.play();

                                count = 5;
                                break;
                            case 5:
                                Thread.sleep(5000);
                                TranslateTransition slider21 = new TranslateTransition();
                                slider21.setNode(image1);
                                slider21.setDuration(Duration.seconds(3));
                                slider21.setToX(0);
                                slider21.play();

                                TranslateTransition slider22 = new TranslateTransition();
                                slider22.setNode(image2);
                                slider22.setDuration(Duration.seconds(3));
                                slider22.setToX(0);
                                slider22.play();

                                TranslateTransition slider23 = new TranslateTransition();
                                slider23.setNode(image3);
                                slider23.setDuration(Duration.seconds(3));
                                slider23.setToX(0);
                                slider23.play();

                                TranslateTransition slider24 = new TranslateTransition();
                                slider24.setNode(image4);
                                slider24.setDuration(Duration.seconds(3));
                                slider24.setToX(0);
                                slider24.play();

                                count = 0;
                                break;
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error : " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }.start();
    }


}
