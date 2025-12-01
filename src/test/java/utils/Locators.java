package utils;

import org.openqa.selenium.By;

public class Locators {

    public static By byExactText(String text) {
        return By.xpath(String.format("//*[normalize-space() = '%s']", text));
    }

    public static By byExactText(String tagName, String text) {
        return By.xpath(String.format("//%s[normalize-space() = '%s']", tagName, text));
    }
}
