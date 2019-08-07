package elements;

import elementfactory.base.Element;
import elementfactory.base.ElementImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PressImpl extends ElementImpl implements Press {
    public PressImpl(WebElement wrappedElement) {
        super(wrappedElement);
    }

    @FindBy(className = "owl-item")
    private List<Press> presses;


    @Override
    public boolean isLogoThere() {
        return presses.get(5).findElement(By.tagName("img")).isEnabled();
    }

    @Override
    public String getContent() {
        return presses.get(5).findElement(By.tagName("p")).getText().trim();
    }

    @Override
    public void clickButton() {
        presses.get(5).findElement(By.tagName("a")).click();
    }
}
