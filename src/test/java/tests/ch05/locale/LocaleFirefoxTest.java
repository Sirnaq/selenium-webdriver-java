package tests.ch05.locale;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import pages.MultiLanguagePage;
import pages.TestContext;
import utils.Config;

import java.util.HashMap;
import java.util.Map;

public class LocaleFirefoxTest {
    TestContext context;
    String lang;

    @BeforeEach
    void setup() {
        lang = "es-ES";
        FirefoxOptions options = new FirefoxOptions();
        if (Config.isHeadless()) {
            options.addArguments("--headless");
        }
        options.addPreference("intl.accept_languages", lang);

        WebDriver driver = WebDriverManager.firefoxdriver().capabilities(options).create();
        context = new TestContext(driver);
    }

    @AfterEach
    void tearDown() {
        context.driver().quit();
    }

    @Test
    void testChromeLogging() {
        new MultiLanguagePage(context).open()
                .bodyShouldHaveText(lang);
    }
}
