package tests.ch05.headless;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.HandsOnPage;
import pages.TestContext;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HeadlessChromeTest {

    private TestContext context;
    private HandsOnPage handsOnPage;

    @BeforeAll
    void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        context = new TestContext(new ChromeDriver(options));
        handsOnPage = new HandsOnPage(context);
    }

    @Test
    void headless() {
        handsOnPage.open();
        assertThat(context.driver().getTitle()).contains("Selenium WebDriver");
    }
}
