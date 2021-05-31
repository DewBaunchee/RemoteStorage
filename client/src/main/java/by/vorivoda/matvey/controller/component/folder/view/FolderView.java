package by.vorivoda.matvey.controller.component.folder.view;

import by.vorivoda.matvey.controller.component.folder.factory.ElementFactory;
import by.vorivoda.matvey.controller.component.folder.factory.IElementFactory;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.util.List;

public class FolderView {

    private final IElementFactory elementFactory;
    private final VBox elementsView;

    public FolderView(VBox container) {
        elementsView = container;
        elementFactory = new ElementFactory();
    }

    public void setElements(ObservableList<String> elements) {
        ListProperty<String> currentElements = new SimpleListProperty<>(elements);
        currentElements.addListener((observableValue, oldValue, newValue) -> drawList(newValue));
        drawList(currentElements.get());
    }

    public void drawList(List<String> elements) {
        elementsView.getChildren().clear();
        for(String path : elements) {
            elementsView.getChildren().add(elementFactory.newElement(path));
        }
    }
}
