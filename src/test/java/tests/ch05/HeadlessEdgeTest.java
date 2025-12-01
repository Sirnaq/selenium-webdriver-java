package tests.ch05;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.HandsOnPage;
import pages.TestContext;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HeadlessEdgeTest {

    TestContext context;

    @BeforeAll
    void setClass() {
        WebDriverManager.edgedriver().setup();
    }

    @BeforeEach
    void setUp() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless=new");
        WebDriver driver = RemoteWebDriver.builder().oneOf(options).build();
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
