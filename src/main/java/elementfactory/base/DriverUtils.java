package elementfactory.base;

import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class DriverUtils {

    public static void disableDriverTimeout(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    public static void resetDriverTimeout(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }
}
