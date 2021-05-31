package by.vorivoda.matvey;

import by.vorivoda.matvey.spring.support.AbstractApplicationWithSpring;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ClientApplication extends AbstractApplicationWithSpring {

    public static void switchRoot(Stage stage, ApplicationScene applicationScene) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClientApplication.class.getResource(applicationScene.getPath()));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        switchRoot(primaryStage, ApplicationScene.STORAGE_MANAGER);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launchApp(ClientApplication.class, args);
    }
}
