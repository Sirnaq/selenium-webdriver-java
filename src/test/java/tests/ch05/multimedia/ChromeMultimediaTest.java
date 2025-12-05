package tests.ch05.multimedia;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.GetUserMediaPage;
import pages.TestContext;
import utils.Config;

public class ChromeMultimediaTest {

    private TestContext context;

    @BeforeEach
    void setup(){
        ChromeOptions options = new ChromeOptions();
        if(Config.isHeadless()){
            options.addArguments("--headless=new");
        }
        options.addArguments("--use-fake-ui-for-media-stream");
        options.addArguments("--use-fake-device-for-media-stream");
        WebDriver driver = WebDriverManager.chromedriver().capabilities(options).create();
        context = new TestContext(driver);
    }

    @AfterEach
    void tearDown(){
        context.driver().quit();
    }

    @Test
    void testChromeMultimedia(){
        new GetUserMediaPage(context).open()
                .startClick()
                .waitForVideoDevice();
    }
}
