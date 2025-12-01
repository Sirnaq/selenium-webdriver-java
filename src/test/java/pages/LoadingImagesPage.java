package pages;

import utils.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class LoadingImagesPage {

    private final TestContext context;

    private static final By LANDSCAPE = By.id("landscape");

    public LoadingImagesPage(TestContext context) {
        this.context = context;
    }

    public LoadingImagesPage open() {
        context.driver().get(Config.url("loading-images.html"));
        return this;
    }

    public LoadingImagesPage setImplicitWaitTo(Duration duration) {
        context.options().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return this;
    }

    public LoadingImagesPage resetImplicitWait() {
        context.options().timeouts().implicitlyWait(Duration.ofSeconds(0));
        return this;
    }

    public LoadingImagesPage checkIfLandscapeLinkContains(String expectedValue) {
        assertThat(context.driver().findElement(LANDSCAPE).getAttribute("src")).containsIgnoringCase(expectedValue);
        return this;
    }

    public LoadingImagesPage waitForLandscape(int timeout) {
        context.withTimeout(timeout).present(LANDSCAPE);
        return this;
    }

    public LoadingImagesPage fluentWaitForLandscape() {
        context.fluentWait().until(ExpectedConditions.presenceOfElementLocated(LANDSCAPE));
        return this;
    }
}
