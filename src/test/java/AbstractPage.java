import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by user on 14.03.2017.
 */
public abstract class AbstractPage {
    protected WebDriver driver;
    WebDriverWait explWait;
    JavascriptExecutor jse;
//    @FindBy(xpath = "//div[@class='preloader ng-scope']") WebElement loader;
    public AbstractPage (WebDriver driver) {
        this.driver = driver;
        explWait = new WebDriverWait(driver, 10);
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


    public boolean fieldIsInvalid(WebElement field){
        return field.getAttribute("class").contains("invalid");
    }

    public AbstractPage moveFromAField(WebElement field) {
        field.click();
        field.sendKeys(Keys.TAB);
        return this;
    }

    public boolean fieldBorderIsRed(WebElement field) {
        return field.getCssValue("border-color").equals("rgb(221, 44, 0)");
    }

    public boolean elementIsRed(WebElement element) {
//        System.out.println(element.getCssValue("color"));
        return element.getCssValue("color").equals("rgba(255, 0, 0, 1)");
    }
}
