package cs211.project.cs211661project;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import cs211.project.services.FXRouter;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    private static HostServices hostServicesInstance;
    @Override
    public void start(Stage stage) throws IOException {
        Font.loadFont(getClass().getResourceAsStream("/cs211/project/views/assets/fonts/Kanit-Light.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/cs211/project/views/assets/fonts/Kanit-Regular.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/cs211/project/views/assets/fonts/Kanit-Bold.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/cs211/project/views/assets/fonts/Prompt-Light.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/cs211/project/views/assets/fonts/Prompt-Regular.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/cs211/project/views/assets/fonts/Prompt-Bold.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/cs211/project/views/assets/fonts/Sarabun-Light.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/cs211/project/views/assets/fonts/Sarabun-Regular.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/cs211/project/views/assets/fonts/Sarabun-Bold.ttf"), 12);
        FXRouter.setGlobalStylesheet("/cs211/project/views/style/global.css");
        FXRouter.setCurrentFontSizeStylesheet("/cs211/project/views/style/font-large.css", "Large");
        FXRouter.setCurrentFontStyleClass("kanit");
        stage.setResizable(false);
        //set logo program
        stage.getIcons().add(new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResource("/cs211/project/views/assets/logo/EventLogo.png")).openStream()));
        FXRouter.bind(this, stage, "Event Hub - Phuea Khrai Party",1200,800);
        configRoute();

        FXRouter.goTo("login");
        hostServicesInstance = getHostServices();
    }

    private static void configRoute() {
        String resourcesPath = "cs211/project/views/";
        FXRouter.when("hello", resourcesPath + "hello-view.fxml");
        FXRouter.when("myTeam", resourcesPath + "my-team-view.fxml");
        FXRouter.when("navbar", resourcesPath + "navbar.fxml");
        FXRouter.when("index", resourcesPath + "index-view.fxml");
        FXRouter.when("login", resourcesPath + "login-view.fxml");
        FXRouter.when("myEvent", resourcesPath + "my-event.fxml");
        FXRouter.when("eventDetail", resourcesPath + "event-detail-view.fxml");
        FXRouter.when("eventActivity", resourcesPath + "event-activity-view.fxml");
        FXRouter.when("register", resourcesPath + "register-view.fxml");
        FXRouter.when("joinTeam", resourcesPath + "join-team-view.fxml");
        FXRouter.when("registerEvent", resourcesPath + "register-event-view.fxml");
        //Event
        FXRouter.when("createEvent", resourcesPath + "create-event-view.fxml");
        FXRouter.when("editEvent", resourcesPath + "create-event-view.fxml");
        FXRouter.when("myEvent", resourcesPath + "my-event-view.fxml");
        FXRouter.when("listTeam", resourcesPath + "list-team-view.fxml");
        FXRouter.when("createTeam", resourcesPath + "create-team-view.fxml");
        FXRouter.when("editTeam", resourcesPath + "create-team-view.fxml");
        FXRouter.when("createForm", resourcesPath + "create-form-view.fxml");
        FXRouter.when("eventParticipant", resourcesPath + "event-participant-view.fxml");

        FXRouter.when("teamManagement", resourcesPath + "team-management-view.fxml");
        FXRouter.when("allEvent", resourcesPath + "all-event-view.fxml");

        FXRouter.when("contactUs", resourcesPath + "contact-us-view.fxml");

        FXRouter.when("createActivity", resourcesPath + "create-activity-view.fxml");
        FXRouter.when("createEventActivity", resourcesPath + "create-event-activity-view.fxml");
        FXRouter.when("profile", resourcesPath + "profile-view.fxml");
        FXRouter.when("chat", resourcesPath + "team-chat-view.fxml");
        FXRouter.when("dashboard", resourcesPath + "dashboard-view.fxml");
        FXRouter.when("search", resourcesPath + "search-view.fxml");
        FXRouter.when("eventHistory", resourcesPath + "event-history-view.fxml");
    }
    public static HostServices getHostServicesStatic() {
        return hostServicesInstance;
    }

    public static void main(String[] args) {
        launch();
    }
}