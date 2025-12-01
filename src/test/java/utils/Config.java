package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class Config {

    private static final Properties config = readConfigProperties();
    private static final String BASE_URL = constructAttribute("base.url");
    private static final boolean HEADLESS = Boolean.parseBoolean(constructAttribute("headless"));

    public static String baseUrl() {
        return BASE_URL.endsWith("/") ? BASE_URL : BASE_URL + "/";
    }

    public static String url(String path) {
        return baseUrl() + path.replaceFirst("^/", "");
    }

    public static Boolean isHeadless() {
        return HEADLESS;
    }

    public static Properties getConfig() {
        return config;
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
