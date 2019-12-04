package pages;

import elements.Language;
import elements.ProductItem;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(className = "blocks__item--cfd")
    public ProductItem cfdProduct;

    @FindBy(id = "language-menu")
    public Language homeLanguage;

    public HomePage(WebDriver driver) {
        super(driver);
    }
}
