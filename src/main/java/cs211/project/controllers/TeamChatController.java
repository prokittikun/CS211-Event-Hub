package cs211.project.controllers;
import cs211.project.controllers.components.LeftChatLayoutController;
import cs211.project.controllers.components.ProfileImageController;
import cs211.project.controllers.components.RightChatLayoutController;
import cs211.project.controllers.components.TeamMemberCard;
import cs211.project.models.Chat;
import cs211.project.models.collections.ChatCollection;
import cs211.project.services.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeamChatController {
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

    @FXML
    private void initialize() {
        chatList = new ChatCollection();
        Chat newChat = new Chat("John", "Hello!", "https://picsum.photos/200");
        chatList.addNewChat(newChat);
        newChat = new Chat("Mark", "Hi", "https://picsum.photos/200");
        chatList.addNewChat(newChat);
        newChat = new Chat("John", "what is ur name hehe", "https://picsum.photos/200");
        chatList.addNewChat(newChat);
        newChat = new Chat("Jane", "Hi, my name is Jane!", "https://picsum.photos/200");
        chatList.addNewChat(newChat);
        newChat = new Chat("Mark", "my name is Mark", "https://picsum.photos/200");
        chatList.addNewChat(newChat);
        newChat = new Chat("John", "Nice to meet u", "https://picsum.photos/200");
        chatList.addNewChat(newChat);
        newChat = new Chat("Jane", "Nice to meet you too", "https://picsum.photos/200");
        chatList.addNewChat(newChat);
        newChat = new Chat("John", "what time to start of the event?", "https://picsum.photos/200");
        chatList.addNewChat(newChat);
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
            chatList.addNewChat(new Chat("John", inputMessage.getText(), "https://picsum.photos/200"));
            try {
                FXMLLoader chatMessageLoader = new FXMLLoader();
                AnchorPane chatLayoutComponent;
                chatMessageLoader.setLocation(getClass().getResource("/cs211/project/views/components/right-chat-layout-component.fxml"));
                chatLayoutComponent = chatMessageLoader.load();
                RightChatLayoutController rightChatLayoutController = chatMessageLoader.getController();
                rightChatLayoutController.setMyMessage(inputMessage.getText());
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

    private void initChat(){
        for (Chat chat : chatList.getChats()) {
            try {
                FXMLLoader chatMessageLoader = new FXMLLoader();
                AnchorPane chatLayoutComponent;
                if(chat.getSender().equals("John")) {
                    chatMessageLoader.setLocation(getClass().getResource("/cs211/project/views/components/right-chat-layout-component.fxml"));
                    chatLayoutComponent = chatMessageLoader.load();
                    RightChatLayoutController rightChatLayoutController = chatMessageLoader.getController();
                    rightChatLayoutController.setMyMessage(chat.getMessage());
                }else{
                    chatMessageLoader.setLocation(getClass().getResource("/cs211/project/views/components/left-chat-layout-component.fxml"));
                    chatLayoutComponent = chatMessageLoader.load();
                    LeftChatLayoutController leftChatLayoutController = chatMessageLoader.getController();
                    leftChatLayoutController.setSenderName(chat.getSender());
                    leftChatLayoutController.setSenderMessage(chat.getMessage());
                    leftChatLayoutController.setSenderImage(chat.getSenderImagePath());
                }

                showChat.getChildren().add(chatLayoutComponent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        chatScroll.setVvalue(1.0);
    }

    private void initAllMemberProfile(){
        profileImageControllerList = new ArrayList<>(memberProfiles());
        int column = 0;
        int row = 1;
        try {
            for (ProfileImageController profileImageController : profileImageControllerList) {
                FXMLLoader imageProfileLoader = new FXMLLoader();
                AnchorPane imageProfileComponent;
                imageProfileLoader.setLocation(getClass().getResource("/cs211/project/views/components/profile-image-component.fxml"));
                imageProfileComponent = imageProfileLoader.load();
                ProfileImageController imageProfileController = imageProfileLoader.getController();
                imageProfileController.setProfileImage(profileImageController.getImagePath());
                if (column == 5) {
                    column = 0;
                    ++row;
                }

                showMember.add(imageProfileComponent, column++, row);
                GridPane.setMargin(imageProfileComponent, new Insets(10));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<ProfileImageController> memberProfiles(){
        List<ProfileImageController> profileImageControllerList = new ArrayList<>();
        ProfileImageController profileImageController;
        for (int i = 0; i < 10; i++){
            profileImageController = new ProfileImageController("https://picsum.photos/200");
//            profileImageController.setProfileImage("https://picsum.photos/200");
            profileImageControllerList.add(profileImageController);
        }
        return profileImageControllerList;
    }

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
