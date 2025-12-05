package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScriptKey;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.http.Contents;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import utils.Config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.assertj.core.api.Assertions.assertThat;

public class HandsOnPage {

    private final TestContext context;

    private static final By NAVIGATION = By.linkText("Navigation");
    private static final By WEB_FORM = By.linkText("Web form");
    private static final By IMAGE = By.tagName("img");

    public HandsOnPage(TestContext context) {
        this.context = context;
    }

    public HandsOnPage open() {
        context.driver().get(Config.baseUrl());
        return this;
    }

    public HandsOnPage logTitleOfThePage() {
        context.log().debug("The title of {} is {}", Config.baseUrl(), context.driver().getTitle());
        return this;
    }

    public NavigationPage navigationClick() {
        context.click(NAVIGATION);
        return new NavigationPage(context);
    }

    public HandsOnPage checkIfPageTitleIs(String expectedTitle) {
        assertThat(context.driver().getTitle()).isEqualTo(expectedTitle);
        return this;
    }

    public HandsOnPage checkIfUrlEqualsBaseUrl() {
        assertThat(context.driver().getCurrentUrl()).isEqualTo(Config.baseUrl());
        return this;
    }

    public HandsOnPage checkIfPageSourceContains(String expectedString) {
        assertThat(context.driver().getPageSource()).containsIgnoringCase(expectedString);
        return this;
    }

    public HandsOnPage checkIfSessionIdExists() {
        assertThat(getSessionId()).isNotNull();
        return this;
    }

    public HandsOnPage logSessionId() {
        context.log().debug("The session ID is: {}", getSessionId());
        return this;
    }

    public WebFormPage webFormClick() {
        context.click(WEB_FORM);
        return new WebFormPage(context);
    }

    public HandsOnPage pinScripts() {
        context.js().pin("return document.getElementsByTagName('a')[2];");
        context.js().pin("return arguments[0]");
        Set<ScriptKey> pinnedScripts = context.js().getPinnedScripts();
        assertThat(pinnedScripts).hasSize(2);
        return this;
    }

    public HandsOnPage clickGithubLinkByJS() {
        List<ScriptKey> scriptKeyList = getListOfScripts();
        ScriptKey firstScript = scriptKeyList.getFirst();
        ScriptKey secondScript = scriptKeyList.getLast();
        WebElement link = (WebElement) context.js().executeScript(firstScript);
        if (link == null){
            link = (WebElement) context.js().executeScript(secondScript);
        }
        link.click();
        return this;
    }

    public HandsOnPage checkIfPageURLChanged() {
        assertThat(context.driver().getCurrentUrl()).isNotEqualTo(Config.baseUrl());
        return this;
    }

    public HandsOnPage executeSecondPinnedScript(String message) {
        String result = (String) context.js().executeScript(getListOfScripts().getLast(), message);
        assertThat(result).isEqualTo(message);
        return this;
    }

    public HandsOnPage unpinFirstScript() {
        ScriptKey firstScript = getListOfScripts().getFirst();
        context.js().unpin(firstScript);
        return this;
    }

    public HandsOnPage checkIfPinnedScriptsSizeIs(int expectedSize) {
        assertThat(getListOfScripts().size()).isEqualTo(expectedSize);
        return this;
    }

    public HandsOnPage executeAsyncScriptExample() {
        Duration pause = Duration.ofSeconds(2);
        String script = String.format("""
                const callback = arguments[arguments.length - 1];
                window.setTimeout(callback,%s);
                """, pause.toMillis());

        long initMillis = System.currentTimeMillis();
        context.js().executeAsyncScript(script);
        Duration elapsed = Duration.ofMillis(System.currentTimeMillis() - initMillis);

        context.log().debug("The script took {} ms to be executed", elapsed.toMillis());
        assertThat(elapsed).isGreaterThanOrEqualTo(pause);

        return this;
    }

    public Path takeScreenshot() {
        File screenshot = context.screenshot().getScreenshotAs(OutputType.FILE);
        context.log().debug("Screenshot created on {}", screenshot);

        Path destination = Paths.get("screenshots/screenshot.png");
        try {
            Files.move(screenshot.toPath(), destination, REPLACE_EXISTING);
        } catch (IOException e) {
            context.log().error("Error while moving file: {}", e.getMessage());
        }
        return destination;
    }

    public HandsOnPage takeScreenshotInBase64() {
        String screenshot = context.screenshot().getScreenshotAs(OutputType.BASE64);
        context.log().debug("Screenshot in base 64 "
                + "(you can copy it to a browser navigation bar to watch it)\n"
                + "data:image/png;base64,{}", screenshot);
        assertThat(screenshot).isNotEmpty();
        return this;
    }

    public HandsOnPage setPageLoadTimeout(Duration duration) {
        context.options().timeouts().pageLoadTimeout(duration);
        return this;
    }

    public HandsOnPage setScriptTimeout(Duration duration) {
        context.options().timeouts().scriptTimeout(Duration.ofSeconds(3));
        return this;
    }

    public HandsOnPage executeLongScript() {
        long waitMillis = Duration.ofSeconds(5).toMillis();
        String script = String.format("""
                const callback = arguments[arguments.length - 1];
                window.setTimeout(callback,%d);
                """, waitMillis);
        context.js().executeAsyncScript(script);
        return this;
    }

    public HandsOnPage interceptImageReplaceWith(String image) {
        Path img;
        byte[] bytes;
        try {
            img = Paths.get(ClassLoader.getSystemResource(image).toURI());
            bytes = Files.readAllBytes(img);
        } catch (Exception e) {
            throw new RuntimeException("Test failed during initiation faze: ", e);
        }
        try (NetworkInterceptor interceptor = new NetworkInterceptor(context.driver(),
                Route.matching(httpRequest -> httpRequest.getUri().endsWith(".png"))
                        .to(() -> req -> new HttpResponse().setContent(
                                Contents.bytes(bytes)
                        ))
        )) {
            open();
            return this;
        }
    }

    public HandsOnPage imgShouldHaveWidthGreaterThan(int expectedWidth) {
        assertThat(Integer.parseInt(Objects.requireNonNull(context.visible(IMAGE).getAttribute("width"))))
                .isGreaterThan(expectedWidth);
        return this;
    }

    private SessionId getSessionId() {
        return ((RemoteWebDriver) context.driver()).getSessionId();
    }

    private List<ScriptKey> getListOfScripts() {
        Set<ScriptKey> scriptKeys = context.js().getPinnedScripts();
        return new ArrayList<>(scriptKeys);
    }
}
