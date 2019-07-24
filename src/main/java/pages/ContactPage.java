package pages;

import elementfactory.ElementFactory;
import elements.Contact;
import elements.Input;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;


public class ContactPage extends BasePage {


    @FindBy(className = "contacts-submit")
    public Input sendBtn;

    @FindBy(xpath = "//input[@id='name']/..")
    public Input nameInput;

    @FindBy(xpath = "//input[@id='email']/..")
    public Input emailInput;

    @FindBy(xpath = "//input[@id='subject']/..")
    public Input subjectInput;

    @FindBy(xpath = "//textarea[@id='message']/..")
    public Input messageField;

//    @FindBy(className = "addr-box")
//    public Contact leftContactsForm;

//    @FindBy(className = "addr-box")
////    public List<Contact> contacts;

    public Contact getContactInfoForRegion(String regionName) {
        return ElementFactory.initElements(
                getDriver().findElement(By.className("region-" + regionName.toUpperCase())),
                Contact.class
        );
    }

    public ContactPage(WebDriver driver) {
        super(driver);
        ElementFactory.initElements(driver, this);
    }

}
