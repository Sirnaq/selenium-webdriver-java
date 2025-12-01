package tests.ch03;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.HandsOnPage;
import pages.TestContext;

public class WdmBuilderTest {

    TestContext context;
    HandsOnPage handsOnPage;

    @BeforeEach
    void setup() {
        WebDriver driver = WebDriverManager.chromedriver().create();
        context = new TestContext(driver);
        handsOnPage = new HandsOnPage(context);
    }

    @Test
    void testBasicMethods() {
        handsOnPage.open()
                .checkIfPageTitleIs("Hands-On Selenium WebDriver with Java")
                .checkIfUrlEqualsBaseUrl()
                .checkIfPageSourceContains("</html>");
    }

    @Test
    void testSessionId() {
        handsOnPage.open()
                .checkIfSessionIdExists()
                .logSessionId();
    }

    @AfterEach
    void tearDown() {
        context.driver().quit();
    }
}
