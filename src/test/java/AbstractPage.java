import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 14.03.2017.
 */
public abstract class AbstractPage {
    protected WebDriver driver;
    JavascriptExecutor jse;
    public AbstractPage (WebDriver driver) {
        this.driver = driver;
        this.jse = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    WebElement findWithXPath(String text) {
        WebElement el = driver.findElement(By.xpath(text));
        return el;
    }
    WebElement findWithCSS(String text) {
        WebElement el = driver.findElement(By.cssSelector(text));
        return el;
    }



    void waitFor() {

    }


}
