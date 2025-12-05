package tests.ch05.cdp;

import base.TestBase;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v142.dom.model.Rect;
import org.openqa.selenium.devtools.v142.network.Network;
import org.openqa.selenium.devtools.v142.network.model.ConnectionType;
import org.openqa.selenium.devtools.v142.network.model.Headers;
import org.openqa.selenium.devtools.v142.network.model.NetworkConditions;
import org.openqa.selenium.devtools.v142.page.Page;
import org.openqa.selenium.devtools.v142.page.model.Viewport;
import org.openqa.selenium.devtools.v142.performance.Performance;
import org.openqa.selenium.devtools.v142.performance.model.Metric;
import pages.HandsOnPage;
import pages.LongPage;
import utils.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DevToolsTest extends TestBase {

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
    void testPerformanceMetrics(){
        devTools.send(Performance.enable(Optional.empty()));
        new HandsOnPage(context).open();
        List<Metric> metrics = devTools.send(Performance.getMetrics());
        assertThat(metrics).isNotEmpty();
        metrics.forEach(metric -> context.log().debug("{}: {}",metric.getName(),metric.getValue()));
    }

    void logHeaders(Headers headers) {
        context.log().debug("\t Headers:");
        headers.toJson().forEach((k, v) -> context.log().debug("\t\t{}:{}", k, v));
    }

}
