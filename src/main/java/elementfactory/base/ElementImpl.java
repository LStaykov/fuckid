package elementfactory.base;


import elementfactory.ElementFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ElementImpl implements Element {

    private WebElement wrappedElement;

    public ElementImpl(WebElement wrappedElement) {
        this.wrappedElement = wrappedElement;
        ElementFactory.initElements(wrappedElement, this);
    }

    @Override
    public WebElement getWrappedElement() {
        return wrappedElement;
    }

    @Override
    public WebDriver getDriver() {
        return ((WrapsDriver) wrappedElement).getWrappedDriver();
    }

    @Override
    public boolean isActive() {
        return !getAttribute("class").contains("disabled");
    }

    @Override
    public void scrollToElement() {
        scrollToElement(true);
    }

    @Override
    public void scrollToElement(Boolean alignToTop) {
        String script = "arguments[0].scrollIntoView(" + alignToTop.toString() + ")";
        ((JavascriptExecutor) getDriver()).executeScript(script, getWrappedElement());
    }

    @Override
    public void scrollToElementCenter() {
        String script = "arguments[0].scrollIntoView({block: 'center', inline: 'center'})";
        ((JavascriptExecutor) getDriver()).executeScript(script, getWrappedElement());
    }

    @Override
    public void waitClickable() {
        new WebDriverWait(getDriver(), 5)
                .withMessage("Element is not clickable")
                .until(ExpectedConditions.elementToBeClickable(getWrappedElement()));
    }

    @Override
    public void waitStaleness() {
        waitStaleness(false);
    }

    /**
     * Wait for element to become stale and if it does not,
     * check if there is some kind of widget message that prevent the element to become stale
     *
     * @param checkForCause to check for cause or not
     */
    @Override
    public void waitStaleness(boolean checkForCause) {
        new WebDriverWait(getDriver(), 5, 50)
                .withMessage("'" + getInterfaceName() + "'" + ": is not stale")
                .until(ExpectedConditions.stalenessOf(getWrappedElement()));
    }

    @Override
    public void waitForStablePosition() {
        waitForStablePosition(10);
    }

    @Override
    public void waitForStablePosition(int desiredMatchesCount) {
        new WebDriverWait(getDriver(), 5)
                .withMessage("Element does not have stable position")
                .until((dr) -> {
                    int matchCount = 0;
                    Point lastPoint = getWrappedElement().getLocation();

                    for (int i = 0; i <= desiredMatchesCount; i++) {
                        Point currentPoint = getWrappedElement().getLocation();
                        if (currentPoint.equals(lastPoint)) {
                            matchCount += 1;
                        } else {
                            lastPoint = currentPoint;
                            matchCount = 0;
                        }
                    }
                    return matchCount >= desiredMatchesCount;
                });
    }

    @Override
    public void waitVisible() {
        new WebDriverWait(getDriver(), 5)
                .withMessage("Element is not visible")
                .until(ExpectedConditions.visibilityOf(getWrappedElement()));
    }

    @Override
    public void waitNotVisible() {
        new WebDriverWait(getDriver(), 5)
                .withMessage("Element is still visible after")
                .until(ExpectedConditions.invisibilityOf(getWrappedElement()));
    }

    @Override
    public void waitEnabled() {
        new WebDriverWait(getDriver(), 5)
                .withMessage("Element is not enabled after")
                .until(driver -> isEnabled());
    }

    @Override
    public void waitDisabled() {
        new WebDriverWait(getDriver(), 5)
                .withMessage("Element is enabled after")
                .until(driver -> !isActive());
    }

    @Override
    public String getId() {
        return getAttribute("id");
    }

    /**
     * Wait for loader not to be present within the wrapped element
     */
    @Override
    public void waitForLoaderToDisappear() {
        try {
            new WebDriverWait(getDriver(), 5)
                    .withMessage("Element still have loader after")
                    .until(driver -> !hasChildLocatedBy(By.className("loader-circular")));
        } catch (StaleElementReferenceException e) {
            // Do nothing. Wrapped element is stale, so there is no child loader element
        }
    }

    /**
     * Search parent element for visible child element
     *
     * @param selector to search
     * @return true if visible element is located or false if the element is not found or
     */
    @Override
    public boolean hasChildLocatedBy(By selector) {
        DriverUtils.disableDriverTimeout(getDriver());
        WebElement element;
        try {
            element = findElement(selector);
        } catch (NoSuchElementException e) {
            return false;
        } finally {
            DriverUtils.resetDriverTimeout(getDriver());
        }
        try {
            return element.isDisplayed();
        } catch (StaleElementReferenceException e) {
            return false;
        }
    }

    @Override
    public int getLeft() {
        String style = getAttribute("style");
        if (style == null || style.equals("")) {
            throw new WebDriverException("This element has no style attribute.");
        }

        String[] styles = style.split(";");

        for (String s : styles) {
            if (s.trim().startsWith("left:")) {
                return Integer.valueOf(s.substring(s.lastIndexOf("left:") + 5, s.indexOf("px")).trim());
            }
        }
        throw new WebDriverException("Element's style has no Left.");
    }

    @Override
    public int getTop() {
        String style = getAttribute("style");
        if (style == null || style.equals("")) {
            throw new WebDriverException("This element has no style attribute.");
        }

        String[] styles = style.split(";");

        for (String s : styles) {
            if (s.trim().startsWith("top:")) {
                return Integer.valueOf(s.substring(s.lastIndexOf("top:") + 4, s.indexOf("px")).trim());
            }
        }
        throw new WebDriverException("Element's style has no Top.");
    }

    @Override
    public boolean hasTutorialOverlay() {
        try {
            getDriver().manage().timeouts().implicitlyWait(50, TimeUnit.MILLISECONDS);
            findElement(By.cssSelector("div.component-overlay"));
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        } finally {
            DriverUtils.resetDriverTimeout(getDriver());
        }
    }

    @Override
    public String getClassAttribute() {
        return getAttribute("class");
    }

    @Override
    public void jsClick() {
        String script = "arguments[0].click()";
        ((JavascriptExecutor) getDriver()).executeScript(script, getWrappedElement());
    }

    @Override
    public String getHTML() {
        String script = "return arguments[0].innerHTML;";
        return (String) ((JavascriptExecutor) getDriver()).executeScript(script, getWrappedElement());
    }

    @Override
    public Element hover() {
        return hover(1000);
    }

    @Override
    public Element hover(int milliseconds) {
        new Actions(getDriver())
                .moveToElement(getWrappedElement())
                .pause(Duration.ofMillis(milliseconds))
                .perform();

        return this;
    }

    @Override
    public boolean isElementVisibleInViewPort() {
        return (Boolean) ((JavascriptExecutor) getDriver()).executeScript(
                "var elem = arguments[0],                 " +
                        "  box = elem.getBoundingClientRect(),    " +
                        "  cx = box.left + box.width / 2,         " +
                        "  cy = box.top + box.height / 2,         " +
                        "  e = document.elementFromPoint(cx, cy); " +
                        "for (; e; e = e.parentElement) {         " +
                        "  if (e === elem)                        " +
                        "    return true;                         " +
                        "}                                        " +
                        "return false;                            ",
                getWrappedElement());
    }

    @Override
    public String getValue() {
        new WebDriverWait(getDriver(),5)
                .until(_ignore -> !getAttribute("value").equals(""));
        return getAttribute("value");
    }

    @Override
    public boolean isNextToAnother(Element other) {
        Dimension size = getSize();
        Dimension otherSize = other.getSize();

        Point center = getCenter();
        Point otherCenter = other.getCenter();

        return !(center.x - otherCenter.x < (size.width + otherSize.width) / 2
                || Math.abs(center.y - otherCenter.y) > 10);
    }

    @Override
    public boolean isUnderAnother(Element other) {
        Dimension size = getSize();
        Dimension otherSize = other.getSize();

        Point center = getCenter();
        Point otherCenter = other.getCenter();

        return !(center.y - otherCenter.y < (size.height + otherSize.height) / 2
                || Math.abs(center.x - otherCenter.x) > 10);
    }

    @Override
    public boolean isTheSameSizeAsAnother(Element another) {
        return getSize().equals(another.getSize());
    }

    @Override
    public Point getCenter() {
        Point position = getLocation();
        Dimension size = getSize();

        return new Point(position.x + (size.width / 2), position.y + (size.height / 2));
    }

    @Override
    public void ensureVisibleAndClick() {
        if (!isElementVisibleInViewPort()) {
            scrollToElementCenter();
        }
        click();
    }

    @Override
    public void clickIfPresent() {
        if (isDisplayed()) {
            ensureVisibleAndClick();
        }
    }

    /**
     * Try to successfully click the element until timeout
     * This fix a lot of issues due to element not clickable because of animation, element transitions ...etc
     */
    @Override
    public void click() {
        new WebDriverWait(getDriver(), 5)
                .withMessage("Element " + toString() + "is not clickable after")
                .until(driver -> {
                    try {
                        wrappedElement.click();
                        return true;
                    } catch (WebDriverException e) {
                        if (e.getMessage().contains("not clickable")) {
                            return false;
                        }
                        throw e;
                    }
                });
    }

    @Override
    public void submit() {
        wrappedElement.submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        wrappedElement.sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        wrappedElement.clear();
    }

    @Override
    public String getTagName() {
        return wrappedElement.getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return wrappedElement.getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return wrappedElement.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return wrappedElement.isEnabled();
    }

    @Override
    public String getText() {
        return wrappedElement.getText().trim();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return wrappedElement.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return wrappedElement.findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        try {
            return wrappedElement.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public Point getLocation() {
        return wrappedElement.getLocation();
    }

    @Override
    public Dimension getSize() {
        return wrappedElement.getSize();
    }

    @Override
    public Rectangle getRect() {
        return wrappedElement.getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        return wrappedElement.getCssValue(propertyName);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return wrappedElement.getScreenshotAs(target);
    }

    @Override
    public String toString() {
        String toString = getWrappedElement().toString();
        Pattern pattern = Pattern.compile("->(.+)");
        Matcher matcher = pattern.matcher(toString);
        if (matcher.find()) {
            try {
                return matcher.group(1);
            } catch (IndexOutOfBoundsException e) {
                return toString;
            }
        } else {
            return toString;
        }
    }

    private String getInterfaceName() {
        try {
            return getClass().getInterfaces()[0].getSimpleName();
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            return "Element";
        }
    }
}
