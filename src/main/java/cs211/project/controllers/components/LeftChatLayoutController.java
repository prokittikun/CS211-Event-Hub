package cs211.project.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.File;

public class LeftChatLayoutController {
    @FXML
    private Circle senderImage;

    @FXML
    private Text senderMessage;

    @FXML
    private Label senderName;

    @FXML
    private Label timestamp;

    public String getSenderMessage() {
        return senderMessage.getText();
    }

    public String getSenderName() {
        return senderName.getText();
    }

    public String getTimestamp(){
        return timestamp.getText();
    }

    public void setSenderName(String name) {
        senderName.setText(name);
    }
    public void setSenderMessage(String message) {
        senderMessage.setText(message);
    }

    public void setSenderImage(String imageName) {
        Image image = new Image("file:data" + File.separator + "image" + File.separator + "avatar" + File.separator + imageName);
        senderImage.setFill(new ImagePattern(image));
    }

    public void setTimestamp(String timestamp){
        this.timestamp.setText(timestamp);
    }
}
