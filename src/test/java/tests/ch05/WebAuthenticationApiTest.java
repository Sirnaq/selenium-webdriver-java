package tests.ch05;

import base.TestBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.virtualauthenticator.HasVirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;
import pages.WebAuthnPage;
import utils.Config;

import java.util.UUID;

import static org.assertj.core.api.Assumptions.assumeThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WebAuthenticationApiTest extends TestBase {

    @BeforeAll
    void setupClass(){
        assumeThat(Config.getConfig().getProperty("browserType")).isEqualTo("chrome");
    }

    @Test
    void testWebAuthn(){
        WebAuthnPage webAuthnPage = new WebAuthnPage(context).open();
        HasVirtualAuthenticator hasVirtualAuthenticator = (HasVirtualAuthenticator) context.driver();
        VirtualAuthenticator authenticator = hasVirtualAuthenticator
                .addVirtualAuthenticator(new VirtualAuthenticatorOptions());

        String randomId = UUID.randomUUID().toString();
        webAuthnPage.emailType(randomId)
                .registerClick()
                .alertShouldHaveText("Success! Now try to authenticate...")
                .loginClick()
                .mainContentShouldHaveText("You're logged in!");

        hasVirtualAuthenticator.removeVirtualAuthenticator(authenticator);
    }
}
