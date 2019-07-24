package elementfactory.base;

import elementfactory.ElementFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@ImplementedBy()
public interface Element extends WebElement {

    WebElement getWrappedElement();

    WebDriver getDriver();

    boolean isActive();

    void scrollToElement();

    void scrollToElement(Boolean alignToTop);

    void scrollToElementCenter();

    void waitClickable();

    void waitStaleness();

    void waitStaleness(boolean checkForCause);

    void waitForStablePosition();

    void waitForStablePosition(int matchCount);

    void waitVisible();

    void waitNotVisible();

    void waitEnabled();

    void waitDisabled();

    String getId();

    void waitForLoaderToDisappear();

    boolean hasChildLocatedBy(By by);

    int getLeft();

    int getTop();

    boolean hasTutorialOverlay();

    String getClassAttribute();

    void jsClick();

    void ensureVisibleAndClick();

    void clickIfPresent();

    String getHTML();

    Element hover();

    Element hover(int milliseconds);

    boolean isElementVisibleInViewPort();

    String getValue();

    boolean isNextToAnother(Element other);

    boolean isUnderAnother(Element other);

    boolean isTheSameSizeAsAnother(Element other);

    Point getCenter();

    static Element getNewInstance(WebElement wrappedElement) {
        return ElementFactory.initElements(wrappedElement, Element.class);
    }


}
