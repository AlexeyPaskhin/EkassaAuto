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
public class RegPage extends AbstractPage {
    private Form regForm;

    @FindBy(xpath = "//input[@name='name']") WebElement nameField;
    @FindBy(xpath = "//input[@name='lastName']") WebElement lastNameField;
    @FindBy(xpath = "//input[@name='pesel']") WebElement peselField;
    @FindBy(xpath = "(//input[@name='phone'])[2]") WebElement registrationPhoneField;
    @FindBy (xpath = "//input[@name='email']") WebElement emailField;
    @FindBy(xpath = "(//input[@name='password'])[2]") WebElement passwordField;
    @FindBy(xpath = "//input[@name='passwordConfirm']") WebElement passConfirmField;
    @FindBy(xpath = "(//md-checkbox[@name='agreedToConditions'])[2]") WebElement registrationTermsCheckBox;
    @FindBy(xpath = "(//button[@type='submit'])[2]") WebElement regButton;
    @FindBy(xpath = "//span[@ng-click='showAgreements($event)']") WebElement linkRegTerms;
    @FindBy(xpath = "//md-checkbox[@name='agreedToMarketingDistribution']") WebElement marketingCheckbox;
    @FindBy(xpath = "//span[@ng-click='showMarketingAgreements($event)']") WebElement linkMarketingTerms;

    public RegPage(WebDriver driver) {
        super(driver);
        initPageElements();
    }

    private void initPageElements() {
        regForm = new Form(findWithXPath("(//form)[2]"));
    }

    RegPage inputToName(String text) {
        regForm.setToFieldWithOverlay(nameField, text);
        return this;
    }
    RegPage inputToLastName(String text) {
        regForm.setToFieldWithOverlay(lastNameField, text);
        return this;
    }
    RegPage inputToPesel(String text) {
        regForm.setToFieldWithOverlay(peselField, text);
        return this;
    }
    RegPage inputToRegistrationPhone(String text) {
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

        regForm.setToFieldWithOverlay(registrationPhoneField, text);
        return this;
    }

    String getValue(WebElement field) {
        return regForm.getFieldValue(field);
    }

    public RegPage inputToEmailField(String text) {
        regForm.setToFieldWithOverlay(emailField, text);
        return this;
    }

    public RegPage inputToPasswordField(String text) {
        regForm.setToFieldWithOverlay(passwordField, text);
        return this;
    }

    public RegPage inputToPassConfirmField(String pass) {
        regForm.setToFieldWithOverlay(passConfirmField, pass);
        return this;
    }

    RegPage fillRegFormWithValidData() {
        inputToName(name)
                .inputToLastName(surname)
                .inputToPesel(pesel)
                .inputToRegistrationPhone(regPhone)
                .inputToEmailField(email)
                .inputToPasswordField(password)
                .inputToPassConfirmField(password);
        if(userCredentialsDAO.getUserByEmail(email).size()>0) {
            userCredentialsDAO.deleteUserByEmail(email);
        }
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
        regForm.markCheckBoxWithOverlay(registrationTermsCheckBox);
        return this;
    }

    RegPage unmarkRegCheckbox() {
        regForm.uncheck(registrationTermsCheckBox);
        return this;
    }

    RegPage unmarkMarketingCheckbox() {
        regForm.uncheck(marketingCheckbox);
        return this;
    }

    RegPage submitInvalRegForm(){
        regForm.submit(regButton);
        return this;
    }

    public RegPage submitValidRegForm() {
        regForm.submit(regButton);
        return this;
    }

    public SmsVerificationPage submitRegFormWithVerifiedData() {
//        if (!regButton.isEnabled()) {
            findWithXPath("(//div[@class='auth__overlay'])[2]").click();
//        }
        fillRegFormWithValidData()
                .markRegCheckbox()
                .submitValidRegForm()
                .waitForAngularRequestsToFinish();
        return new SmsVerificationPage(driver);
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

    RegPage waitForClosingTerms() {
        explWait.until(invisibilityOfElementLocated(By.xpath("//md-dialog")));
        return this;
    }
    RegPage waitForOpeningTerms() {
        explWait.until(visibilityOfElementLocated(By.xpath("//md-dialog")));
        return this;
    }

    public RegPage clickMarketingTerms() {
        linkMarketingTerms.click();
        return this;
    }


}
