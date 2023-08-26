package cs211.project.controllers;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
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

    public void initialize() {
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

        if ("admin".equals(username) && "password123".equals(password)) {
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
