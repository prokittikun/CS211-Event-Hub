package cs211.project.controllers.components;

import cs211.project.controllers.JoinTeamController;
import cs211.project.models.Team;
import cs211.project.models.TeamMember;
import cs211.project.models.collections.TeamCollection;
import cs211.project.models.collections.TeamMemberCollection;
import cs211.project.services.Datasource;
import cs211.project.services.TeamListFileDatasource;
import cs211.project.services.TeamMemberListFileDatasource;
import cs211.project.services.alert.ToastAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.File;
import java.lang.String;
import java.util.HashMap;
import java.util.UUID;

public class JoinTeamCard {

    private UUID teamId;
    private UUID userId;
    @FXML
    private Button joinTeamButton;

    @FXML
    private Label teamParticipant;

    @FXML
    private Label teamName;

    @FXML
    private Label dateLabel;

    private HashMap<String, Object> data;

    private Datasource<TeamMemberCollection> teamMemberDatasource;
    private Datasource<TeamCollection> teamDatasource;


    private JoinTeamController joinTeamController;

    public JoinTeamCard() {
        this.teamParticipant = new Label();
        this.teamName = new Label();
        this.dateLabel = new Label();
    }
    @FXML
    private void initialize() {
        teamMemberDatasource = new TeamMemberListFileDatasource("data/team", "teamMember.csv");
        teamDatasource = new TeamListFileDatasource("data/team", "team.csv");
    }

    private boolean teamIsFull(){
        TeamMemberCollection teamMemberCollection = teamMemberDatasource.query("teamId = "+teamId.toString());
        Team team = teamDatasource.query("id = "+this.teamId.toString()).getTeams().get(0);
        int maxMember = Integer.parseInt(team.getMaxMember());
        return teamMemberCollection.getTeamMembers().size() == maxMember;
    }
    public void checkTeamIsFull(){
        if(this.teamIsFull()){
            joinTeamButton.setDisable(true);
            joinTeamButton.setText("ทีมเต็มแล้ว");
        }

    }
    @FXML
    void onHandleJoinTeam(ActionEvent event) {
        //check team is full

        if(this.teamIsFull()){
            ToastAlert.show("ไม่สามารถเข้าร่วมทีมได้ เนื่องจากทีมเต็มแล้ว", ToastAlert.AlertType.ERROR);
            return;
        }

        TeamMemberCollection newMember = new TeamMemberCollection();
        TeamMember teamMember = new TeamMember(UUID.randomUUID().toString(),userId.toString(),teamId.toString());
        newMember.addTeamMember(teamMember);
        teamMemberDatasource.writeData(newMember);
        ToastAlert.show("เข้าร่วมทีมสำเร็จ", ToastAlert.AlertType.SUCCESS);
        isJoinedTeam();
        this.joinTeamController.reload();
    }

    public void isJoinedTeam(){
        joinTeamButton.setDisable(true);
        joinTeamButton.setText("เข้าร่วมแล้ว");
    }
    public String getTeamParticipant() { return teamParticipant.getText(); }

    public String getTeamName() { return teamName.getText(); }

    public String getTeamId() {
        return teamId.toString();
    }

    public String getUserId() {
        return userId.toString();
    }

    public void setTeamParticipant(String teamParticipant) { this.teamParticipant.setText(teamParticipant); }

    public void setTeamName(String teamName) { this.teamName.setText(teamName); }

    public void setTeamId(String teamId) {
        this.teamId = UUID.fromString(teamId);
    }

    public void setUserId(String userId) {
        this.userId = UUID.fromString(userId);
    }

    public void setJoinTeamController(JoinTeamController joinTeamController){
        this.joinTeamController = joinTeamController;
    }

    public void setJoinTeamButton(boolean visible, String label){
        this.joinTeamButton.setText(label);
        this.joinTeamButton.setDisable(visible);
    }

    public void setDate(String startDateTime, String endDateTime){
        this.dateLabel.setText("รับสมัคร " +startDateTime+" น. - "+endDateTime + " น.");
    }
}
