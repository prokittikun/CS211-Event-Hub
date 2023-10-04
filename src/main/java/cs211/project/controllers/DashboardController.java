package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.models.collections.UserCollection;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import cs211.project.services.UserListFileDatasource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

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
    private TableColumn<?, ?> orderColumn;

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
    private ImageView avatarPicture;
    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<?, ?> usernameColumn;
    private UUID userId;
    private HashMap<String, Object> data;

    @FXML
    private void initialize() {

        UserListFileDatasource userListFileDatasource = new UserListFileDatasource("data", "user.csv");

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

        nameLabel.setText(user.getFullName());
        roleLabel.setText(user.getRole());
        //avatarPicture.setImage(new Image("file:data" + File.separator + "image" + File.separator + "avatar" + File.separator + user.getAvatar()));
        totalEventLabel.setText("");
        totalAdminLabel.setText("");
        totalUserLabel.setText("");
        registerDateLabel.setText(user.getCreatedAt());
        lastLoginDateLabel.setText(user.getLastLogin());

        loadUsersIntoTable();

    }

    private void loadUsersIntoTable() {
        UserListFileDatasource userListFileDatasource = new UserListFileDatasource("data", "user.csv");
        List<User> users = userListFileDatasource.readData().getAllUsers();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        userTable.setItems(FXCollections.observableArrayList(users));
    }
}
