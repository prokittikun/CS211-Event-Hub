package cs211.project.controllers;

import cs211.project.models.*;
import cs211.project.models.collections.EventActivityCollection;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.JoinEventCollection;
import cs211.project.services.*;
import cs211.project.services.alert.ToastAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateEventActivityController {

    @FXML
    private TableColumn<Activity, String> dateColumn;

    @FXML
    private Label errorActivityDetail;

    @FXML
    private Label errorActivityName;

    @FXML
    private Label errorInputEndDate;

    @FXML
    private Label errorInputEndTime;

    @FXML
    private Label errorInputStartDate;

    @FXML
    private Label errorInputStartTime;

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
    private DatePicker inputActivityDateEnd;

    @FXML
    private DatePicker inputActivityDateStart;

    @FXML
    private TextArea inputActivityDetail;

    @FXML
    private TextField inputActivityName;

    @FXML
    private TextField inputActivityTimeEnd;

    @FXML
    private TextField inputActivityTimeStart;

    @FXML
    private TableColumn<Activity, String> nameColumn;

    @FXML
    private AnchorPane navbar;

    @FXML
    private TableView<Activity> scheduleTable;

    @FXML
    private TableColumn<Activity, String> timestampColumn;

    @FXML
    private Label titlePage;

    private boolean isEdit = false;

    private HashMap<String, Object> data;

    private EventActivityCollection eventActivityCollection;

    private Datasource<EventCollection> eventDatasource;
    private Datasource<JoinEventCollection> joinEventDatasource;

    private Datasource<EventActivityCollection> eventActivityDatasource;

    private UUID eventId;
    private UUID activityId;

    @FXML
    private void initialize() {
        this.errorActivityDetail.setVisible(false);
        this.errorActivityName.setVisible(false);
        this.errorInputEndDate.setVisible(false);
        this.errorInputEndTime.setVisible(false);
        this.errorInputStartDate.setVisible(false);
        this.errorInputStartTime.setVisible(false);
        data = FXRouter.getData();
        eventId = UUID.fromString(data.get("eventId").toString());
        if(data.get("activityId") != null){
            activityId = UUID.fromString(data.get("activityId").toString());
        }else{
            activityId = null;
        }
        eventDatasource = new EventListFileDatasource("data/event", "event.csv");
        joinEventDatasource = new JoinEventListFileDatasource("data/event", "joinEvent.csv");
        eventActivityDatasource = new EventActivityListFileDatasource("data/event", "activity.csv");

        initHeader();

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

        showTable();

        if(activityId != null){
            Activity activity = eventActivityDatasource.query("id = " + activityId.toString()).getActivities().get(0);
            inputActivityName.setText(activity.getName());
            inputActivityDetail.setText(activity.getDetail());

            inputActivityDateStart.setValue(LocalDate.parse(activity.getStartDate()));
            inputActivityDateEnd.setValue(LocalDate.parse(activity.getEndDate()));
            inputActivityTimeStart.setText(activity.getStartTime());
            inputActivityTimeEnd.setText(activity.getEndTime());
            isEdit = true;
            titlePage.setText("แก้ไขกิจกรรม");
        }else{
            titlePage.setText("สร้างกิจกรรม");
        }
    }

    private void showTable() {
        eventActivityCollection = eventActivityDatasource.query("eventId = " + eventId.toString());
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));


        // ล้าง column เดิมทั้งหมดที่มีอยู่ใน table แล้วเพิ่ม column ใหม่
        scheduleTable.getColumns().clear();
        scheduleTable.getColumns().add(nameColumn);
        scheduleTable.getColumns().add(dateColumn);
        scheduleTable.getColumns().add(timestampColumn);

        scheduleTable.getItems().clear();

        for (Activity activity: eventActivityCollection.getActivities()) {
            scheduleTable.getItems().add(activity);
        }
    }
    void initHeader() {
        Event event = eventDatasource.query("id = " + eventId.toString()).getEvents().get(0);
        eventName.setText(event.getName());
        eventLocation.setText(event.getLocation());
        eventDate.setText(DateTimeService.toString(event.getStartDate()) + " - " + DateTimeService.toString(event.getEndDate()));
        JoinEventCollection joinEventCollection = joinEventDatasource.query("eventId = " + event.getId());
        eventParticipant.setText(joinEventCollection.getJoinEvents().size() + "/" + event.getMaxParticipant());
        Image image = new Image("file:data" + File.separator + "image" + File.separator + "event" + File.separator + event.getImage());
        eventImage.setImage(image);
    }

    @FXML
    void onHandleBackToPreviousPage(ActionEvent event) {
        try {
            FXRouter.goTo("eventActivity", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleSaveButton(ActionEvent event) {
        boolean isAllInputValid = true;
        //check all input is not empty
        if (inputActivityName.getText().isEmpty()) {
            this.errorActivityName.setVisible(true);
            isAllInputValid = false;
        } else {
            this.errorActivityName.setVisible(false);
        }
        if (inputActivityDetail.getText().isEmpty()) {
            this.errorActivityDetail.setVisible(true);
            isAllInputValid = false;
        } else {
            this.errorActivityDetail.setVisible(false);
        }
        if (inputActivityDateStart.getValue() == null) {
            this.errorInputStartDate.setVisible(true);
            isAllInputValid = false;
        } else {
            this.errorInputStartDate.setVisible(false);
        }
        if (inputActivityDateEnd.getValue() == null) {
            this.errorInputEndDate.setVisible(true);
            isAllInputValid = false;
        } else {
            this.errorInputEndDate.setVisible(false);
        }
        if (inputActivityTimeStart.getText().isEmpty()) {
            this.errorInputStartTime.setVisible(true);
            isAllInputValid = false;
        } else {
            this.errorInputStartTime.setVisible(false);
        }
        if (inputActivityTimeEnd.getText().isEmpty()) {
            this.errorInputEndTime.setVisible(true);
            isAllInputValid = false;
        } else {
            this.errorInputEndTime.setVisible(false);
        }

        if(isAllInputValid){
            if(!isEdit){
                EventActivityCollection activityCollectionForWriteData = new EventActivityCollection();
                String startTime = inputActivityTimeStart.getText();
                String endTime = inputActivityTimeEnd.getText();
                if(startTime.contains(".")){
                    startTime = startTime.replace(".", ":");
                }
                if(endTime.contains(".")){
                    endTime = endTime.replace(".", ":");
                }
                EventActivity newActivity = new EventActivity(UUID.randomUUID().toString(), eventId.toString(), inputActivityName.getText(), inputActivityDetail.getText(), inputActivityDateStart.getValue().toString(), startTime, inputActivityDateEnd.getValue().toString(), endTime);
                activityCollectionForWriteData.addNewActivity(newActivity);
                eventActivityCollection.addNewActivity(newActivity);
                scheduleTable.getItems().add(newActivity);
                eventActivityDatasource.writeData(activityCollectionForWriteData);
                clearInput();
                ToastAlert.show("สร้างกิจกรรมสำเร็จ", ToastAlert.AlertType.SUCCESS);
            }else{
                Map<String, String> updateData = new HashMap<>();
                String startTime = inputActivityTimeStart.getText();
                String endTime = inputActivityTimeEnd.getText();
                if(startTime.contains(".")){
                    startTime = startTime.replace(".", ":");
                }
                if(endTime.contains(".")){
                    endTime = endTime.replace(".", ":");
                }
                updateData.put("name", inputActivityName.getText());
                updateData.put("detail", inputActivityDetail.getText());
                updateData.put("startDate", inputActivityDateStart.getValue().toString());
                updateData.put("startTime", startTime);
                updateData.put("endDate", inputActivityDateEnd.getValue().toString());
                updateData.put("endTime", endTime);

                eventActivityDatasource.updateColumnsById(activityId.toString(), updateData);
                showTable();
                ToastAlert.show("แก้ไขกิจกรรมสำเร็จ", ToastAlert.AlertType.SUCCESS);
            }
        }
    }

    void clearInput(){
        inputActivityName.setText("");
        inputActivityDetail.setText("");
        inputActivityDateStart.setValue(null);
        inputActivityDateEnd.setValue(null);
        inputActivityTimeStart.setText("");
        inputActivityTimeEnd.setText("");
    }

}
