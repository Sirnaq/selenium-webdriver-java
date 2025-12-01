package base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import pages.*;

public class TestBase {
    public TestContext context;
    public LongPage longPage;
    public FramesPage framesPage;
    public GooglePage googlePage;
    public WebFormPage webFormPage;
    public HandsOnPage handsOnPage;
    public CookiesPage cookiesPage;
    public IframesPage iframesPage;
    public MouseOverPage mouseOverPage;
    public ShadowDomPage shadowDomPage;
    public InfiniteScroll infiniteScroll;
    public WebStoragePage webStoragePage;
    public DragAndDropPage dragAndDropPage;
    public DialogBoxesPage dialogBoxesPage;
    public DropdownMenuPage dropdownMenuPage;
    public DrawInCanvasPage drawInCanvasPage;
    public LoadingImagesPage loadingImagesPage;
    public SlowCalculatorPage slowCalculatorPage;

    @BeforeEach
    void setup() {
        this.context = new TestContext();
        longPage = new LongPage(context);
        framesPage = new FramesPage(context);
        googlePage = new GooglePage(context);
        webFormPage = new WebFormPage(context);
        handsOnPage = new HandsOnPage(context);
        cookiesPage = new CookiesPage(context);
        iframesPage = new IframesPage(context);
        mouseOverPage = new MouseOverPage(context);
        shadowDomPage = new ShadowDomPage(context);
        infiniteScroll = new InfiniteScroll(context);
        webStoragePage = new WebStoragePage(context);
        dragAndDropPage = new DragAndDropPage(context);
        dialogBoxesPage = new DialogBoxesPage(context);
        dropdownMenuPage = new DropdownMenuPage(context);
        drawInCanvasPage = new DrawInCanvasPage(context);
        loadingImagesPage = new LoadingImagesPage(context);
        slowCalculatorPage = new SlowCalculatorPage(context);
    }

    @AfterEach
    void tearDown() {
        context.driver().quit();
    }
}
