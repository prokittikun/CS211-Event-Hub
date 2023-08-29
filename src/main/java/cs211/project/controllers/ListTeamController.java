package cs211.project.controllers;

import cs211.project.controllers.components.ListTeamCard;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListTeamController {
    @FXML
    private AnchorPane navbar;

    @FXML
    private AnchorPane footer;

    @FXML
    private VBox teamComponent;

    private List<ListTeamCard> ListTeamCardList;

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
        ListTeamCardList = ListTeamCardList();
        for (ListTeamCard listTeamCardData : ListTeamCardList) {
            try {
                FXMLLoader listTeamCardLoader = new FXMLLoader();
                listTeamCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/list-team-card.fxml"));
                Pane listTeamCardComponent = listTeamCardLoader.load();
                ListTeamCard listTeamCard = listTeamCardLoader.getController();
                //Set Value in List
                listTeamCard.setHeadTeamLabel(listTeamCardData.getHeadTeamLabel());
                listTeamCard.setTeamLabel(listTeamCardData.getTeamLabel());
                listTeamCard.setHeadTeamImage(listTeamCardData.getHeadTeamImage());
                listTeamCard.setOrderNumber(listTeamCardData.getOrderNumber());

                //Insert to Component
                teamComponent.getChildren().add(listTeamCardComponent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //HardCode
    private List<ListTeamCard> ListTeamCardList(){
        List<ListTeamCard> listTeamCardList = new ArrayList<>();
        ListTeamCard listTeamCard;
        for (int i = 0; i < 1; i++){
            listTeamCard = new ListTeamCard();
            //Set Value in List
            listTeamCard.setHeadTeamImage("https://picsum.photos/200");
            listTeamCard.setOrderNumber(""+ (i+1));
            listTeamCard.setTeamLabel("Production");
            listTeamCard.setHeadTeamLabel("Kittikun Boontoyut");
            //Append in List
            listTeamCardList.add(listTeamCard);
        }
        return listTeamCardList;
    }

    public void goToCreateTeam(){
        try {
            FXRouter.goTo("createTeam");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void backToMyEvent(){
        try {
            FXRouter.goTo("myEvent");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
