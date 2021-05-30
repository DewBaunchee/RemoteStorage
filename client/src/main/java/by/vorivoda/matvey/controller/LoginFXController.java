package by.vorivoda.matvey.controller;

import by.vorivoda.matvey.ApplicationScene;
import by.vorivoda.matvey.ClientApplication;
import by.vorivoda.matvey.controller.util.AlertMessage;
import by.vorivoda.matvey.controller.util.CommonControllerMethods;
import by.vorivoda.matvey.model.GlobalConstants;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFXController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField hiddenPasswordField;

    @FXML
    private TextField visiblePasswordField;

    @FXML
    private ImageView openEyeBtn;

    @FXML
    private ImageView closeEyeBtn;

    @FXML
    private Button signInBtn;

    @FXML
    private Button signUpBtn;

    @FXML
    private AnchorPane closeBtn;

    @FXML
    private HBox titleHBox;

    private BooleanProperty isPasswordHidden;
    private String usernameRegex = GlobalConstants.USERNAME_REGEX; // TODO

    private double xOffsetDrag;
    private double yOffsetDrag;

    @FXML
    void initialize() {
        isPasswordHidden = new SimpleBooleanProperty(true);
        visiblePasswordField.textProperty().bindBidirectional(hiddenPasswordField.textProperty());
        hiddenPasswordField.visibleProperty().bind(isPasswordHidden);
        visiblePasswordField.visibleProperty().bind(isPasswordHidden.not());

        closeEyeBtn.visibleProperty().bind(isPasswordHidden);
        openEyeBtn.visibleProperty().bind(isPasswordHidden.not());

        closeBtn.setOnMouseClicked(mouseEvent -> ((Stage) closeBtn.getScene().getWindow()).close());
        openEyeBtn.setOnMouseClicked(mouseEvent -> isPasswordHidden.set(true));
        closeEyeBtn.setOnMouseClicked(mouseEvent -> isPasswordHidden.set(false));

        signInBtn.setOnAction(actionEvent -> {

        });

        signUpBtn.setOnAction(actionEvent -> {
            try {
                ClientApplication.switchRoot((Stage) signUpBtn.getScene().getWindow(), ApplicationScene.REGISTRATION);
            } catch (IOException e) {
                e.printStackTrace();
                CommonControllerMethods.alert(AlertMessage.ERROR_WHEN_SWITCHING_SCENES);
            }
        });

        titleHBox.setOnMouseDragged(event -> {
            titleHBox.getScene().getWindow().setX(event.getScreenX() + xOffsetDrag);
            titleHBox.getScene().getWindow().setY(event.getScreenY() + yOffsetDrag);
        });
        titleHBox.setOnMousePressed(event -> {
            xOffsetDrag = titleHBox.getScene().getWindow().getX() - event.getScreenX();
            yOffsetDrag = titleHBox.getScene().getWindow().getY() - event.getScreenY();
        });
    }
}
