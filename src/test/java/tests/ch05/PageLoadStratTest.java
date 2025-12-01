package tests.ch05;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.HandsOnPage;
import pages.TestContext;
import utils.Config;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class PageLoadStratTest {
    TestContext context;
    PageLoadStrategy pageLoadStrategy;

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        if(Config.isHeadless()){
            options.addArguments("--headless=new");
        }
        pageLoadStrategy = PageLoadStrategy.NORMAL;
        options.setPageLoadStrategy(pageLoadStrategy);
        WebDriver driver = WebDriverManager.chromedriver().capabilities(options).create();
        context = new TestContext(driver);
    }

    @AfterEach
    void tearDown() {
        context.driver().quit();
    }

    @Test
    void testPageLoad() {
        Long initMillis = System.currentTimeMillis();
        new HandsOnPage(context).open();
        Duration elapsed = Duration.ofMillis(System.currentTimeMillis() - initMillis);
        Capabilities capabilities = ((RemoteWebDriver) context.driver()).getCapabilities();
        Object pageLoad = capabilities.getCapability(CapabilityType.PAGE_LOAD_STRATEGY);
        String browserName = capabilities.getBrowserName();
        context.log().debug("The page took {} ms to be loaded using a '{}' strategy in {}",
                elapsed.toMillis(), pageLoad, browserName);
        assertThat(pageLoad).isEqualTo(pageLoadStrategy.toString());
    }
}
