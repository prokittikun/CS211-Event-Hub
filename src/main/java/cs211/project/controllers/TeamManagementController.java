package cs211.project.controllers;

import cs211.project.models.*;
import cs211.project.models.collections.*;
import cs211.project.services.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cs211.project.controllers.components.TeamMemberCard;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class TeamManagementController {

    private UUID teamId;

    private UUID leaderId;
    @FXML
    private AnchorPane navbar;

    @FXML
    private AnchorPane footer;

    @FXML
    private Button addActivityButton;

    @FXML
    private VBox memberVbox;

    private Datasource<TeamMemberCollection> teamMemberDatasource;

    private Datasource<TeamCollection> teamDatasource;

    private Datasource<UserCollection> userDatasource;
    private Datasource<EventCollection> eventDatasource;

    private TeamMemberCollection teamMemberCollection;
    private HashMap<String, Object> data;

    private Event currentEvent;

    private Team currentTeam;

    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @FXML
    private TableView<Activity> scheduleTable;
    @FXML
    private TableColumn<Activity, String> orderColumn;
    @FXML
    private TableColumn<Activity, String> nameColumn;
    @FXML
    private TableColumn<Activity, Label> statusColumn;
    @FXML
    private TableColumn<Activity, HBox> tool;
    private Datasource<TeamActivityCollection> teamActivityDatasource;
    private boolean isLeader = false;

    @FXML
    private Label teamName;

    @FXML
    private Text activityDetail;

    @FXML
    private Label startAndEndDateTime;

    @FXML
    private Button closeModal;

    @FXML
    private AnchorPane backDrop;

    @FXML
    private Pane modal;

    private String previousPage;

    @FXML
    private void initialize() {
        data = FXRouter.getData();
        teamId = UUID.fromString(data.get("teamId").toString());
        data.remove("activityId");
        previousPage = (String) data.get("previousPage");
        teamActivityDatasource = new TeamActivityListFileDatasource("data/team", "activity.csv");
        teamDatasource = new TeamListFileDatasource("data/team", "team.csv");
        eventDatasource = new EventListFileDatasource("data/event", "event.csv");
        backDrop.setVisible(false);
        modal.setVisible(false);
        checkIsLeader();
        initTeamMember();

        scheduleTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //Navbar
        FXMLLoader navbarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/navbar.fxml"));
        //Footer
        FXMLLoader footerComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/footer.fxml"));
        try {
            //Navbar
            AnchorPane navbarComponent = navbarComponentLoader.load();
            //get controller
            NavbarController navbarController = navbarComponentLoader.getController();
            navbarController.setData(data);
            navbar.getChildren().add(navbarComponent);

            //Footer
            AnchorPane footerComponent = footerComponentLoader.load();
            footer.getChildren().add(footerComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scheduleTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Activity>() {
            @Override
            public void changed(ObservableValue observable, Activity oldValue, Activity newValue) {
                if (newValue != null) {
                    //open modal
                    openModalDialog(newValue);
                }
            }
        });
        //todo order column
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
        orderColumn.setSortable(false);
        showTable();
    }

    private void checkIsLeader() {
        currentTeam = teamDatasource.query("id = " + teamId.toString()).getTeams().get(0);
        teamName.setText(currentTeam.getName());
        currentEvent = eventDatasource.query("id = " + currentTeam.getEventId()).getEvents().get(0);
        leaderId = UUID.fromString(currentTeam.getLeaderId());
        if (leaderId.toString().equals(data.get("userId").toString()) || currentEvent.getUserId().equals(data.get("userId").toString())) {
            isLeader = true;
            addActivityButton.setVisible(true);
        } else {
            isLeader = false;
            addActivityButton.setVisible(false);
        }
    }

    private void showTable() {
        TeamActivityCollection activityList = teamActivityDatasource.query("teamId = " + teamId.toString());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        statusColumn.setCellValueFactory(cellData -> {
            Activity activity = cellData.getValue();
            boolean status = activity.getStatus();
            Label label = new Label();

            if (status) {
                label.setText("เสร็จสิ้น");
                label.getStyleClass().add("bg-green");
            } else {
                label.setText("รอดำเนินการ");
                label.getStyleClass().add("bg-red-for-darkMode");
            }
            label.getStyleClass().add("text-white");
            label.getStyleClass().add("rounded-20px");
            label.getStyleClass().add("text-sm");
            label.setStyle("-fx-padding: 5px 10px;");

            return new SimpleObjectProperty<>(label);
        });

        Callback<TableColumn<Activity, HBox>, TableCell<Activity, HBox>> cellFactory
                = //
                new Callback<TableColumn<Activity, HBox>, TableCell<Activity, HBox>>() {
                    @Override
                    public TableCell call(final TableColumn<Activity, HBox> param) {
                        final TableCell<Activity, HBox> cell = new TableCell<Activity, HBox>() {


                            final Button openmodalButton = new Button();

                            final Button chatButton = new Button();

                            final Button changeStatus = new Button();
                            final Button deleteButton = new Button();

                            final Button editButton = new Button();

                            @Override
                            public void updateItem(HBox item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    HBox hbox = new HBox();
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

                                    ImageView chatIcon = new ImageView(new Image(getClass().getResource("/cs211/project/views/assets/Icons/chat.png").toExternalForm()));
                                    chatIcon.setFitHeight(20);
                                    chatIcon.setFitWidth(20);

                                    chatButton.setGraphic(chatIcon);
                                    chatButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                                    // open chat
                                    chatButton.setOnAction(event -> {
                                        try {
                                            HashMap<String, Object> newData = new HashMap<>();
                                            newData.put("teamId", teamId.toString());
                                            newData.put("activityId", activity.getId());
                                            newData.put("userId", data.get("userId"));
                                            newData.put("eventId", currentTeam.getEventId());
                                            newData.put("previousPage", previousPage);
                                            FXRouter.goTo("chat", newData);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });

                                    ImageView statusIcon = new ImageView(new Image(getClass().getResource("/cs211/project/views/assets/Icons/select.png").toExternalForm()));
                                    statusIcon.setFitHeight(20);
                                    statusIcon.setFitWidth(20);
                                    changeStatus.setGraphic(statusIcon);
                                    changeStatus.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                                    //change status
                                    changeStatus.setOnAction(event -> {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                                        if (activity.getStatus()) {
                                            alert.setTitle("คุณต้องการเปลี่ยนสถานะของกิจกรรมเป็นรอดำเนินการใช่หรือไม่ ?");
                                            alert.setHeaderText("หากเปลี่ยนแล้วจะสามารถสื่อสารภายใต้กิจกรรมนี้ได้");
                                        } else {
                                            alert.setTitle("คุณต้องการเปลี่ยนสถานะของกิจกรรมเป็นเสร็จสิ้นใช่หรือไม่ ?");
                                            alert.setHeaderText("หากเปลี่ยนแล้วจะไม่สามารถสื่อสารภายใต้กิจกรรมนี้ได้");
                                        }

                                        Optional<ButtonType> result = alert.showAndWait();
                                        ButtonType button = result.orElse(ButtonType.CANCEL);
                                        if (button == ButtonType.OK) {
                                            activity.setStatus(!activity.getStatus());
                                            teamActivityDatasource.updateColumnById(activity.getId(), "status", activity.getStatus().toString());
                                            showTable();
                                        }

                                    });

                                    ImageView trashIcon = new ImageView(new Image(getClass().getResource("/cs211/project/views/assets/Icons/trash-red.png").toExternalForm()));
                                    trashIcon.setFitHeight(20);
                                    trashIcon.setFitWidth(20);
                                    deleteButton.setGraphic(trashIcon);
                                    deleteButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                                    //open delete modal
                                    deleteButton.setOnAction(event -> {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("คุณต้องการลบกิจกรรม " + activity.getName() +" ใช่หรือไม่ ?");
                                        alert.setHeaderText("หากลบแล้วจะไม่สามารถนำกลับมาได้");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        ButtonType button = result.orElse(ButtonType.CANCEL);

                                        if (button == ButtonType.OK) {
                                            teamActivityDatasource.deleteById(activity.getId());
                                            showTable();
                                        } else {
                                            alert.close();
                                        }
                                    });

                                    ImageView editIcon = new ImageView(new Image(getClass().getResource("/cs211/project/views/assets/Icons/edit-red.png").toExternalForm()));
                                    editIcon.setFitHeight(20);
                                    editIcon.setFitWidth(20);
                                    editButton.setGraphic(editIcon);
                                    editButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                                    //open edit modal
                                    editButton.setOnAction(event -> {
                                        try {
                                            HashMap<String, Object> newData = new HashMap<>();
                                            newData.put("teamId", teamId.toString());
                                            newData.put("eventId", currentTeam.getEventId());
                                            newData.put("activityId", activity.getId());
                                            newData.put("userId", data.get("userId"));
                                            newData.put("previousPage", previousPage);
                                            FXRouter.goTo("createActivity", newData);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });

                                    hbox.getChildren().clear();
                                    if (isLeader || currentEvent.getUserId().equals(data.get("userId").toString())) {
                                        hbox.getChildren().addAll(chatButton, openmodalButton, changeStatus, editButton, deleteButton);
                                    } else {
                                        if(activity.getStatus()){
                                            hbox.getChildren().addAll(openmodalButton);
                                        }else{
                                            hbox.getChildren().addAll(chatButton, openmodalButton);
                                        }
                                    }
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
        scheduleTable.getColumns().add(statusColumn);
        scheduleTable.getColumns().add(tool);

        scheduleTable.getItems().clear();

        for (Activity activity : activityList.getActivities()) {
            scheduleTable.getItems().add(activity);
        }
    }

    private void openModalDialog(Activity rowData) {
        backDrop.setVisible(true);
        modal.setVisible(true);
        startAndEndDateTime.setText(DateTimeService.toString(rowData.getStartDate()) + " " + rowData.getStartTime() + " - " + DateTimeService.toString(rowData.getEndDate()) + " " + rowData.getEndTime());
        activityDetail.setText(rowData.getDetail());
    }

    @FXML
    void onHandleCloseModal(ActionEvent event) {
        backDrop.setVisible(false);
        modal.setVisible(false);
    }

    void initTeamMember() {
        executorService.submit(() -> {

            teamMemberDatasource = new TeamMemberListFileDatasource("data/team", "teamMember.csv");
            userDatasource = new UserListFileDatasource("data", "user.csv");
            teamMemberCollection = teamMemberDatasource.query("teamId = " + this.teamId.toString());

//        teamMemberCardList = new ArrayList<>(TeamMemberCardList());
            for (TeamMember member : teamMemberCollection.getTeamMembers()) {
                try {
                    User user = userDatasource.query("id = " + member.getUserId()).getAllUsers().get(0);
                    FXMLLoader teamMemberCardLoader = new FXMLLoader();
                    teamMemberCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/team-member-card.fxml"));
                    AnchorPane teamMemberCardComponent = teamMemberCardLoader.load();
                    TeamMemberCard teamMemberCard = teamMemberCardLoader.getController();
                    teamMemberCard.setParentController(this);
                    teamMemberCard.setUserId(member.getUserId());
                    teamMemberCard.setTeamId(member.getTeamId());
                    teamMemberCard.setImage(user.getAvatar());
                    teamMemberCard.setName(user.getFirstName() + " " + user.getLastName());
                    teamMemberCard.setRole((leaderId.toString().equals(member.getUserId())) ? "หัวหน้าทีม" : "สมาชิก");
                    teamMemberCard.setIsLeader(leaderId.toString().equals(member.getUserId()));
                    teamMemberCard.setChangeRoleButtonVisible(currentEvent.getUserId().equals(data.get("userId").toString()));
                    if (data.get("userId").toString().equals(member.getUserId())) {
                        teamMemberCard.setDropdownButtonVisible(false);
                    } else {
                        teamMemberCard.setDropdownButtonVisible(isLeader);
                    }


                    javafx.application.Platform.runLater(() -> {
                        memberVbox.getChildren().add(teamMemberCardComponent);
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    @FXML
    void onHandleAddActivity(ActionEvent event) {
        try {
            FXRouter.goTo("createActivity", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleBackToMyTeam(ActionEvent event) {
        try {

            FXRouter.goTo(this.previousPage, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void reloadData() {
        this.memberVbox.getChildren().clear();
        checkIsLeader();
        initTeamMember();
    }
}
