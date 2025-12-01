package tests.ch04;

import base.TestBase;
import org.junit.jupiter.api.Test;
import pages.WebStoragePage;

public class WebStorageTest extends TestBase {

    @Test
    void testWebStorage() {
        String sessionStorageKey = "new element";
        String sessionStorageValue = "new value";

        new WebStoragePage(context).open()
                .logSessionStorage()
                .checkIfSessionStorageSizeIs(2)
                .addSessionStorageElement(sessionStorageKey, sessionStorageValue)
                .logSessionStorage()
                .checkIfSessionStorageSizeIs(3);
    }
}
