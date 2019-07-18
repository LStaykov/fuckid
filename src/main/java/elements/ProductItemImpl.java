package elements;

import elementfactory.base.Element;
import elementfactory.base.ElementImpl;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductItemImpl extends ElementImpl implements ProductItem {

    @FindBy(className = "blocks__item__title")
    private Element title;

    @FindBy(className = "blocks__item__text")
    private Element description;

    @FindBy(partialLinkText = "Learn more")
    private Element learnMoreLink;

    @FindBy(partialLinkText = "Open account")
    private Element openAccountLink;

    public ProductItemImpl(WebElement wrappedElement) {
        super(wrappedElement);
    }

    @Override
    public void gotToLearnMore() {
        learnMoreLink.ensureVisibleAndClick();
    }

    @Override
    public void goToOpenAccount() {
        openAccountLink.ensureVisibleAndClick();
    }

    @Override
    public String getTitle() {
        return title.getText();
    }

    @Override
    public String getDescription() {
        return description.getText();
    }
}
