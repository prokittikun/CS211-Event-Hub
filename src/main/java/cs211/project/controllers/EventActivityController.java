package cs211.project.controllers;

import cs211.project.models.Activity;
import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.collections.EventActivityCollection;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.JoinEventCollection;
import cs211.project.models.collections.TeamActivityCollection;
import cs211.project.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class EventActivityController {

    @FXML
    private Label eventDate;

    @FXML
    private ImageView eventImage;

    @FXML
    private Label eventLocation;

    @FXML
    private Label eventName;

    @FXML
    private Label eventParticipant;

    @FXML
    private AnchorPane footer;

    @FXML
    private AnchorPane navbar;

    private UUID eventId;
    private HashMap<String, Object> data;

    private Datasource<EventActivityCollection> eventActivityDatasource;
    private Datasource<EventCollection> eventDatasource;
    private Datasource<JoinEventCollection> joinEventCollectionDatasource;

    @FXML
    private TableColumn<Activity, String> orderColumn;

    @FXML
    private TableView<Activity> scheduleTable;

    @FXML
    private TableColumn<Activity, String> nameColumn;

    @FXML
    private TableColumn<Activity, String> dateTimeEndColumn;

    @FXML
    private TableColumn<Activity, String> dateTimeStartColumn;

    @FXML
    private TableColumn<Activity, String> detailColumn;

    @FXML
    private TableColumn<Activity, HBox> tool;

    @FXML
    private Text activityDetail;

    @FXML
    private AnchorPane backDrop;

    @FXML
    private Button closeModal;

    @FXML
    private Pane modal;

    private UUID creatorId;

    private boolean isCreator;

    private Event currentEvent;

    @FXML
    private Button addActivityButton;

    private String previousPage;

    @FXML
    void initialize() {
        data = FXRouter.getData();
        eventId = UUID.fromString(data.get("eventId").toString());
        data.remove("activityId");
        previousPage = data.get("previousPage").toString();
        eventActivityDatasource = new EventActivityListFileDatasource("data/event", "activity.csv");
        eventDatasource = new EventListFileDatasource("data/event", "event.csv");
        joinEventCollectionDatasource = new JoinEventListFileDatasource("data/event", "joinEvent.csv");
        backDrop.setVisible(false);
        modal.setVisible(false);
        initHeader();
        checkIsEventCreator();
        //Navbar
        FXMLLoader navbarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/navbar.fxml"));
        //Footer
        FXMLLoader footerComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/footer.fxml"));
        try {
            //Navbar
            AnchorPane navbarComponent = navbarComponentLoader.load();
            NavbarController navbarController = navbarComponentLoader.getController();
            navbarController.setData(this.data);
            navbar.getChildren().add(navbarComponent);

            //Footer
            AnchorPane footerComponent = footerComponentLoader.load();
            footer.getChildren().add(footerComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        orderColumn.setCellFactory(column -> {
            TableCell<Activity, String> cell = new TableCell<Activity, String>() {
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
        showTable();
    }


    private void initHeader(){
        Event event = eventDatasource.query("id = " + this.eventId).getEvents().get(0);
        currentEvent = event;
        eventName.setText(event.getName());
        eventLocation.setText(event.getLocation());
        Image image = new Image("file:data" + File.separator + "image" + File.separator + "event" + File.separator + event.getImage());
        eventImage.setImage(image);
        JoinEventCollection joinEventCollection = joinEventCollectionDatasource.query("eventId = " + event.getId());
        eventParticipant.setText(joinEventCollection.getJoinEvents().size() + "/" + event.getMaxParticipant());
        eventDate.setText(DateTimeService.toString(event.getStartDate()) + " - " + DateTimeService.toString(event.getEndDate()));
    }

    private void checkIsEventCreator() {
        creatorId = UUID.fromString(currentEvent.getUserId());
        if (creatorId.toString().equals(data.get("userId").toString())) {
            isCreator = true;
            addActivityButton.setVisible(true);
        } else {
            isCreator = false;
            addActivityButton.setVisible(false);
        }
    }

    private void showTable() {
        EventActivityCollection activityList = eventActivityDatasource.query("eventId = " + eventId.toString());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        detailColumn.setCellValueFactory(new PropertyValueFactory<>("detail"));

        dateTimeStartColumn.setCellValueFactory(new PropertyValueFactory<>("dateTimeStart"));
        dateTimeEndColumn.setCellValueFactory(new PropertyValueFactory<>("dateTimeEnd"));



        Callback<TableColumn<Activity, HBox>, TableCell<Activity, HBox>> cellFactory
                = //
                new Callback<TableColumn<Activity, HBox>, TableCell<Activity, HBox>>() {
                    @Override
                    public TableCell call(final TableColumn<Activity, HBox> param) {
                        final TableCell<Activity, HBox> cell = new TableCell<Activity, HBox>() {

                            final Button openmodalButton = new Button();
                            final Button deleteButton = new Button();

                            final Button editButton = new Button();

                            @Override
                            public void updateItem(HBox  item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    HBox hbox = new HBox(openmodalButton, deleteButton);
                                    Activity activity = getTableView().getItems().get(getIndex());

                                    ImageView infoIcon = new ImageView(new Image(getClass().getResource("/cs211/project/views/assets/Icons/info.png").toExternalForm()));
                                    infoIcon.setFitHeight(20);
                                    infoIcon.setFitWidth(20);

                                    openmodalButton.setGraphic(infoIcon);
                                    openmodalButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                                    // open modal
                                    openmodalButton.setOnAction(event -> {
                                        openModalDialog(activity);
                                    });


                                    ImageView trashIcon = new ImageView(new Image (getClass().getResource("/cs211/project/views/assets/Icons/trash-red.png").toExternalForm()));
                                    trashIcon.setFitHeight(20);
                                    trashIcon.setFitWidth(20);
                                    deleteButton.setGraphic(trashIcon);
                                    deleteButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                                    //open delete modal
                                    deleteButton.setOnAction(event -> {
                                        eventActivityDatasource.deleteById(activity.getId());
                                        showTable();
                                    });

                                    ImageView editIcon = new ImageView(new Image (getClass().getResource("/cs211/project/views/assets/Icons/edit-red.png").toExternalForm()));
                                    editIcon.setFitHeight(20);
                                    editIcon.setFitWidth(20);
                                    editButton.setGraphic(editIcon);
                                    editButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                                    //open edit modal
                                    editButton.setOnAction(event -> {
                                        try {
                                            HashMap<String, Object> newData = new HashMap<>();
                                            newData.put("eventId", eventId.toString());
                                            newData.put("activityId", activity.getId());
                                            newData.put("userId", data.get("userId"));
                                            newData.put("previousPage", previousPage);
                                            FXRouter.goTo("createEventActivity", newData);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });

                                    hbox.getChildren().clear();
                                    if (isCreator) {
                                        hbox.getChildren().addAll(openmodalButton, editButton, deleteButton);
                                    } else {
                                        hbox.getChildren().add(openmodalButton);
                                    }

//                                    hbox.getChildren().add(openmodalButton, editButton);

                                    hbox.alignmentProperty().set(javafx.geometry.Pos.CENTER);
                                    setGraphic(hbox);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        tool.setCellFactory(cellFactory);
        // ล้าง column เดิมทั้งหมดที่มีอยู่ใน table แล้วเพิ่ม column ใหม่
        scheduleTable.getColumns().clear();
        scheduleTable.getColumns().add(orderColumn);
        scheduleTable.getColumns().add(nameColumn);
        scheduleTable.getColumns().add(detailColumn);
        scheduleTable.getColumns().add(dateTimeStartColumn);
        scheduleTable.getColumns().add(dateTimeEndColumn);
        scheduleTable.getColumns().add(tool);

        scheduleTable.getItems().clear();

        for (Activity activity : activityList.getActivities()) {
            scheduleTable.getItems().add(activity);
        }
    }

    private void openModalDialog(Activity rowData) {
        backDrop.setVisible(true);
        modal.setVisible(true);
        activityDetail.setText(rowData.getDetail());

    }

    @FXML
    void onHandleCloseModal(ActionEvent event) {
        backDrop.setVisible(false);
        modal.setVisible(false);
    }

    @FXML
    void onHandleAddActivity(ActionEvent event) {
        try {
            FXRouter.goTo("createEventActivity", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void backToPreviousPage(ActionEvent event) {
        try {
            if(previousPage.equals("myEvent")) {
                FXRouter.goTo("myEvent", data);
            }else{
                FXRouter.goTo("eventDetail", data);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
