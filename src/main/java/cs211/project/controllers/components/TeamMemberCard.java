package cs211.project.controllers.components;

import cs211.project.controllers.TeamManagementController;
import cs211.project.models.collections.TeamCollection;
import cs211.project.models.collections.TeamMemberCollection;
import cs211.project.services.Datasource;
import cs211.project.services.TeamListFileDatasource;
import cs211.project.services.TeamMemberListFileDatasource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeamMemberCard {
    @FXML
    private Circle image;

    private UUID userId;

    private UUID teamId;

    @FXML
    private ImageView dropdown;

    @FXML
    private Label name;

    @FXML
    private Label role;

    @FXML
    private Pane menuDropdown;

    @FXML
    private VBox dropdownVBox;

    @FXML
    private Pane border;

    @FXML
    private Button changeRoleButton;

    @FXML
    private Button deleteTeamMemberButton;

    private TeamManagementController teamManagementController;

    private Boolean showMenuDropdown = false;
    private Datasource<TeamCollection> teamDatasource;
    private Datasource<TeamMemberCollection> teamMemberDatasource;

    private Boolean isLeader;

    public TeamMemberCard(){
        this.image = new Circle();
        this.name = new Label();
        this.role = new Label();
    }

    @FXML
    private void initialize() {
        teamDatasource = new TeamListFileDatasource("data/team", "team.csv");
        teamMemberDatasource = new TeamMemberListFileDatasource("data/team", "teamMember.csv");
        menuDropdown.setVisible(false);
        setChangeRoleButtonVisible(false);
    }
    @FXML
    void onHandleMenuDropdownClick(MouseEvent event) {
        showMenuDropdown = !showMenuDropdown;
        menuDropdown.setVisible(showMenuDropdown);
    }

    @FXML
    void onHandleChangeRole(ActionEvent event) {
        teamDatasource.updateColumnById(teamId.toString(), "leaderId", userId.toString());
        this.teamManagementController.reloadData();
    }

    @FXML
    void onHandleDeleteTeamMember(ActionEvent event) {
        Map<String, String> conditions = new HashMap<>();
        conditions.put("userId", userId.toString());
        conditions.put("teamId", teamId.toString());
        teamMemberDatasource.deleteByConditions(conditions);
        this.teamManagementController.reloadData();
    }
    public String getImage() {
        return image.getFill().toString();
    }

    public String getName() {
        return name.getText();
    }

    public String getRole() {
        return role.getText();
    }

    public String  getUserId() {
        return userId.toString();
    }
    public String getTeamId() {
        return teamId.toString();
    }
    public void setUserId(String userId) {
        this.userId = UUID.fromString(userId);
    }
    public void setTeamId(String teamId) {
        this.teamId = UUID.fromString(teamId);
    }

    public void setImage(String imageName) {
        Image image = new Image("file:data" + File.separator + "image" + File.separator + "avatar" + File.separator + imageName);
        this.image.setFill(new ImagePattern(image));
    }

    public void setIsLeader(Boolean isLeader){
        this.isLeader = isLeader;
    }
    public void setDropdownButtonVisible(Boolean visible){
        dropdown.setVisible(visible);
    }
    public void setName(String name) {
        this.name.setText(name);
    }

    public void setRole(String role) {
        this.role.setText(role);
    }

    public void setParentController(TeamManagementController teamManagementController){
        this.teamManagementController = teamManagementController;
    }

    public void setChangeRoleButtonVisible(Boolean visible){

        if (visible && !isLeader) {
            // Move the changeRoleButton to the top of the VBox
            if (dropdownVBox.getChildren().contains(changeRoleButton)) {
                dropdownVBox.getChildren().remove(changeRoleButton);
            }
            dropdownVBox.getChildren().add(0, changeRoleButton);
        } else {
            // Remove the button from the VBox
            dropdownVBox.getChildren().remove(changeRoleButton);
            dropdownVBox.getChildren().remove(border);
        }
    }
}
