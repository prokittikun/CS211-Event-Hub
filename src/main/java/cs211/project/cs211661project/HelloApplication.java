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
        FXRouter.when("register", resourcesPath + "register-view.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}