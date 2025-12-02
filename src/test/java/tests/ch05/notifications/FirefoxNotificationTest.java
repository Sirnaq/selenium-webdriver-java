package tests.ch05.notifications;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pages.NotificationsPage;
import pages.TestContext;
import utils.Config;

import static org.assertj.core.api.Assertions.assertThat;

public class FirefoxNotificationTest {

    TestContext context;

    @BeforeEach
    void setup() {
        FirefoxOptions options = new FirefoxOptions();
        if (Config.isHeadless()) {
            options.addArguments("--headless");
        }
        options.addPreference("permissions.default.desktop-notification", 1);

        WebDriver driver = WebDriverManager.firefoxdriver().capabilities(options).create();
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
