package Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertMessage {
    private Alert alert;
    private boolean confirm;
    public void errorMessage(String message) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void informationMessage(String message) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void warningMessage(String message) {
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void confirmationMessage(String message) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == null) {
            confirm = false;
        } else if (option.get() == ButtonType.OK) {
            confirm = true;
        } else if (option.get() == ButtonType.CANCEL) {
            confirm = false;
        }
    }
    public boolean checkconfirm(){
        return confirm;
    }
}
