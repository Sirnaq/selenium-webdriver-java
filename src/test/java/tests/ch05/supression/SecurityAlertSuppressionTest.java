package tests.ch05.supression;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.Color;
import pages.BadSslPage;
import pages.TestContext;
import utils.Config;

public class SecurityAlertSuppressionTest {

    private TestContext context;

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        if (Config.isHeadless()) {
            options.addArguments("--headless=new");
        }
        options.setAcceptInsecureCerts(true);
        WebDriver driver = WebDriverManager.chromedriver().capabilities(options).create();
        context = new TestContext(driver);
    }

    @AfterEach
    void tearDown() {
        context.driver().quit();
    }

    @Test
    void testChromeMultimedia() {
        Color red = new Color(255, 0, 0, 1);
        new BadSslPage(context).open()
                .bodyShouldHaveColor(red);
    }
}
