package tests.ch05.emulation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeOptions;
import pages.HandsOnPage;
import pages.TestContext;
import utils.Config;

import java.util.HashMap;
import java.util.Map;

public class PhoneEmulationAdvancedTest {

    TestContext context;

    @BeforeEach
    void setup() {
        EdgeOptions options = new EdgeOptions();
        Map<String, Object> mobileEmulation = new HashMap<>();
        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 360);
        deviceMetrics.put("height", 640);
        deviceMetrics.put("pixelRatio", 3.0);
        deviceMetrics.put("touch", true);
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla /5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP4OD) "
                + "AppleWebKit/535.19 (KHTML, like Gecko) "
                + "Chrome/18.0.1025.166 Mobile Safari/535.19");
        options.setExperimentalOption("mobileEmulation", mobileEmulation);
        if(Config.isHeadless()){
            options.addArguments("--headless=new");
        }
        WebDriver driver = WebDriverManager.edgedriver().capabilities(options).create();
        context = new TestContext(driver);
    }

    @AfterEach
    void tearDown() {
        context.driver().quit();
    }

    @Test
    void testPhoneEmulation() {
        new HandsOnPage(context).open()
                .checkIfPageTitleIs("Hands-On Selenium WebDriver with Java");
    }
}
