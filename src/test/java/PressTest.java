import elementfactory.base.DriverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;

public class PressTest {

    private WebDriver driver;

    @BeforeMethod
    private void setUpDriver(){
        driver = new ChromeDriver();
        DriverUtils.resetDriverTimeout(driver);
        driver.get("https://www.trading212.com/en?hl=GB");
    }

    @Test
    public void pressaTest(){

        HomePage homePage = new HomePage(driver);
        homePage.pressSection.scrollToElement();
        System.out.println(homePage.pressSection.isLogoThere());
        System.out.println(homePage.pressSection.getContent());
        homePage.pressSection.clickButton();
    }

}
