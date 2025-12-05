package pages;

import org.openqa.selenium.By;
import utils.Config;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;

public class MultiLanguagePage {

    private final TestContext context;

    private static final By BODY = By.tagName("body");

    public MultiLanguagePage(TestContext context) {
        this.context = context;
    }

    public MultiLanguagePage open(){
        context.driver().get(Config.url("multilanguage.html"));
        return this;
    }

    public MultiLanguagePage bodyShouldHaveText(String lang) {
        ResourceBundle strings = ResourceBundle.getBundle("strings",
                Locale.forLanguageTag(lang));
        String home = strings.getString("home");
        String content = strings.getString("content");
        String about = strings.getString("about");
        String contact = strings.getString("contact");

        String bodyText = context.getText(BODY);
        assertThat(bodyText)
                .contains(home)
                .contains(content)
                .contains(about)
                .contains(contact);
        return this;
    }
}
