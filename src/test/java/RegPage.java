import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;


/**
 * Created by user on 13.04.2017.
 */
public class RegPage extends AbstractPage {
    private Form regForm;

    @FindBy(xpath = "//input[@name='name']") WebElement nameField;
    @FindBy(xpath = "//input[@name='lastName']") WebElement lastNameField;
    @FindBy (xpath = "//input[@name='email']") WebElement emailField;
    @FindBy(xpath = "//input[@name='password']") WebElement passwordField;
    @FindBy(xpath = "//input[@name='passwordConfirm']") WebElement passConfirmField;
    @FindBy(xpath = "//md-checkbox[@name='agreedToConditions']") WebElement termsCheckBox;
    @FindBy(xpath = "//button[@type='submit']") WebElement regButton;
    @FindBy(xpath = "//span[@ng-click='showAgreements($event)']") WebElement linkRegTerms;
    @FindBy(xpath = "//md-checkbox[@name='agreedToMarketingDistribution']") WebElement marketingCheckbox;
    @FindBy(xpath = "//span[@ng-click='showMarketingAgreements($event)']") WebElement linkMarketingTerms;

    public RegPage(WebDriver driver) {
        super(driver);
        initPageElements();
    }

    private void initPageElements() {
        regForm = new Form(findWithXPath("//form"));
    }

    RegPage inputToName(String text) {
        regForm.set(nameField, text);
        return this;
    }
    RegPage inputToLastName(String text) {
        regForm.set(lastNameField, text);
        return this;
    }

    String getValue(WebElement field) {
        return regForm.getFieldValue(field);
    }

    public RegPage inputToEmailField(String text) {
        regForm.set(emailField, text);
        return this;
    }

    public RegPage inputToPasswordField(String text) {
        regForm.set(passwordField, text);
        return this;
    }

    public RegPage inputToPassConfirmField(String pass) {
        regForm.set(passConfirmField, pass);
        return this;
    }

    RegPage fillRegFormWithValidData() {
        inputToName(Registration.name)
                .inputToLastName(Registration.surname)
                .inputToEmailField(Registration.email)
                .inputToPasswordField(Registration.password)
                .inputToPassConfirmField(Registration.password);
        return this;
    }

    RegPage setBlankValuesToRegForm() {
        inputToName("")
                .inputToLastName("")
                .inputToEmailField("")
                .inputToPasswordField("")
                .inputToPassConfirmField("");
        return this;
    }

    RegPage markRegCheckbox() {
        regForm.markCheckBox(termsCheckBox);
        return this;
    }

    RegPage unmarkRegCheckbox() {
        regForm.uncheck(termsCheckBox);
        return this;
    }

    RegPage submitInvalRegForm(){
        regForm.submit(regButton);
        return this;
    }

    public void waitForReaction() throws InterruptedException {
        Thread.sleep(1000);
        explWait.until(or(elementToBeClickable(regButton),
                elementToBeClickable(By.xpath("//input[@name='smsVerificationCode']"))));
    }

    public RegPage clickRegTerms() {
        linkRegTerms.click();
        return this;
    }

    public RegPage waitForClosingTerms() {
        explWait.until(invisibilityOfElementLocated(By.xpath("//md-dialog")));
        return this;
    }

    public RegPage clickMarketingTerms() {
        linkMarketingTerms.click();
        return this;
    }
}
