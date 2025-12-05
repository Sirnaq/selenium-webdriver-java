package pages;

import org.openqa.selenium.By;
import utils.Config;

public class LongPage {

    private final TestContext context;

    private static final By LOREM_IPSUM = By.xpath("//*[contains(text(),'Lorem ipsum')]");
    private static final By LAST_PARAGRAPH = By.cssSelector("p:last-child");
    private static final By PARAGRAPH = By.tagName("p");
    private static final By CONTAINER = By.className("container");

    public LongPage(TestContext context) {
        this.context = context;
    }

    public LongPage open() {
        context.driver().get(Config.url("long-page.html"));
        return this;
    }

    public LongPage waitForTextToLoad() {
        context.present(LOREM_IPSUM);
        return this;
    }

    public LongPage scrollBy(String px) {
        context.js().executeScript(String.format("window.scrollBy(0,%s)", px));
        context.log().debug("Window was scrolled by {}px", px);
        return this;
    }

    public LongPage scrollToLastParagraph() {
        context.js().executeScript("""
                arguments[0].scrollIntoView();
                """,context.present(LAST_PARAGRAPH));
        return this;
    }
}
