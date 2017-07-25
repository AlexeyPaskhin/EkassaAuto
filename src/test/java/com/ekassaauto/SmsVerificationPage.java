package com.ekassaauto;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.ekassaauto.Registration.*;

/**
 * Created by user on 29.06.2017.
 */
public class SmsVerificationPage extends AbstractPage {
    private Form smsCodeForm;
    @FindBy(xpath = "//*[contains(text(), 'Proszę sprawdzić SMS')]") WebElement smsCodeSubmitButton;
    @FindBy(xpath = "//input[@name='smsVerificationCode']") WebElement smsCodeInput;
    @FindBy(xpath = "//i[@class='fa fa-refresh']") WebElement refreshCodeButton;

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

    AboutMePage submitSmsCodeFormWithRightCode() {
        inputToCodeField(sentSmsDAO.getSmsCodeByPhone(regPhone));
        smsCodeForm.submit(smsCodeSubmitButton);
        customWaitForPerformingJS();
        return new AboutMePage(driver);
    }

    SmsVerificationPage refreshSmsCode() {
        refreshCodeButton.click();
        return this;
    }
    
    SmsVerificationPage goToNewSmsCodePage() {
        mainPage = new MainPage(driver);
        regPage = mainPage.submitAnUnregisteredNumberThroughPDLForm();
        smsVerificationPage = regPage.submitRegFormWithVerifiedData();
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
