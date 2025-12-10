package pages;

import org.openqa.selenium.By;
import utils.Config;

public class LoginFormPage {

    private final TestContext context;

    private static final By USERNAME = By.id("username");
    private static final By PASSWORD = By.id("password");
    private static final By SUBMIT = By.cssSelector("button");
    private static final By BODY = By.tagName("body");

    public LoginFormPage(TestContext context) {
        this.context = context;
    }

    public LoginFormPage open() {
        context.driver().get(Config.url("login-form.html"));
        return this;
    }

    public LoginFormPage logIn(String username, String password) {
        context.type(USERNAME, username);
        context.type(PASSWORD, password);
        context.click(SUBMIT);
        return this;
    }

    public String getBodyText() {
        return context.getText(BODY);
    }
}
