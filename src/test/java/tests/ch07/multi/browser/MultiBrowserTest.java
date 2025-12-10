package tests.ch07.multi.browser;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import pages.HandsOnPage;
import pages.TestContext;
import utils.Config;

public class MultiBrowserTest {

    TestContext context;

    @AfterEach
    void tearDown() {
        context.driver().quit();
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "edge", "firefox"})
    void testMultiBrowser(String browserName) {
        WebDriver driver = WebDriverManager.getInstance(browserName).capabilities(getCaps(browserName)).create();
        context = new TestContext(driver);
        new HandsOnPage(context).open().checkIfPageTitleIs("Hands-On Selenium WebDriver with Java");
    }

    MutableCapabilities getCaps(String browserName) {
        switch (browserName) {
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                if (Config.isHeadless()) {
                    options.addArguments("--headless=new");
                }
                return options;
            }
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                if (Config.isHeadless()) {
                    options.addArguments("--headless");
                }
                return options;
            }
            case "edge" -> {
                EdgeOptions options = new EdgeOptions();
                if (Config.isHeadless()) {
                    options.addArguments("--headless=new");
                }
                return options;
            }
            default -> throw new RuntimeException("undefined browser");
        }
    }
}
