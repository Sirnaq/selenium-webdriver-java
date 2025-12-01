package pages;

import utils.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MouseOverPage {

    private final TestContext context;

    public MouseOverPage(TestContext context) {
        this.context = context;
    }

    public MouseOverPage open() {
        context.driver().get(Config.url("mouse-over.html"));
        return this;
    }

    public MouseOverPage hoverOverEachAndCheckCaption(List<String> inputList) {
        for (String name : inputList) {
            By imageLocator = By.xpath(String.format("//img[@src='img/%s.png']", name));
            WebElement image = context.visible(imageLocator);
            context.actions().moveToElement(image).build().perform();
            WebElement caption = context.driver().findElement(RelativeLocator.with(By.tagName("div")).near(image));
            assertThat(caption.getText()).containsIgnoringCase(name);
        }
        return this;
    }
}
