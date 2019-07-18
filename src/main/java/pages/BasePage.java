package pages;

import elementfactory.ElementFactory;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {
    private WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        ElementFactory.initElements(driver, this);
    }

    public WebDriver getDriver() {
        return driver;
    }
}
