package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Config;
import utils.Locators;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class GetUserMediaPage {

    private final TestContext context;

    private static final By START = Locators.byExactText("button","Start");
    private static final By VIDEO_DEVICE = By.id("video-device");

    public GetUserMediaPage(TestContext context) {
        this.context = context;
    }

    public GetUserMediaPage open(){
        context.driver().get(Config.url("get-user-media.html"));
        return this;
    }

    public GetUserMediaPage startClick(){
        context.click(START);
        return this;
    }

    public void waitForVideoDevice() {
        Pattern nonEmptyString = Pattern.compile(".+");
        context.wdWait().until(ExpectedConditions.textMatches(VIDEO_DEVICE,nonEmptyString));
        assertThat(context.visible(VIDEO_DEVICE).getText()).isNotEmpty();
    }
}
