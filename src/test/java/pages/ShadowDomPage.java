package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import utils.Config;

public class ShadowDomPage {

    private final TestContext context;

    private static final By CONTENT = By.id("content");
    private static final By PARAGRAPH = By.cssSelector("p");

    public ShadowDomPage(TestContext context) {
        this.context = context;
    }

    public ShadowDomPage open() {
        context.driver().get(Config.url("shadow-dom.html"));
        return this;
    }

    public String getTextFromShadowDom() {
        SearchContext shadowRoot = context.present(CONTENT).getShadowRoot();
        String paragraph = shadowRoot.findElement(PARAGRAPH).getText();
        context.log().debug("Text from the shadow DOM element: {}", paragraph);
        return paragraph;
    }
}