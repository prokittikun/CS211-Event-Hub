package cs211.project.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;

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
    public void setProfileImage(String imageName) {
        Image image = new Image("file:data" + File.separator + "image" + File.separator + "avatar" + File.separator + imageName);
        profileImage.setFill(new ImagePattern(image));
    }
    public String getImagePath() {
        return imagePath;
    }
}
