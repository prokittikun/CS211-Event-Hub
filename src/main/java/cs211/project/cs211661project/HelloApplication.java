package cs211.project.cs211661project;

import javafx.application.Application;
import javafx.stage.Stage;
import cs211.project.services.FXRouter;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        configRoute();

        FXRouter.bind(this, stage, "CS211 661 Project");
        FXRouter.goTo("hello");
    }

    private static void configRoute() {
        String resourcesPath = "cs211/project/views/";
        FXRouter.when("hello", resourcesPath + "hello-view.fxml");
    }


    public static void main(String[] args) {
        launch();
    }
}