package pages;

import elements.Press;
import elements.ProductItem;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(className = "blocks__item--cfd")
    public ProductItem cfdProduct;

    @FindBy(className = "owl-stage")
    public Press pressSection;

    public HomePage(WebDriver driver) {
        super(driver);
    }
}
