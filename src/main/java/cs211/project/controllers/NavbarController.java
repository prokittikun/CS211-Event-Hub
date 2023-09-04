package cs211.project.controllers;

import cs211.project.services.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class NavbarController {
    @FXML
    private Circle navImageProfile;

    @FXML
    private Pane manageDropdown;

    private Boolean showManageDropdown = false;
    private Boolean showProfileDropdown = false;

    @FXML
    private Pane profileDropdown;
    @FXML
    private void initialize() {
        manageDropdown.setVisible(showManageDropdown);
        profileDropdown.setVisible(showProfileDropdown);
    }
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

    @FXML
    void onHandleGoToMyEvent(MouseEvent event) {
        try {
            FXRouter.goTo("myEvent");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleGoToMyTeam(MouseEvent  event) {
        try {
            FXRouter.goTo("myTeam");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleGoToMyProfile(MouseEvent event) {
        try {
            FXRouter.goTo("profile");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleGoToHistory(MouseEvent event) {
        try {
            FXRouter.goTo("history");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleGoToDashboard(MouseEvent event) {
        try {
            FXRouter.goTo("dashboard");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    @FXML
    void onHandleManageDropdown(MouseEvent event) {
        manageDropdown.setVisible(!showManageDropdown);
        showManageDropdown = !showManageDropdown;

    }

    @FXML
    void onHandleProfileDropdown(MouseEvent event) {
        profileDropdown.setVisible(!showProfileDropdown);
        showProfileDropdown = !showProfileDropdown;
    }

    @FXML
    void onHandleLogout(ActionEvent event) {
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
