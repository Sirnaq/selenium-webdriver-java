package tests.ch04;

import base.TestBase;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WindowType;

import static org.assertj.core.api.Assertions.assertThat;

public class WindowNavigationTest extends TestBase {


    @Test
    void testNewTab() {
        handsOnPage.open();
        String initHandle = context.driver().getWindowHandle();
        context.driver().switchTo().newWindow(WindowType.TAB);
        webFormPage.open();
        assertThat(context.driver().getWindowHandles().size()).isEqualTo(2);
        context.driver().switchTo().window(initHandle);
        context.driver().close();
        assertThat(context.driver().getWindowHandles().size()).isEqualTo(1);
    }

    @Test
    void testNewWindow() {
        handsOnPage.open();
        String initHandle = context.driver().getWindowHandle();
        context.driver().switchTo().newWindow(WindowType.WINDOW);
        webFormPage.open();
        assertThat(context.driver().getWindowHandles().size()).isEqualTo(2);
        context.driver().switchTo().window(initHandle);
        context.driver().close();
        assertThat(context.driver().getWindowHandles().size()).isEqualTo(1);
    }

    @Test
    void testIFrames() {
        iframesPage.open()
                .switchToIframe()
                .shouldHaveParagraphSize(20);
    }

    @Test
    void googleIframe() {
        googlePage.open()
                .rejectAllClick()
                .mainTextAreaType("selenium")
                .switchToReCaptchaIframe()
                .checkIfNotARobotMessageDisplayed();
    }

    @Test
    void testFrames() {
        framesPage.open()
                .switchToFrame()
                .checkIfParagraphSizeIs(20);
    }
}
