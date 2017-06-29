import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

import java.util.List;

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

    List<WebElement> findElementsByXPath(String locator) {
        return driver.findElements(By.xpath(locator));
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

    AbstractPage closeDialogWindow() {
        findWithXPath("//body").sendKeys(Keys.ESCAPE);
        return this;
    }

    public boolean fieldBorderIsRed(WebElement field) {
        return field.getCssValue("border-color").equals("rgb(221, 44, 0)");
    }

    public boolean elementIsRed(WebElement element) {
//        System.out.println(element.getCssValue("color"));
        return element.getCssValue("color").equals("rgba(255, 0, 0, 1)");
    }

    void goBack() {
        driver.navigate().back();
    }

    public boolean elementIsGreen(WebElement element) {
        return element.getCssValue("color").equals("rgba(67, 160, 71, 1)");
    }

    public boolean CheckboxIsMarked(WebElement checkbox) {
        return checkbox.getAttribute("aria-checked").equals("true");
    }

    public void waitForPerformingJS() {
        try {   // try - на случай, если js в каком то кейсе не начал выполнятся, попытка перехода на другую страницу не произвелась
            new WebDriverWait(driver, 1).until(numberOfElementsToBe(By.xpath("//div[@class='preloader ng-scope']"), 1));
        } catch (TimeoutException e) {
            return;
        }
        explWait.until(numberOfElementsToBe(By.xpath("//div[@class='preloader ng-scope']"), 0));
    }
}
