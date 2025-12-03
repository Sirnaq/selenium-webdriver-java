package tests.ch05.Logging;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import pages.ConsoleLogsPage;
import pages.TestContext;
import utils.Config;

import java.util.logging.Level;

public class ChromeLoggingTest {

    TestContext context;

    @BeforeEach
    void setup() {
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.ALL);

        ChromeOptions options = new ChromeOptions();
        if (Config.isHeadless()) {
            options.addArguments("--headless=new");
        }
        options.setCapability("goog:loggingPrefs", logs);
        WebDriver driver = WebDriverManager.chromedriver().capabilities(options).create();
        context = new TestContext(driver);
    }

    @AfterEach
    void tearDown() {
        context.driver().quit();
    }

    @Test
    void testChromeLogging() {
        new ConsoleLogsPage(context).open()
                .logBrowserConsoleLogs();
    }
}
