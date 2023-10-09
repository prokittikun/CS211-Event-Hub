package cs211.project.controllers;

import cs211.project.controllers.components.EventCard;
import cs211.project.models.Event;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.JoinEventCollection;
import cs211.project.services.Datasource;
import cs211.project.services.EventListFileDatasource;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinEventListFileDatasource;
import cs211.project.services.comparator.LatestEventComparator;
import cs211.project.services.comparator.LeastEventParticipantComparator;
import cs211.project.services.comparator.MostEventParticipantComparator;
import cs211.project.services.comparator.OldestEventComparator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
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
    private TextField searchInput;

    @FXML
    private RadioButton  latestButton;

    @FXML
    private RadioButton  oldestButton;

    @FXML
    private RadioButton  leastJoinButton;

    @FXML
    private RadioButton mostJoinButton;


    EventCollection optionEventCollection;


    @FXML
    private void initialize() {
        data = FXRouter.getData();
        data.remove("eventId");
        data.remove("teamId");
        eventDatasource = new EventListFileDatasource("data/event", "event.csv");
        joinEventDatasource = new JoinEventListFileDatasource("data/event", "joinEvent.csv");
        eventCollection = new EventCollection();
        eventCollection = eventDatasource.readData();
        //add participant to event
        optionEventCollection = new EventCollection();
        optionEventCollection = eventDatasource.readData();
        latestButton.setSelected(true);
        ToggleGroup toggleGroup1 = new ToggleGroup();
        latestButton.setToggleGroup(toggleGroup1);
        oldestButton.setToggleGroup(toggleGroup1);
        leastJoinButton.setToggleGroup(toggleGroup1);
        mostJoinButton.setToggleGroup(toggleGroup1);
        initAllEvent(getLatestEvent());

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
    private void initAllEvent(EventCollection eventCollection){
        executorService.submit(() -> {
            final int[] column = {0};
            final int[] row = {1};
            try {
                for (Event event : eventCollection.getEvents()) {
                    System.out.println("123");
                    FXMLLoader eventCardLoader = new FXMLLoader();
                    eventCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/event-card.fxml"));
                    AnchorPane eventComponent = eventCardLoader.load();
                    EventCard allEventCard = eventCardLoader.getController();

                    allEventCard.setEventImage(event.getImage());
                    allEventCard.setEventName(event.getName());
                    allEventCard.setEventDate(event.getStartDate());
                    allEventCard.setEventLocation(event.getLocation());

                    JoinEventCollection joinEventCollection = joinEventDatasource.query("eventId = " + event.getId());
                    allEventCard.setEventParticipant(joinEventCollection.getJoinEvents().size() + "/" + event.getMaxParticipant());
                    allEventCard.setCurrentParticipant(joinEventCollection.getJoinEvents().size());

                    javafx.application.Platform.runLater(() -> {
                        if (column[0] == 5) {
                            column[0] = 0;
                            ++row[0];
                        }
                        HashMap<String, Object> data = new HashMap<>();
                        data.put("userId", this.data.get("userId"));
                        data.put("eventId", event.getId());
                        data.put("previousPage", "allEvent");
                        allEventCard.setData(data);
                        showAllEvent.add(eventComponent, column[0]++, row[0]);
                        GridPane.setMargin(eventComponent, new Insets(5));
                    });
                }
            } catch (IOException e) {
                    throw new RuntimeException(e);
            }
        });
    }

    @FXML
    void onHandleBackToIndex(ActionEvent backToIndex) {
        try {
            FXRouter.goTo("index", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleLatestButton(ActionEvent event) {
        showAllEvent.getChildren().clear();
        //sort by new event

        initAllEvent(getLatestEvent());
    }
    private EventCollection getLatestEvent(){
        String search = searchInput.getText();
        EventCollection searchEventCollection = new EventCollection();
        for (Event event1 : eventCollection.sortByComparator(new LatestEventComparator())) {
            if (event1.getName().contains(search)) {
                searchEventCollection.addEvent(event1);
            }
        }
        return searchEventCollection;
    }
    @FXML
    void onHandleOldestButton(ActionEvent event) {
        showAllEvent.getChildren().clear();
        //sort by old event
        String search = searchInput.getText();
        EventCollection searchEventCollection = new EventCollection();
        for (Event event1 : eventCollection.sortByComparator(new OldestEventComparator())) {
            if (event1.getName().contains(search)) {
                searchEventCollection.addEvent(event1);
            }
        }
        initAllEvent(searchEventCollection);

    }
    @FXML
    void onHandleLeastJoinButton(ActionEvent event) {
        showAllEvent.getChildren().clear();
        //sort by least join
        String search = searchInput.getText();
        EventCollection searchEventCollection = new EventCollection();
        for (Event event1 : eventCollection.sortByComparator(new LeastEventParticipantComparator())) {
            if (event1.getName().contains(search)) {
                searchEventCollection.addEvent(event1);
            }
        }
        initAllEvent(searchEventCollection);



    }

    @FXML
    void onHandleMostJoinButton(ActionEvent event) {
        showAllEvent.getChildren().clear();
        //sort by most join
        String search = searchInput.getText();
        EventCollection searchEventCollection = new EventCollection();
        for (Event event1 : eventCollection.sortByComparator(new MostEventParticipantComparator())) {
            if (event1.getName().contains(search)) {
                searchEventCollection.addEvent(event1);
            }
        }
        initAllEvent(searchEventCollection);
    }



    @FXML
    void onHandleSearchEvent(ActionEvent event) {
        String search = searchInput.getText();
        ///use contain name from eventCollection without read file again
        EventCollection searchEventCollection = new EventCollection();
        for (Event event1 : eventCollection.getEvents()) {
            System.out.println(event1.getName());
            if (event1.getName().contains(search)) {
                searchEventCollection.addEvent(event1);
            }
        }
        showAllEvent.getChildren().clear();
        initAllEvent(searchEventCollection);
    }
}
