package tests.ch02;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pages.HandsOnPage;
import pages.TestContext;

public class HelloWorldJupiterTest {

    TestContext context;

    @Test
    void test() {
        context = new TestContext();
        HandsOnPage handsOnPage = new HandsOnPage(context);
        handsOnPage.open()
                .logTitleOfThePage()
                .checkIfPageTitleIs("Hands-On Selenium WebDriver with Java");
    }

    @AfterEach
    void tearDown() {
        context.driver().quit();
    }

}
