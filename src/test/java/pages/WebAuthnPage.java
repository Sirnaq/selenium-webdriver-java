package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WebAuthnPage {

    private final TestContext context;

    private static final By EMAIL = By.id("input-email");
    private static final By REGISTER = By.id("register-button");
    private static final By ALERT = By.className("alert-success");
    private static final By LOGIN = By.id("login-button");
    private static final By MAIN_CONTENT = By.className("main-content");

    public WebAuthnPage(TestContext context) {
        this.context = context;
    }

    public WebAuthnPage open(){
        context.driver().get("https://webauthn.io/");
        return this;
    }

    public WebAuthnPage emailType(String text){
        context.type(EMAIL,text);
        return this;
    }

    public WebAuthnPage registerClick(){
        context.click(REGISTER);
        return this;
    }

    public WebAuthnPage alertShouldHaveText(String text){
        context.withTimeout(20).wdWait().until(
                ExpectedConditions.textToBePresentInElementLocated(ALERT,text));
        return this;
    }

    public WebAuthnPage loginClick(){
        context.click(LOGIN);
        return this;
    }

    public WebAuthnPage mainContentShouldHaveText(String text){
        context.withTimeout(20).wdWait().until(
                ExpectedConditions.textToBePresentInElementLocated(MAIN_CONTENT,text));
        return this;
    }
}
