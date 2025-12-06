package base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import pages.*;

public class TestBase {
    public TestContext context;

    @BeforeEach
    void setup() {
        this.context = new TestContext();
    }

    @AfterEach
    void tearDown() {
        context.driverQuit();
    }
}
