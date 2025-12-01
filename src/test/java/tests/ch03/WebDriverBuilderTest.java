package tests.ch03;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.TestContext;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WebDriverBuilderTest {

    TestContext context;

    @BeforeAll
    void setupClass() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    void setup() {
        WebDriver driver = RemoteWebDriver.builder().oneOf(new FirefoxOptions()).build();
        context = new TestContext(driver);
    }

    @Test
    void hollow() {
        //todo test implementation
    }

    @AfterEach
    void tearDown() {
        context.driver().quit();
    }

}
