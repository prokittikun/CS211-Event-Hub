package cs211.project.controllers;

import cs211.project.controllers.components.ListTeamPreview;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateTeamController {
    @FXML
    private AnchorPane navbar;
    @FXML
    private AnchorPane footer;

    private List<ListTeamPreview> listTeamPreviewList;

    @FXML
    private VBox teamPreviewComponent;

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
        listTeamPreviewList = ListTeamPreviewList();
        for (ListTeamPreview listTeamPreviewData : listTeamPreviewList) {
            try {
                FXMLLoader listTeamCardLoader = new FXMLLoader();
                listTeamCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/list-team-preview.fxml"));
                Pane listTeamCardComponent = listTeamCardLoader.load();
                ListTeamPreview listTeamCard = listTeamCardLoader.getController();
                //Set Value in List
                listTeamCard.setTitleTeamLabel(listTeamPreviewData.getTitleTeamLabel());
                listTeamCard.setHeadTeamImageCircle(listTeamPreviewData.getHeadTeamImageCircle());

                //Insert to Component
                teamPreviewComponent.getChildren().add(listTeamCardComponent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //HardCode
    private List<ListTeamPreview> ListTeamPreviewList(){
        List<ListTeamPreview> listTeamPreviewList = new ArrayList<>();
        ListTeamPreview listTeamCard;
        for (int i = 0; i < 1; i++){
            listTeamCard = new ListTeamPreview();
            //Set Value in List
            listTeamCard.setHeadTeamImageCircle("https://picsum.photos/200");
            listTeamCard.setTitleTeamLabel("Production");
            //Append in List
            listTeamPreviewList.add(listTeamCard);
        }
        return listTeamPreviewList;
    }

    @FXML
    public void backToListTeam(){
        try {
            FXRouter.goTo("listTeam");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
