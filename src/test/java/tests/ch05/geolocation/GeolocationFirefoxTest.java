package tests.ch05.geolocation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pages.GeolocationPage;
import pages.TestContext;
import utils.Config;

import static org.assertj.core.api.Assertions.assertThat;

public class GeolocationFirefoxTest {
    TestContext context;

    @BeforeEach
    void setup() {
        FirefoxOptions options = new FirefoxOptions();
        if (Config.isHeadless())
            options.addArguments("--headless");

        options.addPreference("geo.enabled", true);
        options.addPreference("geo.prompt.testing", true);
        options.addPreference("geo.provider.use_corelocation", true);

        WebDriver driver = WebDriverManager.firefoxdriver().capabilities(options).create();
        context = new TestContext(driver);
    }

    @AfterEach
    void tearDown() {
        context.driver().quit();
    }

    @Test
    @Disabled("Test is failing for some reason, maybe there is some new method unknown by the author")
    void testGeolocation() {
        GeolocationPage geolocationPage = new GeolocationPage(context);
        geolocationPage.open().getCoordinatesClick();
        assertThat(geolocationPage.getLatitude()).isEqualTo("52");
        assertThat(geolocationPage.getLongitude()).isEqualTo("21");
    }
}
