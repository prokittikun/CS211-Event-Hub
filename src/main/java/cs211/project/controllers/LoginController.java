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
    private Button backButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    public void initialize() {
        // Disable the login button by default
        loginButton.setDisable(true);
        usernameError.setText("");
        passwordError.setText("");

        // Listener to check if both fields are filled to enable the login button
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

        // TODO: Replace with your actual login logic
        if ("admin".equals(username) && "password123".equals(password)) {
            try {
                FXRouter.goTo("index");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Failed login
            // Show an error message or warning
            usernameError.setText("Username is invalid");
            passwordError.setText("Password is invalid");

        }
    }

    @FXML
    private void handleRegisterButtonClick(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("path/to/register-view.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // Add other methods as necessary for your application
}
