package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import utils.Config;

import static org.assertj.core.api.Assertions.assertThat;

public class CookiesPage {
    private final TestContext context;

    private static final By REFRESH_COOKIES = By.id("refresh-cookies");

    public CookiesPage(TestContext context) {
        this.context = context;
    }

    public CookiesPage open() {
        context.driver().get(Config.url("cookies.html"));
        return this;
    }

    public CookiesPage checkIfNumberOfCookiesEquals(int expectedNumberOfCookies) {
        assertThat(context.options().getCookies().size()).isEqualTo(expectedNumberOfCookies);
        return this;
    }

    public CookiesPage checkIfCookieValueEquals(String cookieName, String expectedValue) {
        assertThat(getCookie(cookieName).getValue()).isEqualTo(expectedValue);
        return this;
    }

    public CookiesPage checkIfCookiePathEquals(String cookieName, String expectedPath) {
        assertThat(getCookie(cookieName).getPath()).isEqualTo(expectedPath);
        return this;
    }

    public CookiesPage logCookieInformation(String cookieName) {
        Cookie cookie = getCookie(cookieName);
        context.log().debug("Cookie collected: name: {}, value {}, path {}, domain {}, expiration date {}, same site {}, body {}",
                cookie.getName(), cookie.getValue(), cookie.getPath(), cookie.getDomain(), cookie.getExpiry(),
                cookie.getSameSite(), cookie);
        return this;
    }

    public CookiesPage refreshCookies() {
        context.click(REFRESH_COOKIES);
        return this;
    }

    public CookiesPage addCookie(Cookie newCookie) {
        context.options().addCookie(newCookie);
        return this;
    }

    public CookiesPage modifyCookieWithNewValue(String cookieName, String newValue) {
        Cookie cookieToModify = getCookie(cookieName);
        Cookie newCookie = new Cookie(cookieToModify.getName(), newValue);
        context.options().addCookie(newCookie);
        return this;
    }

    public CookiesPage deleteCookie(String username) {
        Cookie cookieToDelete = context.options().getCookieNamed(username);
        context.options().deleteCookie(cookieToDelete);
        return this;
    }

    private Cookie getCookie(String cookieName) {
        return context.options().getCookieNamed(cookieName);
    }
}
