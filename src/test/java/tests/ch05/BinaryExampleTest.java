package tests.ch05;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import pages.HandsOnPage;
import pages.TestContext;
import utils.Config;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assumptions.assumeThat;

public class BinaryExampleTest {

    TestContext context;

    @BeforeEach
    void setup() {
        Path browserBinary = Paths.get("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        assumeThat(browserBinary).exists();

        FirefoxOptions options = new FirefoxOptions();
        if(Config.isHeadless()){
            options.addArguments("--headless");
        }
        options.setBinary(browserBinary);
        WebDriver driver = WebDriverManager.firefoxdriver().capabilities(options).create();
        context = new TestContext(driver);
    }

    @AfterEach
    void tearDown() {
        if (context != null) {
            context.driver().quit();
        }
    }

    @Test
    void testBinary() {
        new HandsOnPage(context).open()
                .checkIfPageTitleIs("Hands-On Selenium WebDriver with Java");
    }
}
