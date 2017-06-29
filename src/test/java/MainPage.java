import org.openqa.selenium.*;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

/**
 * Created by user on 10.03.2017.
 */
public class MainPage extends AbstractPage {
    private Form mainRegForm;
    @FindBy(xpath = "//input[@name='phone']") protected WebElement input;
    @FindBy(xpath = "//*[@type='submit']") WebElement submitPDLButton;
    @CacheLookup
    @FindBy(xpath = "//md-dialog[@aria-label='Potwierdzam, że ...']") protected WebElement mdDialogOfAccessToPersData;
    @CacheLookup
    @FindBy(xpath = "//md-dialog[@aria-label='Chwilówka w ...']") protected WebElement mdDialogOfLoanInfo;
    @FindBy(xpath = "//md-checkbox[@ng-model='agreedToConditions']") private WebElement mainRegCheckbox;
    @FindBy(xpath = "//span[@ng-click='showAgreements($event)']") protected WebElement linkTerms;
    @FindBy(xpath = "//span[@ng-click='showLoanInfo($event)']") private WebElement linkLoanInfo;
    @FindBy(xpath = "(//*[@type='submit'])[2]")  WebElement submitConsButton;


    MainPage(WebDriver driver) {
        super(driver);
        driver.get("http://test.ekassa.com");
        waitForOpennessOfCalc();
        if (!"Pożyczki na raty: chwilówki ratalne online - Ekassa".equals(driver.getTitle())) {
            throw new IllegalStateException("This is not the main page");
        }
        initPageElements();
    }

    private void initPageElements() {
        mainRegForm = new Form(findWithXPath("//form"));
    }

    MainPage submitInvalPDLForm() {
        mainRegForm.submit();
        return this;
    }

//    RegPage submitNewUserRegForm() {
////        mainRegForm.submit(submitPDLButton);
//
//    }

    RegPage submitAnUnregNumber() {
        mainRegForm.set(input, Registration.regPhone)
                .markCheckBox(mainRegCheckbox)
                .submit(submitPDLButton);
        waitForPerformingJS();

//        explWait.until(numberOfElementsToBe(By.xpath("//div[@class='preloader ng-scope']"), 0));
////        explWait.until(presenceOfElementLocated(By.xpath("//input[@name='name']")));
//        explWait.until(elementToBeClickable(By.xpath("//input[@name='name']")));
        return new RegPage(driver);
    }

    PasswordPage submitExistUserRegForm() {
        mainRegForm.submit(submitPDLButton);
        return new PasswordPage(driver);
    }

    MainPage markPDLCheckbox() {
        mainRegForm.markCheckBox(mainRegCheckbox);
        return this;
    }

    MainPage uncheckPDLChBox() {
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
        explWait.until(presenceOfElementLocated(By.xpath("//md-dialog[@aria-label='Potwierdzam, że ...']")));
        return this;
    }

    MainPage clickLoanInfo() {
        linkLoanInfo.click();
        explWait.until(presenceOfElementLocated(By.xpath("//md-dialog[@aria-label='Chwilówka w ...']")));
        return this;
    }

    void waitForReaction() throws InterruptedException {
        Thread.sleep(1000);
        explWait.until(or(elementToBeClickable(submitPDLButton),
                elementToBeClickable(By.xpath("//input[@name='name']"))));
    }

    MainPage waitForClosingTerms() {
        explWait.until(invisibilityOf(mdDialogOfAccessToPersData));
        return this;
    }

    MainPage waitForClosingLoanInfo() {
        explWait.until(invisibilityOf(mdDialogOfLoanInfo));
        return this;
    }

    MainPage waitForOpennessOfCalc() {
        explWait.until(invisibilityOf(findWithXPath("//div[text()='Chwilówki']")));
        return this;
    }

    MainPage waitPhonePdlInputIsAccessible() {
        explWait.until(elementToBeClickable(input));
        return this;
    }
}
