package tests.ch04;

import base.TestBase;
import org.junit.jupiter.api.Test;
import utils.Config;

import static org.assertj.core.api.Assertions.assertThat;

public class DriverHistoryTest extends TestBase {

    @Test
    void testHistoryNavigation() {
        String firstPage = Config.url("navigation1.html");
        String secondPage = Config.url("navigation2.html");
        String thirdPage = Config.url("navigation3.html");
        handsOnPage.open();
        context.driver().navigate().to(firstPage);
        context.driver().navigate().to(secondPage);
        context.driver().navigate().to(thirdPage);
        context.driver().navigate().back();
        context.driver().navigate().forward();
        context.driver().navigate().refresh();
        String currentUrl = context.driver().getCurrentUrl();
        context.log().debug("Current url is: {}", currentUrl);
        assertThat(currentUrl).isEqualTo(thirdPage);
    }
}