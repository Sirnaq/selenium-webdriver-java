package pages;

import utils.Config;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class WebStoragePage {

    private final TestContext context;

    public WebStoragePage(TestContext context) {
        this.context = context;
    }

    public WebStoragePage open() {
        context.driver().get(Config.url("web-storage.html"));
        return this;
    }

    public WebStoragePage logSessionStorage() {
        Map<String, String> sessionStorage = getSessionStorage();
        context.log().debug("Local storage elements: {}", sessionStorage.size());
        sessionStorage.forEach(this::logSessionStorage);
        return this;
    }

    public WebStoragePage checkIfSessionStorageSizeIs(int expectedSize) {
        assertThat(getSessionStorage().size()).isEqualTo(expectedSize);
        return this;
    }

    public WebStoragePage addSessionStorageElement(String sessionStorageKey, String sessionStorageValue) {
        context.js().executeScript(String.format("window.sessionStorage.setItem('%s', '%s');",
                sessionStorageKey, sessionStorageValue));
        return this;
    }

    private Map<String, String> getSessionStorage() {
        Object result = context.js().executeScript("""
                return Object.fromEntries(Object.entries(sessionStorage));
                """
        );
        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) result;
        return map;
    }

    private void logSessionStorage(String key, String value) {
        context.log().debug("Session storage: {}={}", key, value);
    }
}
