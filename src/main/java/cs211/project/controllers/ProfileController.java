package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.models.collections.UserCollection;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import cs211.project.services.UserListFileDatasource;
import cs211.project.services.alert.ToastAlert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.UUID;

public class ProfileController {
    @FXML
    private ImageView avatarPicture;
    @FXML
    private AnchorPane navbar;
    @FXML
    private AnchorPane footer;
    @FXML
    private Label registerDateLabel;
    @FXML
    private Label lastLoginDateLabel;
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
    private UUID userId;
    private File avatarFile;
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
        avatarPicture.setImage(new Image("file:data" + File.separator + "image" + File.separator + "avatar" + File.separator + user.getAvatar()));

        registerDateLabel.setText(user.getCreatedAt());
        lastLoginDateLabel.setText(user.getLastLogin());

        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        usernameField.setText(user.getUsername());
    }

    @FXML
    private void handleSaveButtonClick(ActionEvent event) {
        User user = userListFileDatasource.query("id = " + userId).getAllUsers().get(0);
        if (!firstNameField.getText().isEmpty()) {
            userListFileDatasource.updateColumnById(user.getId(), "firstName", firstNameField.getText());
        }
        if (!lastNameField.getText().isEmpty()) {
            userListFileDatasource.updateColumnById(user.getId(), "lastName", lastNameField.getText());
        }
        if (!usernameField.getText().isEmpty()) {
            userListFileDatasource.updateColumnById(user.getId(), "userName", usernameField.getText());
        }
        if (!currentPasswordField.getText().isEmpty() || !newPasswordField.getText().isEmpty() || !confirmNewPasswordField.getText().isEmpty()) {
            validateCurrentPassword();
            validateNewPassword();
            validateConfirmNewPassword();
            if (currentPasswordField.getText().equals(user.getPassword())) {
                userListFileDatasource.updateColumnById(user.getId(), "password", newPasswordField.getText());
            } else {
                currentPasswordError.setText("รหัสผ่านปัจจุบันไม่ถูกต้อง");
            }
        }


        if (avatarFile != null) {
            String[] fileSplit = avatarFile.getName().split("\\.");
            String filename = user.getUsername() + "." + fileSplit[fileSplit.length - 1];
            Path dest = Paths.get("data" + File.separator + "image" + File.separator + "avatar" + File.separator + filename);
            try {
                Files.copy(avatarFile.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
                userListFileDatasource.updateColumnById(user.getId(), "avatar", filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ToastAlert.show("แก้ไขโพรไฟล์สำเร็จ", ToastAlert.AlertType.SUCCESS);
    }

    @FXML
    void handleEditAvatarButtonClick(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
        Node source = (Node) event.getSource();
        File imageFile = chooser.showOpenDialog(source.getScene().getWindow());
        if (imageFile != null) {
            avatarFile = imageFile;
            try (FileInputStream fis = new FileInputStream(imageFile)) {
                Image image = new Image(fis);
                avatarPicture.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
