import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by user on 10.03.2017.
 */
public class MainPage extends AbstractPage {
    private Form mainRegForm;

    @FindBy(xpath = "//input[@name='phone']")
    protected WebElement input;
    @FindBy(xpath = "//md-checkbox[@ng-model='agreedToConditions']")
    private WebElement mainRegCheckbox;
    @FindBy(xpath = "//*[@type='submit']")
    protected WebElement submitLoanButton;
    @FindBy(xpath = "//span[@ng-click='showAgreements($event)']")
    private WebElement linkTerms;
    @CacheLookup
    @FindBy(xpath = "//md-dialog[@class='p-15 _md md-dialog-fullscreen md-transition-in']")
    protected WebElement mdDialogOfTerms;


    MainPage(WebDriver driver) {
        super(driver);
        driver.get("http://test.ekassa.com");
        waitForOpennessOfCalc();
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

//    RegPage submitNewUserRegForm() {
////        mainRegForm.submit(submitLoanButton);
//
//    }

    RegPage submitAnUnregNumber() {
        mainRegForm.set(input, Registration.regNumber)
                .markCheckBox(mainRegCheckbox)
                .submit();
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
        findWithXPath("//body").sendKeys(Keys.ESCAPE);
        return this;
    }

    void waitForReaction() throws InterruptedException {
        Thread.sleep(1000);
        explWait.until(or(elementToBeClickable(submitLoanButton),
                elementToBeClickable(By.xpath("(//div[@class='fb-login-button fb_iframe_widget'])"))));
    }

    MainPage waitForClosingTerms() {
        explWait.until(invisibilityOf(mdDialogOfTerms));
        return this;
    }

    public MainPage waitForOpennessOfCalc() {
        explWait.until(invisibilityOf(findWithXPath("//div[text()='Chwilówki']")));
        return this;
    }
}
