package tests.ch07.parameterized;

import base.TestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import pages.LoginFormPage;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ParametrizedTest extends TestBase {

    @ParameterizedTest
    @ValueSource(strings = {"hi","bye"})
    void testValueSource(String argument){
        context.log().debug("arg: {}",argument);
    }

    @ParameterizedTest
    @EnumSource(TimeUnit.class)
    void testEnumSource(TimeUnit argument){
        context.log().debug("arg: {}",argument);
    }

    @ParameterizedTest
    @MethodSource("loginData")
    void testParameterizedMethodSource(String username, String password, String expectedText) {
        String bodyText = new LoginFormPage(context).open()
                .logIn(username, password)
                .getBodyText();
        assertThat(bodyText).contains(expectedText);
    }

    @ParameterizedTest
    @CsvSource({"hello, 1", "world, 2"})
    void testParameterizedLogCsv(String first, int second) {
        context.log().debug("{} and {}", first, second);
    }

    @ParameterizedTest
    @CsvSource({"user, user, Login successful", "bad-user, bad-passwd, Invalid credentials"})
    void testParameterizedCsv(String username, String password, String expectedText) {
        String bodyText = new LoginFormPage(context).open()
                .logIn(username, password)
                .getBodyText();
        assertThat(bodyText).contains(expectedText);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/example.csv")
    void testParameterizedCsvFile(String username, String password, String expectedText) {
        String bodyText = new LoginFormPage(context).open()
                .logIn(username, password)
                .getBodyText();
        assertThat(bodyText).contains(expectedText);
    }

    @ParameterizedTest
    @ArgumentsSource(MyArgs.class)
    void testParameterizedArgumentsSource(String first, int second) {
        context.log().debug("{} and {}", first, second);
    }

    @ParameterizedTest
    @ValueSource(strings = {"one","two"})
    @NullSource
    void testParameterizedNullSource(String argument) {
        context.log().debug("arg: {}",argument);
    }

    @ParameterizedTest
    @ValueSource(strings = {"three","four"})
    @EmptySource
    void testParameterizedEmptySource(String argument) {
        context.log().debug("arg: {}",argument);
    }

    @ParameterizedTest
    @ValueSource(strings = {"five","six"})
    @NullAndEmptySource
    void testParameterizedNullAndEmptySource(String argument) {
        context.log().debug("arg: {}",argument);
    }

    private static Stream<Arguments> loginData() {
        return Stream.of(Arguments.of("user", "user", "Login successful"),
                Arguments.of("bad-user", "bad-passwd", "Invalid credentials"));
    }
}
