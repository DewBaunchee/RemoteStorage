package by.vorivoda.matvey.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

@Component
public class RemoteStorageClient implements IRemoteStorageClient {

    private final Logger logger = LoggerFactory.getLogger(RemoteStorageClient.class);
    private final RestTemplate requester;
    private final String serverAddress;

    @Autowired
    public RemoteStorageClient(RestTemplate requester,
                               @Value("${server.address}") String serverAddress) {
        this.requester = requester;
        this.serverAddress = serverAddress;
    }

    @Override
    public Map<String, String> registration(String username, String password) {
        logger.info("Attempting to register user with username \"" + username + "\"...");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("username", username);
        parameters.add("password", encode(password));

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);
        ResponseEntity<String> response = requester.postForEntity(serverAddress, request, String.class);

        String errorReport = response.getBody();
        Map<String, String> errors = parseErrorReport(errorReport);

        if (errors.size() > 0) logger.error("Errors occurred: " + errorReport);
        else logger.info("Registered.");

        return errors;
    }

    private static Map<String, String> parseErrorReport(String errorReport) {
        if (errorReport == null || errorReport.length() == 0) return new HashMap<>();

        errorReport = errorReport.toLowerCase(Locale.ROOT);
        Scanner scanner = new Scanner(errorReport);

        Map<String, String> errors = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String key = line.substring(0, line.indexOf(":"));
            String value = line.substring(line.indexOf(":") + 1).trim();

            errors.merge(key, value, (currentErrors, newError) -> currentErrors + "; " + newError);
        }

        return errors;
    }

    private static String encode(String password) {
        char[] encoded = new char[password.length()];

        for (int i = 0; i < encoded.length; i++) {
            encoded[i] = (char) ((password.charAt(i) * i) % 31);
        }

        return String.valueOf(encoded);
    }

    @Override
    public boolean signIn(String username, String password) {
        return false;
    }

    @Override
    public String[] folderElements(String path) {
        return new String[0];
    }

    @Override
    public File getFile(String path) {
        return null;
    }

    @Override
    public Integer sizeOf(String path) {
        return null;
    }

    @Override
    public boolean uploadFile(String path, File file) {
        return false;
    }

    @Override
    public boolean uploadFile(String path, byte[] content) {
        return false;
    }

    @Override
    public boolean appendFile(String path, File file) {
        return false;
    }

    @Override
    public boolean appendFile(String path, byte[] content) {
        return false;
    }

    @Override
    public boolean createFolder(String path) {
        return false;
    }

    @Override
    public boolean move(String src, String dest) {
        return false;
    }

    @Override
    public boolean copy(String src, String dest) {
        return false;
    }

    @Override
    public boolean delete(String path) {
        return false;
    }
}
