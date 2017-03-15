import net.lightbody.bmp.proxy.ProxyServer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by user on 14.03.2017.
 */
public abstract class AbstractPage {


    private WebDriver driver;
    public AbstractPage (WebDriver driver) {
        this.driver = driver;
    }

    WebElement findWithXPath(String text) {
        WebElement el = driver.findElement(By.xpath(text));
        return el;
    }
    WebElement findWithCSS(String text) {
        WebElement el = driver.findElement(By.cssSelector(text));
        return el;
    }

    void waiterFor() {

    }
}
