package com.ekassaauto;

import com.ekassaauto.PageObjects.AuthPage;
import org.testng.annotations.Test;

import static com.ekassaauto.Registration.password;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by user on 01.09.2017.
 */
public class Password {

    private AuthPage authPage;

    //todo
    @Test(priority = 3)
    public void equalityValidationOfPasswordsWhileEditingMainPassField() throws InterruptedException {
        authPage.fillAuthFormForRegistrationWithValidData()
                .inputToPasswordField(password + "a")
                .moveFromAField(authPage.passwordField);   //этот шаг необязательный, но селениум слишком быстро проверяет цвет и приходится что то еще сделать))
        assertTrue(authPage.fieldBorderIsRed(authPage.passConfirmField));
        authPage.submitInvalRegForm()
                .waitForAngularRequestsToFinish();
        assertEquals(authPage.findElementsByXPath("//*[contains(text(), 'Hasła wprowadzone nie pasują do siebie!')]").size(),
                1, "Error message isn't displayed!");
    }
}
