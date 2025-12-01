package pages;

import utils.Config;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

public class NavigationPage {
    private final TestContext context;

    private static final By NEXT = By.linkText("Next");
    private static final By THREE = By.linkText("3");
    private static final By TWO = By.linkText("2");
    private static final By PREVIOUS = By.linkText("Previous");
    private static final By BODY = By.tagName("body");

    public NavigationPage open() {
        context.driver().get(Config.url("navigation.html"));
        return this;
    }

    public NavigationPage(TestContext context) {
        this.context = context;
    }

    public NavigationPage nextClick() {
        context.click(NEXT);
        return this;
    }

    public NavigationPage threeClick() {
        context.click(THREE);
        return this;
    }

    public NavigationPage twoClick() {
        context.click(TWO);
        return this;
    }

    public NavigationPage previousClick() {
        context.click(PREVIOUS);
        return this;
    }

    public NavigationPage checkThatBodyContains(String expectedContent) {
        assertThat(context.getText(BODY)).contains(expectedContent);
        return this;
    }

}
