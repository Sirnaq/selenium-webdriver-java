package tests.ch05.extensions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import pages.HandsOnPage;
import pages.TestContext;
import utils.Config;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FirefoxExtensionTest {

    TestContext context;

    @BeforeEach
    void setup() {
        FirefoxOptions options = new FirefoxOptions();
        if (Config.isHeadless()) {
            options.addArguments("---headless");
        }
        Path extension;
        try {
            extension = Paths.get(ClassLoader.getSystemResource("dark-bg.xpi").toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to load extension dark-bg.crx", e);
        }
        FirefoxProfile profile = new FirefoxProfile();
        profile.addExtension(extension.toFile());
        options.setProfile(profile);
        WebDriver driver = WebDriverManager.firefoxdriver().capabilities(options).create();
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
