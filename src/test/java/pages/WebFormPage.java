package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.locators.RelativeLocator.RelativeBy;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.Select;
import utils.Config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.assertj.core.api.Assertions.assertThat;

public class WebFormPage {

    private final TestContext context;

    private static final By TEXT_INPUT = By.name("my-text");
    private static final By TEXT_INPUT_BY_ID = By.id("my-text-id");
    private static final By FORM_CLASS = By.className("form-control");
    private static final By TEXT_AREA = By.name("my-textarea");
    private static final By FILE_INPUT = By.name("my-file");
    private static final By FORM = By.tagName("form");
    private static final By EXAMPLE_RANGE = By.name("my-range");
    private static final By DATE = By.name("my-date");
    private static final By CHECKED_CHECK_BOX = By.cssSelector("[type=checkbox]:checked");
    private static final By UNCHECKED_CHECK_BOX = By.cssSelector("[type=checkbox]:not(:checked)");
    private static final By DEFAULT_CHECKBOX = By.id("my-check-2");
    private static final By DEFAULT_RADIO = By.id("my-radio-2");
    private static final By CHECKED_RADIO = By.xpath("//*[@type='radio' and @checked]");
    private static final By UNCHECKED_RADIO = By.xpath("//*[@type='radio' and not(@checked)]");
    private static final By RETURN_TO_INDEX = By.linkText("Return to index");
    private static final By RETURN_TO_INDEX_PARTIAL = By.partialLinkText("index");
    private static final By HIDDEN = By.xpath("//input[@type='hidden']");
    private static final By MY_FILE = new ByIdOrName("my-file");
    private static final By ROWS_IN_FORM = new ByChained(By.tagName("form"), By.className("row"));
    private static final By ALL_ROWS_AND_FORM = new ByAll(By.tagName("form"), By.className("row"));
    private static final By SELECT = By.name("my-select");
    private static final By DATA_LIST = By.name("my-datalist");
    private static final By DATA_LIST_OPTION_TWO = By.xpath("//datalist/option[2]");
    private static final By COLOR_PICKER = By.name("my-colors");
    private static final RelativeBy INPUT = RelativeLocator.with(By.tagName("input"));
    private static final RelativeBy TH = RelativeLocator.with(By.tagName("th"));
    private static final RelativeBy FOCUSED = RelativeLocator.with(By.cssSelector("span[class$=focused]"));

    public WebFormPage(TestContext context) {
        this.context = context;
    }

    public WebFormPage open() {
        context.driver().get(Config.url("web-form.html"));
        return this;
    }

    public WebFormPage textInputSendKeys(String input) {
        context.type(TEXT_INPUT, input);
        return this;
    }

    public WebFormPage copyFromTextInput() {
        context.actions()
                .keyDown(context.getModifier())
                .sendKeys(context.visible(TEXT_INPUT), "a")
                .sendKeys(context.visible(TEXT_INPUT), "c")
                .keyUp(context.getModifier())
                .build().perform();
        return this;
    }

    public WebFormPage copyToTextArea() {
        context.actions()
                .keyDown(context.getModifier())
                .sendKeys(context.visible(TEXT_AREA), "v")
                .keyUp(context.getModifier())
                .build().perform();
        return this;
    }

    public WebFormPage checkIfAreaAndInputValuesMatch() {
        assertThat(context.getValue(TEXT_AREA)).isEqualTo(context.getValue(TEXT_INPUT));
        return this;
    }

    public WebFormPage clearTextInput() {
        context.visible(TEXT_INPUT).clear();
        return this;
    }

    public WebFormPage checkThatTextInputValueIs(String expectedValue) {
        assertThat(context.getValue(TEXT_INPUT)).isEqualTo(expectedValue);
        return this;
    }

    public WebFormPage checkThatTextInputValueIsEmpty() {
        assertThat(context.getValue(TEXT_INPUT)).isEmpty();
        return this;
    }

    public WebFormPage fileInputSendKeys(String input) {
        context.type(FILE_INPUT, input);
        return this;
    }

    public WebFormPage formSubmit() {
        context.visible(FORM).submit();
        return this;
    }

    public WebFormPage checkThatCurrentUrlIsNotWebFormPage() {
        assertThat(context.driver().getCurrentUrl()).isNotEqualTo(Config.url("web-form.html"));
        return this;
    }

