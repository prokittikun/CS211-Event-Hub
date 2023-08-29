package cs211.project.controllers;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.IOException;

public class ProfileController {
    @FXML
    private AnchorPane navbar;

    @FXML
    private Button saveButton;
    @FXML
    private Label registerDateLabel;
    @FXML
    private Label lastLoginDateLabel;
    @FXML
    private Button backButton;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private Label nameError;
    @FXML
    private TextField usernameField;
    @FXML
    private Label usernameError;
    @FXML
    private PasswordField currentPasswordField;
    @FXML
    private Label currentPasswordError;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private Label newPasswordError;
    @FXML
    private PasswordField confirmNewPasswordField;
    @FXML
    private Label confirmNewPasswordError;

    private String currentFirstName = "Pro";
    private String currentLastName = "Kittikhun";
    private String currentUsername = "pro.xx";
    private String currentRegisterDate = "";
    private String currentLastLoginDate = "";

    @FXML
    private void initialize() {

        FXMLLoader navbarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/navbar.fxml"));
        try {
            AnchorPane navbarComponent = navbarComponentLoader.load();
            navbar.getChildren().add(navbarComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        nameError.setText("");
        usernameError.setText("");
        currentPasswordError.setText("");
        newPasswordError.setText("");
        confirmNewPasswordError.setText("");

        registerDateLabel.setText(currentRegisterDate);
        lastLoginDateLabel.setText(currentLastLoginDate);

        firstNameField.setPromptText(currentFirstName);
        lastNameField.setPromptText(currentLastName);
        usernameField.setPromptText(currentUsername);
    }

    @FXML
    private void handleSaveButtonClick(ActionEvent event) {
        validateName();
        validateUsername();
        validateCurrentPassword();
        validateNewPassword();
        validateConfirmNewPassword();
    }

    @FXML
    private void handleBackButtonClick(ActionEvent event) {
        try {
            FXRouter.goTo("index");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateName() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        if (firstName.isEmpty() || lastName.isEmpty()) {
            nameError.setText("Name are required.");
        } else {
            nameError.setText("");
        }
    }

    private void validateUsername() {
        String username = usernameField.getText();

        if (username.isEmpty()) {
            usernameError.setText("Username is required.");
        } else {
            usernameError.setText("");
        }
    }

    private void validateCurrentPassword() {
        String currentPassword = currentPasswordField.getText();

        if (currentPassword.isEmpty()) {
            currentPasswordError.setText("Current password is required.");
        } else {
            currentPasswordError.setText("");
        }
    }

    private void validateNewPassword() {
        String newPassword = newPasswordField.getText();

        if (newPassword.isEmpty()) {
            newPasswordError.setText("New password is required.");
        } else {
            newPasswordError.setText("");
        }
    }

    private void validateConfirmNewPassword() {
        String newPassword = newPasswordField.getText();
        String confirmNewPassword = confirmNewPasswordField.getText();

        if (!confirmNewPassword.equals(newPassword)) {
            confirmNewPasswordError.setText("Passwords do not match.");
        } else {
            confirmNewPasswordError.setText("");
        }
    }

}
