package tests.ch05;

import base.TestBase;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.PrintsPage;
import org.openqa.selenium.print.PrintOptions;
import pages.HandsOnPage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

public class PrintPdfTest extends TestBase {

    @Test
    void testPrint() {
        new HandsOnPage(context).open();
        PrintsPage printer = (PrintsPage) context.driver();
        PrintOptions printOptions = new PrintOptions();
        Pdf pdf = printer.print(printOptions);

        String pdfBase64 = pdf.getContent();
        assertThat(pdfBase64).contains("JVBER");

        byte[] decodedImg = Base64.getDecoder().decode(pdfBase64.getBytes(StandardCharsets.UTF_8));
        Path destinationFile = Paths.get("screenshots/my-pdf.pdf");
        try {
            Files.write(destinationFile, decodedImg);
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong while riting the file: ", e);
        }
    }
}
