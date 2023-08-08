package cs211.project.controllers;

import cs211.project.services.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class NavbarController {
    @FXML
    private Circle navImageProfile;

    @FXML
    void onHandleGoToContactUs(ActionEvent event) {
        try {
            FXRouter.goTo("contactUs");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleGoToHomePage(ActionEvent event) {
        try {
            FXRouter.goTo("index");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
