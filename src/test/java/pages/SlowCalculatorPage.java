package pages;

import utils.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static utils.Locators.byExactText;

public class SlowCalculatorPage {

    private final TestContext context;

    private static final By ONE = byExactText("span", "1");
    private static final By PLUS = byExactText("span", "+");
    private static final By THREE = byExactText("span", "3");
    private static final By EQUALS = byExactText("span", "=");
    private static final By SCREEN = By.className("screen");

    public SlowCalculatorPage(TestContext context) {
        this.context = context;
    }

    public SlowCalculatorPage open() {
        context.driver().get(Config.url("slow-calculator.html"));
        return this;
    }

    public SlowCalculatorPage pressOne() {
        context.click(ONE);
        return this;
    }

    public SlowCalculatorPage pressPlus() {
        context.click(PLUS);
        return this;
    }

    public SlowCalculatorPage pressThree() {
        context.click(THREE);
        return this;
    }

    public SlowCalculatorPage pressEquals() {
        context.click(EQUALS);
        return this;
    }

    public SlowCalculatorPage waitUntilScreenEquals(String expectedResult) {
        context.wdWait().until(ExpectedConditions.textToBe(SCREEN, expectedResult));
        return this;
    }
}
