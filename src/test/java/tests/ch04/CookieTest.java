package tests.ch04;

import base.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

public class CookieTest extends TestBase {

    @BeforeEach
    void setUp() {
        cookiesPage.open();
    }

    @Test
    void testReadCookie() {
        cookiesPage.checkIfNumberOfCookiesEquals(2)
                .checkIfCookieValueEquals("username", "John Doe")
                .checkIfCookiePathEquals("username", "/")
                .logCookieInformation("username")
                .refreshCookies();
    }

    @Test
    void testAddCookie() {
        Cookie newCookie = new Cookie("new-cookie-key", "new-cookie-value");
        cookiesPage.addCookie(newCookie)
                .checkIfCookieValueEquals(newCookie.getName(), newCookie.getValue())
                .refreshCookies();
    }

    @Test
    void testEditCookie() {
        cookiesPage.modifyCookieWithNewValue("username", "new-value")
                .checkIfCookieValueEquals("username", "new-value")
                .refreshCookies();
    }

    @Test
    void deleteCookie() {
        cookiesPage.deleteCookie("username")
                .checkIfNumberOfCookiesEquals(1)
                .refreshCookies();
    }
}
