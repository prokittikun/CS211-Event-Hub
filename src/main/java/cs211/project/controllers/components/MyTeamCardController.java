package cs211.project.controllers.components;

import cs211.project.controllers.MyTeamController;
import cs211.project.models.collections.TeamMemberCollection;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import cs211.project.services.TeamMemberListFileDatasource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MyTeamCardController {

    private UUID teamId;

    private UUID userId;

    private UUID eventId;

    @FXML
    private Label applicants;

    @FXML
    private Label teamApplicants;

    @FXML
    private ImageView eventImage;

    @FXML
    private Label eventName;

    private MyTeamController myTeamController;

    @FXML
    private Button order;

    @FXML
    private Label startDate;

    @FXML
    private Label teamName;

    @FXML
    private Label eventLocation;

    private Datasource<TeamMemberCollection> datasourceTeamMember;

    public MyTeamCardController() {
        this.teamId = null;
        this.applicants = new Label();
        this.eventImage = new ImageView();
        this.eventName = new Label();
        this.eventLocation = new Label();
        this.order = new Button();
        this.startDate = new Label();
        this.teamName = new Label();
        this.teamApplicants = new Label();

        datasourceTeamMember = new TeamMemberListFileDatasource("data/team", "teamMember.csv");
    }


    //setter

    public void setParentController(MyTeamController parentController) {
        this.myTeamController = parentController;
    }


    public void setTeamApplicants(String  teamApplicants) {
        this.teamApplicants.setText(teamApplicants);
    }

    public void setTeamId(String teamId){
        this.teamId = UUID.fromString(teamId);
    }

    public void setUserId(String userId){
        this.userId = UUID.fromString(userId);
    }

    public void setEventId(String eventId){
        this.eventId = UUID.fromString(eventId);
    }
    public void setApplicants(String applicants) {
        this.applicants.setText(applicants);
    }

    public void setEventImage(String imageName) {
        Image image = new Image("file:data" + File.separator + "image" + File.separator + "event" + File.separator + imageName);
        this.eventImage.setImage(image);
    }

    public void setEventName(String eventName) {
        this.eventName.setText(eventName);
    }

    public void setEventLocation(String location) {
        this.eventLocation.setText(location);
    }

    public void setOrder(String order) {
        this.order.setText(order);
    }

    public void setStartDate(String startDate) {
        this.startDate.setText(startDate);
    }

    public void setTeamName(String teamName) {
        this.teamName.setText(teamName);
    }

    //getter
    public String getTeamId(){
        return this.teamId.toString();
    }
    public String getUserId(){
        return this.userId.toString();
    }
    public String getApplicants() {
        return applicants.getText();
    }
    public String getEventId(){
        return this.eventId.toString();
    }
    public String getEventImage() {
        return eventImage.getImage().getUrl();
    }

    public String getEventName() {
        return eventName.getText();
    }

    public String getEventLocation() {
        return eventLocation.getText();
    }

    public String getOrder() {
        return order.getText();
    }

    public String getStartDate() {
        return startDate.getText();
    }

    public String getTeamName() {
        return teamName.getText();
    }

    public String getTeamApplicants() {
        return teamApplicants.getText();
    }


    @FXML
    void onHandleGoToTeamManagement(ActionEvent event) {
        try {
            HashMap<String, Object> data = new HashMap<>();
            data.put("teamId", teamId.toString());
            data.put("userId", userId.toString());
            data.put("eventId", eventId.toString());
            data.put("previousPage", "myTeam");
            FXRouter.goTo("teamManagement", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleLeaveTeam(ActionEvent event) {
        Map<String, String> conditions = new HashMap<>();
        conditions.put("teamId", this.teamId.toString());
        conditions.put("userId", this.userId.toString());
        datasourceTeamMember.deleteByConditions(conditions);

        this.myTeamController.reloadData();

    }
}

