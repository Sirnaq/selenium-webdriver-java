package tests.ch04;

import base.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.DialogBoxesPage;

public class DialogTest extends TestBase {

    private DialogBoxesPage dialogBoxesPage;

    @BeforeEach
    void setup() {
        dialogBoxesPage = new DialogBoxesPage(context);
        dialogBoxesPage.open();
    }

    @Test
    void testAlert() {
        dialogBoxesPage.alertClick()
                .checkIfAlertTextIsEqualTo("Hello world!")
                .acceptAlert();
    }

    @Test
    void testConfigm() {
        dialogBoxesPage.confirmClick()
                .checkIfAlertTextIsEqualTo("Is this correct?")
                .dismissAlert();
    }

    @Test
    void testPrompt() {
        dialogBoxesPage.promptClick()
                .checkIfAlertTextIsEqualTo("Please enter your name")
                .fillOutPromptWith("Jane Doe")
                .acceptAlert();
    }

    @Test
    void testModal() {
        dialogBoxesPage.modalClick()
                .checkIfCloseTakNameIs("button")
                .closeClick();
    }
}
