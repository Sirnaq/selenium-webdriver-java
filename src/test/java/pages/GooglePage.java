package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Locators;

import static org.assertj.core.api.Assertions.assertThat;

public class GooglePage {

    private final TestContext context;

    private static final By GOOGLE_WARNING_TEXT = By.xpath("//*[contains(normalize-space(),'Zanim przejdziesz do Google')]");
    private static final By REJECT_ALL = Locators.byExactText("div", "Odrzuć wszystko");
    private static final By MAIN_TEXT_AREA = By.tagName("textarea");
    private static final By CAPTCHA = By.xpath("//iframe[@title='reCAPTCHA']");
    private static final By NOT_A_ROBOT = Locators.byExactText("label", "Nie jestem robotem");

    public GooglePage(TestContext testContext) {
        this.context = testContext;
    }

    public GooglePage open() {
        context.driver().get("https://google.com");
        return this;
    }

    public GooglePage rejectAllClick() {
        context.visible(GOOGLE_WARNING_TEXT);
        context.click(REJECT_ALL);
        context.withTimeout(3).wdWait()
                .until(ExpectedConditions.invisibilityOfElementLocated(REJECT_ALL));
        return this;
    }

    public GooglePage mainTextAreaType(String text) {
        context.visible(MAIN_TEXT_AREA).clear();
        context.type(MAIN_TEXT_AREA, text);
        context.driver().findElement(MAIN_TEXT_AREA).sendKeys(Keys.ENTER);
        return this;
    }

    public GooglePage switchToReCaptchaIframe() {
        context.withTimeout(3).wdWait()
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(CAPTCHA));
        return this;
    }

    public GooglePage checkIfNotARobotMessageDisplayed() {
        context.withTimeout(3).wdWait()
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(NOT_A_ROBOT, 0));
        assertThat(context.present(NOT_A_ROBOT).isDisplayed()).isTrue();
        context.log().debug("Nie jestem robotem message is displayed");
        return this;
    }
}
