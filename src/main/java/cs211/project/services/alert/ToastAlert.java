package cs211.project.services.alert;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.control.MenuBar;

public class ToastAlert {
    public enum AlertType {
        SUCCESS, ERROR
    }

    public static void show(String message, AlertType alertType) {
        Stage stage = new Stage();
        stage.initOwner(null);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);

        Label label = new Label(message);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        StackPane stackPane = new StackPane(label);
        stackPane.setAlignment(Pos.TOP_RIGHT);
        stackPane.setMinWidth(300);
        stackPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
        stackPane.setPadding(new Insets(10));

        String backgroundColor = alertType == AlertType.SUCCESS ? "rgba(0, 128, 0, 0.7)" : "rgba(255, 0, 0, 0.7)";
        stackPane.setStyle("-fx-background-color: " + backgroundColor + ";" +
                "-fx-border-color: white;" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: 5px;");

        Scene scene = new Scene(stackPane);
        stage.setScene(scene);


        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(stage.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(stage.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(3.5), new KeyValue(stage.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(4), new KeyValue(stage.opacityProperty(), 0))
        );

        timeline.setOnFinished(event -> stage.close());
        timeline.play();

        stage.show();
    }
}
