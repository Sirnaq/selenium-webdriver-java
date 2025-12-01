package tests.ch04;

import base.TestBase;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.ScriptTimeoutException;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TimeoutsTest extends TestBase {

    @Test
    void testPageLoadTimeout() {
        assertThatThrownBy(() -> handsOnPage.setPageLoadTimeout(Duration.ofMillis(1)).open())
                .isInstanceOf(TimeoutException.class);
    }

    @Test
    void testScriptTimeout() {
        assertThatThrownBy(() ->
                handsOnPage.open().setScriptTimeout(Duration.ofSeconds(3)).executeLongScript()
        ).isInstanceOf(ScriptTimeoutException.class);
    }
}
