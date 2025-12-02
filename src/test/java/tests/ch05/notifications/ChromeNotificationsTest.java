package tests.ch05.notifications;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.NotificationsPage;
import pages.TestContext;
import utils.Config;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ChromeNotificationsTest {

    TestContext context;

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        if (Config.isHeadless()) {
            options.addArguments("--headless=new");
        }
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 1);
        options.setExperimentalOption("prefs", prefs);
        WebDriver driver = WebDriverManager.chromedriver().capabilities(options).create();
        context = new TestContext(driver);
    }

    @AfterEach
    void tearDown() {
        context.driver().quit();
    }

    @Test
    void testChromeNotification() {
        new NotificationsPage(context).open()
                .notifyMeClick();
    }

    @Test
    void jsNotification() {
        NotificationsPage notificationsPage = new NotificationsPage(context).open();
        String notificationTitle = notificationsPage.interceptNotification();
        assertThat(notificationTitle).isEqualTo("This is a notification");

    }
}
