package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.models.collections.UserCollection;
import cs211.project.services.DateTimeService;
import cs211.project.services.FXRouter;
import cs211.project.services.UserListFileDatasource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class RegisterController {

    @FXML
    private TextField firstNameField;

    @FXML
    private Label firstNameError;

    @FXML
    private TextField lastNameField;

    @FXML
    private Label lastNameError;

    @FXML
    private TextField usernameField;

    @FXML
    private Label usernameError;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordError;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label confirmPasswordError;

    @FXML
    private Button registerButton;

    private UserListFileDatasource userListFileDatasource = new UserListFileDatasource("data", "user.csv");
    private UserCollection userCollection;

    public void initialize() {
        registerButton.setDisable(true);
        firstNameError.setText("");
        lastNameError.setText("");
        usernameError.setText("");
        passwordError.setText("");
        confirmPasswordError.setText("");

        firstNameField.setPromptText("ชื่อ");
        lastNameField.setPromptText("นามสกุล");
        usernameField.setPromptText("ชื่อผู้ใช้");
        passwordField.setPromptText("รหัสผ่าน");
        confirmPasswordField.setPromptText("ยืนยันรหัสผ่าน");

        firstNameField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        lastNameField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());

        userCollection = userListFileDatasource.readData();
    }

    private void validateFields() {
        if (!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty() && !usernameField.getText().isEmpty() && !passwordField.getText().isEmpty() && !confirmPasswordField.getText().isEmpty()) {
            registerButton.setDisable(false);
        } else {
            registerButton.setDisable(true);
        }
    }

    public void handleRegisterButtonClick() {
        validateFirstName();
        validateLastName();
        validateUsername();
        validatePassword();
        validateConfirmPassword();

        String username = usernameField.getText();
        UserCollection users = userListFileDatasource.query("username = " + username);

        if (!users.getAllUsers().isEmpty()) {
            usernameError.setText("ชื่อผู้ใช้นี้มีอยู่แล้ว");
        } else if (firstNameError.getText().isEmpty() &&
                lastNameError.getText().isEmpty() &&
                usernameError.getText().isEmpty() &&
                passwordError.getText().isEmpty() &&
                confirmPasswordError.getText().isEmpty()) {

            HashMap<String, String> userData = new HashMap<>();
            userData.put("id", UUID.randomUUID().toString());
            userData.put("avatar", "default.jpg");
            userData.put("firstName", firstNameField.getText());
            userData.put("lastName", lastNameField.getText());
            userData.put("username", usernameField.getText());
            userData.put("password", passwordField.getText());
            userData.put("role", "user");
            userData.put("createdAt", DateTimeService.getCurrentDateTime());

            User newUser = new User(userData);
            UserCollection newUserCollection = new UserCollection();
            newUserCollection.addUser(newUser);

            userListFileDatasource.writeData(newUserCollection);

            try {
                FXRouter.goTo("login");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void handleLoginButtonClick(ActionEvent event) throws Exception {
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateFirstName() {
        if (firstNameField.getText().isEmpty()) {
            firstNameError.setText("โปรดระบุชื่อ");
        } else {
            firstNameError.setText("");
        }
    }

    private void validateLastName() {
        if (lastNameField.getText().isEmpty()) {
            lastNameError.setText("โปรดระบุนามสกุล");
        } else {
            lastNameError.setText("");
        }
    }

    private void validateUsername() {
        if (usernameField.getText().isEmpty()) {
            usernameError.setText("โปรดระบุชื่อผู้ใช้");
        } else {
            usernameError.setText("");
        }
    }

    private void validatePassword() {
        if (passwordField.getText().isEmpty()) {
            passwordError.setText("โปรดระบุรหัสผ่าน");
        } else {
            passwordError.setText("");
        }
    }

    private void validateConfirmPassword() {
        if (!confirmPasswordField.getText().equals(passwordField.getText())) {
            confirmPasswordError.setText("รหัสผ่านไม่ตรงกัน");
        } else {
            confirmPasswordError.setText("");
        }
    }
}
