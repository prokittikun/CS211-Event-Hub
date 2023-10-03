package cs211.project.controllers;

import cs211.project.models.JoinEvent;
import cs211.project.models.collections.JoinEventCollection;
import cs211.project.models.collections.UserCollection;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinEventListFileDatasource;
import cs211.project.services.UserListFileDatasource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class EventParticipantController {
    @FXML
    private AnchorPane footer;
    @FXML
    private AnchorPane navbar;
    private HashMap<String, Object> data;
    @FXML
    private TableColumn<JoinEvent, String> orderTable;
    @FXML
    private TableView<JoinEvent> participantTable;
    @FXML
    private TableColumn<JoinEvent, String> nameTable;
    @FXML
    private TableColumn<JoinEvent, HBox> actionTable;
    @FXML
    private TableColumn<JoinEvent, String> dateRegTable;
    private Datasource<JoinEventCollection> joinEventDatasource;
    private Datasource<UserCollection> userDatasource;
    private UserCollection users;

    @FXML
    private void initialize() {
        data = FXRouter.getData();
        joinEventDatasource = new JoinEventListFileDatasource("data/event", "joinEvent.csv");
        userDatasource = new UserListFileDatasource("data", "user.csv");
        participantTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //Navbar
        FXMLLoader navbarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/navbar.fxml"));
        //Footer
        FXMLLoader footerComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/footer.fxml"));
        try {
            //Navbar
            AnchorPane navbarComponent = navbarComponentLoader.load();
            NavbarController navbarController = navbarComponentLoader.getController();
            navbarController.setData(data);
            navbar.getChildren().add(navbarComponent);

            //Footer
            AnchorPane footerComponent = footerComponentLoader.load();
            footer.getChildren().add(footerComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Order Column
        orderTable.setCellFactory(column -> {
            TableCell<JoinEvent, String> cell = new TableCell<JoinEvent, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        // Display the row number (index + 1)
                        setText(String.valueOf(getIndex() + 1));
                    }
                }
            };
            return cell;
        });
        orderTable.setSortable(false);
        //Table
        showTable();
    }

    private void showTable() {
        JoinEventCollection joinEvents = joinEventDatasource.query("eventId = " + data.get("eventId"));
        System.out.println(joinEvents.toString());

        nameTable.setCellValueFactory(new PropertyValueFactory<>("userId"));
        dateRegTable.setCellValueFactory(new PropertyValueFactory<>("joinTime"));

        Callback<TableColumn<JoinEvent, HBox>, TableCell<JoinEvent, HBox>> cellFactory = new Callback<TableColumn<JoinEvent, HBox>, TableCell<JoinEvent, HBox>>() {
                    @Override
                    public TableCell call(final TableColumn<JoinEvent, HBox> param) {
                        final TableCell<JoinEvent, HBox> cell = new TableCell<JoinEvent, HBox>() {
                            final Button openmodalButton = new Button();
                            final Button deleteButton = new Button();
                            @Override
                            public void updateItem(HBox  item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    HBox hbox = new HBox(deleteButton);
                                    JoinEvent joinEvent = getTableView().getItems().get(getIndex());

                                    //Icon Delete
                                    ImageView trashIcon = new ImageView(new Image (getClass().getResource("/cs211/project/views/assets/Icons/trash-red.png").toExternalForm()));
                                    trashIcon.setFitHeight(20);
                                    trashIcon.setFitWidth(20);

                                    deleteButton.setGraphic(trashIcon);
                                    deleteButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                                    //open delete modal
                                    deleteButton.setOnAction(event -> {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("คุณต้องการลบผู้เข้าร่วมอีเวนต์นี้ใช่หรือไม่ ?");
                                        alert.setHeaderText("หากลบแล้วจะไม่สามารถนำกลับมาได้!");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        ButtonType button = result.orElse(ButtonType.CANCEL);

                                        if (button == ButtonType.OK) {
                                            //Delete Event
                                            joinEventDatasource.deleteById(joinEvent.getId());
                                            showTable();
                                        } else {
                                            alert.close();
                                        }
                                    });

                                    hbox.getChildren().clear();
                                    hbox.getChildren().add(deleteButton);
                                    hbox.alignmentProperty().set(javafx.geometry.Pos.CENTER);
                                    setGraphic(hbox);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        actionTable.setCellFactory(cellFactory);
        // ล้าง column เดิมทั้งหมดที่มีอยู่ใน table แล้วเพิ่ม column ใหม่
        participantTable.getColumns().clear();
        participantTable.getColumns().add(orderTable);
        participantTable.getColumns().add(dateRegTable);
        participantTable.getColumns().add(nameTable);
        participantTable.getColumns().add(actionTable);
        participantTable.getItems().clear();

        //Change Value From String User id to name by datasource query
        for (JoinEvent joinEvent : joinEvents.getJoinEvents()) {
            joinEvent.setUserId(userDatasource.query("id = " + joinEvent.getUserId()).getAllUsers().get(0).getFullName());
            participantTable.getItems().add(joinEvent);
        }
    }

    @FXML
    void goToMyEvent(ActionEvent event) {
        //Remove EventId
        data.remove("eventId");
        try {
            FXRouter.goTo("myEvent", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
