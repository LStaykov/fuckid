package elements;

import elementfactory.base.Element;
import elementfactory.base.ElementImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PressImpl extends ElementImpl implements Press {

    @FindBy(tagName = "img")
    private Element logo;

    @FindBy(tagName = "p")
    private Element content;

    @FindBy(tagName = "a")
    private Element rearMoreBtn;

    public PressImpl(WebElement wrappedElement) {
        super(wrappedElement);
    }

    @Override
    public boolean isLogoThere() {
        return logo.isDisplayed();
    }

    @Override
    public String getContent() {
        return content.getText().trim();
    }

    public void clickReadMoreButton() {
        rearMoreBtn.click();
    }
}
