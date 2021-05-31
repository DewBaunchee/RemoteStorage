package by.vorivoda.matvey.controller;

import javafx.animation.FadeTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CommonController {

    private double xOffset;
    private double yOffset;

    public void setWindowGrabber(Node windowGrabber) {
        windowGrabber.setOnMousePressed(event -> {
            xOffset = windowGrabber.getScene().getWindow().getX() - event.getScreenX();
            yOffset = windowGrabber.getScene().getWindow().getY() - event.getScreenY();
        });
        windowGrabber.setOnMouseDragged(event -> {
            windowGrabber.getScene().getWindow().setX(event.getScreenX() + xOffset);
            windowGrabber.getScene().getWindow().setY(event.getScreenY() + yOffset);
        });
    }

    public void setCloseBtn(Node closeBtn) {
        closeBtn.setOnMouseClicked(mouseEvent -> ((Stage) closeBtn.getScene().getWindow()).close());
    }

   private Label errorLabel;

    public void setErrorLabel(Label label) {
        errorLabel = label;
        label.setVisible(false);
    }

    public void errorOccurred(String text) {
        errorLabel.setText(text);
        errorLabel.visibleProperty().unbind();
        errorLabel.setVisible(true);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500), errorLabel);
        fadeTransition.setDelay(Duration.millis(3000));
        fadeTransition.setOnFinished(actionEvent -> errorLabel.setVisible(false));
        fadeTransition.playFromStart();
    }

    private Map<String, TextField> wrongFields;

    public void errorOccurred(String text, List<TextField> wrongFieldList) {
        wrongFields = new HashMap<>();
        for(TextField current : wrongFieldList) {
            wrongFields.put(current.getText(), current);
            setWrongField(current);
        }

        errorLabel.setText(text);
        errorLabel.setVisible(true);
    }

    public void cleanError() {
        wrongFields = null;
        errorLabel.setVisible(false);
    }

    private void setWrongField(TextField wrongField) {
        wrongField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            boolean hasErrors = false;

            for(String key : wrongFields.keySet()) {
                TextField current = wrongFields.get(key);

                if(current.getText().equals(key)) {
                   current.getStyleClass().remove("wrong-field");
                } else {
                    current.getStyleClass().add("wrong-field");
                    hasErrors = true;
                }
            }

            errorLabel.setVisible(hasErrors);
        });
    }
}
