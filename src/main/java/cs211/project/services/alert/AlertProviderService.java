package cs211.project.services.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;

public class AlertProviderService {
    public void showInfoAlert(String message) {
        showAlert(AlertType.INFORMATION, message);
    }

    public void showErrorAlert(String message) {
        showAlert(AlertType.ERROR, message);
    }

    private void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}
