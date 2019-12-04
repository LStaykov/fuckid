import elementfactory.base.DriverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;

public class TestClass {
    private WebDriver driver;

    @BeforeMethod
    private void setUpDriver() {
        driver = new ChromeDriver();
        DriverUtils.resetDriverTimeout(driver);

        driver.get("https://www.trading212.com");
    }


    @Test
    public void productTest() {
        HomePage homePage = new HomePage(driver);

        System.out.println(homePage.cfdProduct.getDescription());
        System.out.println(homePage.cfdProduct.getTitle());
        homePage.cfdProduct.gotToLearnMore();
    }

    @Test
    public void currentLanguage(){
        HomePage homePage = new HomePage(driver);

        homePage.homeLanguage.waitForStablePosition();
        System.out.println(homePage.homeLanguage.getText());
        homePage.homeLanguage.click();

    }


    @AfterMethod
    private void tearDown() {
        driver.quit();
    }
}
