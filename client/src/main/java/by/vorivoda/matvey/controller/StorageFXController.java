package by.vorivoda.matvey.controller;

import by.vorivoda.matvey.controller.component.folder.structure.view.FolderStructureView;
import by.vorivoda.matvey.controller.component.folder.view.FolderView;
import by.vorivoda.matvey.model.IRemoteStorageClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class StorageFXController extends CommonController {

    @FXML
    private TreeView<HBox> storageStructure;

    @FXML
    private VBox folderElements;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private AnchorPane closeBtn;

    @FXML
    private HBox titleHBox;

    @FXML
    private ScrollPane folderElementsScrollBar;

    @Autowired
    private IRemoteStorageClient storage;
    private ObservableList<String> currentFolderElements;
    private ObservableList<String> storageStructureModel;

    @FXML
    void initialize() {
        folderElements.prefWidthProperty().bind(folderElementsScrollBar.widthProperty().multiply(0.9));
        folderElements.prefHeightProperty().bind(folderElementsScrollBar.heightProperty().multiply(0.9));

        currentFolderElements = FXCollections.observableArrayList();
        FolderView folderView = new FolderView(folderElements);
        folderView.setElements(currentFolderElements);

        storageStructureModel = FXCollections.observableArrayList();
        FolderStructureView folderStructureView = new FolderStructureView(storageStructure);
        folderStructureView.setElements(storageStructureModel);

        folderElements.setOnMouseClicked(mouseEvent -> {

        });

        storageStructure.setOnMouseClicked(mouseEvent -> {

        });

        setCloseBtn(closeBtn);
        setWindowGrabber(titleHBox);
    }
}
