package by.vorivoda.matvey.model;

import java.util.HashMap;
import java.util.Map;

public class GlobalConstants {
    // Security
    public static final String USERNAME_SYMBOLS_REGEX = "[A-Za-z0-9_]*";
    public static final String PASSWORD_SYMBOLS_REGEX = "[A-Za-z0-9_]*";
    public static final Integer USERNAME_MIN_LENGTH = 4;
    public static final Integer USERNAME_MAX_LENGTH = 32;
    public static final Integer PASSWORD_MIN_LENGTH = 8;
    public static final Integer PASSWORD_MAX_LENGTH = 64;
    public static final Integer TOKEN_LIFETIME_IN_MINUTES = 60;

    // Mapping
    public static final String LOGIN_PATH = "/login";
    public static final String REGISTRATION_PATH = "/registration";
    public static final String STORAGE_PATH = "/storage";

    // STORAGE
    public static final String STORAGES_LOCATION = "/storages";

    // WEB
    public static final Map<String, String> CONTENT_TYPES = new HashMap<>(){{
        put("txt", "text/plain");
        put("css", "text/css");
        put("html", "text/html");
        put("js", "text/javascript");
        put("ts", "text/javascript");
        put("php", "text/php");
        put("xml", "text/xml");

        put("ogg", "video/ogg");
        put("mp4", "video/mp4");

        put("png", "image/png");
        put("jpeg", "image/jpeg");
        put("gif", "image/gif");

        put("mp3", "audio/mpeg");

        put("zip", "application/zip");
        put("pdf", "application/pdf");
    }};

    public static final String DEFAULT_CONTENT_TYPE = "text/plain";
}
