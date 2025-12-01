package tests.ch02;

import org.junit.jupiter.api.Test;
import pages.HandsOnPage;
import pages.TestContext;

public class HelloWorldJupiterTest {

    @Test
    void test() {
        TestContext context = new TestContext();
        HandsOnPage handsOnPage = new HandsOnPage(context);
        handsOnPage.open()
                .logTitleOfThePage()
                .checkTitleText("Hands-On Selenium WebDriver with Java");
    }

}
