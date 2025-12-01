package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Config;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FramesPage {
    private final TestContext context;

    private static final By FRAME = By.name("frame-body");
    private static final By PARAGRAPH = By.tagName("p");

    public FramesPage(TestContext context) {
        this.context = context;
    }

    public FramesPage open() {
        context.driver().get(Config.url("frames.html"));
        return this;
    }

    public FramesPage switchToFrame() {
        context.withTimeout(3).wdWait()
                .until(ExpectedConditions.presenceOfElementLocated(FRAME));
        context.driver().switchTo().frame(context.visible(FRAME));
        return this;
    }

    public FramesPage checkIfParagraphSizeIs(int expectedNumber) {
        context.withTimeout(3).wdWait()
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(PARAGRAPH, 0));
        List<WebElement> paragraphs = context.driver().findElements(PARAGRAPH);
        assertThat(paragraphs).hasSize(expectedNumber);
        return this;
    }
}
