package tests.ch05;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pages.LongPage;
import pages.TestContext;
import utils.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class FirefoxFullPageScreenshotTest {

    TestContext context;

    @BeforeEach
    void setup() {
        FirefoxOptions options = new FirefoxOptions();
        if (Config.isHeadless()) {
            options.addArguments("--headless");
        }
        WebDriver driver = WebDriverManager.firefoxdriver().capabilities(options).create();
        context = new TestContext(driver);
    }

    @AfterEach
    void tearDown() {
        context.driver().quit();
    }

    @Test
    void testFirefoxFullPageScreenshot() {
        new LongPage(context).open().waitForTextToLoad();

        byte[] imageBytes = ((FirefoxDriver) context.driver()).getFullPageScreenshotAs(OutputType.BYTES);
        Path destination = Paths.get("screenshots/fullpage-screenshot-firefox.png");
        try {
            Files.write(destination, imageBytes);
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong while writing the file: ", e);
        }
        assertThat(destination).exists();
    }

}
