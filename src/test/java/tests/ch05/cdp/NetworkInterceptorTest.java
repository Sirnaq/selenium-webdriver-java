package tests.ch05.cdp;

import base.TestBase;
import org.junit.jupiter.api.Test;
import pages.HandsOnPage;

public class NetworkInterceptorTest extends TestBase {

    @Test
    void testNetworkInterceptor() {
        new HandsOnPage(context)
                .interceptImageReplaceWith("tools.png")
                .imgShouldHaveWidthGreaterThan(80);
    }
}
