package by.vorivoda.matvey.controller;

import by.vorivoda.matvey.ApplicationScene;
import by.vorivoda.matvey.ClientApplication;
import by.vorivoda.matvey.controller.util.AlertMessage;
import by.vorivoda.matvey.model.IRemoteStorageClient;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class LoginFXController extends CommonController {

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

    @FXML
    private Label errorLabel;

    private BooleanProperty isPasswordHidden;
    private StringProperty usernameProperty;
    private StringProperty passwordProperty;

    @Autowired
    private IRemoteStorageClient storage;

    @FXML
    void initialize() {
        isPasswordHidden = new SimpleBooleanProperty(true);

        usernameProperty = new SimpleStringProperty();
        passwordProperty = new SimpleStringProperty();

        usernameProperty.bindBidirectional(usernameField.textProperty());
        passwordProperty.bindBidirectional(hiddenPasswordField.textProperty());

        visiblePasswordField.textProperty().bindBidirectional(hiddenPasswordField.textProperty());
        hiddenPasswordField.visibleProperty().bind(isPasswordHidden);
        visiblePasswordField.visibleProperty().bind(isPasswordHidden.not());

        closeEyeBtn.visibleProperty().bind(isPasswordHidden);
        openEyeBtn.visibleProperty().bind(isPasswordHidden.not());

        openEyeBtn.setOnMouseClicked(mouseEvent -> isPasswordHidden.set(true));
        closeEyeBtn.setOnMouseClicked(mouseEvent -> isPasswordHidden.set(false));

        signInBtn.setOnAction(actionEvent -> {
            boolean isUsernameFieldEmpty = usernameProperty.get().length() == 0;
            boolean isPasswordFieldEmpty = passwordProperty.get().length() == 0;
            if (isUsernameFieldEmpty || isPasswordFieldEmpty) {
                errorOccurred("Fill the fields!");
                return;
            }
            cleanError();

            boolean isSigned = storage.signIn(usernameProperty.get(), passwordProperty.get());
            if(isSigned) {
                try {
                    ClientApplication.switchRoot((Stage) signInBtn.getScene().getWindow(), ApplicationScene.STORAGE_MANAGER);
                } catch (IOException e) {
                    e.printStackTrace();
                    AlertMessage.ERROR_WHEN_SWITCHING_SCENES.showAndWait();
                }
            } else {
                errorOccurred("Wrong username or password.");
            }
        });

        signUpBtn.setOnAction(actionEvent -> {
            try {
                ClientApplication.switchRoot((Stage) signUpBtn.getScene().getWindow(), ApplicationScene.REGISTRATION);
            } catch (IOException e) {
                e.printStackTrace();
                AlertMessage.ERROR_WHEN_SWITCHING_SCENES.showAndWait();
            }
        });

        setErrorLabel(errorLabel);

        setCloseBtn(closeBtn);
        setWindowGrabber(titleHBox);
    }
}
