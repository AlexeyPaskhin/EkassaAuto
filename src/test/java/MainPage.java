//import net.lightbody.bmp.proxy.ProxyServer;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by user on 10.03.2017.
 */
public class MainPage extends AbstractPage {

    void goToMainPage() {
        driver.get("http://test.ekassa.com");
    }


    void submitRegForm() {
        findWithXPath("//*[@id=\"first-task\"]").submit();
    }

    void markCheckbox() {
        findWithCSS("md-checkbox[aria-label='Zgadzam się z warunkami']").click();
    }

    void pressEnter() {
        findWithXPath("/html").sendKeys(Keys.ENTER);
    }

}
