package by.vorivoda.matvey.controller.component.folder.structure.view;

import by.vorivoda.matvey.controller.component.folder.factory.ElementFactory;
import by.vorivoda.matvey.controller.component.folder.factory.IElementFactory;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class FolderStructureView {

    private final IElementFactory elementFactory;
    private final TreeView<HBox> elementsView;

    public FolderStructureView(TreeView<HBox> container) {
        this.elementsView = container;
        elementFactory = new ElementFactory();
    }

    public void setElements(ObservableList<String> elements) {
        ListProperty<String> currentElements = new SimpleListProperty<>(elements);
        currentElements.addListener((observableValue, oldValue, newValue) -> drawList(newValue));
        drawList(currentElements.get());
    }

    public void drawList(List<String> elements) {
        elementsView.setRoot(recFill(elements, "/"));
    }

    private String reconstructPath(TreeItem<AnchorPane> item) {
        StringBuilder sb = new StringBuilder();
        TreeItem<AnchorPane> current = item;
        while (current.getParent() != null) {
            sb.insert(0, "/" + getTreeViewText(current));
            current = current.getParent();
        }
        return sb.toString();
    }

    private boolean isDirectChild(String parent, String child) {
        child = child.substring(0, child.length() - 1);
        if (child.startsWith(parent)) {
            return child.lastIndexOf("/") == parent.length() - 1;
        }
        return false;
    }

    private String getTreeViewText(TreeItem<AnchorPane> item) {
        return ((Label) item.getValue().getChildren().get(1)).getText();
    }

    private TreeItem<HBox> recFill(List<String> paths, String current) {
        TreeItem<HBox> currentItem = new TreeItem<>((HBox) elementFactory.newElement(current));
        currentItem.setGraphic(null);

        if (current.endsWith("/")) {
            for (String path : paths) {
                if (isDirectChild(current, path)) {
                    currentItem.getChildren().add(recFill(paths, path));
                }
            }
        }

        return currentItem;
    }
}
