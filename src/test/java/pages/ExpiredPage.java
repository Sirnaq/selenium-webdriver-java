package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.Color;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpiredPage {

    private final TestContext context;

    private static final By BODY = By.tagName("body");

    public ExpiredPage(TestContext context) {
        this.context = context;
    }

    public ExpiredPage open(){
        context.driver().get("https://expired.badssl.com/");
        return this;
    }

    public ExpiredPage bodyShouldHaveColor(Color color){
        String bodyColor = context.visible(BODY).getCssValue("background-color");
        assertThat(Color.fromString(bodyColor)).isEqualTo(color);
        return this;
    }
}
