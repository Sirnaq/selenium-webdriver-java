package tests.ch05.locale;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.MultiLanguagePage;
import pages.TestContext;
import utils.Config;

import static org.assertj.core.api.Assumptions.assumeThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LocaleDefaultLangTest {
    TestContext context;
    String lang;

    @BeforeAll
    void setupClass(){
        assumeThat(Config.getConfig().getProperty("browserType")).isEqualTo("chrome");
    }

    @BeforeEach
    void setup() {
        lang = "es-ES";
        ChromeOptions options = new ChromeOptions();
        if (Config.isHeadless()) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--lang=" + lang);

        WebDriver driver = WebDriverManager.chromedriver().capabilities(options).create();
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
