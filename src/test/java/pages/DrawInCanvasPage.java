package pages;

import utils.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DrawInCanvasPage {

    private final TestContext context;

    private static final By CANVAS = By.tagName("canvas");

    public DrawInCanvasPage(TestContext context) {
        this.context = context;
    }

    public DrawInCanvasPage open() {
        context.driver().get(Config.url("draw-in-canvas.html"));
        return this;
    }

    public DrawInCanvasPage drawCircle(int numPoints, int radius) {
        WebElement elementCanvas = context.visible(CANVAS);
        context.actions().moveToElement(elementCanvas).clickAndHold();
        for (int i = 0; i < numPoints; i++) {
            double angle = Math.toRadians(360 * i / numPoints);
            double x = Math.sin(angle) * radius;
            double y = Math.cos(angle) * radius;
            context.actions().moveByOffset((int) x, (int) y);
        }
        context.actions().release(elementCanvas).build().perform();
        return this;
    }
}
