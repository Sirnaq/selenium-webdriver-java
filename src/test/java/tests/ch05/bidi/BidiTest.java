package tests.ch05.bidi;

import base.TestBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.devtools.events.CdpEventTypes;
import org.openqa.selenium.devtools.events.DomMutationEvent;
import org.openqa.selenium.logging.HasLogEvents;
import pages.ConsoleLogsPage;
import pages.HandsOnPage;
import utils.Config;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BidiTest extends TestBase {

    @BeforeAll
    void setupClass(){
        assumeThat(Config.getConfig().getProperty("browserType")).isEqualTo("chrome");
    }

    @Test
    void testDomMutation() throws InterruptedException {
        HandsOnPage handsOnPage = new HandsOnPage(context).open();
        HasLogEvents logger = (HasLogEvents) context.driver();
        AtomicReference<DomMutationEvent> seen = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);
        logger.onLogEvent(CdpEventTypes.domMutation(mutation -> {
            seen.set(mutation);
            latch.countDown();
        }));

        String newSrc = "img/award.png";

        handsOnPage.mutateImage(newSrc);

        assertThat(latch.await(10, TimeUnit.SECONDS)).isTrue();
        assertThat(seen.get().getElement().getAttribute("src")).endsWith(newSrc);
    }

    @Test
    void testConsoleEvent() throws InterruptedException {
        HasLogEvents logger = (HasLogEvents) context.driver();

        CountDownLatch latch = new CountDownLatch(4);
        logger.onLogEvent(CdpEventTypes.consoleEvent(consoleEvent -> {
            context.log().debug("{} {}: {}",consoleEvent.getTimestamp(),consoleEvent.getType(),consoleEvent.getMessages());
            latch.countDown();
        }));

        new ConsoleLogsPage(context).open();

        assertThat(latch.await(10,TimeUnit.SECONDS)).isTrue();
    }

}
