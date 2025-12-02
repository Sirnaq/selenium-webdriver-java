package pages;

import org.openqa.selenium.By;
import utils.Config;
import utils.Locators;

public class GeolocationPage {

    private final TestContext context;

    private static final By GET_COORDINATES = Locators.byExactText("button", "Get coordinates");
    private static final By COORDINATES = By.id("coordinates");

    public GeolocationPage(TestContext context) {
        this.context = context;
    }

    public GeolocationPage open(){
        context.driver().get(Config.url("geolocation.html"));
        return this;
    }

    public GeolocationPage getCoordinatesClick(){
        context.click(GET_COORDINATES);
        return this;
    }

    public String getLatitude(){
        String coordinates = context.getText(COORDINATES);
        return coordinates.substring(10,12);
    }

    public String getLongitude(){
        String coordinates = context.getText(COORDINATES);
        return coordinates.substring(33,35);
    }
}
