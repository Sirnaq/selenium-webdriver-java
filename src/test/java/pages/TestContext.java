package pages;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DriverFactory;

import java.time.Duration;

import static java.lang.invoke.MethodHandles.lookup;

public class TestContext {

    private final WebDriver driver;
    private final JavascriptExecutor jsExecutor;
    private final Actions actions;
    private final TakesScreenshot takesScreenshot;
    private final Logger log;
    private volatile WebDriverWait wait;
    private final Wait<WebDriver> fluentWait;
    private final Options options;

    public TestContext() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.jsExecutor = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
        this.takesScreenshot = (TakesScreenshot) driver;
        this.log = LoggerFactory.getLogger(lookup().lookupClass());
        this.fluentWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(500)).ignoring(NoSuchElementException.class);
        this.options = driver.manage();
    }

    public TestContext(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.jsExecutor = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
        this.takesScreenshot = (TakesScreenshot) driver;
        this.log = LoggerFactory.getLogger(lookup().lookupClass());
        this.fluentWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(500)).ignoring(NoSuchElementException.class);
        this.options = driver.manage();
    }

    public WebDriver driver() {
        return driver;
    }

    public WebDriverWait wdWait() {
        return wait;
    }

    public JavascriptExecutor js() {
        return jsExecutor;
    }

    public Actions actions() {
        return actions;
    }

    public TakesScreenshot screenshot() {
        return takesScreenshot;
    }

    public Logger log() {
        return log;
    }

    public Wait<WebDriver> fluentWait() {
        return fluentWait;
    }

    public Options options(){
        return options;
    }

    public TestContext withTimeout(int timeoutInSeconds) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return this;
    }

    public TestContext withDefaultTimeout() {
        return withTimeout(15);
    }

    public WebElement clickable(By locator) {
        return wdWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement visible(By locator) {
        return wdWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement present(By locator) {
        return wdWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public Boolean untilUrlContains(String expectedValue) {
        return wdWait().until(ExpectedConditions.urlContains(expectedValue));
    }

    public void click(By locator) {
        clickable(locator).click();
    }

    public void type(By locator, String content) {
        WebElement element = visible(locator);
        element.clear();
        element.sendKeys(content);
    }

    public String getValue(By locator) {
        return present(locator).getAttribute("value");
    }

    public String getText(By locator) {
        return visible(locator).getText();
    }

    public Keys getModifier() {
        return SystemUtils.IS_OS_MAC ? Keys.COMMAND : Keys.CONTROL;
    }
}
