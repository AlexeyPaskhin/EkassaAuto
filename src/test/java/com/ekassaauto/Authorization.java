package com.ekassaauto;

import com.ekassaauto.PageObjects.AboutMePage;
import com.ekassaauto.PageObjects.AuthPage;
import com.ekassaauto.PageObjects.DataProviders;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static com.ekassaauto.Registration.*;
import static org.testng.Assert.*;

/**
 * Created by user on 01.09.2017.
 */
public class Authorization {

    private AuthPage authPage;
    private AboutMePage aboutMePage;

    @Test(priority = 7)
    public void successfulSmsCodeConfirmation() throws SQLException {
        aboutMePage = smsVerificationPage.submitSmsCodeFormWithRightCode();
        assertTrue(aboutMePage.breadcrumbs.isDisplayed(),
                "Page 'About me' isn't displayed after submitting right sms code!");
        assertEquals(userCredentialsDAO.getUserByPhone(regPhone).size(), 1,
                "An entry with the phone isn't added to the userCredentials table!");
        assertEquals(userCredentialsDAO.getUserByEmail(email).size(), 1,
                "An entry with the email isn't added to the plainUsers table!");
    }

    @Test(priority = 6)
    public void refreshingSmsCode() throws InterruptedException {
        String initialCode = sentSmsDAO.getSmsCodeByPhone(regPhone);
        smsVerificationPage.refreshSmsCode()
                .waitForAngularRequestsToFinish();
        String newCode = sentSmsDAO.getSmsCodeByPhone(regPhone);

        for (int i = 0; i < 10; i++) {
            if (newCode.equals(initialCode)) {
                Thread.sleep(500);
                newCode = sentSmsDAO.getSmsCodeByPhone(regPhone); //ждем на всякий случай, чтоб обновился код в базе
            } else break;
        }
        assertNotEquals(initialCode, newCode, "Sms confirmation code isn't refreshed!");
    }

    @Test(priority = 6)
    public void enteringWrongSmsCode() {
        int code = 1000;
        if (String.valueOf(code).equals(sentSmsDAO.getSmsCodeByPhone(regPhone))) {
            code++;
        }
        smsVerificationPage.inputToCodeField("" + code)
                .submitInvalSmsCodeForm()
                .customWaitForPerformingJS();
        try {
            assertTrue(smsVerificationPage.smsCodeInput.isDisplayed());
        } catch (NoSuchElementException e) {
            smsVerificationPage.goToNewSmsCodePage();
            throw new AssertionError("Invalid sms confirmation form was submitted");
        }
        assertEquals(smsVerificationPage.findElementsByXPath("//*[contains(text(), 'Hasło zostało wprowadzone niepoprawnie')]").size(),
                1, "Error message isn't displayed!");
        assertFalse(smsVerificationPage.findWithXPath("//*[contains(text(), 'Hasło zostało wprowadzone niepoprawnie')]").getText().contains("wysłane nowe"),
                "Excess message about sending new code is displayed - actually new code isn't sent!");
    }

    @Test(priority = 6, dataProvider = "invalidSmsCodes", dataProviderClass = DataProviders.class)
    public void enteringInvalidSmsCode(String code) {
        smsVerificationPage.inputToCodeField(code)
                .submitInvalSmsCodeForm()
                .customWaitForPerformingJS();
        try {
            assertTrue(smsVerificationPage.fieldBorderIsRed(smsVerificationPage.smsCodeInput),
                    "Invalid sms code input field doesn't have red color!");
        } catch (NoSuchElementException e) {
            smsVerificationPage.goToNewSmsCodePage();
            throw new AssertionError("Invalid sms confirmation form was submitted");
        }
        assertEquals(smsVerificationPage.findElementsByXPath("//*[contains(text(), 'Hasło zostało wprowadzone niepoprawnie')]").size(),
                1, "Error message isn't displayed!");
        assertFalse(smsVerificationPage.findWithXPath("//*[contains(text(), 'Hasło zostało wprowadzone niepoprawnie')]").getText().contains("wysłane nowe"),
                "Excess message about sending new code is displayed - actually new code isn't sent!");
    }

    @Test(priority = 5)
    public void creationSmsCodeInDB() {
        assertEquals(sentSmsDAO.getSmsCodeEntryByPhone(regPhone).size(), 1,
                "Sms code for confirmation was not sent!");
    }

    @Test(priority = 4)
    public void visibilityOfSmsCodeField() {
        aboutMePage = authPage.submitAuthFormForRegistrationWithVerifiedData();
        assertTrue(smsVerificationPage.smsCodeSubmitButton.isDisplayed(), "The block for sms code isn't displayed!");
    }
}
