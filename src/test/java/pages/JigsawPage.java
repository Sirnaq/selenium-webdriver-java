package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.UsernameAndPassword;

import static org.assertj.core.api.Assertions.assertThat;

public class JigsawPage {

    private final TestContext context;

    private static final By BODY = By.tagName("body");

    public JigsawPage(TestContext context) {
        this.context = context;
    }

    public JigsawPage openWithBasicAuth(String login, String password) {
        ((HasAuthentication) context.driver()).register(() -> new UsernameAndPassword(login, password));
        context.driver().get("https://jigsaw.w3.org/HTTP/Digest/");

        return this;
    }

    public JigsawPage bodyShouldHaveText(String text) {
        assertThat(context.getText(BODY)).contains(text);
        return this;
    }

    public JigsawPage openWithGenericAuth(String login, String password) {
        context.driver().get(String.format("https://%s:%s@jigsaw.w3.org/HTTP/Digest/",login,password));
        return this;
    }

    public JigsawPage open() {
        context.driver().get("https://jigsaw.w3.org/HTTP/Basic/");
        return this;
    }
}