    public String logAndGetValueOfExampleRange() {
        String value = context.getValue(EXAMPLE_RANGE);
        context.log().debug("The value of the slider is: {}", value);
        return value;
    }

    public WebFormPage increaseValueOfExampleRange(int howManyTimes) {
        for (int i = 0; i < howManyTimes; i++) {
            context.visible(EXAMPLE_RANGE).sendKeys(Keys.ARROW_RIGHT);
        }
        return this;
    }

    public WebFormPage defaultCheckBoxClick() {
        context.click(DEFAULT_CHECKBOX);
        return this;
    }

    public WebFormPage defaultRadioClick() {
        context.click(DEFAULT_RADIO);
        return this;
    }

    public WebFormPage checkThatDefaultCheckBoxIsSelected() {
        assertThat(context.visible(DEFAULT_CHECKBOX).isSelected()).isTrue();
        return this;
    }

    public WebFormPage checkThatDefaultRadioIsSelected() {
        assertThat(context.visible(DEFAULT_RADIO).isSelected()).isTrue();
        return this;
    }

    public WebFormPage checkTextAreaDomAttributeEquals(String domAttributeName, String expectedValue) {
        assertThat(context.visible(TEXT_AREA).getDomAttribute(domAttributeName)).isEqualTo(expectedValue);
        return this;
    }

    public WebFormPage checkIfTextInputEnabled() {
        assertThat(context.visible(TEXT_INPUT).isEnabled()).isTrue();
        return this;
    }

    public WebFormPage checkIfTextInputThreeAttributesEqual(String expectedValue) {
        assertThat(context.visible(TEXT_INPUT_BY_ID).getAttribute("type")).isEqualTo(expectedValue);
        assertThat(context.visible(TEXT_INPUT_BY_ID).getDomAttribute("type")).isEqualTo(expectedValue);
        assertThat(context.visible(TEXT_INPUT_BY_ID).getDomProperty("type")).isEqualTo(expectedValue);
        return this;
    }

    public WebFormPage checkIfFirstFormControlElementHasName(String expectedName) {
        List<WebElement> formControlElements = context.driver().findElements(FORM_CLASS);
        assertThat(formControlElements.size()).isPositive();
        assertThat(formControlElements.getFirst().getAttribute("name")).isEqualTo(expectedName);
        return this;
    }

    public WebFormPage checkReturnToIndexTagNameAndCursor() {
        assertThat(context.visible(RETURN_TO_INDEX).getTagName()).isEqualTo("a");
        assertThat(context.visible(RETURN_TO_INDEX).getCssValue("cursor")).isEqualTo("pointer");
        return this;
    }

    public WebFormPage checkIfRectAndLocationMatchBetweenPartialAndExactText() {
        assertThat(context.visible(RETURN_TO_INDEX_PARTIAL).getRect())
                .isEqualTo(context.visible(RETURN_TO_INDEX).getRect());
        assertThat(context.visible(RETURN_TO_INDEX_PARTIAL).getLocation())
                .isEqualTo(context.visible(RETURN_TO_INDEX).getLocation());
        return this;
    }

    public WebFormPage checkIfIdOfTheFirstCheckBoxIs(String expectedId) {
        assertThat(context.visible(CHECKED_CHECK_BOX).getAttribute("id")).isEqualTo(expectedId);
        return this;
    }

    public WebFormPage checkIfFirstCheckBoxIsSelected() {
        assertThat(context.visible(CHECKED_CHECK_BOX).isSelected()).isTrue();
        return this;
    }

    public WebFormPage checkIfIdOfTheSecondCheckBoxIs(String expectedId) {
        assertThat(context.visible(UNCHECKED_CHECK_BOX).getAttribute("id")).isEqualTo(expectedId);
        return this;
    }

    public WebFormPage checkIfSecondCheckBoxIsNotSelected() {
        assertThat(context.visible(UNCHECKED_CHECK_BOX).isSelected()).isFalse();
        return this;
    }

    public WebFormPage checkIfHiddenElementIsNotDisplayed() {
        assertThat(context.present(HIDDEN).isDisplayed()).isFalse();
        return this;
    }

