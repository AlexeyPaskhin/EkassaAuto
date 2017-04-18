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
    private WebElement submitLoanButton;
    @FindBy(xpath = "//span[@ng-click='showAgreements($event)']") private WebElement linkWarunkami;

    public MainPage(WebDriver driver) {
        super(driver);
        if (!"EKassa - Szybka Pożyczka Przez Internet".equals(driver.getTitle())) {
            throw new IllegalStateException("This is not the main page");
        }
        initPageElements();
    }

    private void initPageElements() {
        mainRegForm = new Form(findWithXPath("//form"));
    }

    MainPage submitInvalRegForm() {
        mainRegForm.submit(submitLoanButton);
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
        return input.getAttribute("class").contains("invalid");
    }


    public boolean fieldWithChecboxIsInvalid() { return mainRegForm.getElementClass(linkWarunkami).contains("error");
    }
}
