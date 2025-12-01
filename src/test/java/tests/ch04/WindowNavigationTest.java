package tests.ch04;

import base.TestBase;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WindowType;
import pages.*;

import static org.assertj.core.api.Assertions.assertThat;

public class WindowNavigationTest extends TestBase {


    @Test
    void testNewTab() {
        new HandsOnPage(context).open();
        String initHandle = context.driver().getWindowHandle();
        context.driver().switchTo().newWindow(WindowType.TAB);
        new WebFormPage(context).open();
        assertThat(context.driver().getWindowHandles().size()).isEqualTo(2);
        context.driver().switchTo().window(initHandle);
        context.driver().close();
        assertThat(context.driver().getWindowHandles().size()).isEqualTo(1);
    }

    @Test
    void testNewWindow() {
        new HandsOnPage(context).open();
        String initHandle = context.driver().getWindowHandle();
        context.driver().switchTo().newWindow(WindowType.WINDOW);
        new WebFormPage(context).open();
        assertThat(context.driver().getWindowHandles().size()).isEqualTo(2);
        context.driver().switchTo().window(initHandle);
        context.driver().close();
        assertThat(context.driver().getWindowHandles().size()).isEqualTo(1);
    }

    @Test
    void testIFrames() {
        new IframesPage(context).open()
                .switchToIframe()
                .shouldHaveParagraphSize(20);
    }

    @Test
    void googleIframe() {
        new GooglePage(context).open()
                .rejectAllClick()
                .mainTextAreaType("selenium")
                .switchToReCaptchaIframe()
                .checkIfNotARobotMessageDisplayed();
    }

    @Test
    void testFrames() {
        new FramesPage(context).open()
                .switchToFrame()
                .checkIfParagraphSizeIs(20);
    }
}
