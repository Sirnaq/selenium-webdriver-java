package tests.ch05.multimedia;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pages.GetUserMediaPage;
import pages.TestContext;
import utils.Config;

public class FirefoxMultimediaTest {

    private TestContext context;

    @BeforeEach
    void setup() {
        FirefoxOptions options = new FirefoxOptions();
        if (Config.isHeadless()) {
            options.addArguments("--headless");
        }
        options.addPreference("media.navigator.permission.disabled", true);
        options.addPreference("media.navigator.streams.fake", true);
        WebDriver driver = WebDriverManager.firefoxdriver().capabilities(options).create();
        context = new TestContext(driver);
    }

    @AfterEach
    void tearDown() {
        context.driver().quit();
    }

    @Test
    void testChromeMultimedia() {
        new GetUserMediaPage(context).open()
                .startClick()
                .waitForVideoDevice();
    }
}
