package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class Config {

    private static final Properties config = readConfigProperties();
    private static final String BASE_URL = constructAttribute("base.url");
    private static final String GRID_URL = constructAttribute("grid.url");
    private static final boolean HEADLESS = Boolean.parseBoolean(constructAttribute("headless"));
    private static final boolean BROWSER_REMOTE = Boolean.parseBoolean(constructAttribute("browser.remote"));

    public static String baseUrl() {
        return BASE_URL.endsWith("/") ? BASE_URL : BASE_URL + "/";
    }

    public static String url(String path) {
        return baseUrl() + path.replaceFirst("^/", "");
    }

    public static boolean isHeadless() {
        return HEADLESS;
    }

    public static Properties getConfig() {
        return config;
    }

    public static boolean isRemote(){
        return BROWSER_REMOTE;
    }

    public static String gridUrl(){
        return GRID_URL;
    }

    private static String constructAttribute(String attributeName) {
        String attribute = Optional.ofNullable(System.getenv(
                        attributeName.toUpperCase().replace(".", "_")))
                .orElse(System.getProperty(attributeName, ""));
        if (attribute.isEmpty()) {
            attribute = config.getProperty(attributeName);
        }
        return attribute;
    }

    private static Properties readConfigProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = Config.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                throw new RuntimeException("There is no config.properties file");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error while loading config", e);
        }
        return properties;
    }
}
