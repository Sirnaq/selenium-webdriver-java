package tests.ch05.cdp;

import base.TestBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pages.HandsOnPage;
import utils.Config;

import static org.assertj.core.api.Assumptions.assumeThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NetworkInterceptorTest extends TestBase {

    @BeforeAll
    void setupClass() {
        assumeThat(Config.isRemote()).isFalse();
    }

    @Test
    void testNetworkInterceptor() {
        new HandsOnPage(context)
                .interceptImageReplaceWith("tools.png")
                .imgShouldHaveWidthGreaterThan(80);
    }
}
