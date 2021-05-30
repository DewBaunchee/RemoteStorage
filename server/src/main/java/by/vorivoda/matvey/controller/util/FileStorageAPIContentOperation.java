package by.vorivoda.matvey.controller.util;

import by.vorivoda.matvey.model.manager.FileStorage;
import by.vorivoda.matvey.model.manager.exception.NotAFile;

import java.io.IOException;

public interface FileStorageAPIContentOperation {
    String operate(FileStorage fileStorage, String path, byte[] content) throws IOException, NotAFile;
}
