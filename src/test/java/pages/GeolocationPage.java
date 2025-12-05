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
        return extractCoordinate(coordinates,"Latitude");
    }

    public String getLongitude(){
        String coordinates = context.getText(COORDINATES);
        return extractCoordinate(coordinates,"Longitude");
    }

    private static String extractCoordinate(String text, String key) {
        String pattern = key + ":\\s*(\\d+(?=\\.|°))";
        java.util.regex.Pattern r = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = r.matcher(text);
        if (m.find()) {
            return m.group(1);
        }
        throw new IllegalArgumentException("Nie znaleziono współrzędnej: " + key);
    }
}
