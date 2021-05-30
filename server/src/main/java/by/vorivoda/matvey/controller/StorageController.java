package by.vorivoda.matvey.controller;

import by.vorivoda.matvey.controller.util.APIOperation;
import by.vorivoda.matvey.controller.util.FileStorageAPIContentOperation;
import by.vorivoda.matvey.controller.util.FileStorageAPIOperation;
import by.vorivoda.matvey.model.GlobalConstants;
import by.vorivoda.matvey.model.manager.FileStorage;
import by.vorivoda.matvey.model.manager.FileStorageFactory;
import by.vorivoda.matvey.model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping(value = {
        GlobalConstants.STORAGE_PATH,
        GlobalConstants.STORAGE_PATH + "/{path}"
})
public class StorageController {

    public static void main(String[] args) {
        List<String> s = List.of("asd", "sad");
        System.out.println(s);
    }

    private final Logger logger = LoggerFactory.getLogger(StorageController.class);
    private final FileStorageFactory fileStorageFactory;
    private final UserService userService;

    private final Map<APIOperation, FileStorageAPIOperation> operations = new HashMap<>() {{
        put(APIOperation.GET, (storage, path, ignored) -> { // Get folder or file content
            logger.info("Executing \"GET\" APIOperation...");
            String answer;
            if (storage.isDirectory(path)) {
                answer = storage.getFolderContent(path).toString();
            } else {
                answer = new String(storage.getFile(path));
            }
            logger.info("Executed successfully.");
            return answer;
        });
        put(APIOperation.SIZE, (storage, path, ignored) -> { // Get size
            logger.info("Executing \"SIZE\" APIOperation...");
            BigInteger size = storage.sizeOf(path);
            logger.info("Executed successfully.");
            return size.toString();
        });
        put(APIOperation.COPY, (storage, src, dest) -> { // Copy
            logger.info("Executing \"COPY\" APIOperation...");
            storage.copy(src, dest);
            logger.info("Executed successfully.");
            return "OK";
        });
        put(APIOperation.MOVE, (storage, src, dest) -> { // Move
            logger.info("Executing \"MOVE\" APIOperation...");
            storage.move(src, dest);
            logger.info("Executed successfully.");
            return "OK";
        });
        put(APIOperation.DELETE, (storage, path, ignored) -> { // Delete
            logger.info("Executing \"DELETE\" APIOperation...");
            storage.delete(path);
            logger.info("Executed successfully.");
            return "OK";
        });
    }};

    private final Map<APIOperation, FileStorageAPIContentOperation> contentOperations = new HashMap<>() {{
        put(APIOperation.PUT, (storage, path, content) -> { // Put or create and put file
            logger.info("Executing \"PUT\" APIOperation...");
            storage.writeFile(path, content);
            logger.info("Executed successfully.");
            return "OK";
        });
        put(APIOperation.POST, (storage, path, content) -> { // Post file
            logger.info("Executing \"POST\" APIOperation...");
            storage.appendFile(path, content);
            logger.info("Executed successfully.");
            return "OK";
        });
        put(APIOperation.CREATE_FOLDER, (storage, path, ignored) -> { // Create folder
            logger.info("Executing \"CREATE_FOLDER\" APIOperation...");
            storage.createFolder(path);
            logger.info("Executed successfully.");
            return "OK";
        });
    }};

    @Autowired
    public StorageController(UserService userService,
                             FileStorageFactory fileStorageFactory,
                             ServletContext context) {
        this.userService = userService;
        this.fileStorageFactory = fileStorageFactory;
        FileStorage.setContextPath(context.getRealPath(""));
    }

    @GetMapping
    public String getRequest(@RequestParam Map<String, String> requestParams,
                             @PathVariable(required = false) String path) throws HttpRequestMethodNotSupportedException, IOException {
        path = path == null ? "/" : path;
        FileStorage storage = getAuthenticatedStorage();
        String operationType = requestParams.get("operation");
        FileStorageAPIOperation operation = getAPIOperation("GET", operationType);

        if (operation == null)
            throw new HttpRequestMethodNotSupportedException("Operation type " + operationType + " (GET) is not supported.");

        return operation.operate(storage, path, requestParams.get("additional"));
    }

    @DeleteMapping
    public String deleteRequest(@RequestParam Map<String, String> requestParams,
                                @PathVariable(required = false) String path) throws HttpRequestMethodNotSupportedException, IOException {
        path = path == null ? "/" : path;
        FileStorage storage = getAuthenticatedStorage();
        FileStorageAPIOperation operation = getAPIOperation("DELETE", null);

        if (operation == null)
            throw new HttpRequestMethodNotSupportedException("Operation type (DELETE) is not supported.");

        return operation.operate(storage, path, requestParams.get("additional"));
    }

    @PostMapping
    public String postRequest(@RequestParam Map<String, String> requestParams,
                              @PathVariable(required = false) String path,
                              @RequestBody byte[] content) throws HttpRequestMethodNotSupportedException, IOException {

        path = path == null ? "/" : path;
        FileStorage storage = getAuthenticatedStorage();
        String operationType = requestParams.get("operation");
        FileStorageAPIContentOperation operation = getAPIContentOperation("POST", operationType);

        if (operation == null)
            throw new HttpRequestMethodNotSupportedException("Operation type " + operationType + " (POST) is not supported.");

        return operation.operate(storage, path, content);
    }

    @PutMapping
    public String putRequest(@RequestParam Map<String, String> requestParams,
                             @PathVariable(required = false) String path,
                             @RequestBody byte[] content) throws HttpRequestMethodNotSupportedException, IOException {

        path = path == null ? "/" : path;
        FileStorage storage = getAuthenticatedStorage();
        String operationType = requestParams.get("operation");
        FileStorageAPIContentOperation operation = getAPIContentOperation("PUT", operationType);

        if (operation == null)
            throw new HttpRequestMethodNotSupportedException("Operation type " + operationType + " (PUT) is not supported.");

        return operation.operate(storage, path, content);
    }

    private FileStorageAPIOperation getAPIOperation(String httpMethod, String paramOperation) {
        APIOperation operation = APIOperation.valueOf(paramOperation == null ?
                httpMethod : paramOperation.toUpperCase(Locale.ROOT)); // TODO .valueOf may cause an IllegalArgumentException
        return operations.get(operation);
    }

    private FileStorageAPIContentOperation getAPIContentOperation(String httpMethod, String paramOperation) {
        APIOperation operation = APIOperation.valueOf(paramOperation == null ?
                httpMethod : paramOperation.toUpperCase(Locale.ROOT)); // TODO .valueOf may cause an IllegalArgumentException
        return contentOperations.get(operation);
    }

    private FileStorage getAuthenticatedStorage() {
        /*
        logger.info("Trying to get file storage of authenticated...");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(authentication.getName());
        if (user == null) {
            throw new UsernameNotFoundException("No user with username " + authentication.getName());
        }
        return fileStorageFactory.getFileStorage(user.getStorageInfo());*/
        return new FileStorage("/test");
    }
}
