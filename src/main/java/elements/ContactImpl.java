package elements;

import elementfactory.base.Element;
import elementfactory.base.ElementImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ContactImpl extends ElementImpl implements Contact {

    public ContactImpl(WebElement wrappedElement) {
        super(wrappedElement);
    }

    @FindBy(css = "contacts-heading")
    private Element countryTitle;

    @FindBy(tagName = "tr")
    private List<Element> rows;


    @Override
    public String getCountry() {
        return countryTitle.getText();
    }

    @Override
    public String getPhone() {
        return rows.get(0).findElement(By.className("no-revert")).getText().trim();
    }

    @Override
    public String getEmail() {
        return rows.get(1).findElement(By.tagName("a")).getText().trim();
    }

    @Override
    public String getAddress() {
        return rows.get(2).findElements(By.tagName("td")).get(1).getText().trim();
    }

    private String getColumntValue(Element element) {
        return element.findElement(By.className("no-revert")).getText().trim();
    }
}
