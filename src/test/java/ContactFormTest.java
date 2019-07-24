import elementfactory.base.DriverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactPage;

public class ContactFormTest {

    private WebDriver driver;

    @BeforeMethod
    private void setUpDriver() {
        driver = new ChromeDriver();
        DriverUtils.resetDriverTimeout(driver);
        driver.get("https://www.trading212.com/en/Contact");
    }

    @Test
    public void contactFormNameTest() {
        ContactPage contactFormName = new ContactPage(driver);

        contactFormName.sendBtn.ensureVisibleAndClick();
        System.out.println(contactFormName.nameInput.getLabel());
        System.out.println(contactFormName.emailInput.hasError());
        System.out.println(contactFormName.nameInput.getErrorMessage());
    }

    @Test
    public void contactFormEmailTest() {
        ContactPage contactFormEmail = new ContactPage(driver);

        contactFormEmail.sendBtn.ensureVisibleAndClick();
        System.out.println(contactFormEmail.emailInput.getLabel());
        System.out.println(contactFormEmail.emailInput.hasError());
        System.out.println(contactFormEmail.emailInput.getErrorMessage());
    }
    @Test
    public void contactFormSubjectTest() {
        ContactPage contactFormSubject = new ContactPage(driver);

        contactFormSubject.sendBtn.ensureVisibleAndClick();
        System.out.println(contactFormSubject.subjectInput.getLabel());
        System.out.println(contactFormSubject.subjectInput.hasError());
        System.out.println(contactFormSubject.subjectInput.getErrorMessage());
    }
    @Test
    public void contactFormMessageTest() {
        ContactPage contactFormMessage = new ContactPage(driver);

        contactFormMessage.sendBtn.ensureVisibleAndClick();
        System.out.println(contactFormMessage.messageField.getLabel());
        System.out.println(contactFormMessage.messageField.hasError());
        System.out.println(contactFormMessage.messageField.getErrorMessage());
    }

    @Test
    public void contactFormContacts() {

        ContactPage contactFormContacts = new ContactPage(driver);
        System.out.println(contactFormContacts.getContactInfoForRegion("GB").getCountry());
        System.out.println(contactFormContacts.getContactInfoForRegion("GB").getAddress());
        System.out.println(contactFormContacts.getContactInfoForRegion("GB").getEmail());
        System.out.println(contactFormContacts.getContactInfoForRegion("GB").getPhone());
//        System.out.println(contactFormContacts.leftContactsForm.getCountry());
//        System.out.println(contactFormContacts.leftContactsForm.getAddress());
//        System.out.println(contactFormContacts.leftContactsForm.getEmail());
//        System.out.println(contactFormContacts.leftContactsForm.getPhone());
    }


    @AfterMethod
    private void tearDown() {
        driver.quit();
    }




}
