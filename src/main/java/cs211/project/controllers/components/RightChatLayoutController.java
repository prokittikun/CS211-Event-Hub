package cs211.project.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class RightChatLayoutController {
    @FXML
    private Text myMessage;
    @FXML
    private Label timestamp;
    public String getMyMessage() {
        return myMessage.getText();
    }
    public String getTimestamp(){
        return timestamp.getText();
    }
    public void setMyMessage(String message) {
        myMessage.setText(message);
    }
    public void setTimestamp(String timestamp){
        this.timestamp.setText(timestamp);
    }
}
