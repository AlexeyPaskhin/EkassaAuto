import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by user on 29.06.2017.
 */
public class SmsVerificationPage extends AbstractPage {
    private Form smsCodeForm;
    @FindBy(xpath = "//*[contains(text(), 'Proszę sprawdzić SMS')]") WebElement smsCodeSubmitButton;
    @FindBy(xpath = "//input[@name='smsVerificationCode']") WebElement smsCodeInput;

    public SmsVerificationPage(WebDriver driver) {
        super(driver);
        initPageElements();
    }

    private void initPageElements() {
        smsCodeForm = new Form(findWithXPath("//form[@name='verificationSms']"));
    }

    SmsVerificationPage inputToCodeField(String code) {
        smsCodeForm.set(smsCodeInput, code);
        return this;
    }

    SmsVerificationPage submitInvalSmsCodeForm(){
        smsCodeForm.submit(smsCodeSubmitButton);
        return this;
    }
    
    SmsVerificationPage goToNewSmsCodePage() {
        Registration.mainPage = new MainPage(driver);
        Registration.regPage = Registration.mainPage.submitAnUnregNumber();
        Registration.smsVerificationPage = Registration.regPage.submitRegFormWithVerifiedData();
        return this;
    }

}
