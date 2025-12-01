package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class Config {

    private static final Properties config = readConfigProperties();
    private static final String BASE_URL = constructBaseUrl();

    public static String baseUrl() {
        return BASE_URL.endsWith("/") ? BASE_URL : BASE_URL + "/";
    }

    public static String url(String path) {
        return baseUrl() + path.replaceFirst("^/", "");
    }

    public static Properties getConfig() {
        return config;
    }

    private static String constructBaseUrl() {
        String baseUrl = Optional.ofNullable(System.getenv("BASE_URL"))
                .orElse(System.getProperty("base.url", ""));
        if (baseUrl.isEmpty()) {
            baseUrl = config.getProperty("base.url");
        }
        return baseUrl;
    }

    private static Properties readConfigProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = DriverFactory.class.getClassLoader()
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
