package by.vorivoda.matvey.model;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@Component
public interface IRemoteStorageClient {

    Map<String, String> registration(String username, String password);

    boolean signIn(String username, String password);

    String[] folderElements(String path);

    File getFile(String path);

    Integer sizeOf(String path);

    boolean uploadFile(String path, File file);

    boolean uploadFile(String path, byte[] content);

    boolean appendFile(String path, File file);

    boolean appendFile(String path, byte[] content);

    boolean createFolder(String path);

    boolean move(String src, String dest);

    boolean copy(String src, String dest);

    boolean delete(String path);
}