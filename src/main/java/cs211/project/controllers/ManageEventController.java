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
            FXRouter.goTo("myEvent", data);
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
            errorLabelName.setText("Name is required");
            isValid = false;
        } else {
            errorLabelName.setText("");
        }

        if (textAreaDetail.getText().isEmpty()) {
            errorLabelDetail.setText("Detail is required");
            isValid = false;
        } else {
            errorLabelDetail.setText("");
        }

        if (textFieldLocation.getText().isEmpty()) {
            errorLabelLocation.setText("Location is required");
            isValid = false;
        } else {
            errorLabelLocation.setText("");
        }

        if (pickerStartDate.getValue() == null) {
            errorLabelStartDate.setText("Start Date is required");
            isValid = false;
        } else {
            errorLabelStartDate.setText("");
        }

        if (textFieldStartTime.getText().isEmpty()) {
            errorLabelStartTime.setText("Start Time is required");
            isValid = false;
        } else {
            errorLabelStartTime.setText("");
        }

        if (textFieldStartTime.getText().length() != 5) {
            errorLabelStartTime.setText("Start Time must be in format xx:xx");
            isValid = false;
        } else {
            errorLabelStartTime.setText("");
        }

        if (pickerEndDate.getValue() == null) {
            errorLabelEndDate.setText("End Date is required");
            isValid = false;
        } else {
            errorLabelEndDate.setText("");
        }

        if (textFieldEndTime.getText().isEmpty()) {
            errorLabelEndTime.setText("End Time is required");
            isValid = false;
        } else {
            errorLabelEndTime.setText("");
        }

        if (textFieldEndTime.getText().length() != 5) {
            errorLabelEndTime.setText("End Time must be in format xx:xx");
            isValid = false;
        } else {
            errorLabelEndTime.setText("");
        }

        if (textFieldMaxParticipant.getText().isEmpty()) {
            errorLabelMaxParticipant.setText("Max Participant is required");
            isValid = false;
        } else {
            errorLabelMaxParticipant.setText("");
        }

        try {
            int maxParticipant = Integer.parseInt(textFieldMaxParticipant.getText());
        } catch (NumberFormatException e) {
            errorLabelMaxParticipant.setText("Max Participant must be a number");
            isValid = false;
        }

        if (imageView.getImage() == null) {
            errorDragImage.setText("Image is required");
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
                if (data.get("eventId") == null) {
                    Event event = new Event(
                            userId,
                            name,
                            detail,
                            location,
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
                } else { //Edit

                    HashMap<String, String> newData = new HashMap<>();
                    newData.put("name", name);
                    newData.put("image", filename);
                    newData.put("detail", detail);
                    newData.put("location", location);
                    newData.put("startDate", startDate);
                    newData.put("startTime", startTime);
                    newData.put("endDate", endDate);
                    newData.put("endTime", endTime);
                    newData.put("maxParticipant", String.valueOf(maxParticipant));
                    eventDatasource.updateColumnsById(data.get("eventId").toString(), newData);
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
