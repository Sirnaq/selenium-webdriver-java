package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Config;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class IframesPage {

    private final TestContext context;

    private static final By PARAGRAPH = By.tagName("p");

    public IframesPage(TestContext context) {
        this.context = context;
    }

    public IframesPage open() {
        context.driver().get(Config.url("iframes.html"));
        return this;
    }

    public IframesPage switchToIframe() {
        context.withTimeout(10).wdWait()
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("my-iframe"));
        return this;
    }

    public IframesPage shouldHaveParagraphSize(int expectedNumber) {
        context.withTimeout(10).wdWait()
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(PARAGRAPH,0));
        List<WebElement> paragraphs = context.driver().findElements(PARAGRAPH);
        assertThat(paragraphs).hasSize(expectedNumber);
        return this;
    }
}
