package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Config;

import static org.assertj.core.api.Assertions.assertThat;

public class DialogBoxesPage {

    private final TestContext context;

    private static final By ALERT = By.id("my-alert");
    private static final By CONFIRM = By.id("my-confirm");
    private static final By PROMPT = By.id("my-prompt");
    private static final By MODAL = By.id("my-modal");
    private static final By CLOSE = By.xpath("//button[text()='Close']");

    public DialogBoxesPage(TestContext context) {
        this.context = context;
    }

    public DialogBoxesPage open() {
        context.driver().get(Config.url("dialog-boxes.html"));
        return this;
    }

    public DialogBoxesPage alertClick() {
        context.click(ALERT);
        return this;
    }

    public DialogBoxesPage confirmClick() {
        context.click(CONFIRM);
        return this;
    }

    public DialogBoxesPage promptClick() {
        context.click(PROMPT);
        return this;
    }

    public DialogBoxesPage modalClick() {
        context.click(MODAL);
        return this;
    }

    public DialogBoxesPage checkIfAlertTextIsEqualTo(String expectedText) {
        context.withTimeout(3).wdWait().until(ExpectedConditions.alertIsPresent());
        Alert alert = getAlert();
        assertThat(alert.getText()).isEqualTo(expectedText);
        return this;
    }

    public DialogBoxesPage acceptAlert() {
        getAlert().accept();
        return this;
    }

    public DialogBoxesPage dismissAlert() {
        getAlert().dismiss();
        return this;
    }

    public DialogBoxesPage fillOutPromptWith(String text) {
        getAlert().sendKeys(text);
        return this;
    }

    public DialogBoxesPage checkIfCloseTakNameIs(String expectedTagName) {
        assertThat(context.visible(CLOSE).getTagName()).isEqualTo(expectedTagName);
        return this;
    }

    public DialogBoxesPage closeClick() {
        context.click(CLOSE);
        return this;
    }

    private Alert getAlert(){
        return context.driver().switchTo().alert();
    }
}
