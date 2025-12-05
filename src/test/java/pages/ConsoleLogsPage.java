package pages;

import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import utils.Config;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsoleLogsPage {

    private final TestContext context;

    public ConsoleLogsPage(TestContext context) {
        this.context = context;
    }

    public ConsoleLogsPage open() {
        context.driver().get(Config.url("console-logs.html"));
        return this;
    }

    public ConsoleLogsPage logBrowserConsoleLogs() {
        LogEntries browserLogs = context.options().logs().get(LogType.BROWSER);
        assertThat(browserLogs.getAll()).isNotEmpty();
        browserLogs.forEach(entry -> context.log().debug("This is the log from this site: {}", entry));
        return this;
    }
}
