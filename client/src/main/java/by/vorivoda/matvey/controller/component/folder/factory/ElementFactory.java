package by.vorivoda.matvey.controller.component.folder.factory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class ElementFactory implements IElementFactory {

    public static final String pathToFolderWithImages = "/view/image/folderImage";
    public static final Image defaultImage = new Image(pathToFolderWithImages + "/plain.png");
    public static final Map<String, Image> imageByExtension = new HashMap<>() {{
        put("", new Image(pathToFolderWithImages + "/plain.png"));
        put("/", new Image(pathToFolderWithImages + "/folder.png"));
    }};

    // TODO ADD NATIVE ICONS
    private static Image getFileIcon(File file) {
        Image image = imageByExtension.get(getFileExtension(file));
        return image == null ? defaultImage : image;
    }

    private static void deleteTempFile(File file) {
        boolean ignored = file.delete();
    }

    private static File createTempFile(String path) throws IOException {
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
            return Files.createTempDirectory(path).toFile();
        }

        return Files.createTempFile(path, null).toFile();
    }

    private static String getFileExtension(File file) {
        if (file.isDirectory()) return "/";

        int dotIndex = file.getName().lastIndexOf(".");
        return dotIndex == -1 ? "" : file.getName().substring(dotIndex + 1);
    }

    @Override
    public Node newElement(String path) {
        int lastSlash = path.lastIndexOf("/");
        lastSlash = lastSlash == path.length() - 1 ?
                path.lastIndexOf("/", lastSlash - 1)
                : lastSlash;
        path = path.substring(lastSlash + 1);

        HBox container = new HBox();
        container.setPadding(new Insets(4));
        container.setAlignment(Pos.CENTER_LEFT);
        container.getStyleClass().add("folder-element");

        try {
            File tempFile = createTempFile(path);
            ImageView fileIcon = new ImageView(getFileIcon(tempFile));
            deleteTempFile(tempFile);

            double rowHeight = 30;
            fileIcon.setFitHeight(rowHeight - 4);
            fileIcon.setFitWidth(rowHeight - 4);

            Label name = new Label(path.replace("/", ""));
            name.setPadding(new Insets(0, 0, 0, 10));

            container.getChildren().add(fileIcon);
            container.getChildren().add(name);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return container;
    }
}
