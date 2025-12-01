package tests.ch04;

import base.TestBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import pages.HandsOnPage;
import pages.TestContext;
import utils.MyEventListener;

public class EventListenerJupiterTest extends TestBase {

    TestContext decoratedContext;
    HandsOnPage handsOnPage;

    @BeforeEach
    void setup() {
        MyEventListener listener = new MyEventListener();
        WebDriver decoratedDriver = new EventFiringDecorator<>(listener).decorate(context.driver());
        decoratedContext = new TestContext(decoratedDriver);
        handsOnPage = new HandsOnPage(decoratedContext);
    }

    @Test
    void testEventListener() {
        handsOnPage.open()
                .checkIfPageTitleIs("Hands-On Selenium WebDriver with Java")
                .webFormClick();
    }

    @AfterEach
    void tearDown() {
        decoratedContext.driver().quit();
    }
}
