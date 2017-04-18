import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by user on 10.03.2017.
 */
public class MainPage extends AbstractPage {
    private Form mainRegForm;

    @FindBy(xpath = "//input[@name='phone']")
    private WebElement input;
    @FindBy(xpath = "//md-checkbox[@ng-model='agreedToConditions']")
    private WebElement mainRegCheckbox;
    @FindBy(xpath = "//*[@type='submit']")
    protected WebElement submitLoanButton;
    @FindBy(xpath = "//span[@ng-click='showAgreements($event)']")
    private WebElement linkTerms;
    @FindBy(xpath = "//md-dialog[@class='p-15 _md md-dialog-fullscreen md-transition-in']")
    protected WebElement frameOfTerms;


    MainPage(WebDriver driver) {
        super(driver);
        driver.get("http://test.ekassa.com");
        if (!"EKassa - Szybka Pożyczka Przez Internet".equals(driver.getTitle())) {
            throw new IllegalStateException("This is not the main page");
        }
        initPageElements();
    }

    private void initPageElements() {
        mainRegForm = new Form(findWithXPath("//form"));
    }

    MainPage submitInvalRegForm() {
        mainRegForm.submit();
        return this;
    }

    RegPage submitNewUserRegForm() {
//        mainRegForm.submit(submitLoanButton);
        mainRegForm.submit();
        return new RegPage(driver);
    }

    PasswordPage submitExistUserRegForm() {
        mainRegForm.submit(submitLoanButton);
        return new PasswordPage(driver);
    }

    MainPage markRegCheckbox() {
        mainRegForm.markCheckBox(mainRegCheckbox);
        return this;
    }

    MainPage uncheckRegChBox() {
        mainRegForm.uncheck(mainRegCheckbox);
        return this;
    }

    MainPage inputToPhone(String data) {
//        input.sendKeys(data);
        mainRegForm.set(input, data);
        return this;
    }

//    void pressEnter() {
//        findWithXPath("/html").sendKeys(Keys.ENTER);
//    }

    String getValueFromPhoneInput() {
        return mainRegForm.getFieldValue(input);
    }

    boolean inputIsInvalid() {
        return mainRegForm.getElementClass(input).contains("invalid");
    }

    boolean fieldWithCheckboxIsInvalid() {
        return mainRegForm.getElementClass(linkTerms).contains("error");
    }

    MainPage clickTheTerms() {
        linkTerms.click();
        return this;
    }

    MainPage exitFromTerms() {
        findWithXPath("/html").sendKeys(Keys.ESCAPE);
        return this;
    }
}
