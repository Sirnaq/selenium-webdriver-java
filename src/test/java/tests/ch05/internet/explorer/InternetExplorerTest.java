package tests.ch05.internet.explorer;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import pages.HandsOnPage;
import pages.TestContext;

import java.nio.file.Path;
import java.util.Optional;

import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;
import static org.assertj.core.api.Assumptions.assumeThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled("Test freezes on my machine, just don't use IE in 2025")
public class InternetExplorerTest {

    TestContext context;

    @BeforeAll
    void setupClass() {
        assumeThat(IS_OS_WINDOWS).isTrue();
        WebDriverManager.iedriver().setup();
    }

    @BeforeEach
    void setup() {
        Optional<Path> browserPath = WebDriverManager.edgedriver().getBrowserPath();
        assumeThat(browserPath).isPresent();

        InternetExplorerOptions options = new InternetExplorerOptions();
        options.attachToEdgeChrome();
        options.withEdgeExecutablePath(browserPath.get().toString());
        WebDriver driver = new InternetExplorerDriver(options);
        context = new TestContext(driver);
    }

    @AfterEach
    void tearDown() {
        context.driver().quit();
    }

    @Test
    @Disabled
    void testBinary() {
        new HandsOnPage(context).open()
                .checkIfPageTitleIs("Hands-On Selenium WebDriver with Java");
    }
}
