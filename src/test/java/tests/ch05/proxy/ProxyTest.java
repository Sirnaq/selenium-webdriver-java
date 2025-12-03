package tests.ch05.proxy;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.HandsOnPage;
import pages.TestContext;
import utils.Config;

public class ProxyTest {

    TestContext context;

    @BeforeEach
    void setup(){
        Proxy proxy = new Proxy();
        String proxyStr = "proxy:port";
        proxy.setHttpProxy(proxyStr);
        proxy.setSslProxy(proxyStr);

        ChromeOptions options = new ChromeOptions();
        if(Config.isHeadless()){
            options.addArguments("--headless=new");
        }
        options.setAcceptInsecureCerts(true);
        options.setProxy(proxy);
        WebDriver driver = WebDriverManager.chromedriver().capabilities(options).create();
        context = new TestContext(driver);
    }

    @AfterEach
    void tearDown(){
        context.driver().quit();
    }

    @Test
    void testProxyChrome(){
        new HandsOnPage(context).open()
                .checkIfPageTitleIs("Hands-On Selenium WebDriver with Java");
    }

}
