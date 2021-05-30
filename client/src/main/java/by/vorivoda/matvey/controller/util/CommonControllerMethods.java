package by.vorivoda.matvey.controller.util;

import javafx.scene.control.Alert;

public class CommonControllerMethods {

    public static void alert(AlertMessage alertMessage) {
        Alert alert = new Alert(alertMessage.getAlertType());
        alert.setTitle(alertMessage.getTitle());
        alert.setContentText(alertMessage.getMessage());
        alert.showAndWait();
    }
}
