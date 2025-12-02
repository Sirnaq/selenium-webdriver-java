package tests.ch05.headless;

import io.github.bonigarcia.seljup.Arguments;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HandsOnPage;
import pages.TestContext;

@ExtendWith(SeleniumJupiter.class)
public class HeadlessChromeJupiterTest {

    TestContext context;

    @Test
    void testHeadless(@Arguments("--headless=new")ChromeDriver driver){
        context = new TestContext(driver);
        new HandsOnPage(context).open()
                .checkIfPageTitleIs("Hands-On Selenium WebDriver with Java");
    }

    @AfterEach
    void tearDown(){
        context.driver().quit();
    }
}
