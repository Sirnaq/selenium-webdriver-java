package tests.ch04;

import base.TestBase;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.Color;
import pages.HandsOnPage;
import pages.InfiniteScrollPage;
import pages.LongPage;
import pages.WebFormPage;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaScriptExecutionerTest extends TestBase {

    @Test
    void testScrollBy() {
        String scrollValue = "1000";
        new LongPage(context).open()
                .waitForTextToLoad()
                .scrollBy(scrollValue);
    }

    @Test
    void scrollIntoView() {
        new LongPage(context).open()
                .scrollToLastParagraph();
    }

    @Test
    void infiniteScroll() {
        new InfiniteScrollPage(context).open()
                .waitForParagraphsToLoad()
                .scrollToTheLastParagraphLoaded();
    }

    @Test
    void testColorPicker() {
        Color red = new Color(255, 0, 0, 1);
        WebFormPage webFormPage = new WebFormPage(context);

        String initColor = webFormPage.open().getColorPickerValue();
        String finalColor = webFormPage.changeColorPickerValue(red).getColorPickerValue();

        assertThat(finalColor).isNotEqualTo(initColor);
        assertThat(Color.fromString(finalColor)).isEqualTo(red);
    }

    /**
     * Pinned scripts tests are flaky, don't make such tests
     **/
    @Test
    void testPinnedScripts() {
        String message = "Hello World!";

        new HandsOnPage(context).open()
                .pinScripts()
                .clickGithubLinkByJS()
                .checkIfPageURLChanged()
                .executeSecondPinnedScript(message)
                .unpinFirstScript()
                .checkIfPinnedScriptsSizeIs(1);
    }

    @Test
    void testAsyncScript() {
        new HandsOnPage(context).open()
                .executeAsyncScriptExample();
    }
}
