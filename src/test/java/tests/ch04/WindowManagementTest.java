package tests.ch04;

import base.TestBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver.Window;
import pages.HandsOnPage;
import utils.Config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WindowManagementTest extends TestBase {

    @BeforeAll
    void setupAll(){
        assumeThat(Config.isHeadless()).isFalse();
    }

    @Test
    void testWindow() {
        new HandsOnPage(context).open();
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
