import elementfactory.base.DriverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest {
    private WebDriver driver;

    @BeforeMethod
    private void setUpDriver() {
        driver = new ChromeDriver();
        DriverUtils.resetDriverTimeout(driver);

        driver.get("https://www.trading212.com/en/login");
    }

    @Test
    public void inputTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.submitBtn.ensureVisibleAndClick();
        System.out.println(loginPage.login.hasError());
        System.out.println(loginPage.login.getErrorMessage());
    }

    @AfterMethod
    private void tearDown() {
        driver.quit();
    }
}
