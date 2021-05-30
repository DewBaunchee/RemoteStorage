package by.vorivoda.matvey;

public enum ApplicationScene {
    LOGIN("/view/fxml/login.fxml"),
    REGISTRATION("/view/fxml/registration.fxml"),
    STORAGE_MANAGER("/view/fxml/storageManager.fxml");

    private final String path;
    private final int width = 0;
    private final int height = 0;

    ApplicationScene(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
