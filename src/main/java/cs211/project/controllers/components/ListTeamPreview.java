package cs211.project.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class ListTeamPreview {
    @FXML
    private Circle headTeamImageCircle;

    @FXML
    private Label titleTeamLabel;

    private String pathHeadTeamImageCircle = "";

    public ListTeamPreview(){
        headTeamImageCircle = new Circle();
        titleTeamLabel = new Label();
        pathHeadTeamImageCircle = new String();
    }

    //Getter
    public String getHeadTeamImageCircle() {
        return pathHeadTeamImageCircle;
    }

    public String getTitleTeamLabel() {
        return titleTeamLabel.getText();
    }

    //Setter
    public void setHeadTeamImageCircle(String path) {
        Image image = new Image(path);
        headTeamImageCircle.setFill(new ImagePattern(image));
        pathHeadTeamImageCircle= path;
    }

    public void setTitleTeamLabel(String titleTeamLabel) {
        this.titleTeamLabel.setText(titleTeamLabel);
    }

    public void setPathHeadTeamImageCircle(String pathHeadTeamImageCircle) {
        this.pathHeadTeamImageCircle = pathHeadTeamImageCircle;
    }
}
