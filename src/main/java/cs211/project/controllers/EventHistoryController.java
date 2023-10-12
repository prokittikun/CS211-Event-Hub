package cs211.project.controllers;

import cs211.project.models.Activity;
import cs211.project.models.Event;
import cs211.project.models.JoinEvent;
import cs211.project.models.User;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.JoinEventCollection;
import cs211.project.models.collections.UserCollection;
import cs211.project.services.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class EventHistoryController {
    @FXML
    private AnchorPane footer;

    @FXML
    private AnchorPane navbar;

    @FXML
    private TableView<Event> eventHistoryTable;

    @FXML
    private TableColumn<Event, String> joinTimeColumn;
    @FXML
    private TableColumn<Event, ?> eventColumn;
    @FXML
    private TableColumn<Event, Integer> orderColumn;
    @FXML
    private TableColumn<Event, String> statusColumn;
    @FXML
    private TableColumn<Event, HBox> toolColumn;
    private EventCollection eventCollection;
    private UUID userId;
    private HashMap<String, Object> data;
    private Datasource<UserCollection> userListFileDatasource;
    private Datasource<EventCollection> eventListFileDatasource;
    private Datasource<JoinEventCollection> joinEventCollectionDatasource;

    @FXML
    private void initialize() {
        userListFileDatasource = new UserListFileDatasource("data", "user.csv");
        eventListFileDatasource = new EventListFileDatasource("data/event", "event.csv");
        joinEventCollectionDatasource = new JoinEventListFileDatasource("data/event", "joinEvent.csv");

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

        loadEventsIntoTable();

    }

    private String getJoinTimeForEvent(String eventId) {
        JoinEvent joinEvent = joinEventCollectionDatasource.query("eventId = " + eventId + " AND userId = " + this.userId).getJoinEvents().get(0);
        return DateTimeService.toString(joinEvent.getJoinTime());
    }

    private void loadEventsIntoTable() {
        JoinEventCollection joinEventCollection = joinEventCollectionDatasource.query("userId = " + userId);
        EventCollection eventCollection = new EventCollection();
        for (JoinEvent joinEvent : joinEventCollection.getJoinEvents()) {
            Event event = eventListFileDatasource.query("id = " + joinEvent.getEventId()).getEvents().get(0);
            eventCollection.addEvent(event);
        }

        orderColumn.setCellValueFactory(column -> {
            int rowIndex = eventHistoryTable.getItems().indexOf(column.getValue()) + 1;
            return new SimpleObjectProperty<>(rowIndex);
        });
        eventColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        joinTimeColumn.setCellValueFactory(param -> {
            Event event = param.getValue();
            String joinTime = getJoinTimeForEvent(event.getId());
            return new SimpleObjectProperty<>(joinTime);
        });
        statusColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStatus()));

        Callback<TableColumn<Event, HBox>, TableCell<Event, HBox>> cellFactory
                =
                new Callback<TableColumn<Event, HBox>, TableCell<Event, HBox>>() {
                    @Override
                    public TableCell call(final TableColumn<Event, HBox> param) {
                        final TableCell<Event, HBox> cell = new TableCell<Event, HBox>() {

                            final Button gotoEventButton = new Button();

                            @Override
                            public void updateItem(HBox item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    HBox hbox = new HBox();
                                    Event currentEvent = getTableView().getItems().get(getIndex());

                                    ImageView infoIcon = new ImageView(new Image(getClass().getResource("/cs211/project/views/assets/Icons/info.png").toExternalForm()));
                                    infoIcon.setFitHeight(20);
                                    infoIcon.setFitWidth(20);

                                    gotoEventButton.setGraphic(infoIcon);
                                    gotoEventButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                                    gotoEventButton.setOnAction(event -> {
                                        data.put("eventId", currentEvent.getId());
                                        onHandleGoToEventDetail(event);
                                    });


                                    hbox.getChildren().clear();
                                    hbox.getChildren().addAll(gotoEventButton);
                                    hbox.alignmentProperty().set(javafx.geometry.Pos.CENTER);
                                    setGraphic(hbox);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        toolColumn.setCellFactory(cellFactory);
        eventHistoryTable.getColumns().clear();
        eventHistoryTable.getColumns().add(orderColumn);
        eventHistoryTable.getColumns().add(eventColumn);
        eventHistoryTable.getColumns().add(joinTimeColumn);
        eventHistoryTable.getColumns().add(statusColumn);
        eventHistoryTable.getColumns().add(toolColumn);
        eventHistoryTable.getItems().clear();

        for (Event event : eventCollection.getEvents()) {
            eventHistoryTable.getItems().add(event);
        }
    }

    public void onHandleGoToEventDetail(ActionEvent event) {
        try {
            data.put("previousPage", "eventHistory");
            FXRouter.goTo("eventDetail", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
