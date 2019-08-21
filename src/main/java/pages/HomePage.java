package pages;

import elements.Press;
import elements.ProductItem;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(className = "blocks__item--cfd")
    public ProductItem cfdProduct;

    @FindBy(css = "div.owl-item:not(.cloned)")
    public List<Press> pressList;

    public HomePage(WebDriver driver) {
        super(driver);
    }
}
