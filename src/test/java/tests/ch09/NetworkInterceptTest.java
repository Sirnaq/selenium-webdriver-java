package tests.ch09;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.proxy.CaptureType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pages.HandsOnPage;
import pages.TestContext;
import utils.Config;

import java.util.List;

@Epic("Independent tools")
@Feature("Interception")
public class NetworkInterceptTest {

    private BrowserMobProxy proxy;
    private TestContext context;

    @BeforeEach
    void setup() {
        proxy = new BrowserMobProxyServer();
        proxy.start();
        proxy.newHar();
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        FirefoxOptions options = new FirefoxOptions();
        options.setProxy(seleniumProxy);
        options.setAcceptInsecureCerts(true);
        if (Config.isHeadless()) {
            options.addArguments("--headless");
        }
        WebDriver driver = WebDriverManager.firefoxdriver().capabilities(options).create();
        context = new TestContext(driver);
    }

    @AfterEach
    void tearDown() {
        proxy.stop();
        context.driver().quit();
    }

    @Test
    @Story("Starting proxy network interception server")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test shows how to intercept the communication between back end and front end using selenium and proxy server")
    @Link(name = "Test Capture Network Traffic Firefox", url = "https://bonigarcia.dev/selenium-webdriver-java/")
    void testCaptureNetworkTrafficFirefox() {
        new HandsOnPage(context).open().checkIfPageTitleIs("Hands-On Selenium WebDriver with Java");
        List<HarEntry> logEntries = proxy.getHar().getLog().getEntries();
        logEntries.forEach(logEntry -> {
            context.log().debug("Request: {}, Response: {}",
                    logEntry.getRequest().getUrl(),
                    logEntry.getResponse().getStatus());
        });
    }
}
