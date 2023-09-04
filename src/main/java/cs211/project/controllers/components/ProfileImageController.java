package cs211.project.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class ProfileImageController {

    @FXML
    private Circle profileImage;

    private String imagePath;

    public ProfileImageController() {
        profileImage = new Circle();
    }
    public ProfileImageController(String imagePath) {
        profileImage = new Circle();
        this.imagePath = imagePath;
        profileImage.setFill(new ImagePattern(new Image(imagePath)));
    }
    public void setProfileImage(String imagePath) {
        this.imagePath = imagePath;
        profileImage.setFill(new ImagePattern(new Image(imagePath)));
    }

    public String getImagePath() {
        return imagePath;
    }
}
