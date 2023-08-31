package cs211.project.controllers;

import cs211.project.controllers.components.MyEventCard;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyEventController {
    @FXML
    private AnchorPane navbar;
    @FXML
    private AnchorPane footer;
    @FXML
    private VBox myEventComponent;

    private List<MyEventCard> myEventCardList;

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
        
        //Component
        myEventCardList = MyEventCardList();
        for (MyEventCard myEventCardData : myEventCardList) {
            try {
                FXMLLoader myEventCardControllerLoader = new FXMLLoader();
                myEventCardControllerLoader.setLocation(getClass().getResource("/cs211/project/views/components/my-event-card.fxml"));
                Pane myEventCardComponent = myEventCardControllerLoader.load();
                MyEventCard myEventCard= myEventCardControllerLoader.getController();
                //Set Value in List
                myEventCard.setEventDate(myEventCardData.getEventDate());
                myEventCard.setEventImage(myEventCardData.getEventImage());
                myEventCard.setEventLocation(myEventCardData.getEventLocation());
                myEventCard.setParticipantEvent(myEventCardData.getParticipantEvent());
                myEventCard.setEventTitle(myEventCardData.getEventTitle());
                myEventCard.setOrderNumber(myEventCardData.getOrderNumber());
                //Insert to Component
                myEventComponent.getChildren().add(myEventCardComponent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //HardCode
    private List<MyEventCard> MyEventCardList(){
        List<MyEventCard> myEventCardList = new ArrayList<>();
        MyEventCard myEventCard;
        for (int i = 0; i < 1; i++){
            myEventCard = new MyEventCard();
            //Set Value in List
            myEventCard.setEventImage("https://picsum.photos/200");
            myEventCard.setOrderNumber(""+ (i+1));
            myEventCard.setEventTitle("KU Freshy Festival");
            myEventCard.setEventLocation("Kasetsart University");
            myEventCard.setParticipantEvent("30/40");
            myEventCard.setEventDate("30 Aug 2066");
            //Append in List
            myEventCardList.add(myEventCard);
        }
        return myEventCardList;
    }

    public void goToCreateEvent(){
        try {
            FXRouter.goTo("createEvent");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
