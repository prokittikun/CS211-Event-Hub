package cs211.project.controllers.components;

import cs211.project.controllers.MyTeamController;
import cs211.project.models.collections.TeamMemberCollection;
import cs211.project.services.Datasource;
import cs211.project.services.TeamMemberListFIleDatasource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MyTeamCardController {

    private UUID teamId;

    private UUID userId;

    @FXML
    private Label applicants;

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

        datasourceTeamMember = new TeamMemberListFIleDatasource("data/team", "teamMember.csv");
    }


    //setter

    public void setParentController(MyTeamController parentController) {
        this.myTeamController = parentController;
    }

    public void setTeamId(String teamId){
        this.teamId = UUID.fromString(teamId);
    }
    public void setApplicants(String applicants) {
        this.applicants.setText(applicants);
    }

    public void setEventImage(String imagePath) {
        this.eventImage.setImage(new Image(imagePath));
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

    public String getApplicants() {
        return applicants.getText();
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


    @FXML
    void onHandleGoToTeamManagement(ActionEvent event) {

    }

    @FXML
    void onHandleLeaveTeam(ActionEvent event) {
        Map<String, String> conditions = new HashMap<>();
        conditions.put("teamId", this.teamId.toString());
        conditions.put("userId", "b1e473a8-5175-11ee-be56-0242ac120002");
        datasourceTeamMember.deleteByConditions(conditions);

        this.myTeamController.reloadData();

    }
}

