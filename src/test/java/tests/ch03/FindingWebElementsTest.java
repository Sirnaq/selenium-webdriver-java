package tests.ch03;

import base.TestBase;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.WebFormPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class FindingWebElementsTest extends TestBase {

    @Test
    void testByTagName() {
        new WebFormPage(context).open()
                .checkTextAreaDomAttributeEquals("rows", "3");
    }

    @Test
    void testByHtmlAttributes() {
        new WebFormPage(context).open()
                .checkIfTextInputEnabled()                                       //po nazwie
                .checkIfTextInputThreeAttributesEqual("text")       //po identyfikatorze
                .checkIfFirstFormControlElementHasName("my-text");  //Po nazwie klasy
    }

    @Test
    void testByLinkText() {
        new WebFormPage(context).open()
                .checkReturnToIndexTagNameAndCursor()
                .checkIfRectAndLocationMatchBetweenPartialAndExactText();
    }

    @Test
    void testByCssSelectorBasic() {
        new WebFormPage(context).open();
        WebElement hidden = context.driver().findElement(By.cssSelector("input[type=hidden]"));
        assertThat(hidden.isDisplayed()).isFalse();
    }

    @Test
    void testByCssSelectorAdvanced() {
        new WebFormPage(context).open()
                .checkIfIdOfTheFirstCheckBoxIs("my-check-1")
                .checkIfFirstCheckBoxIsSelected()
                .checkIfIdOfTheSecondCheckBoxIs("my-check-2")
                .checkIfSecondCheckBoxIsNotSelected();
    }

    @Test
    void testByXpathBasic() {
        new WebFormPage(context).open().checkIfHiddenElementIsNotDisplayed();
    }

    @Test
    void testByXpathAdvanced() {
        new WebFormPage(context).open()
                .checkIfCheckedRadioIsSelectedAndHasId("my-radio-1")
                .checkIfUncheckedRadioIsNotSelectedAndHasId("my-radio-2");
    }

    @Test
    void testByIdOrName() {
        new WebFormPage(context).open().checkIfMyFileIdIsBlankAndNameIsNotBlank();
    }

    @Test
    void testByChained() {
        new WebFormPage(context).open().checkIfRowsInFormSizeIs(1);
    }

    @Test
    void testByAll() {
        new WebFormPage(context).open().checkIfAllRowsAndFormSizeIs(5);
    }

    @Test
    void testRelativeLocators() {
        new WebFormPage(context).open().checkIfRelativeLocatorHasName("my-readonly");
    }

    @Test
    void testDatePicker() {
        WebFormPage webFormPage = new WebFormPage(context);
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