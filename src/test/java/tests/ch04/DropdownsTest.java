package tests.ch04;

import base.TestBase;
import org.junit.jupiter.api.Test;

public class DropdownsTest extends TestBase {

    @Test
    void testDropdownSelect() {
        webFormPage.open()
                .selectOption("Three")
                .checkIfSelectValueIs("Three");
    }

    @Test
    void testDropdownDatalist() {
        webFormPage.open()
                .dataListClick()
                .dataListSetOptionTwo()
                .checkIfDataListValueIs("New York");
    }
}
