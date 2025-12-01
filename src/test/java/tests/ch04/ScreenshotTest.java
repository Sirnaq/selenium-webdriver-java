package tests.ch04;

import base.TestBase;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class ScreenshotTest extends TestBase {

    @Test
    void testScreenshot() {
        Path destination = handsOnPage.open().takeScreenshot();
        assertThat(destination).exists();
    }

    @Test
    void testScreenshotBase64() {
        handsOnPage.open().takeScreenshotInBase64();
    }

    @Test
    void testPartialScreenshot() {
        Path destination = webFormPage.open().takeFormScreenshot();
        assertThat(destination).exists();
    }
}
