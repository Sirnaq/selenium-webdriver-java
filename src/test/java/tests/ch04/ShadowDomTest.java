package tests.ch04;

import base.TestBase;
import org.junit.jupiter.api.Test;
import pages.ShadowDomPage;

import static org.assertj.core.api.Assertions.assertThat;

public class ShadowDomTest extends TestBase {

    @Test
    void testShadowDom() {
        String textFromTheShadow = new ShadowDomPage(context).open().getTextFromShadowDom();
        assertThat(textFromTheShadow).contains("Hello Shadow DOM");
    }
}
