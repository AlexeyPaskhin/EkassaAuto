import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by user on 10.03.2017.
 */
public class PageObjectMethods {
    public WebDriver driver;
    public void goToMainPage() {
        driver.get("https://ekassa.com/#/");
    }

    WebElement findWithXPath(String text) {
        WebElement el = driver.findElement(By.xpath(text));
        return el;
    }

    void submitRegForm() {
        findWithXPath("//*[@id=\"first-task\"]").submit();
    }

    void markCheckbox() {

    }

}
