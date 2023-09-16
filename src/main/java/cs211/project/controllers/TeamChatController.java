package cs211.project.controllers;
import cs211.project.controllers.components.LeftChatLayoutController;
import cs211.project.controllers.components.ProfileImageController;
import cs211.project.controllers.components.RightChatLayoutController;
import cs211.project.models.*;
import cs211.project.models.collections.*;
import cs211.project.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TeamChatController {

    private UUID teamId;
    private UUID userId;
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
    private AnchorPane navbar;

    @FXML
    private AnchorPane footer;

    @FXML
    private VBox showChat;

    @FXML
    private GridPane showMember;

    @FXML
    private Label teamName;

    @FXML
    private TextField inputMessage;

    private ChatCollection chatList;

    @FXML
    private ScrollPane chatScroll;

    private List<ProfileImageController> profileImageControllerList;

    private Datasource<ChatCollection>  chatDatasource;

    private Datasource<TeamCollection>  teamCollectionDatasource;

    private Datasource<TeamMemberCollection>  teamMemberCollectionDatasource;

    private Datasource<EventCollection>  eventDatasource;

    private Datasource<JoinEventCollection> joinEventCollectionDatasource;
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    @FXML
    private void initialize() {
        HashMap<String, Object> data = FXRouter.getData(); // get data from router
        teamId = UUID.fromString((String) data.get("teamId"));

        userId = UUID.fromString("b1e473a8-5175-11ee-be56-0242ac120002");
        chatDatasource = new ChatListFileDatasource("data/team", "chat.csv");
        eventDatasource = new EventListFileDatasource("data/event", "event.csv");
        teamCollectionDatasource = new TeamListFileDatasource("data/team", "team.csv");
        joinEventCollectionDatasource = new JoinEventListFileDatasource("data/event", "joinEvent.csv");
        teamMemberCollectionDatasource = new TeamMemberListFileDatasource("data/team", "teamMember.csv");
        chatList = new ChatCollection();

        this.initHeader();
        this.initAllMemberProfile();
        this.initChat();
        //Navbar
        FXMLLoader navbarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/navbar.fxml"));
        //Footer
        FXMLLoader footerComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/footer.fxml"));
        try {
            //Navbar
            AnchorPane navbarComponent = navbarComponentLoader.load();
            navbar.getChildren().add(navbarComponent);

            //Footer
            AnchorPane footerComponent = footerComponentLoader.load();
            footer.getChildren().add(footerComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleBackToPreviousPage(ActionEvent event) {
        try {
            FXRouter.goTo("teamManagement");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleSendMessage(ActionEvent event) {
        if(!inputMessage.getText().isEmpty()){
            String timestamp = DateTimeService.toString(DateTimeService.getCurrentDate()) + " " + DateTimeService.getCurrentTime();
            Chat newChat = new Chat(this.userId.toString(), inputMessage.getText(), this.teamId.toString(), timestamp);
            ChatCollection newChatList = new ChatCollection();
            chatList.addNewChat(newChat);
            newChatList.addNewChat(newChat);
            chatDatasource.writeData(newChatList);
            try {
                FXMLLoader chatMessageLoader = new FXMLLoader();
                AnchorPane chatLayoutComponent;
                chatMessageLoader.setLocation(getClass().getResource("/cs211/project/views/components/right-chat-layout-component.fxml"));
                chatLayoutComponent = chatMessageLoader.load();
                RightChatLayoutController rightChatLayoutController = chatMessageLoader.getController();
                rightChatLayoutController.setMyMessage(inputMessage.getText());
                rightChatLayoutController.setTimestamp(timestamp);
                showChat.getChildren().add(chatLayoutComponent);
                inputMessage.setText("");

                chatScroll.layout();
                chatScroll.setVvalue(1.0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
    }
    private void initHeader(){
        Team team = teamCollectionDatasource.query("id = " + teamId.toString()).getTeams().get(0);
        Event event = eventDatasource.query("id = " + team.getEventId()).getEvents().get(0);
        eventName.setText(event.getName());
        eventLocation.setText(event.getLocation());
        eventImage.setImage(new Image(event.getImage()));
        JoinEventCollection joinEventCollection = joinEventCollectionDatasource.query("eventId = " + event.getId());
        eventParticipant.setText(joinEventCollection.getJoinEvents().size() + "/" + event.getMaxParticipant());
        eventDate.setText(DateTimeService.toString(event.getStartDate()) + " - " + DateTimeService.toString(event.getEndDate()));
        teamName.setText(team.getName());
    }
    private void initChat(){
        chatList = chatDatasource.query("teamId = " + this.teamId.toString());
        for (Chat chat : chatList.getChats()) {
            try {
                FXMLLoader chatMessageLoader = new FXMLLoader();
                AnchorPane chatLayoutComponent;
                if(chat.getSenderUserId().equals(this.userId.toString())) {
                    chatMessageLoader.setLocation(getClass().getResource("/cs211/project/views/components/right-chat-layout-component.fxml"));
                    chatLayoutComponent = chatMessageLoader.load();
                    RightChatLayoutController rightChatLayoutController = chatMessageLoader.getController();
                    rightChatLayoutController.setMyMessage(chat.getMessage());
                    rightChatLayoutController.setTimestamp(chat.getTimestamp());
                }else{
                    chatMessageLoader.setLocation(getClass().getResource("/cs211/project/views/components/left-chat-layout-component.fxml"));
                    chatLayoutComponent = chatMessageLoader.load();
                    LeftChatLayoutController leftChatLayoutController = chatMessageLoader.getController();
                    Datasource<UserCollection> userDatasource = new UserListFileDatasource("data", "user.csv");
                    User user = userDatasource.query("id = " + chat.getSenderUserId()).getAllUsers().get(0);

                    leftChatLayoutController.setSenderName(user.getFirstName() + " " + user.getLastName());
                    leftChatLayoutController.setSenderMessage(chat.getMessage());
                    leftChatLayoutController.setTimestamp(chat.getTimestamp());
                    leftChatLayoutController.setSenderImage(user.getAvatar());
                }

                showChat.getChildren().add(chatLayoutComponent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        chatScroll.setVvalue(1.0);
    }

    private void initAllMemberProfile(){
        executorService.submit(() -> {

        TeamMemberCollection teamMemberCollection = teamMemberCollectionDatasource.query("teamId = " + this.teamId.toString());

            final int[] column = {0};
            final int[] row = {1};
        try {
            for (TeamMember member : teamMemberCollection.getTeamMembers()){
                FXMLLoader imageProfileLoader = new FXMLLoader();
                AnchorPane imageProfileComponent;
                imageProfileLoader.setLocation(getClass().getResource("/cs211/project/views/components/profile-image-component.fxml"));
                imageProfileComponent = imageProfileLoader.load();
                ProfileImageController imageProfileController = imageProfileLoader.getController();
                User user = new UserListFileDatasource("data", "user.csv").query("id = " + member.getUserId()).getAllUsers().get(0);
                imageProfileController.setProfileImage(user.getAvatar());
                javafx.application.Platform.runLater(() -> {

                if (column[0] == 5) {
                    column[0] = 0;
                    ++row[0];
                }
                    showMember.add(imageProfileComponent, column[0]++, row[0]);
                GridPane.setMargin(imageProfileComponent, new Insets(10));
                });

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        });
    }

//    private List<ProfileImageController> memberProfiles(){
//        List<ProfileImageController> profileImageControllerList = new ArrayList<>();
//        ProfileImageController profileImageController;
//        for (int i = 0; i < 10; i++){
//            profileImageController = new ProfileImageController("https://picsum.photos/200");
////            profileImageController.setProfileImage("https://picsum.photos/200");
//            profileImageControllerList.add(profileImageController);
//        }
//        return profileImageControllerList;
//    }

    private static void ensureVisible(ScrollPane pane, Node node) {
        double contentHeight = pane.getContent().getBoundsInLocal().getHeight();
        double viewportHeight = pane.getViewportBounds().getHeight();

        // Calculate the vertical value to scroll to the bottom
        double vValue = Math.max(0, (contentHeight - viewportHeight));
        // Set the vertical value to scroll to the middle
        pane.setVvalue(vValue);

        // Just for usability
        pane.requestFocus();
    }

}
