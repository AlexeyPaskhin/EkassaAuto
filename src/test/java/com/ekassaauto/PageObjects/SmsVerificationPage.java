package com.ekassaauto.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.ekassaauto.Registration.*;

/**
 * Created by user on 29.06.2017.
 */
public class SmsVerificationPage extends AbstractPage {
    private Form smsCodeForm;
    @FindBy(xpath = "//*[contains(text(), 'Proszę sprawdzić SMS')]")
    public WebElement smsCodeSubmitButton;
    @FindBy(xpath = "//input[@name='smsVerificationCode']")
    public WebElement smsCodeInput;
    @FindBy(xpath = "//i[@class='fa fa-refresh']") WebElement refreshCodeButton;

    public SmsVerificationPage(WebDriver driver) {
        super(driver);
        initPageElements();
    }

    private void initPageElements() {
        smsCodeForm = new Form(findWithXPath("//form[@name='verificationSms']"));
    }

    public SmsVerificationPage inputToCodeField(String code) {
        smsCodeForm.set(smsCodeInput, code);
        return this;
    }

    public SmsVerificationPage submitInvalSmsCodeForm(){
        smsCodeForm.submit(smsCodeSubmitButton);
        return this;
    }

    public AboutMePage submitSmsCodeFormWithRightCode() {
        inputToCodeField(sentSmsDAO.getSmsCodeByPhone(regPhone));
        smsCodeForm.submit(smsCodeSubmitButton);
        waitForAngularRequestsToFinish();
        return new AboutMePage(driver);
    }

    public SmsVerificationPage refreshSmsCode() {
        refreshCodeButton.click();
        return this;
    }
    
    public SmsVerificationPage goToNewSmsCodePage() {
        new MainPage(driver).submitAnUnregisteredNumberThroughPDLForm();
//        smsVerificationPage = authPage.submitAuthFormForRegistrationWithVerifiedData();
        return this;
    }

//    void waitForSendingSmsCode(String initCode, String newCode) {
//        explWait.until(new ExpectedCondition<Boolean>() {
//            @Override
//            public Boolean apply(WebDriver driver){
//
//                return true;    чисто шаблон себе пока сохранил
//            }
//        });
//    }

}
