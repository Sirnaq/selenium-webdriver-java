package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.Color;


import static org.assertj.core.api.Assertions.assertThat;

public class BadSslPage {

    private final TestContext context;

    private static final By BODY = By.tagName("body");

    public BadSslPage(TestContext context) {
        this.context = context;
    }

    public BadSslPage open() {
        context.driver().get("https://self-signed.badssl.com/");
        return this;
    }

    public BadSslPage bodyShouldHaveColor(Color color) {
        assertThat(Color.fromString(context.visible(BODY).getCssValue("background-color"))).isEqualTo(color);
        return this;
    }


}
