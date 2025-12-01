package tests.ch03;

import base.TestBase;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class FindingWebElementsTest extends TestBase {

    @Test
    void testByTagName() {
        webFormPage.open()
                .checkTextAreaDomAttributeEquals("rows", "3");
    }

    @Test
    void testByHtmlAttributes() {
        webFormPage.open()
                .checkIfTextInputEnabled()                                       //po nazwie
                .checkIfTextInputThreeAttributesEqual("text")       //po identyfikatorze
                .checkIfFirstFormControlElementHasName("my-text");  //Po nazwie klasy
    }

    @Test
    void testByLinkText() {
        webFormPage.open()
                .checkReturnToIndexTagNameAndCursor()
                .checkIfRectAndLocationMatchBetweenPartialAndExactText();
    }

    @Test
    void testByCssSelectorBasic() {
        webFormPage.open();
        WebElement hidden = context.driver().findElement(By.cssSelector("input[type=hidden]"));
        assertThat(hidden.isDisplayed()).isFalse();
    }

    @Test
    void testByCssSelectorAdvanced() {
        webFormPage.open()
                .checkIfIdOfTheFirstCheckBoxIs("my-check-1")
                .checkIfFirstCheckBoxIsSelected()
                .checkIfIdOfTheSecondCheckBoxIs("my-check-2")
                .checkIfSecondCheckBoxIsNotSelected();
    }

    @Test
    void testByXpathBasic() {
        webFormPage.open().checkIfHiddenElementIsNotDisplayed();
    }

    @Test
    void testByXpathAdvanced() {
        webFormPage.open()
                .checkIfCheckedRadioIsSelectedAndHasId("my-radio-1")
                .checkIfUncheckedRadioIsNotSelectedAndHasId("my-radio-2");
    }

    @Test
    void testByIdOrName() {
        webFormPage.open().checkIfMyFileIdIsBlankAndNameIsNotBlank();
    }

    @Test
    void testByChained() {
        webFormPage.open().checkIfRowsInFormSizeIs(1);
    }

    @Test
    void testByAll() {
        webFormPage.open().checkIfAllRowsAndFormSizeIs(5);
    }

    @Test
    void testRelativeLocators() {
        webFormPage.open().checkIfRelativeLocatorHasName("my-readonly");
    }

    @Test
    void testDatePicker() {
        webFormPage.open();
        LocalDate today = LocalDate.now(); //get current date from system clock
        int currentYear = today.getYear();
        int currentDay = today.getDayOfMonth();
        webFormPage.dateClick()         //click to open calendar
                .yearClick(currentYear)         //click on current month found based on text
                .leftArrowClick(currentYear)    //click on left arrow
                .focusedMonthClick(currentYear) //Click on the same month last year
                .currentDayClick(currentDay);   //click on current day
        LocalDate previousYear = today.minusYears(1);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String expectedDate = previousYear.format(dateFormat);
        webFormPage.logFinalDateAndCheckIfItMatches(expectedDate);//get final day from input
    }
}