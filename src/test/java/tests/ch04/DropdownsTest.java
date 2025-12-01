package tests.ch04;

import base.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.WebFormPage;

public class DropdownsTest extends TestBase {

    WebFormPage webFormPage;

    @BeforeEach
    void setup() {
        webFormPage = new WebFormPage(context);
        webFormPage.open();
    }

    @Test
    void testDropdownSelect() {
        webFormPage.selectOption("Three")
                .checkIfSelectValueIs("Three");
    }

    @Test
    void testDropdownDatalist() {
        webFormPage.dataListClick()
                .dataListSetOptionTwo()
                .checkIfDataListValueIs("New York");
    }
}
