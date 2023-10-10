package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.JoinEventCollection;
import cs211.project.models.collections.UserCollection;
import cs211.project.services.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class EventHistoryController {
    @FXML
    private TableColumn<?, ?> dateColumn;

    @FXML
    private TableColumn<?, ?> eventColumn;

    @FXML
    private TableView<?> eventHistoryTable;

    @FXML
    private AnchorPane footer;

    @FXML
    private AnchorPane navbar;

    @FXML
    private TableColumn<?, ?> orderColumn;

    @FXML
    private TableColumn<?, ?> statusColumn;

    @FXML
    private TableColumn<?, ?> toolColumn;
    
    private UUID userId;
    private HashMap<String, Object> data;
    private Datasource<UserCollection> userListFileDatasource;
    private Datasource<EventCollection> eventListFileDatasource;
    private Datasource<JoinEventCollection> joinEventCollectionDatasource;

    @FXML
    private void initialize() {

        userListFileDatasource = new UserListFileDatasource("data", "user.csv");
        eventListFileDatasource = new EventListFileDatasource("data/event", "event.csv");
        joinEventCollectionDatasource = new JoinEventListFileDatasource("data/event", "jointEvent.csv");

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
        JoinEventCollection joinEventCollection = joinEventCollectionDatasource.readData();

    }

}
