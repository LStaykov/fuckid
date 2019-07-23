package pages;

import elementfactory.ElementFactory;
import elementfactory.base.Element;
import elements.Input;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//input[@id='username-real']/..")
    public Input login;

    @FindBy(css = "input[type='submit']")
    public Element submitBtn;

    public LoginPage(WebDriver driver) {
        super(driver);
        ElementFactory.initElements(driver, this);
    }
}
