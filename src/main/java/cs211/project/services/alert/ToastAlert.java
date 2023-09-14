package cs211.project.services.alert;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ToastAlert {
    public static void show(String message) {
        Stage stage = new Stage();
        stage.initOwner(null); // Null owner means it's a standalone window
        stage.initStyle(StageStyle.UTILITY);
        stage.setAlwaysOnTop(true);

        Label label = new Label(message);
        label.getStyleClass().add("toast-label");

        StackPane stackPane = new StackPane(label);
        stackPane.setAlignment(Pos.TOP_RIGHT);
        stackPane.setPrefWidth(300); // Adjust width as needed
        stackPane.setPadding(new Insets(10));
        stackPane.getStyleClass().add("toast-background");

        Scene scene = new Scene(stackPane);
        stage.setScene(scene);

        // Animation for fade-in and fade-out
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(stage.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(stage.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(3.5), new KeyValue(stage.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(4), new KeyValue(stage.opacityProperty(), 0))
        );

        timeline.setOnFinished(event -> stage.close());
        timeline.play();

        scene.getStylesheets().add("cs211/project/views/style/global.css");

        stage.show();
    }
}
