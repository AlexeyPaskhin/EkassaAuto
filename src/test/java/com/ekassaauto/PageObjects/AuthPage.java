package com.ekassaauto.PageObjects;

import com.ekassaauto.database.entities.aui.UserCredential;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.ekassaauto.Registration.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;


/**
 * Created by user on 13.04.2017.
 */
public class AuthPage extends AbstractPage {
    private Form authForm;

    private static final String smsCodeInputLocator = "//input[@name='smsVerificationCode']";

    @FindBy(xpath = "//input[@name='name']")
    public WebElement nameField;
    @FindBy(xpath = "//input[@name='lastName']")
    public WebElement lastNameField;
    @FindBy(xpath = "//input[@name='pesel']") WebElement peselField;
    @FindBy(xpath = "//input[@name='phone']")
    public WebElement phoneField;
    @FindBy (xpath = "//input[@name='email']")
    public WebElement emailField;
    @FindBy(xpath = "(//input[@name='password'])[2]")
    public WebElement passwordField;
    @FindBy(xpath = "//input[@name='passwordConfirm']")
    public WebElement passConfirmField;
    @FindBy(xpath = "//md-checkbox[@name='agreedToConditions']") public WebElement termsCheckBox;
    @FindBy(xpath = "//button[@type='submit']") WebElement authForwardButton;
    @FindBy(xpath = "//span[@ng-click='$ctrl.showAgreements($event)']")
    public WebElement linkRegTerms;
    @FindBy(xpath = "//md-checkbox[@name='agreedToMarketingDistribution']")
    public WebElement marketingCheckbox;
    @FindBy(xpath = "//span[@ng-click='$ctrl.showMarketingAgreements($event)']") WebElement linkMarketingTerms;
    @FindBy(xpath = smsCodeInputLocator) WebElement smsCodeField;

    public AuthPage(WebDriver driver) {
        super(driver);
        initPageElements();
    }

    private void initPageElements() {
        authForm = new Form(findWithXPath("//form"));
    }

    public AuthPage inputToSmsCodeField(String code) {
        authForm.set(smsCodeField, code);
        return this;
    }

    public String getValueFromPDLPhoneInput() {
        return authForm.getFieldValue(phoneField);
    }

    public AuthPage inputToName(String text) {
        authForm.set(nameField, text);
        return this;
    }
    public AuthPage inputToLastName(String text) {
        authForm.set(lastNameField, text);
        return this;
    }

    public AuthPage inputToPhoneField(String data) {
        authForm.set(phoneField, data);
//        pdlMainForm.setToFieldWithOverlay(pdlPhoneInput, data);
        return this;
    }

    AuthPage inputToPesel(String text) {
        authForm.setToFieldWithOverlay(peselField, text);
        return this;
    }
    AuthPage enterUnregisteredPhoneToPhoneInput(String text) {
            List<UserCredential> requestedUserCredentials = userCredentialsDAO.getUserByPhone(regPhone);
            if (requestedUserCredentials.size() == 1) {
                userCredentialsDAO.deleteUserByPhone(regPhone);
            } else if (requestedUserCredentials.size() > 1) {
                throw new AssertionError("There are more than 1 unique phone number located in the usercredentials table!");
            }
        //todo удалять процессы так же с камунды с данным номером

        authForm.set(phoneField, text);
        return this;
    }

    public AuthPage inputToEmailField(String text) {
        authForm.setToFieldWithOverlay(emailField, text);
        return this;
    }

    public AuthPage inputToPasswordField(String text) {
        authForm.setToFieldWithOverlay(passwordField, text);
        return this;
    }

    public AuthPage inputToPassConfirmField(String pass) {
        authForm.setToFieldWithOverlay(passConfirmField, pass);
        return this;
    }

    public AuthPage fillAuthFormForRegistrationWithValidData() {
        inputToName(name)
                .inputToLastName(lastName)
//                .inputToPesel(pesel)
                .enterUnregisteredPhoneToPhoneInput(regPhone);
//                .inputToEmailField(email)
//                .inputToPasswordField(password)
//                .inputToPassConfirmField(password);

//        if(userCredentialsDAO.getUserByEmail(email).size()>0) {
//            userCredentialsDAO.deleteUserByEmail(email);
//        }
        return this;
    }

    public AuthPage setBlankValuesToAuthForm() {
        inputToName("")
                .inputToLastName("")
                .inputToPhoneField("");
//                .inputToEmailField("")
//                .inputToPasswordField("")
//                .inputToPassConfirmField("");
        return this;
    }

    public AuthPage markTermsCheckbox() {
        authForm.markCheckBox(termsCheckBox);
        return this;
    }

    public AuthPage unmarkAuthCheckbox() {
        authForm.uncheck(termsCheckBox);
        return this;
    }

    public AuthPage unmarkMarketingCheckbox() {
        authForm.uncheck(marketingCheckbox);
        return this;
    }

    public AuthPage submitInvalRegForm(){
        authForm.submit(authForwardButton);
        return this;
    }

    public AboutMePage submitValidRegForm() {
        authForm.submit(authForwardButton);
        return new AboutMePage(driver);
    }

    public AboutMePage submitAuthFormForRegistrationWithVerifiedData() {
//        if (!authForwardButton.isEnabled()) {
//            findWithXPath("(//div[@class='auth__overlay'])[2]").click();
//        }
        fillAuthFormForRegistrationWithValidData()
                .markTermsCheckbox()
                .submitValidRegForm()
                .waitForAngularRequestsToFinish();
        return new AboutMePage(driver);
    }

//    public void waitForReaction() throws InterruptedException {
//        Thread.sleep(1000);
//        explWait.until(or(elementToBeClickable(authForwardButton),
//                elementToBeClickable(By.xpath("//input[@name='smsVerificationCode']"))));
//    }

    public AuthPage clickRegTerms() {
        linkRegTerms.click();
        return this;
    }

    public AuthPage waitForClosingTerms() {
        explWait.until(invisibilityOfElementLocated(By.xpath("//md-dialog[@aria-label='Potwierdzam, że ...']")));
        return this;
    }
    public AuthPage waitForOpeningTerms() {
        explWait.until(visibilityOfElementLocated(By.xpath("//md-dialog[@aria-label='Potwierdzam, że ...']")));
        return this;
    }

    public AuthPage clickMarketingTerms() {
        linkMarketingTerms.click();
        return this;
    }


}
