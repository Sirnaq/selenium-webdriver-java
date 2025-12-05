package tests.ch05.geolocation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.GeolocationPage;
import pages.TestContext;
import utils.Config;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GeolocationChromeTest {

    TestContext context;

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        if (Config.isHeadless())
            options.addArguments("--headless=new");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.geolocation",1);
        options.setExperimentalOption("prefs",prefs);
        WebDriver driver = WebDriverManager.chromedriver().capabilities(options).create();
        context = new TestContext(driver);
    }

    @AfterEach
    void tearDown(){
        context.driver().quit();
    }

    @Test
    void testGeolocation(){
        GeolocationPage geolocationPage =new GeolocationPage(context);
        geolocationPage.open().getCoordinatesClick();
        assertThat(geolocationPage.getLatitude()).isEqualTo("52");
        assertThat(geolocationPage.getLongitude()).isEqualTo("21");
    }
}
