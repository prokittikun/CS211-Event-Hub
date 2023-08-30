package cs211.project.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class RightChatLayoutController {
    @FXML
    private Text myMessage;

    public String getMyMessage() {
        return myMessage.getText();
    }
    public void setMyMessage(String message) {
        myMessage.setText(message);
    }

}
