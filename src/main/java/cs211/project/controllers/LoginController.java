package cs211.project.controllers;

import cs211.project.cs211661project.HelloApplication;
import cs211.project.models.User;
import cs211.project.models.collections.UserCollection;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import cs211.project.services.DateTimeService;
import cs211.project.services.UserListFileDatasource;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class LoginController {

    @FXML
    private Label loginError;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button backButton;

    @FXML
    private Button loginButton;

    private Datasource<UserCollection> userListFileDatasource;

    public void initialize() {
        userListFileDatasource = new UserListFileDatasource("data", "user.csv");

        loginButton.setDisable(true);
        loginError.setText("");

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

        UserCollection users = userListFileDatasource.query("username = " + username + " AND password = " + password);

        if (!users.getAllUsers().isEmpty()) {
            String lastLoginTime = DateTimeService.getCurrentDateTime();
            User user = users.getAllUsers().get(0);
            userListFileDatasource.updateColumnById(user.getId(), "lastLogin", lastLoginTime);
            try {
                HashMap<String, Object> data = new HashMap<>();
                data.put("userId", user.getId());
                FXRouter.goTo("index", data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            loginError.setText("ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง");
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

    @FXML
    void handleGuideButtonClick(ActionEvent event) {
        HostServices hostServices = HelloApplication.getHostServicesStatic();
        String pdfURL = new File("data/user-guide.pdf").toURI().toString();
        hostServices.showDocument(pdfURL);
    }


}
