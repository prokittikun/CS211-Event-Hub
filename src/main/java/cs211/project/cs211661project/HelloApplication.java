package cs211.project.cs211661project;

import javafx.application.Application;
import javafx.stage.Stage;
import cs211.project.services.FXRouter;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "CS211 661 Project",1200,800);
        configRoute();
        FXRouter.goTo("login");
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

        FXRouter.when("teamManagement", resourcesPath + "team-management-view.fxml");
        FXRouter.when("allEvent", resourcesPath + "all-event-view.fxml");

        FXRouter.when("contactUs", resourcesPath + "contact-us-view.fxml");

        FXRouter.when("createActivity", resourcesPath + "create-activity-view.fxml");
        FXRouter.when("profile", resourcesPath + "profile-view.fxml");
        FXRouter.when("chat", resourcesPath + "team-chat-view.fxml");
        FXRouter.when("dashboard", resourcesPath + "dashboard-view.fxml");
        FXRouter.when("search", resourcesPath + "search-view.fxml");
        FXRouter.when("eventHistory", resourcesPath + "event-history-view.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}