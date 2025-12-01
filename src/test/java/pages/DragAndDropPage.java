package pages;

import utils.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;

public class DragAndDropPage {

    private final TestContext context;

    private static final By DRAGGABLE = By.id("draggable");
    private static final By TARGET = By.id("target");

    public DragAndDropPage(TestContext context) {
        this.context = context;
    }

    public DragAndDropPage open() {
        context.driver().get(Config.url("drag-and-drop.html"));
        return this;
    }

    public DragAndDropPage dragAndDropDraggable(int x, int y) {
        context.actions().dragAndDropBy(context.clickable(DRAGGABLE), x, y).build().perform();
        return this;
    }

    public DragAndDropPage dragDraggableToTarget() {
        context.actions().dragAndDrop(context.clickable(DRAGGABLE), context.visible(TARGET)).build()
                .perform();
        return this;
    }

    public Point getDraggableLocation() {
        return context.visible(DRAGGABLE).getLocation();
    }

    public Point getTargetLocation() {
        return context.visible(TARGET).getLocation();
    }
}
