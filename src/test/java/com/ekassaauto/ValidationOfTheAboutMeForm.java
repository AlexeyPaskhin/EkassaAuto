package com.ekassaauto;

import com.ekassaauto.PageObjects.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

import static com.ekassaauto.Registration.*;
import static org.testng.Assert.*;

/**
 * Created by user on 01.09.2017.
 */
public class ValidationOfTheAboutMeForm {

    private AuthPage authPage;

    //todo
    @Test(priority = 3)
    public void registrationWithEarlierUsedEmail() {
        authPage.fillAuthFormForRegistrationWithValidData()
                .markAuthCheckbox()
                .inputToEmailField(plainUsersDAO.getRegisteredEmail())
                .submitInvalRegForm()
                .customWaitForPerformingJS();
        WebElement[] regInputs = {authPage.nameField, authPage.lastNameField, authPage.emailField,
                authPage.passwordField, authPage.passConfirmField};
        try {
            for (WebElement el : regInputs) {
                assertTrue(el.isDisplayed());
            }
        } catch (NoSuchElementException e) {
            authPage = authPage.goToNewAuthPage();
            throw new AssertionError("Registration form with already registered email was submitted");
        }
        assertEquals(authPage.findElementsByXPath("//*[contains(text(), 'Użytkownik z tym e-mail jest już zarejestrowany')]").size(),
                1, "Error message isn't displayed!");
    }

    //todo
    @Test(priority = 3)
    //при передаче на бэк срабатывает трим - обрезка пробелов, поэтому их ввод в начале конце допустим
    public void enteringEmailWithSpacesAtTheBeginning() {
        authPage.inputToEmailField("   a.paskhin1@gmail.com")
                .moveFromAField(authPage.emailField);
        assertEquals(authPage.getValue(authPage.emailField), "a.paskhin1@gmail.com", "Wrong data input to email field!");
        assertFalse(authPage.fieldIsInvalid(authPage.emailField), "Permissible characters aren't allowed at the email field!");
        assertFalse(authPage.fieldBorderIsRed(authPage.emailField), "Border of valid field is highlighted with red color!");
    }

    //todo
    @Test(priority = 3)
    //при передаче на бэк срабатывает трим - обрезка пробелов, поэтому их ввод в начале конце допустим
    public void enteringEmailWithSpacesAtTheEnd() {
        authPage.inputToEmailField("a.paskhin1@gmail.com  ")
                .moveFromAField(authPage.emailField);
        assertEquals(authPage.getValue(authPage.emailField), "a.paskhin1@gmail.com", "Wrong data input to email field!");
        assertFalse(authPage.fieldIsInvalid(authPage.emailField), "Permissible characters aren't allowed at the email field!");
        assertFalse(authPage.fieldBorderIsRed(authPage.emailField), "Border of valid field is highlighted with red color!");
    }

    //todo
    @Test(priority = 3)
    //видимо селениум видоизменяет кириллицу после "@" в малопонятные символы, поэтому будем считать, что ожидаем вводимую букву увидеть такой, как написано в "expected"
    public void positiveValidationOfEmailField() {
        authPage.inputToEmailField("a.paskhin-@gmail-ы.coms")
                .moveFromAField(authPage.emailField);
        assertEquals(authPage.getValue(authPage.emailField), "a.paskhin-@xn--gmail--ntf.coms", "Wrong data input to email field!");
        assertFalse(authPage.fieldIsInvalid(authPage.emailField), "Permissible characters aren't allowed at the email field!");
        assertFalse(authPage.fieldBorderIsRed(authPage.emailField), "Border of valid field is highlighted with red color!");
    }

    //todo
    @Test(priority = 3, dataProvider = "emailValidation", dataProviderClass = DataProviders.class)
    public void negativeValidationOfEmailField(String text) {
        authPage.inputToEmailField(text)
                .moveFromAField(authPage.emailField);
        assertTrue(authPage.fieldIsInvalid(authPage.emailField), "Impermissible characters are allowed at the email field!");
        assertTrue(authPage.fieldBorderIsRed(authPage.emailField), "Border of invalid field isn't highlighted with red color!");
    }
}
