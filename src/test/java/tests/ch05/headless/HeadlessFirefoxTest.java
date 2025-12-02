package tests.ch05.headless;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pages.HandsOnPage;
import pages.TestContext;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HeadlessFirefoxTest {

    TestContext context;

    @BeforeEach
    void setUp() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");

        WebDriver driver = WebDriverManager.firefoxdriver().capabilities(options).create();
        context = new TestContext(driver);
    }

    @AfterEach
    void tearDown() {
        context.driver().quit();
    }

    @Test
    void testHeadless() {
        new HandsOnPage(context).open()
                .checkIfPageTitleIs("Hands-On Selenium WebDriver with Java");
    }
}
