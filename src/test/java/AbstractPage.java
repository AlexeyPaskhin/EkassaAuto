import net.lightbody.bmp.proxy.ProxyServer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by user on 14.03.2017.
 */
public class AbstractPage {
    public WebDriver driver;
    ProxyServer server;
    WebDriver.Options options;


    WebElement findWithXPath(String text) {
        WebElement el = driver.findElement(By.xpath(text));
        return el;
    }
    WebElement findWithCSS(String text) {
        WebElement el = driver.findElement(By.cssSelector(text));
        return el;
    }
}
