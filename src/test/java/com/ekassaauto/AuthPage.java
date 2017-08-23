package com.ekassaauto;

import com.ekassaauto.database.entities.UserCredential;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.sql.SQLException;
import java.util.List;

import static com.ekassaauto.Registration.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;


/**
 * Created by user on 13.04.2017.
 */
public class AuthPage extends AbstractPage {
    private Form regForm;

    @FindBy(xpath = "//input[@name='name']") WebElement nameField;
    @FindBy(xpath = "//input[@name='lastName']") WebElement lastNameField;
    @FindBy(xpath = "//input[@name='pesel']") WebElement peselField;
    @FindBy(xpath = "//input[@name='phone']") WebElement registrationPhoneField;
    @FindBy (xpath = "//input[@name='email']") WebElement emailField;
    @FindBy(xpath = "(//input[@name='password'])[2]") WebElement passwordField;
    @FindBy(xpath = "//input[@name='passwordConfirm']") WebElement passConfirmField;
    @FindBy(xpath = "//md-checkbox[@name='agreedToConditions']") WebElement termsCheckBox;
    @FindBy(xpath = "//button[@type='submit']") WebElement authForwardButton;
    @FindBy(xpath = "//span[@ng-click='showAgreements($event)']") WebElement linkRegTerms;
    @FindBy(xpath = "//md-checkbox[@name='agreedToMarketingDistribution']") WebElement marketingCheckbox;
    @FindBy(xpath = "//span[@ng-click='showMarketingAgreements($event)']") WebElement linkMarketingTerms;

    public AuthPage(WebDriver driver) {
        super(driver);
        initPageElements();
    }

    private void initPageElements() {
        regForm = new Form(findWithXPath("//form"));
    }

    AuthPage inputToName(String text) {
        regForm.setToFieldWithOverlay(nameField, text);
        return this;
    }
    AuthPage inputToLastName(String text) {
        regForm.setToFieldWithOverlay(lastNameField, text);
        return this;
    }
    AuthPage inputToPesel(String text) {
        regForm.setToFieldWithOverlay(peselField, text);
        return this;
    }
    AuthPage enterUnregisteredPhoneToPhoneInput(String text) {
        try {
            List<UserCredential> requestedUserCredentials = userCredentialsDAO.getUserByPhone(regPhone);
            if (requestedUserCredentials.size() == 1) {
                userCredentialsDAO.deleteUserByPhone(regPhone);
            } else if (requestedUserCredentials.size() > 1) {
                throw new AssertionError("There are more than 1 unique phone number located in the usercredentials table!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //todo удалять процессы так же с камунды с данным номером

        regForm.set(registrationPhoneField, text);
        return this;
    }

    String getValue(WebElement field) {
        return regForm.getFieldValue(field);
    }

    public AuthPage inputToEmailField(String text) {
        regForm.setToFieldWithOverlay(emailField, text);
        return this;
    }

    public AuthPage inputToPasswordField(String text) {
        regForm.setToFieldWithOverlay(passwordField, text);
        return this;
    }

    public AuthPage inputToPassConfirmField(String pass) {
        regForm.setToFieldWithOverlay(passConfirmField, pass);
        return this;
    }

    AuthPage fillRegFormWithValidData() {
        inputToName(name)
                .inputToLastName(lastName)
//                .inputToPesel(pesel)
                .enterUnregisteredPhoneToPhoneInput(regPhone);
//                .inputToEmailField(email)
//                .inputToPasswordField(password)
//                .inputToPassConfirmField(password);
        if(userCredentialsDAO.getUserByEmail(email).size()>0) {
            userCredentialsDAO.deleteUserByEmail(email);
        }
        return this;
    }

    AuthPage setBlankValuesToRegForm() {
        inputToName("")
                .inputToLastName("")
                .inputToEmailField("")
                .inputToPasswordField("")
                .inputToPassConfirmField("");
        return this;
    }

    AuthPage markRegCheckbox() {
        regForm.markCheckBox(termsCheckBox);
        return this;
    }

    AuthPage unmarkRegCheckbox() {
        regForm.uncheck(termsCheckBox);
        return this;
    }

    AuthPage unmarkMarketingCheckbox() {
        regForm.uncheck(marketingCheckbox);
        return this;
    }

    AuthPage submitInvalRegForm(){
        regForm.submit(authForwardButton);
        return this;
    }

    public AuthPage submitValidRegForm() {
        regForm.submit(authForwardButton);
        return this;
    }

    public AboutMePage submitRegFormWithVerifiedData() {
//        if (!authForwardButton.isEnabled()) {
//            findWithXPath("(//div[@class='auth__overlay'])[2]").click();
//        }
        fillRegFormWithValidData()
                .markRegCheckbox()
                .submitValidRegForm()
                .waitForAngularRequestsToFinish();
        return new AboutMePage(driver);
    }

    public void waitForReaction() throws InterruptedException {
        Thread.sleep(1000);
        explWait.until(or(elementToBeClickable(authForwardButton),
                elementToBeClickable(By.xpath("//input[@name='smsVerificationCode']"))));
    }

    public AuthPage clickRegTerms() {
        linkRegTerms.click();
        return this;
    }

    AuthPage waitForClosingTerms() {
        explWait.until(invisibilityOfElementLocated(By.xpath("//md-dialog")));
        return this;
    }
    AuthPage waitForOpeningTerms() {
        explWait.until(visibilityOfElementLocated(By.xpath("//md-dialog")));
        return this;
    }

    public AuthPage clickMarketingTerms() {
        linkMarketingTerms.click();
        return this;
    }


}
