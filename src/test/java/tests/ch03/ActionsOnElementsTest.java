package tests.ch03;

import base.TestBase;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Point;
import utils.FileUtil;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ActionsOnElementsTest extends TestBase {

    @Test
    void testSendKeys() {
        final String TEST_INPUT_STRING = "Hello World";
        webFormPage.open().textInputSendKeys(TEST_INPUT_STRING)
                .checkThatTextInputValueIs(TEST_INPUT_STRING)
                .clearTextInput()
                .checkThatTextInputValueIsEmpty();
    }

    @Test
    void testUploadFile() {
        FileUtil fileUtil = new FileUtil("tempfiles", ".tmp");
        String filename = fileUtil.getAbsolutePath();
        fileUtil.logFileName();

        webFormPage.open().fileInputSendKeys(filename)
                .formSubmit()
                .checkThatCurrentUrlIsNotWebFormPage();
    }

    @Test
    void testSlider() {
        String initValue = webFormPage.open().logAndGetValueOfExampleRange();
        String endValue = webFormPage.increaseValueOfExampleRange(5).logAndGetValueOfExampleRange();
        assertThat(initValue).isNotEqualTo(endValue);
    }

    @Test
    void testNavigation() {
        handsOnPage.open()
                .navigationClick()
                .nextClick()
                .threeClick()
                .twoClick()
                .previousClick()
                .checkThatBodyContains("Lorem ipsum");
    }

    @Test
    void testRadioButtonsAndCheckboxes() {
        webFormPage.open().defaultCheckBoxClick()
                .checkThatDefaultCheckBoxIsSelected()
                .defaultRadioClick()
                .checkThatDefaultRadioIsSelected();
    }

    @Test
    void testContextAndDoubleClick() {
        dropdownMenuPage.open()
                .rightClickOnTheSecondMenu()
                .checkIfTheSecondMenuIsDisplayed()
                .doubleClickOnTheThirdMenu()
                .checkIfTheThirdMenuIsDisplayed();
    }

    @Test
    void testMouseOver() {
        List<String> imageList = Arrays.asList("compass", "calendar", "award", "landscape");
        mouseOverPage.open().hoverOverEachAndCheckCaption(imageList);
    }

    @Test
    void testDragAndDrop() {
        int offset = 100;
        Point initLocation = dragAndDropPage.open().getDraggableLocation();
        Point finalLocation = dragAndDropPage
                .dragAndDropDraggable(offset, 0)
                .dragAndDropDraggable(0, offset)
                .dragAndDropDraggable(-offset, 0)
                .dragAndDropDraggable(0, -offset)
                .getDraggableLocation();
        assertThat(finalLocation).isEqualTo(initLocation);
        dragAndDropPage.dragDraggableToTarget();
        assertThat(dragAndDropPage.getDraggableLocation()).isEqualTo(dragAndDropPage.getTargetLocation());
    }

    @Test
    void testClickAndHold() {
        drawInCanvasPage.open().drawCircle(10, 15);
    }

    @Test
    void testCopyAndPaste() {
        webFormPage.open().textInputSendKeys("Hellow World")
                .copyFromTextInput()
                .copyToTextArea()
                .checkIfAreaAndInputValuesMatch();
    }

    /**
     * Implicit wait not recommended
     **/
    @Test
    void testImplicitWait() {
        loadingImagesPage.open()
                .setImplicitWaitTo(Duration.ofSeconds(10))
                .checkIfLandscapeLinkContains("landscape")
                .resetImplicitWait();
    }

    /**
     * Explicit wait - recommended
     **/
    @Test
    void testExplicitWait() {
        loadingImagesPage.open()
                .waitForLandscape(10)
                .checkIfLandscapeLinkContains("landscape");
    }

    @Test
    void testSlowCalculator() {
        slowCalculatorPage.open()
                .pressOne()
                .pressPlus()
                .pressThree()
                .pressEquals()
                .waitUntilScreenEquals("4");
    }

    /**
     * Fluent wait
     **/
    @Test
    void testFluentWait() {
        loadingImagesPage.open()
                .fluentWaitForLandscape()
                .checkIfLandscapeLinkContains("landscape");
    }
}
