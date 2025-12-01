package pages;

import utils.Config;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

public class DropdownMenuPage {

    private final TestContext context;

    private static final By USE_RIGHT_CLICK_HERE = By.id("my-dropdown-2");
    private static final By USE_RIGHT_CLICK_HERE_CONTEXT_MENU = By.id("context-menu-2");
    private static final By USE_DOUBLE_CLICK_HERE = By.id("my-dropdown-3");
    private static final By USE_DOUBLE_CLICK_HERE_CONTEXT_MENU = By.id("context-menu-3");

    public DropdownMenuPage(TestContext context) {
        this.context = context;
    }

    public DropdownMenuPage open() {
        context.driver().get(Config.url("dropdown-menu.html"));
        return this;
    }

    public DropdownMenuPage rightClickOnTheSecondMenu() {
        context.actions().contextClick(context.driver().findElement(USE_RIGHT_CLICK_HERE)).build().perform();
        return this;
    }

    public DropdownMenuPage checkIfTheSecondMenuIsDisplayed() {
        assertThat(context.driver().findElement(USE_RIGHT_CLICK_HERE_CONTEXT_MENU).isDisplayed()).isTrue();
        return this;
    }

    public DropdownMenuPage doubleClickOnTheThirdMenu() {
        context.actions().doubleClick(context.driver().findElement(USE_DOUBLE_CLICK_HERE)).build().perform();
        return this;
    }

    public DropdownMenuPage checkIfTheThirdMenuIsDisplayed() {
        assertThat(context.driver().findElement(USE_DOUBLE_CLICK_HERE_CONTEXT_MENU).isDisplayed()).isTrue();
        return this;
    }
}
