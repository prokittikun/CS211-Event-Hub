package cs211.project.controllers.components;

import cs211.project.controllers.ListTeamController;
import cs211.project.models.collections.TeamCollection;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import cs211.project.services.TeamListFileDatasource;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class ListTeamCard {
    @FXML
    private Circle headTeamImageCircle;

    @FXML
    private Label headTeamLabel;

    @FXML
    private Button orderNumber;

    @FXML
    private Label teamLabel;

    private String pathHeadTeamImage = "";

    private HashMap<String, Object> data;

    private Datasource<TeamCollection> datasourceTeam;
    private TeamCollection teamList;

    private ListTeamController teamController;

    @FXML
    public void initialize(){
        datasourceTeam = new TeamListFileDatasource("data/team", "team.csv");
    }

    public ListTeamCard() {
        this.headTeamImageCircle = new Circle();
        this.headTeamLabel = new Label();
        this.orderNumber = new Button();
        this.teamLabel = new Label();
    }

    //Method
    @FXML
    public void getGoToSettingTeam() {
        try {
            data.put("previousPage", "listTeam");
            FXRouter.goTo("teamManagement", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goToEditTeam(){
        try {
            FXRouter.goTo("editTeam", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onHandleDeleteTeam() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("คุณต้องการลบทีมนี้ใช่หรือไม่ ?");
        alert.setHeaderText("หากลบแล้วจะไม่สามารถกู้คืนกลับมาได้");

        Optional<ButtonType> result = alert.showAndWait();
        ButtonType button = result.orElse(ButtonType.CANCEL);

        if (button == ButtonType.OK) {
            //Delete Team
            datasourceTeam.deleteById((String) data.get("teamId"));
            teamController.reload();
        } else {
            alert.close();
        }
    }

    //Getter
    public String getHeadTeamImage() {
        return pathHeadTeamImage;
    }

    public String getHeadTeamLabel() {
        return headTeamLabel.getText();
    }

    public String getOrderNumber() {
        return orderNumber.getText();
    }

    public String getTeamLabel() {
        return teamLabel.getText();
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    //Setter
    public void setHeadTeamImage(String path) {
        Image image = new Image(path);
        headTeamImageCircle.setFill(new ImagePattern(image));
        this.pathHeadTeamImage = path;
    }

    public void setHeadTeamLabel(String headTeamLabel) {
        this.headTeamLabel.setText(headTeamLabel);
    }

    public void setOrderNumber(String number) {
        this.orderNumber.setText(number);
    }

    public void setTeamLabel(String teamLabel) {
        this.teamLabel.setText(teamLabel);
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public void setTeamController(ListTeamController teamController) {
        this.teamController = teamController;
    }
}
