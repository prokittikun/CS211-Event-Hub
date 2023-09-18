package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.models.collections.UserCollection;
import cs211.project.services.FXRouter;
import cs211.project.services.DateTimeService;
import cs211.project.services.UserListFileDatasource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.io.IOException;

public class LoginController {

    @FXML
    private Label usernameError;

    @FXML
    private Label passwordError;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    private UserListFileDatasource userListFileDatasource;

    public void initialize() {
        userListFileDatasource = new UserListFileDatasource("data", "user.csv");

        loginButton.setDisable(true);
        usernameError.setText("");
        passwordError.setText("");

        usernameField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
    }

    private void validateFields() {
        if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            loginButton.setDisable(false);
        } else {
            loginButton.setDisable(true);
        }
    }

    @FXML
    private void handleLoginButtonClick(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        UserCollection users = userListFileDatasource.readData();

        User loggedInUser = null;
        for (User user : users.getAllUsers()) {
            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                loggedInUser = user;
                break;
            }
        }

        if (loggedInUser != null) {
            String lastLoginTime = DateTimeService.getCurrentDateTime();
            loggedInUser.setLastLogin(lastLoginTime);
            userListFileDatasource.writeData(users);
            try {
                FXRouter.goTo("index");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            usernameError.setText("Username is invalid");
            passwordError.setText("Password is invalid");
        }
    }

    @FXML
    private void handleRegisterButtonClick(ActionEvent event) throws Exception {
        try {
            FXRouter.goTo("register");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
