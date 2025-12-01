package tests.ch05;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.HandsOnPage;
import pages.TestContext;
import utils.Config;

import java.util.HashMap;
import java.util.Map;

public class PhoneEmulationTest {

    TestContext context;

    @BeforeEach
    void setup(){
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "iPhone 14 Pro Max");
        options.setExperimentalOption("mobileEmulation", mobileEmulation);
        if(Config.isHeadless()){
            options.addArguments("--headless=new");
        }
        WebDriver driver = WebDriverManager.chromedriver().capabilities(options).create();
        context = new TestContext(driver);
    }

    @AfterEach
    void tearDown(){
        context.driver().quit();
    }

    @Test
    void testPhoneEmulation(){
        new HandsOnPage(context).open()
                .checkIfPageTitleIs("Hands-On Selenium WebDriver with Java");
    }
}
