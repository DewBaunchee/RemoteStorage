package by.vorivoda.matvey.controller.util;

import javafx.scene.control.Alert;

public enum AlertMessage {
    ERROR_WHEN_SWITCHING_SCENES("Error", "Error during switching scenes", Alert.AlertType.ERROR);

    private final String title;
    private final String message;
    private final Alert.AlertType alertType;

    AlertMessage(String title, String message, Alert.AlertType alertType) {
        this.title = title;
        this.message = message;
        this.alertType = alertType;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Alert.AlertType getAlertType() {
        return alertType;
    }
}
