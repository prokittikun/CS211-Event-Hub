package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.UserCollection;
import cs211.project.services.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.List;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class DashboardController {

    @FXML
    private AnchorPane footer;

    @FXML
    private Label lastLoginDateLabel;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private Label nameLabel;

    @FXML
    private AnchorPane navbar;

    @FXML
    private TableColumn<User, Number> orderColumn;

    @FXML
    private Label registerDateLabel;

    @FXML
    private TableColumn<?, ?> roleColumn;

    @FXML
    private Label roleLabel;

    @FXML
    private Label totalAdminLabel;

    @FXML
    private Label totalEventLabel;

    @FXML
    private Label totalUserLabel;
    @FXML
    private Circle avatarPicture;
    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<?, ?> usernameColumn;
    private UUID userId;
    private HashMap<String, Object> data;
    private Datasource<UserCollection> userListFileDatasource;
    private Datasource<EventCollection> eventListFileDatasource;

    @FXML
    private void initialize() {
        userListFileDatasource = new UserListFileDatasource("data", "user.csv");
        eventListFileDatasource = new EventListFileDatasource("data/event", "event.csv");

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
        EventCollection eventCollection = eventListFileDatasource.readData();


        nameLabel.setText(user.getFullName());
        roleLabel.setText(user.getRole());
        AtomicReference<Image> avatar = new AtomicReference<>(new Image("file:data" + File.separator + "image" + File.separator + "avatar" + File.separator + user.getAvatar()));
        avatarPicture.setFill(new ImagePattern(avatar.get()));
        totalEventLabel.setText("");
        totalAdminLabel.setText("");
        totalUserLabel.setText("");
        registerDateLabel.setText(user.getCreatedAt());
        lastLoginDateLabel.setText(user.getLastLogin());

        loadUsersIntoTable();
        userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameLabel.setText(newSelection.getFullName());
                roleLabel.setText(newSelection.getRole());
                registerDateLabel.setText(newSelection.getCreatedAt());
                lastLoginDateLabel.setText(newSelection.getLastLogin());
                avatar.set(new Image("file:data" + File.separator + "image" + File.separator + "avatar" + File.separator + newSelection.getAvatar()));
                avatarPicture.setFill(new ImagePattern(avatar.get()));
            }
        });

        updateTotalUsersCount();
        updateTotalAdminsCount();
        totalEventLabel.setText(String.valueOf(eventCollection.getEvents().size()));
    }

    private void loadUsersIntoTable() {
        UserListFileDatasource userListFileDatasource = new UserListFileDatasource("data", "user.csv");
        List<User> users = userListFileDatasource.readData().getAllUsers();

        orderColumn.setCellValueFactory(column -> {
            int rowIndex = userTable.getItems().indexOf(column.getValue()) + 1;
            return new SimpleObjectProperty<>(rowIndex);
        });

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        userTable.setItems(FXCollections.observableArrayList(users));
    }

    private void updateTotalUsersCount() {
        List<User> users = userListFileDatasource.readData().getAllUsers();

        long userCount = users.stream().filter(user -> "user".equalsIgnoreCase(user.getRole())).count();

        totalUserLabel.setText(String.valueOf(userCount));
    }
    private void updateTotalAdminsCount() {
        List<User> users = userListFileDatasource.readData().getAllUsers();

        long userCount = users.stream().filter(user -> "admin".equalsIgnoreCase(user.getRole())).count();

        totalAdminLabel.setText(String.valueOf(userCount));
    }

}
