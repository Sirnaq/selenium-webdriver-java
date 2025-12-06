package tests.ch02;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.HandsOnPage;
import pages.TestContext;

public class HelloWorldFirefoxTest {
    private TestContext context;
    private HandsOnPage handsOnPage;

    @BeforeEach
    public void setupWebDriver() {
        context = new TestContext();
        handsOnPage = new HandsOnPage(context);
    }

    @Test
    public void firefoxTest() {
        handsOnPage.open()
                .logTitleOfThePage()
                .checkIfPageTitleIs("Hands-On Selenium WebDriver with Java");
    }

    @AfterEach
    public void tearDown() {
        context.driverQuit();
    }
}
