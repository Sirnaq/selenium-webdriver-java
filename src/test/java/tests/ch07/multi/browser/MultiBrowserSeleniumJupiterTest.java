package tests.ch07.multi.browser;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import pages.HandsOnPage;
import pages.TestContext;

@ExtendWith(SeleniumJupiter.class)
public class MultiBrowserSeleniumJupiterTest {

    /**
     * This approach works only when you set parallel execution in junit-platform.properties to false
     **/

    @TestTemplate
    void testCrossBrowser(WebDriver driver) {
        TestContext context = new TestContext(driver);
        new HandsOnPage(context).open()
                .checkIfPageTitleIs("Hands-On Selenium WebDriver with Java");
    }
}
