package tests.ch05;

import base.TestBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.GeolocationPage;
import utils.Config;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChromeLocationTest extends TestBase {

    @BeforeAll
    void setupClass(){
        assumeThat(Config.getConfig().getProperty("browserType")).isEqualTo("chrome");
    }

    @Test
    void testLocationContext(){
        Map<String,Object> location = new HashMap<>();
        location.put("latitude", 27.5916);
        location.put("longitude",86.5640);
        location.put("accuracy", 100);
        ((ChromeDriver)context.driver()).executeCdpCommand("Emulation.setGeolocationOverride",location);

        GeolocationPage geolocationPage = new GeolocationPage(context).open().getCoordinatesClick();
        assertThat(geolocationPage.getLatitude()).isEqualTo("27");
        assertThat(geolocationPage.getLongitude()).isEqualTo("86");
    }

}