    public WebFormPage checkIfCheckedRadioIsSelectedAndHasId(String expectedId) {
        assertThat(context.visible(CHECKED_RADIO).isSelected()).isTrue();
        assertThat(context.visible(CHECKED_RADIO).getAttribute("id")).isEqualTo(expectedId);
        return this;
    }

    public WebFormPage checkIfUncheckedRadioIsNotSelectedAndHasId(String expectedId) {
        assertThat(context.visible(UNCHECKED_RADIO).isSelected()).isFalse();
        assertThat(context.visible(UNCHECKED_RADIO).getAttribute("id")).isEqualTo(expectedId);
        return this;
    }

    public WebFormPage checkIfMyFileIdIsBlankAndNameIsNotBlank() {
        assertThat(context.visible(MY_FILE).getAttribute("id")).isBlank();
        assertThat(context.visible(MY_FILE).getAttribute("name")).isNotBlank();
        return this;
    }

    public WebFormPage checkIfRowsInFormSizeIs(int expectedNumberOfRows) {
        assertThat(context.driver().findElements(ROWS_IN_FORM).size()).isEqualTo(expectedNumberOfRows);
        return this;
    }

    public WebFormPage checkIfAllRowsAndFormSizeIs(int expectedNumberOfRows) {
        assertThat(context.driver().findElements(ALL_ROWS_AND_FORM).size()).isEqualTo(expectedNumberOfRows);
        return this;
    }

    public WebFormPage checkIfRelativeLocatorHasName(String expectedName) {
        assertThat(context.visible(INPUT.above(context.visible(RETURN_TO_INDEX)))
                .getAttribute("name")).isEqualTo(expectedName);
        return this;
    }

    public WebFormPage dateClick() {
        context.click(DATE);
        return this;
    }

    public WebFormPage yearClick(int year) {
        context.click(By.xpath(String.format("//th[contains(text(),'%d')]", year)));
        return this;
    }

    public WebFormPage leftArrowClick(int year) {
        context.click(TH.toLeftOf(By.xpath(String.format("//*[text()='%d']", year))));
        return this;
    }

    public WebFormPage focusedMonthClick(int year) {
        context.click(FOCUSED.below(By.xpath(String.format("//*[text()='%d']", year - 1))));
        return this;
    }

    public WebFormPage currentDayClick(int day) {
        context.click(By.xpath(String.format("//td[@class='day' and contains(text(),'%d')]", day)));
        return this;
    }

    public WebFormPage logFinalDateAndCheckIfItMatches(String expectedDate) {
        String dateValue = context.getValue(DATE);
        context.log().debug("Final date in date picker: {}", dateValue);
        assertThat(dateValue).isEqualTo(expectedDate);
        return this;
    }

    public WebFormPage selectOption(String optionLabel) {
        getSelect().selectByVisibleText(optionLabel);
        return this;
    }

    public WebFormPage checkIfSelectValueIs(String expectedText) {
        assertThat(getSelect().getFirstSelectedOption().getText()).isEqualTo(expectedText);
        return this;
    }

    public WebFormPage dataListClick() {
        context.click(DATA_LIST);
        return this;
    }

    public WebFormPage dataListSetOptionTwo() {
        String optionTwoValue = context.getValue(DATA_LIST_OPTION_TWO);
        context.type(DATA_LIST, optionTwoValue);
        return this;
    }

    public WebFormPage checkIfDataListValueIs(String expectedValue) {
        assertThat(context.getValue(DATA_LIST)).isEqualTo(expectedValue);
        return this;
    }

    public String getColorPickerValue() {
        String value = context.getValue(COLOR_PICKER);
        context.log().debug("The color-picker value is {}", value);
        return value;
    }

    public WebFormPage changeColorPickerValue(Color color) {
        context.js().executeScript(String.format("""
                arguments[0].setAttribute('value','%s');
                """, color.asHex()), context.present(COLOR_PICKER));
        return this;
    }

    public Path takeFormScreenshot() {
        WebElement form = context.visible(FORM);
        File screenshot = form.getScreenshotAs(OutputType.FILE);
        Path destination = Paths.get("screenshots/partial-screenshot.png");
        try {
            Files.move(screenshot.toPath(), destination, REPLACE_EXISTING);
        } catch (IOException e) {
            context.log().error("Error while moving file {}", e.getMessage());
        }
        return destination;
    }

    private Select getSelect() {
        return new Select(context.visible(SELECT));
    }
}