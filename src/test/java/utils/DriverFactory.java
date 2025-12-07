package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class DriverFactory {

    private static final Properties config = Config.getConfig();
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            driver.set(createDriver());
        }
        return driver.get();
    }

    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            webDriver.quit();
            driver.remove();
        }
    }

    private static WebDriver createDriver() {
        String browserType = getProperty("browserType");
        Boolean isHeadless = Boolean.parseBoolean(getProperty("headless"));

        return switch (browserType) {
            case "chrome" -> getChromeDriver(isHeadless);
            case "firefox" -> getFirefoxDriver(isHeadless);
            case "edge" -> getEdgeDriver(isHeadless);
            default -> throw new RuntimeException("Unidentified browser");
        };
    }

    private static String getProperty(String propertyName) {
        String property = System.getProperty(propertyName, "");
        if (property.isEmpty()) {
            return config.getProperty(propertyName);
        } else {
            return property;
        }
    }

    private static WebDriver getChromeDriver(Boolean isHeadless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        if (isHeadless) {
            chromeOptions.addArguments("--headless=new");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--window-size=1920,1080");
        }
        if (Config.isRemote()) {
            return getRemoteDriver(chromeOptions);
        } else {
            return new ChromeDriver(chromeOptions);
        }
    }

    private static WebDriver getFirefoxDriver(Boolean isHeadless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        if (isHeadless) {
            firefoxOptions.addArguments("--headless");
            firefoxOptions.addArguments("--window-size=1920,1080");
        }
        if (Config.isRemote()) {
            return getRemoteDriver(firefoxOptions);
        } else {
            return new FirefoxDriver(firefoxOptions);
        }
    }

    private static WebDriver getEdgeDriver(Boolean isHeadless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions edgeOptions = new EdgeOptions();
        if (isHeadless) {
            edgeOptions.addArguments("--headless=new");
            edgeOptions.addArguments("--disable-gpu");
            edgeOptions.addArguments("--no-sandbox");
            edgeOptions.addArguments("--window-size=1920,1080");
        }
        if (Config.isRemote()) {
            return getRemoteDriver(edgeOptions);
        } else {
            return new EdgeDriver(edgeOptions);
        }
    }

    private static RemoteWebDriver getRemoteDriver(MutableCapabilities caps) {
        URL gridUrl;
        try {
            gridUrl = new URL(Config.gridUrl());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Incorrect selenium grid url: ", e);
        }
        return new RemoteWebDriver(gridUrl, caps);
    }
}
