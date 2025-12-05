package tests.ch05.cdp;

import base.TestBase;
import org.junit.jupiter.api.Test;
import pages.JigsawPage;

public class AuthenticationTest extends TestBase {

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
