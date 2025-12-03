package tests.ch05.incognito;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.HandsOnPage;
import pages.TestContext;
import utils.Config;

public class IncognitoChromeTest {

    TestContext context;

    @BeforeEach
    void setup(){
        ChromeOptions options = new ChromeOptions();
        if(Config.isHeadless()){
            options.addArguments("--headless=new");
        }
        options.addArguments("--incognito");
        WebDriver driver = WebDriverManager.chromedriver().capabilities(options).create();
        context = new TestContext(driver);
    }

    @AfterEach
    void tearDown(){
        context.driver().quit();
    }

    @Test
    void testBinary() {
        new HandsOnPage(context).open()
                .checkIfPageTitleIs("Hands-On Selenium WebDriver with Java");
    }
}
