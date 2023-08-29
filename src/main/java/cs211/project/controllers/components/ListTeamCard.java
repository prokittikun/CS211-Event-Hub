package cs211.project.controllers.components;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;

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

    public ListTeamCard() {
        this.headTeamImageCircle = new Circle();
        this.headTeamLabel = new Label();
        this.orderNumber = new Button();
        this.teamLabel = new Label();
    }

    //Method
    public void getGoToSettingTeam() {
        try {
            FXRouter.goTo("manageTeam");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onHandleDeleteTeam() {

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
}
