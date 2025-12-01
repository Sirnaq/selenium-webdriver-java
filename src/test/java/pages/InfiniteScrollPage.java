package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Config;

public class InfiniteScrollPage {

    private static final By PARAGRAPH = By.tagName("p");

    TestContext context;

    public InfiniteScrollPage(TestContext context) {
        this.context = context;
    }

    public InfiniteScrollPage open() {
        context.driver().get(Config.url("infinite-scroll.html"));
        return this;
    }

    public InfiniteScrollPage waitForParagraphsToLoad() {
        context.wdWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(PARAGRAPH, 0));
        return this;
    }

    public InfiniteScrollPage scrollToTheLastParagraphLoaded() {
        int numberOfParagraphsLoaded = context.driver().findElements(PARAGRAPH).size();
        WebElement lastParagraph = context.driver().findElement(
                By.xpath(String.format("//p[%d]", numberOfParagraphsLoaded)));
        context.js().executeScript("arguments[0].scrollIntoView();", lastParagraph);
        context.wdWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(PARAGRAPH, numberOfParagraphsLoaded));
        return this;
    }
}
