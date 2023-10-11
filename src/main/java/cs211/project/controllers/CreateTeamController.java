package cs211.project.controllers;

import cs211.project.controllers.components.ListTeamPreview;
import cs211.project.models.Team;
import cs211.project.models.collections.EventCollection;
import cs211.project.models.collections.JoinEventCollection;
import cs211.project.models.collections.TeamCollection;
import cs211.project.models.collections.UserCollection;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalDateStringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CreateTeamController {
    @FXML
    private Label dateLabel;
    @FXML
    private DatePicker datePickerEndDate;
    @FXML
    private DatePicker datePickerStartDate;
    @FXML
    private Label errorLabelEndDate;
    @FXML
    private Label errorLabelEndTime;
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
    private ImageView imageViewPreview;
    @FXML
    private Label locationLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private AnchorPane navbar;
    @FXML
    private Label participantLabel;
    @FXML
    private VBox teamPreviewComponent;
    @FXML
    private TextField textFieldEndTime;
    @FXML
    private TextField textFieldMaxParticipant;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldStartTime;
    private HashMap<String, Object> data;
    private Datasource<TeamCollection> teamDatasource;
    private Datasource<EventCollection> eventDatasource;
    private Datasource<JoinEventCollection> joinEventDatasource;
    private Datasource<UserCollection> userDatasource;
    private TeamCollection teamList;
    private EventCollection eventList;
    private JoinEventCollection joinEventList;
    private UserCollection userList;

    @FXML
    private void initialize() {
        data = FXRouter.getData();
        eventDatasource = new EventListFileDatasource("data/event", "event.csv");
        teamDatasource = new TeamListFileDatasource("data/team", "team.csv");
        joinEventDatasource = new JoinEventListFileDatasource("data/event", "joinEvent.csv");
        userDatasource = new UserListFileDatasource("data", "user.csv");

        joinEventList = joinEventDatasource.query("eventId = " + data.get("eventId"));
        eventList = eventDatasource.query("id = " + data.get("eventId"));
        teamList = teamDatasource.query("eventId = " + data.get("eventId"));

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

        //Preview
        dateLabel.setText(eventList.getEvents().get(0).getStartDate());
        locationLabel.setText(eventList.getEvents().get(0).getLocation());
        nameLabel.setText(eventList.getEvents().get(0).getName());
        imageViewPreview.setImage(new Image("file:data/image/event/" + eventList.getEvents().get(0).getImage()));
        participantLabel.setText(joinEventList.getJoinEvents().size() + "/" + eventList.getEvents().get(0).getMaxParticipant());

        //Component
        for (Team teamData : teamList.getTeams()) {
            try {
                FXMLLoader listTeamCardLoader = new FXMLLoader();
                listTeamCardLoader.setLocation(getClass().getResource("/cs211/project/views/components/list-team-preview.fxml"));
                Pane listTeamCardComponent = listTeamCardLoader.load();
                ListTeamPreview listTeamCard = listTeamCardLoader.getController();
                //Set Value in List
                listTeamCard.setTitleTeamLabel(teamData.getName());

                //Filter User by userId
                userList = userDatasource.query("id = " + teamData.getLeaderId());
                System.out.println(userList.getAllUsers().toString());
                listTeamCard.setHeadTeamImageCircle("file:data/image/avatar/" + userList.getAllUsers().get(0).getAvatar());

                //Insert to Component
                teamPreviewComponent.getChildren().add(listTeamCardComponent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //Init Error
        errorLabelEndDate.setText("");
        errorLabelEndTime.setText("");
        errorLabelMaxParticipant.setText("");
        errorLabelName.setText("");
        errorLabelStartDate.setText("");
        errorLabelStartTime.setText("");

        //For Edit
        if (data.get("teamId") != null) {
            Team team = teamDatasource.query("id = " + data.get("teamId")).getTeams().get(0);
            textFieldName.setText(team.getName());
            textFieldMaxParticipant.setText(team.getMaxMember());
            textFieldStartTime.setText(team.getStartTime());
            textFieldEndTime.setText(team.getEndTime());
            datePickerStartDate.setValue(
                    LocalDate.parse(team.getStartDate())
            );
            datePickerEndDate.setValue(
                    LocalDate.parse(team.getEndDate())
            );
        }
    }

    @FXML
    public void createTeam(){
        //Init Error
        errorLabelEndDate.setText("");
        errorLabelEndTime.setText("");
        errorLabelMaxParticipant.setText("");
        errorLabelName.setText("");
        errorLabelStartDate.setText("");
        errorLabelStartTime.setText("");

        //Validate
        boolean isValid = true;
        if(textFieldName.getText().isEmpty()){
            errorLabelName.setText("Name is required");
            isValid = false;
        }
        if(textFieldMaxParticipant.getText().isEmpty()){
            errorLabelMaxParticipant.setText("Max Participant is required");
            isValid = false;
        }
        //Check Max Participant is number
        try {
            Integer.parseInt(textFieldMaxParticipant.getText());
        } catch (NumberFormatException e) {
            errorLabelMaxParticipant.setText("Max Participant must be number");
            isValid = false;
        }
        if(textFieldStartTime.getText().isEmpty()){
            errorLabelStartTime.setText("Start Time is required");
            isValid = false;
        }
        if(textFieldEndTime.getText().isEmpty()){
            errorLabelEndTime.setText("End Time is required");
            isValid = false;
        }
        if(datePickerStartDate.getValue() == null){
            errorLabelStartDate.setText("Start Date is required");
            isValid = false;
        }
        if(datePickerEndDate.getValue() == null){
            errorLabelEndDate.setText("End Date is required");
            isValid = false;
        }

        if(isValid){
            //Check name is unique
            try {
                teamList.isNameExist(textFieldName.getText());
            } catch (Exception e) {
                errorLabelName.setText(e.getMessage());
                isValid = false;
            }

            //Get Value
            String name = textFieldName.getText();
            String maxParticipant = textFieldMaxParticipant.getText();
            String startDate = datePickerStartDate.getValue().toString();
            String endDate = datePickerEndDate.getValue().toString();
            String startTime = textFieldStartTime.getText();
            String endTime = textFieldEndTime.getText();

            if(data.get("teamId") != null){
                //Update
                HashMap<String, String> newData = new HashMap<>();
                newData.put("name", name);
                newData.put("maxMember", maxParticipant);
                newData.put("startDate", startDate);
                newData.put("endDate", endDate);
                newData.put("startTime", startTime);
                newData.put("endTime", endTime);

                teamDatasource.updateColumnsById(data.get("teamId").toString(), newData);
            }else {
                //Check name is unique skip my id
                try {
                    teamList.isNameExits(textFieldName.getText(), data.get("teamId").toString());
                } catch (Exception e) {
                    errorLabelName.setText(e.getMessage());
                    isValid = false;
                }

                Team team = new Team(
                        UUID.randomUUID().toString(),
                        name,
                        data.get("eventId").toString(),
                        data.get("userId").toString(),
                        maxParticipant,
                        startDate,
                        endDate,
                        startTime,
                        endTime
                );
                //Collection
                TeamCollection teamCollection = new TeamCollection();
                teamCollection.addTeam(team);
                teamDatasource.writeData(teamCollection);
            }
            try {
                FXRouter.goTo("listTeam", data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void backToListTeam(){
        try {
            FXRouter.goTo("listTeam", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onHandelCreateTeam(){

    }
}
