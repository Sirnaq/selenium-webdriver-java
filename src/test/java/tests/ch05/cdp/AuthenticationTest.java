package tests.ch05.cdp;

import base.TestBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pages.JigsawPage;
import utils.Config;

import static org.assertj.core.api.Assumptions.assumeThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthenticationTest extends TestBase {

    @BeforeAll
    void setupClass(){
        assumeThat(Config.isRemote()).isFalse();
    }

    @Test
    void testBasicAuth() {
        new JigsawPage(context).openWithBasicAuth("guest", "guest")
                .bodyShouldHaveText("Your browser made it!");
    }

    @Test
    void testGenericAuth(){
        new JigsawPage(context).openWithGenericAuth("guest", "guest")
                .bodyShouldHaveText("Your browser made it!");
    }
}
