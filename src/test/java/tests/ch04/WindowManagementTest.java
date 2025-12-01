package tests.ch04;

import base.TestBase;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver.Window;

import static org.assertj.core.api.Assertions.assertThat;

public class WindowManagementTest extends TestBase {

    @Test
    void testWindow() {
        handsOnPage.open();
        Window window = context.options().window();
        Point initialPosition = window.getPosition();
        Dimension initialSize = window.getSize();

        context.log().debug("Initial window: position {} -- size {}", initialPosition, initialSize);

        window.maximize();

        Point maximizedPosition = window.getPosition();
        Dimension maximizedSize = window.getSize();
        context.log().debug("Maximized window: position {} -- size {}", maximizedPosition, maximizedSize);

        assertThat(maximizedPosition).isNotEqualTo(initialPosition);
        assertThat(maximizedSize).isNotEqualTo(initialSize);
    }
}
