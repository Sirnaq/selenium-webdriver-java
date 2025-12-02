package tests.ch05.extensions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.HandsOnPage;
import pages.TestContext;
import utils.Config;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChomeExtensionTest {
    TestContext context;

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        if (Config.isHeadless()) {
            options.addArguments("---headless=new");
        }

        Path extension;
        try {
            extension = Paths.get(ClassLoader.getSystemResource("shade_dark_mode.crx").toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to load extension dark-bg.crx", e);
        }
        options.addExtensions(extension.toFile());
        WebDriver driver = WebDriverManager.chromedriver().capabilities(options).create();
        context = new TestContext(driver);
    }

    @AfterEach
    void tearDown() {
        context.driver().quit();
    }

    @Test
    void testExtension() {
        new HandsOnPage(context).open()
                .checkIfPageTitleIs("Hands-On Selenium WebDriver with Java");
    }
}
