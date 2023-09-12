package cs211.project.controllers;

import cs211.project.controllers.components.MyEventCard;
import cs211.project.models.Event;
import cs211.project.models.JoinEvent;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.JoinEventCollection;
import cs211.project.services.Datasource;
import cs211.project.services.EventListFileDatasource;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinEventListFileDatasource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class MyEventController {
    @FXML
    private AnchorPane navbar;
    @FXML
    private AnchorPane footer;
    @FXML
    private VBox myEventComponent;
    private Datasource<EventCollection> datasource;
    private Datasource<JoinEventCollection> datasourceJoinEvent;
    private EventCollection myEventList;
    private JoinEventCollection joinEventList;

    @FXML
    private void initialize() {
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

        //Datasource
        datasource = new EventListFileDatasource("data/event", "event.csv");
        myEventList = datasource.readData();

        //Filter JoinEvent by eventId
        datasourceJoinEvent = new JoinEventListFileDatasource("data/event", "joinEvent.csv");
        joinEventList = datasourceJoinEvent.readData();

        //Component
        int i = 0;
        for (Event myEventCardData : myEventList.getEvents()) {
            try {
                FXMLLoader myEventCardControllerLoader = new FXMLLoader();
                myEventCardControllerLoader.setLocation(getClass().getResource("/cs211/project/views/components/my-event-card.fxml"));
                AnchorPane myEventCardComponent = myEventCardControllerLoader.load();
                MyEventCard myEventCard = myEventCardControllerLoader.getController();
                //Set Value in List
                myEventCard.setEventTitle(myEventCardData.getName());
                myEventCard.setEventDate(myEventCardData.getStartDate());
                myEventCard.setEventImage(myEventCardData.getImage());
                myEventCard.setEventLocation(myEventCardData.getLocation());

                //Filter JoinEvent by eventId
                List<JoinEvent> filteredJoinEvents = joinEventList.filterByEventId(myEventCardData.getId());
                myEventCard.setParticipantEvent(filteredJoinEvents.size() + "/" + myEventCardData.getMaxParticipant());
                i++;

                myEventCard.setOrderNumber(String.valueOf(i));
                //Insert to Component
                myEventComponent.getChildren().add(myEventCardComponent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void goToCreateEvent(){
        try {
            FXRouter.goTo("createEvent");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
