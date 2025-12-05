package tests.ch05.cdp;

import base.TestBase;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.events.ConsoleEvent;
import org.openqa.selenium.devtools.v142.dom.model.Rect;
import org.openqa.selenium.devtools.v142.emulation.Emulation;
import org.openqa.selenium.devtools.v142.network.Network;
import org.openqa.selenium.devtools.v142.network.model.*;
import org.openqa.selenium.devtools.v142.page.Page;
import org.openqa.selenium.devtools.v142.page.model.Viewport;
import org.openqa.selenium.devtools.v142.performance.Performance;
import org.openqa.selenium.devtools.v142.performance.model.Metric;
import org.openqa.selenium.devtools.v142.security.Security;
import org.openqa.selenium.support.Color;
import pages.*;
import utils.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChromeDevToolsProtocolTest extends TestBase {

    DevTools devTools;

    @BeforeAll
    void setupClass() {
        assumeThat(Config.getConfig().getProperty("browserType")).isEqualTo("chrome");
    }

    @BeforeEach
    void setup() {
        devTools = ((ChromeDriver) context.driver()).getDevTools();
        devTools.createSession();
    }

    @AfterEach
    void tearDown() {
        devTools.close();
    }

    @Test
    void testNetworkConditions() {
        NetworkConditions conditions = new NetworkConditions(
                "*",                             // urlPattern – "*" oznacza wszystkie requesty
                100.0,                                    // latency w ms (Double)
                1024.0,                                   // downloadThroughput w bajtach/s (50 kbps)
                1024.0,                                   // uploadThroughput w bajtach/s (50 kbps)
                Optional.of(ConnectionType.CELLULAR3G),   // typ połączenia: 3G
                Optional.empty(),                         // packetLoss – nie używamy
                Optional.empty(),                         // packetQueueLength – nie używamy
                Optional.empty()                          // packetReordering – nie używamy
        );

        // Lista z jedną regułą (można dodać więcej)
        List<NetworkConditions> matchedConditions = List.of(conditions);

        devTools.send(Network.enable(Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));
        devTools.send(Network.emulateNetworkConditionsByRule(false, matchedConditions));

        Long initMilis = System.currentTimeMillis();
        HandsOnPage handsOnPage = new HandsOnPage(context).open();
        Duration elapsed = Duration.ofMillis(System.currentTimeMillis() - initMilis);
        context.log().debug("The page took {} ms tobe loaded", elapsed.toMillis());

        handsOnPage.checkIfPageTitleIs("Hands-On Selenium WebDriver with Java");
    }

    @Test
    void testNetworkMonitoring() {
        devTools.send(Network.enable(Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));

        devTools.addListener(Network.requestWillBeSent(), request -> {
            context.log().debug("Request {}", request.getRequestId());
            context.log().debug("\t Method: {}", request.getRequest().getMethod());
            context.log().debug("\t URL: {}", request.getRequest().getUrl());
            logHeaders(request.getRequest().getHeaders());
        });

        devTools.addListener(Network.responseReceived(), response -> {
            context.log().debug("Response {}", response.getRequestId());
            context.log().debug("\t URL: {}", response.getResponse().getUrl());
            context.log().debug("\t Status: {}", response.getResponse().getStatus());
            logHeaders(response.getResponse().getHeaders());
        });

        new HandsOnPage(context).open()
                .checkIfPageTitleIs("Hands-On Selenium WebDriver with Java");
    }

    @Test
    void testFullPageScreenshotChrome() {
        new LongPage(context).open().waitForTextToLoad();

        Page.GetLayoutMetricsResponse metrics = devTools.send(Page.getLayoutMetrics());
        Rect contentSize = metrics.getContentSize();
        String screenshotBase64 = devTools.send(Page.captureScreenshot(
                Optional.empty(),
                Optional.empty(),
                Optional.of(new Viewport(0, 0, contentSize.getWidth(), contentSize.getHeight(), 1)),
                Optional.empty(),
                Optional.of(true),
                Optional.empty()
        ));
        Path destination = Paths.get("screenshots/fullpage-screenshot-chrome.png");
        try {
            Files.write(destination, Base64.getDecoder().decode(screenshotBase64));

        } catch (IOException e) {
            throw new RuntimeException("something went wrong during creating image file", e);
        }
        assertThat(destination).exists();
    }

    @Test
    void testPerformanceMetrics() {
        devTools.send(Performance.enable(Optional.empty()));
        new HandsOnPage(context).open();
        List<Metric> metrics = devTools.send(Performance.getMetrics());
        assertThat(metrics).isNotEmpty();
        metrics.forEach(metric -> context.log().debug("{}: {}", metric.getName(), metric.getValue()));
    }

    @Test
    void testExtraHeaders() {
        devTools.send(Network.enable(Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));

        String userName = "guest";
        String password = "guest";
        Map<String, Object> headers = new HashMap<>();
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(
                String.format("%s:%s", userName, password).getBytes()));
        headers.put("Authorization", basicAuth);
        devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));

        new JigsawPage(context).open()
                .bodyShouldHaveText("Odblokuj challenges.cloudflare.com, aby kontynuować.");
    }

    @Test
    void testBlockUrl() {
        devTools.send(Network.enable(Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));

        String urlToBlock = Config.url("img/hands-on-icon.png");
        devTools.send(Network.setBlockedURLs(ImmutableList.of(urlToBlock)));
        devTools.addListener(Network.loadingFailed(), loadingFailed -> {
            BlockedReason reason = loadingFailed.getBlockedReason().get();
            assertThat(reason).isEqualTo(BlockedReason.INSPECTOR);
        });

        new HandsOnPage(context).open()
                .checkIfPageTitleIs("Hands-On Selenium WebDriver with Java");
    }

    @Test
    void testDeviceEmulation() {
        String userAgent = "Mozilla 5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X)"
                + "AppleWebKit/600.1.3 (KHTML, like Gecko)"
                + "Version/8.0 Mobile/12A4345d Safari/600.1.4";
        devTools.send(Network.setUserAgentOverride(userAgent,
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));
        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 375);
        deviceMetrics.put("height", 667);
        deviceMetrics.put("mobile", true);
        deviceMetrics.put("deviceScaleFactor", 2);
        ((ChromeDriver) context.driver()).executeCdpCommand("Emulation.setDeviceMetricsOverride", deviceMetrics);
        new HandsOnPage(context).open()
                .checkIfPageTitleIs("Hands-On Selenium WebDriver with Java");
    }

    @Test
    void testConsoleListener() {
        CompletableFuture<ConsoleEvent> futureEvents = new CompletableFuture<>();
        devTools.getDomains().events().addConsoleListener(futureEvents::complete);

        CompletableFuture<JavascriptException> futureJsExc = new CompletableFuture<>();
        devTools.getDomains().events().addJavascriptExceptionListener(futureJsExc::complete);

        new ConsoleLogsPage(context).open();

        ConsoleEvent consoleEvent;
        try {
            consoleEvent = futureEvents.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get console events: ", e);
        }

        context.log().debug("Console event: {} {} {}",
                consoleEvent.getTimestamp(),
                consoleEvent.getType(),
                consoleEvent.getMessages());

        JavascriptException jsException;
        try {
            jsException = futureJsExc.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get JavaScript exceptions", e);
        }

        context.log().debug("Javascript Exception: {} {]",
                jsException.getMessage(),
                jsException.getSystemInformation());
    }

    @Test
    void testGeolocationOverride() {
        devTools.send(Emulation.setGeolocationOverride(Optional.of(48.8584),
                Optional.of(2.2945),
                Optional.of(100),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));
        GeolocationPage geolocationPage = new GeolocationPage(context).open().getCoordinatesClick();
        String latitude = geolocationPage.getLatitude();
        String longitude = geolocationPage.getLongitude();
        assertThat(latitude).isEqualTo("48");
        assertThat(longitude).isEqualTo("2");
    }

    @Test
    void testManageCookies() {
        devTools.send(Network.enable(Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));

        CookiesPage cookiesPage = new CookiesPage(context).open();

        //reading cookies
        List<String> url = List.of(Config.url("cookies.html"));
        List<Cookie> cookies = devTools.send(Network.getCookies(Optional.of(url)));
        cookies.forEach(cookie -> context.log().debug("{}={}", cookie.getName(), cookie.getValue()));
        List<String> cookieName = cookies.stream().map(Cookie::getName).sorted().toList();
        Set<org.openqa.selenium.Cookie> seleniumCookie = context.options().getCookies();
        List<String> selCookieName = seleniumCookie.stream().map(org.openqa.selenium.Cookie::getName).sorted().toList();
        assertThat(cookieName).isEqualTo(selCookieName);

        //deleting cookies
        devTools.send(Network.clearBrowserCookies());
        List<Cookie> cookiesAfterClearing = devTools.send(Network.getCookies(Optional.of(url)));
        assertThat(cookiesAfterClearing).isEmpty();

        cookiesPage.refreshCookies();
    }

    @Test
    void testLoadInsecure() {
        Color red = new Color(255, 0, 0, 1);

        devTools.send(Security.enable());
        devTools.send(Security.setIgnoreCertificateErrors(true));

        new ExpiredPage(context).open().bodyShouldHaveColor(red);
    }

    private void logHeaders(Headers headers) {
        context.log().debug("\t Headers:");
        headers.toJson().forEach((k, v) -> context.log().debug("\t\t{}:{}", k, v));
    }

}
