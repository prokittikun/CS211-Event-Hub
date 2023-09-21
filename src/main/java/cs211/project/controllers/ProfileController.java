package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.models.collections.UserCollection;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import cs211.project.services.UserListFileDatasource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class ProfileController {
    @FXML
    private AnchorPane navbar;
    @FXML
    private AnchorPane footer;

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

    private String currentFirstName;
    private String currentLastName;
    private String currentUsername;
    private String currentRegisterDate;
    private String currentLastLoginDate;

    private UUID userId;

    private HashMap<String, Object> data;
    private Datasource<UserCollection> userListFileDatasource;

    @FXML
    private void initialize() {
        userListFileDatasource = new UserListFileDatasource("data", "user.csv");

        data = FXRouter.getData();
        userId = UUID.fromString((String) data.get("userId"));

        FXMLLoader navbarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/navbar.fxml"));
        FXMLLoader footerComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/footer.fxml"));
        try {
            AnchorPane navbarComponent = navbarComponentLoader.load();
            NavbarController navbarController = navbarComponentLoader.getController();
            navbarController.setData(data);
            navbar.getChildren().add(navbarComponent);

            AnchorPane footerComponent = footerComponentLoader.load();
            footer.getChildren().add(footerComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        User user = userListFileDatasource.query("id = " + userId).getAllUsers().get(0);

        nameError.setText("");
        usernameError.setText("");
        currentPasswordError.setText("");
        newPasswordError.setText("");
        confirmNewPasswordError.setText("");

        registerDateLabel.setText(user.getCreatedAt());
        lastLoginDateLabel.setText(user.getLastLogin());

        firstNameField.setPromptText(user.getFirstName());
        lastNameField.setPromptText(user.getLastName());
        usernameField.setPromptText(user.getUsername());
    }

    @FXML
    private void handleSaveButtonClick(ActionEvent event) {
        if (!currentPasswordField.getText().isEmpty() || !newPasswordField.getText().isEmpty() || !confirmNewPasswordField.getText().isEmpty()) {
            validateCurrentPassword();
            validateNewPassword();
            validateConfirmNewPassword();
        }
        User user = userListFileDatasource.query("id = " + userId).getAllUsers().get(0);
        if(!firstNameField.getText().isEmpty()){
            userListFileDatasource.updateColumnById(user.getId(), "firstName", firstNameField.getText());
        }
        if(!lastNameField.getText().isEmpty()){
            userListFileDatasource.updateColumnById(user.getId(), "lastName", lastNameField.getText());
        }
        if(!usernameField.getText().isEmpty()){
            userListFileDatasource.updateColumnById(user.getId(), "userName", usernameField.getText());
        }
    }

    @FXML
    private void handleBackButtonClick(ActionEvent event) {
        try {
            FXRouter.goTo("index");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateCurrentPassword() {
        String currentPassword = currentPasswordField.getText();

        if (currentPassword.isEmpty()) {
            currentPasswordError.setText("โปรดระบุรหัสผ่านปัจจุบัน");
        } else {
            currentPasswordError.setText("");
        }
    }

    private void validateNewPassword() {
        String newPassword = newPasswordField.getText();

        if (newPassword.isEmpty()) {
            newPasswordError.setText("โปรดระบุรหัสผ่านใหม่");
        } else {
            newPasswordError.setText("");
        }
    }

    private void validateConfirmNewPassword() {
        String newPassword = newPasswordField.getText();
        String confirmNewPassword = confirmNewPasswordField.getText();

        if (!confirmNewPassword.equals(newPassword)) {
            confirmNewPasswordError.setText("รหัสผ่านไม่ตรงกัน");
        } else {
            confirmNewPasswordError.setText("");
        }
    }

}
