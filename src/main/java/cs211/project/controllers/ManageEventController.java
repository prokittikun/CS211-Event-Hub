package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.collections.EventCollection;
import cs211.project.services.Datasource;
import cs211.project.services.EventListFileDatasource;
import cs211.project.services.FXRouter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cs211.project.services.alert.ToastAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class ManageEventController {

    @FXML
    private Label errorDragImage;

    @FXML
    private Label errorLabelDetail;

    @FXML
    private Label errorLabelEndDate;

    @FXML
    private Label errorLabelEndTime;

    @FXML
    private Label errorLabelLocation;

    @FXML
    private Label errorLabelMaxParticipant;

    @FXML
    private Label errorLabelName;

    @FXML
    private Label errorLabelStartDate;

    @FXML
    private Label errorLabelStartTime;

    @FXML
    private Label errorLabelCloseEndTime;

    @FXML
    private Label errorLabelCloseStartDate;

    @FXML
    private Label errorLabelOpenStartDate;

    @FXML
    private Label errorLabelOpenTimeDate;

    @FXML
    private AnchorPane footer;

    @FXML
    private AnchorPane navbar;

    @FXML
    private DatePicker pickerEndDate;

    @FXML
    private DatePicker pickerStartDate;

    @FXML
    private TextArea textAreaDetail;

    @FXML
    private TextField textFieldEndTime;

    @FXML
    private TextField textFieldLocation;

    @FXML
    private TextField textFieldMaxParticipant;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldStartTime;

    @FXML
    private DatePicker pickerOpenDate;
    @FXML
    private TextField textFieldOpenTime;

    @FXML
    private DatePicker pickerCloseDate;
    @FXML
    private TextField textFieldCloseTime;

    @FXML
    private ImageView imageView;

    private HashMap<String, Object> data;
    private Datasource<EventCollection> eventDatasource;



    private File imageFile;

    @FXML
    private void initialize() {
        //Get Data
        data = FXRouter.getData();
        //Hide Error Label
        errorLabelName.setText("");
        errorLabelDetail.setText("");
        errorLabelLocation.setText("");
        errorLabelStartDate.setText("");
        errorLabelStartTime.setText("");
        errorLabelEndDate.setText("");
        errorLabelEndTime.setText("");
        errorLabelMaxParticipant.setText("");
        errorDragImage.setText("");
        errorLabelOpenStartDate.setText("");
        errorLabelOpenTimeDate.setText("");
        errorLabelCloseEndTime.setText("");
        errorLabelCloseStartDate.setText("");

        //Datasource
        eventDatasource = new EventListFileDatasource("data/event", "event.csv");

        //Navbar
        FXMLLoader navbarComponentLoader = new FXMLLoader(
                getClass().getResource("/cs211/project/views/navbar.fxml")
        );
        //Footer
        FXMLLoader footerComponentLoader = new FXMLLoader(
                getClass().getResource("/cs211/project/views/footer.fxml")
        );
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

        //Get Data When Edit
        if (data.get("eventId") != null) {
            String eventId = (String) data.get("eventId");
            EventCollection eventCollection = eventDatasource.query("id = " + eventId);
            Event event = eventCollection.getEvents().get(0);
            textFieldName.setText(event.getName());
            textAreaDetail.setText(event.getDetail());
            textFieldLocation.setText(event.getLocation());
            pickerOpenDate.setValue(
                    LocalDate.parse(event.getOpenDate())
            );
            textFieldOpenTime.setText(event.getOpenTime());
            pickerCloseDate.setValue(
                    LocalDate.parse(event.getCloseDate())
            );
            textFieldCloseTime.setText(event.getCloseTime());
            pickerStartDate.setValue(
                    LocalDate.parse(event.getStartDate())
            );
            textFieldStartTime.setText(event.getStartTime());
            pickerEndDate.setValue(
                    LocalDate.parse(event.getEndDate())
            );
            textFieldEndTime.setText(event.getEndTime());
            textFieldMaxParticipant.setText(
                    String.valueOf(event.getMaxParticipant())
            );
            try {
                Image image = new Image(new FileInputStream("data/image/event/" + event.getImage()));
                imageView.setImage(image);
                imageFile = new File("data/image/event/" + event.getImage());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void backToEvent() {
        //Remove EventId
        data.remove("eventId");
        try {
            if(data.get("previousPage") == null){
                FXRouter.goTo("myEvent", data);
            }else {
                FXRouter.goTo(data.get("previousPage").toString(), data);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    void onHandelDragDropped(DragEvent event) {
        List<File> files = event.getDragboard().getFiles();
        try {
            Image image = new Image(new FileInputStream(files.get(0)));
            imageFile = files.get(0);
            imageView.setImage(image);
        } catch (FileNotFoundException e) {
            errorDragImage.setText("Image not found.");
        }
    }

    @FXML
    public void onHandleUploadButton(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser
                .getExtensionFilters()
                .add(
                        new FileChooser.ExtensionFilter(
                                "images PNG JPG",
                                "*.png",
                                "*.jpg",
                                "*.jpeg"
                        )
                );
        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) event.getSource();
        imageFile = chooser.showOpenDialog(source.getScene().getWindow());
        // SET IMAGE TO IMAGEVIEW
        if (imageFile != null) {
            try {
                Image image = new Image(new FileInputStream(imageFile));
                imageView.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onHandleCreateEvent() {
        //Validate
        boolean isValid = true;
        if (textFieldName.getText().isEmpty()) {
            errorLabelName.setText("กรุณากรอกชื่ออีเว้นต์");
            isValid = false;
        } else {
            errorLabelName.setText("");
        }

        if (textAreaDetail.getText().isEmpty()) {
            errorLabelDetail.setText("กรุณากรอกรายละเอียด");
            isValid = false;
        } else {
            errorLabelDetail.setText("");
        }

        if (textFieldLocation.getText().isEmpty()) {
            errorLabelLocation.setText("กรุณากรอกสถานที่ที่จัดกิจกรรม");
            isValid = false;
        } else {
            errorLabelLocation.setText("");
        }
        if(pickerOpenDate.getValue() == null){
            errorLabelOpenStartDate.setText("กรุณากรอกวันที่เปิดรับสมัคร");
            isValid = false;
        }
        if(textFieldOpenTime.getText().isEmpty()){
            errorLabelOpenTimeDate.setText("กรุณากรอกเวลาเปิดรับสมัคร");
            isValid = false;
        }
        if(textFieldOpenTime.getText().length() != 5){
            errorLabelOpenTimeDate.setText("เวลาเปิดรับสมัครต้องอยู่ในรูปแบบ xx:xx");
            isValid = false;
        }

        if(pickerCloseDate.getValue() == null){
            errorLabelCloseStartDate.setText("กรุณากรอกวันที่ปิดรับสมัคร");
            isValid = false;
        }
        if(textFieldCloseTime.getText().length() != 5){
            errorLabelCloseEndTime.setText("เวลาปิดรับสมัครต้องอยู่ในรูปแบบ xx:xx");
            isValid = false;
        }
        if(textFieldCloseTime.getText().isEmpty()){
            errorLabelCloseEndTime.setText("กรุณากรอกเวลาปิดรับสมัคร");
            isValid = false;
        }
        if (pickerStartDate.getValue() == null) {
            errorLabelStartDate.setText("กรุณากรอกวันที่เริ่มกิจกรรม");
            isValid = false;
        } else {
            errorLabelStartDate.setText("");
        }

        if (textFieldStartTime.getText().isEmpty()) {
            errorLabelStartTime.setText("กรุณากรอกเวลาเริ่มกิจกรรม");
            isValid = false;
        } else {
            errorLabelStartTime.setText("");
        }

        if (textFieldStartTime.getText().length() != 5) {
            errorLabelStartTime.setText("เวลาเริ่มกิจกรรมต้องอยู่ในรูปแบบ xx:xx");
            isValid = false;
        } else {
            errorLabelStartTime.setText("");
        }

        if (pickerEndDate.getValue() == null) {
            errorLabelEndDate.setText("กรุณากรอกวันที่สิ้นสุดกิจกรรม");
            isValid = false;
        } else {
            errorLabelEndDate.setText("");
        }

        if (textFieldEndTime.getText().isEmpty()) {
            errorLabelEndTime.setText("กรุณากรอกเวลาสิ้นสุดกิจกรรม");
            isValid = false;
        } else {
            errorLabelEndTime.setText("");
        }

        if (textFieldEndTime.getText().length() != 5) {
            errorLabelEndTime.setText("เวลาสิ้นสุดกิจกรรมต้องอยู่ในรูปแบบ xx:xx");
            isValid = false;
        } else {
            errorLabelEndTime.setText("");
        }

        if (textFieldMaxParticipant.getText().isEmpty()) {
            errorLabelMaxParticipant.setText("กรุณากรอกจำนวนผู้เข้าร่วมกิจกรรม");
            isValid = false;
        } else {
            errorLabelMaxParticipant.setText("");
        }

        try {
            int maxParticipant = Integer.parseInt(textFieldMaxParticipant.getText());
        } catch (NumberFormatException e) {
            errorLabelMaxParticipant.setText("จำนวนผู้เข้าร่วมกิจกรรมต้องเป็นตัวเลขเท่านั้น");
            isValid = false;
        }

        if (imageView.getImage() == null) {
            errorDragImage.setText("กรุณาอัพโหลดรูปภาพ");
            isValid = false;
        } else {
            errorDragImage.setText("");
        }

        if (isValid) {
            //Get Data
            String name = textFieldName.getText();
            String userId = (String) data.get("userId");
            String detail = textAreaDetail.getText();
            String location = textFieldLocation.getText();
            String openDate = pickerOpenDate.getValue().toString();
            String openTime = textFieldOpenTime.getText();
            String closeDate = pickerCloseDate.getValue().toString();
            String closeTime = textFieldCloseTime.getText();
            String startDate = pickerStartDate.getValue().toString();
            String startTime = textFieldStartTime.getText();
            String endDate = pickerEndDate.getValue().toString();
            String endTime = textFieldEndTime.getText();
            int maxParticipant = Integer.parseInt(textFieldMaxParticipant.getText());

            //Copy Image
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("data/image/event");
                if (!destDir.exists()) destDir.mkdirs();
                // RENAME FILE
                String[] fileSplit = imageFile.getName().split("\\.");
                String filename =
                        LocalDate.now() +
                                "_" +
                                System.currentTimeMillis() +
                                "." +
                                fileSplit[fileSplit.length - 1];
                Path target = FileSystems
                        .getDefault()
                        .getPath(
                                destDir.getAbsolutePath() +
                                        System.getProperty("file.separator") +
                                        filename
                        );
                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                System.out.println(imageFile.toPath());
                Files.copy(
                        imageFile.toPath(),
                        target,
                        StandardCopyOption.REPLACE_EXISTING
                );

                //Create Event
                if (startTime.contains(".")) {
                    startTime = startTime.replace(".", ":");
                }
                if (endTime.contains(".")) {
                    endTime = endTime.replace(".", ":");
                }
                if (openTime.contains(".")) {
                    openTime = openTime.replace(".", ":");
                }
                if (closeTime.contains(".")) {
                    closeTime = closeTime.replace(".", ":");
                }
                if (data.get("eventId") == null) {
                    Event event = new Event(
                            userId,
                            name,
                            detail,
                            location,
                            openDate,
                            openTime,
                            closeDate,
                            closeTime,
                            startDate,
                            startTime,
                            endDate,
                            endTime,
                            maxParticipant,
                            filename
                    );
                    //Collection
                    EventCollection eventCollection = new EventCollection();
                    eventCollection.addEvent(event);
                    eventDatasource.writeData(eventCollection);
                    ToastAlert.show("สร้างอีเว้นต์สำเร็จ", ToastAlert.AlertType.SUCCESS);

                } else { //Edit

                    HashMap<String, String> newData = new HashMap<>();
                    if(startTime.contains(".")){
                        startTime = startTime.replace(".",":");
                    }
                    if(endTime.contains(".")){
                        endTime = endTime.replace(".",":");
                    }
                    if(openTime.contains(".")){
                        openTime = openTime.replace(".",":");
                    }
                    if(closeTime.contains(".")){
                        closeTime = closeTime.replace(".",":");
                    }
                    newData.put("name", name);
                    newData.put("image", filename);
                    newData.put("detail", detail);
                    newData.put("location", location);
                    newData.put("openDate", openDate);
                    newData.put("openTime", openTime);
                    newData.put("closeDate", closeDate);
                    newData.put("closeTime", closeTime);
                    newData.put("startDate", startDate);
                    newData.put("startTime", startTime);
                    newData.put("endDate", endDate);
                    newData.put("endTime", endTime);
                    newData.put("maxParticipant", String.valueOf(maxParticipant));
                    eventDatasource.updateColumnsById(data.get("eventId").toString(), newData);
                    ToastAlert.show("แก้ไขอีเว้นต์สำเร็จ", ToastAlert.AlertType.SUCCESS);
                }

                //Go to My Event
                try {
                    data.remove("eventId");
                    FXRouter.goTo("myEvent", data);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
