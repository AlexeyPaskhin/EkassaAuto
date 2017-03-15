//import net.lightbody.bmp.proxy.ProxyServer;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by user on 10.03.2017.
 */
public class MainPage extends AbstractPage {
    public MainPage(WebDriver driver) {
        super(driver);
        if (!"EKassa - Szybka Pożyczka Przez Internet".equals(driver.getTitle())) {
            throw new IllegalStateException("This is not the main page");
        }
//        PageFactory.initElements(driver, this);
    }

//    MainPage goToMainPage() {
//        driver.get("http://test.ekassa.com");
//        return this;
//    }

    MainPage submitRegForm() {
        findWithXPath("//*[@id=\"first-task\"]").submit();
        return this;
    }

    MainPage markCheckbox() {
        findWithCSS("md-checkbox[aria-label='Zgadzam się z warunkami']").click();
        return this;
    }

    void pressEnter() {
        findWithXPath("/html").sendKeys(Keys.ENTER);
    }

}
