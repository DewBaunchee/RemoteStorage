package by.vorivoda.matvey;

public enum ApplicationScene {
    LOGIN("/view/fxml/login.fxml"),
    REGISTRATION("/view/fxml/registration.fxml"),
    STORAGE_MANAGER("/view/fxml/storageManager.fxml");

    private final String path;

    ApplicationScene(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
