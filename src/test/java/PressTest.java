import elementfactory.base.DriverUtils;
import elements.Press;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;

public class PressTest extends TestClass{


    @BeforeMethod
    private void setup(){
        driver.get("https://www.trading212.com/en?hl=GB");
    }

    @DataProvider(name = "pressIndexes")
    public Object[][] pressIndexes(){
        return new Object[][] {{0}, {1}, {2}, {3}, {4}};
    }

    @Test(dataProvider = "pressIndexes")
    public void pressaTest(Integer index) throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        Press press = homePage.pressList.get(index);
        press.scrollToElementCenter();
        Assert.assertTrue(press.isLogoThere());
        press.clickReadMoreButton();
        Thread.sleep(2000);
    }

}
