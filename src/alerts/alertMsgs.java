package alerts;

import javafx.scene.control.Alert;

public class alertMsgs {
    public static void showError(String header, String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(header);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
