package tests.ch04;

import base.TestBase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pages.HandsOnPage;
import pages.WebFormPage;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class ScreenshotTest extends TestBase {

    @Test
    @Disabled("Doesn't work on CI, problem with writing file seems like")
    void testScreenshot() {
        Path destination = new HandsOnPage(context).open().takeScreenshot();
        assertThat(destination).exists();
    }

    @Test
    void testScreenshotBase64() {
        new HandsOnPage(context).open().takeScreenshotInBase64();
    }

    @Test
    @Disabled("Doesn't work on CI, problem with writing file seems like")
    void testPartialScreenshot() {
        Path destination = new WebFormPage(context).open().takeFormScreenshot();
        assertThat(destination).exists();
    }
}
