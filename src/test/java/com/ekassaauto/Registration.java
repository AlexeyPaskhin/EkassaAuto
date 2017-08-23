package com.ekassaauto;

import com.ekassaauto.database.PersistenceManager;
import com.ekassaauto.database.dao.InstWormCacheDAO;
import com.ekassaauto.database.dao.SentSmsDAO;
import com.ekassaauto.database.dao.UserCredentialsDAO;
import com.ekassaauto.database.dao.PlainUsersDAO;
import io.github.bonigarcia.wdm.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

/**
 * Created by user on 09.03.2017.
 */
@Test
public class Registration {
    protected static WebDriver driver;
    static String regPhone = "222222218", name = "Alex", lastName = "Paskhin", pesel = "92102107697",
            email = "a.paskhin@gmail.com", password = "111111a", socialNumber = "ATC339884",
            bankAccount = "77777777777777777777777775", contactPersonPhone = "123456789", postalCode = "00000",
            testString = "shutĄąĆćĘę-ŁłŃńÓóŚśŹźŻ", empPhoneField = "987654321", instantorTestNik = "fake60!";
    static WebDriver.Options options;
    static MainPage mainPage;
    static AuthPage authPage;
    static AboutMePage aboutMePage;
    static PdlOfferPage pdlOfferPage;
    static BankAccountVerificationPage bankAccountVerificationPage;
    static CongratulationPage congratulationPage;
//        static MyProfilePage myProfilePage;
    static SmsVerificationPage smsVerificationPage;
    public static UserCredentialsDAO userCredentialsDAO;
    static SentSmsDAO sentSmsDAO;
    static PlainUsersDAO plainUsersDAO;
    static InstWormCacheDAO instWormCacheDAO;

    @BeforeSuite
    public void createDBConnection() {
        EntityManager entityManager = (new PersistenceManager()).getEntityManager();
        userCredentialsDAO = new UserCredentialsDAO(entityManager);
        plainUsersDAO = new PlainUsersDAO(entityManager);
        sentSmsDAO = new SentSmsDAO(entityManager);
        instWormCacheDAO = new InstWormCacheDAO(entityManager);
    }

    @BeforeClass
    public void preparation() {
//        String property = System.getProperty("user.dir") + "/drivers/chromedriver.exe";
//        System.setProperty("webdriver.chrome.driver", property);
//        InternetExplorerDriverManager.getInstance().setup();
//        OperaDriverManager.getInstance().forceCache().setup();
//        EdgeDriverManager.getInstance().setup();
//        PhantomJsDriverManager.getInstance().setup();
//        FirefoxDriverManager.getInstance().setup();
//        driver = new FirefoxDriver();
//        ProxyServer server = new ProxyServer(4444);
//        server.start();
//        server.autoBasicAuthorization("test.ekassa.com", "ekassauser", "Trfccf098");
//        Proxy proxy = server.seleniumProxy();
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability(CapabilityType.PROXY, proxy);
//        driver = new ChromeDriver(capabilities);
//        driver = new OperaDriver();
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        options = driver.manage();
        options.timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        options.window().maximize();
        mainPage = new MainPage(driver);

//        mainPage.switchToConsolidationForm();

//        authPage = mainPage.submitAnUnregisteredNumberThroughPDLForm();
//        smsVerificationPage = authPage.submitRegFormWithVerifiedData();
//        aboutMePage = smsVerificationPage.submitSmsCodeFormWithRightCode();
//        mainPage = new MainPage(driver).switchToConsolidationForm();
//        mainPage.submitAnUnregisteredNumberThroughConsolidationForm()
//                .submitRegFormWithVerifiedData()
//                .submitSmsCodeFormWithRightCode();
    }

//    @Test
//    public void dataBaseTest() {
//        try {
//            List<UserCredential> userCredentials = userCredentialsDAO.getUserByPhone("222222222");
//            System.out.println(userCredentials);
//            for(UserCredential userCredential : userCredentials) {
//                System.out.println(userCredential.getPlainUserEntity().getEmail());
//                System.out.println(userCredential.getPlainUserEntity().getLastName());
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    @Test(priority = 15)
    public void inscriptionInTheConsolidationSubmitButtonAfterCreationConsolidationProcess() {
        mainPage = new MainPage(driver)
                .switchToConsolidationForm();
        assertEquals(mainPage.goToNextTaskConsolidationButton.getText(), "KOLEJNY KROK");
    }

    @Test(priority = 14)
    public void registrationThroughConsForm() throws SQLException {
        aboutMePage = mainPage.submitAnUnregisteredNumberThroughConsolidationForm()
                .submitRegFormWithVerifiedData();
//                .submitSmsCodeFormWithRightCode();
        assertTrue(aboutMePage.breadcrumbs.isDisplayed(),
                "Page 'About me' isn't displayed after submitting right sms code!");
        assertEquals(userCredentialsDAO.getUserByPhone(regPhone).size(), 1,
                "An entry with the phone isn't added to the userCredentials table!");
        assertEquals(userCredentialsDAO.getUserByEmail(email).size(), 1,
                "An entry with the email isn't added to the plainUsers table!");
    }

    @Test(priority = 13)
    public void interactionWithTheLinkConsolidationInfo() {
        mainPage.clickConsolidationInfo();
        try {
            assertTrue(mainPage.mdDialogOfConsolidationInfo.isDisplayed(),
                    "Consolidation info isn't displayed!");
            mainPage.closeDialogWindow();
            mainPage.waitForClosingConsolidationInfo();
        } catch (NoSuchElementException ex) {
            throw new AssertionError("Info about the loans isn't opened!", ex);
        }
    }

    @Test(priority = 13)
    public void accessibilityOfAnAccessToPersDataTextInConsolidationForm() {
        mainPage.clickTheTermsInConsolidationForm();
        try {
            assertTrue(mainPage.mdDialogOfAccessToPersData.isDisplayed(), "Terms aren't displayed!");
            mainPage.closeDialogWindow();
            mainPage.waitForClosingTerms();
        } catch (NoSuchElementException ex) {
            throw new AssertionError("Terms aren't opened!", ex);
        }
    }

    @Test(priority = 13)
    public void enteringLettersAndSymbolsToConsolidationPhone() {
        String def = mainPage.getValueFromConsolidationPhoneInput();
        mainPage.inputToConsolidationPhone("qwe ы!@-");
        assertEquals(mainPage.getValueFromConsolidationPhoneInput(), def, "Letters are inputted to phone field!");
        mainPage.markConsolidationCheckbox()
                .submitInvalidConsolidationForm()
                .customWaitForPerformingJS();
        try {
            assertTrue(mainPage.inputIsInvalid(mainPage.consolidationPhoneInput));
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Invalid consolidation form is submitted", ex);
        }
    }

    @Test(priority = 13)
    //в поле для номера есть маска, поэтому оно всегда имеет непустой атрибут "value"
    public void incompleteConsolidationPhoneNumber() {
        mainPage.inputToConsolidationPhone("22222222");
        String def = mainPage.getValueFromConsolidationPhoneInput();
        mainPage.markConsolidationCheckbox()
                .submitInvalidConsolidationForm()
                .customWaitForPerformingJS();
        try {
            assertTrue(mainPage.inputIsInvalid(mainPage.consolidationPhoneInput));
            assertTrue(mainPage.elementIsRed(mainPage.consolidationPhoneInput),
                    "Invalid field isn't highlighted with red color!");
            assertEquals(mainPage.getValueFromConsolidationPhoneInput(), def, "Digits are deleted from input");
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Invalid consolidation form is submitted", ex);
        }
    }

    @Test(priority = 13)
    public void submittingConsolidationMainFormWhenPhoneIsBlank() {
        mainPage.markConsolidationCheckbox()
                .inputToConsolidationPhone("")
                .submitInvalidConsolidationForm()
                .customWaitForPerformingJS();
        try {
            assertTrue(mainPage.inputIsInvalid(mainPage.consolidationPhoneInput));
            mainPage.submitInvalidConsolidationForm()
                    .customWaitForPerformingJS();
            assertTrue(mainPage.elementIsRed(mainPage.consolidationPhoneInput) ||
                            mainPage.elementIsRed(mainPage.findWithXPath("(//*[contains(text(), 'Telefon komórkowy')])[2]")),
                    "Invalid field isn't highlighted with red color!");
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Invalid consolidation form is submitted", ex);
        }
    }

    @Test(priority = 13)
    public void uncheckedConsolidationCheckbox() {
        mainPage.inputToConsolidationPhone(regPhone)
                .uncheckConsolidationCheckbox()
                .submitInvalidConsolidationForm()
                .customWaitForPerformingJS();
        try {
            assertTrue(mainPage.fieldWithConsolidationCheckboxIsInvalid(),
                    "Unmarked checkbox has valid state after submitting!");
            assertTrue(mainPage.elementIsRed(mainPage.termsLinkInConsolidation),
                    "The link to the terms near to invalid checkbox isn't highlighted with red color!");
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Invalid consolidation form is submitted", ex);
        }
    }

    //здесь когда поле ввода приобретает зеленый цвет - оно становится в фокусе
    //тест должен выполнятся с нетронутым инпутом телефона, поэтому все тесты со взаимодествием с инпутом имеют приоритет выше
    @Test(priority = 12)
    public void submittingEmptyConsolidationForm() {
        aboutMePage.goToMyProfile()
                .logOut();
        mainPage.switchToConsolidationForm()
                .uncheckConsolidationCheckbox()
//                .inputToConsolidationPhone("")
                .submitInvalidConsolidationForm()
                .customWaitForPerformingJS();
        try {
            assertTrue(mainPage.inputIsInvalid(mainPage.consolidationPhoneInput));
            assertTrue(mainPage.elementIsGreen(mainPage.consolidationPhoneInput),
                    "Consolidation input isn't focused after first submitting blank consolidation form!");
            mainPage.submitInvalidConsolidationForm()
                    .customWaitForPerformingJS();

            assertTrue(mainPage.fieldBorderIsRed(mainPage.consolidationPhoneInput) ||
                            mainPage.elementIsRed(mainPage.findWithXPath("(//*[contains(text(), 'Telefon komórkowy')])[2]")),
                    "Border of invalid field isn't highlighted with red color!");
            assertTrue(mainPage.fieldWithConsolidationCheckboxIsInvalid(), "Unmarked checkbox has valid state!");
            assertTrue(mainPage.elementIsRed(mainPage.termsLinkInConsolidation),
                    "The link to the terms near to invalid checkbox isn't highlighted with red color!");
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Invalid consolidation form is submitted", ex);
        }
    }

    @Test(priority = 11)
    public void inscriptionInThePdlSubmitButtonAfterCreationPdlProcess() {
        mainPage = new MainPage(driver);
        mainPage.waitForAngularRequestsToFinish();
        assertEquals(mainPage.goToNextTaskPdlButton.getText(), "KOLEJNY KROK");
    }

    @Test(priority = 10)
    public void submittingRegFormWithUnmarkedConcessionToEmailsCheckbox() {
        aboutMePage = aboutMePage.goToMyProfile()
                .logOut()
                .submitAnUnregisteredNumberThroughPDLForm()
                .unmarkMarketingCheckbox()
                .submitRegFormWithVerifiedData();
//                .submitSmsCodeFormWithRightCode();
        assertFalse(userCredentialsDAO.getStateOfMarketingDistributionByPhone(regPhone),
                "The 'false' value isn't set to relevant field in the plainUsers table after registration a user with unmarked marketing checkbox!");
    }

    @Test(priority = 9)
    public void submittingRegFormWithMarkedConcessionToEmailsCheckbox() {
        assertTrue(userCredentialsDAO.getStateOfMarketingDistributionByPhone(regPhone),
                "The 'true' value isn't set in the plainUsers table after registration a user with marked marketing checkbox!");
    }

    @Test(priority = 8)
    public void successfulSmsCodeConfirmation() throws SQLException {
        aboutMePage = smsVerificationPage.submitSmsCodeFormWithRightCode();
        assertTrue(aboutMePage.breadcrumbs.isDisplayed(),
                "Page 'About me' isn't displayed after submitting right sms code!");
        assertEquals(userCredentialsDAO.getUserByPhone(regPhone).size(), 1,
                "An entry with the phone isn't added to the userCredentials table!");
        assertEquals(userCredentialsDAO.getUserByEmail(email).size(), 1,
                "An entry with the email isn't added to the plainUsers table!");
    }

    @Test(priority = 7)
    public void refreshingSmsCode() throws InterruptedException {
        String initialCode = sentSmsDAO.getSmsCodeByPhone(regPhone);
        smsVerificationPage.refreshSmsCode()
                .customWaitForPerformingJS();
        String newCode = sentSmsDAO.getSmsCodeByPhone(regPhone);

        for (int i = 0; i < 10; i++) {
            if (newCode.equals(initialCode)) {
                Thread.sleep(500);
                newCode = sentSmsDAO.getSmsCodeByPhone(regPhone); //ждем на всякий случай, чтоб обновился код в базе
            } else break;
        }
        assertNotEquals(initialCode, newCode, "Sms confirmation code isn't refreshed!");
    }

    @Test(priority = 7)
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

    @Test(priority = 7, dataProvider = "invalidSmsCodes", dataProviderClass = DataProviders.class)
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

    @Test(priority = 6)
    public void creationSmsCodeInDB() {
        assertEquals(sentSmsDAO.getSmsCodeEntryByPhone(regPhone).size(), 1,
                "Sms code for confirmation was not sent!");
    }

    @Test(priority = 5)
    public void visibilityOfSmsCodeField() {
        aboutMePage = authPage.submitRegFormWithVerifiedData();
        assertTrue(smsVerificationPage.smsCodeSubmitButton.isDisplayed(), "The block for sms code isn't displayed!");
    }

    @Test(priority = 4)
    public void registrationWithEarlierUsedEmail() {
        authPage.fillRegFormWithValidData()
                .markRegCheckbox()
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
            authPage = authPage.goToNewRegPage();
            throw new AssertionError("Registration form with already registered email was submitted");
        }
        assertEquals(authPage.findElementsByXPath("//*[contains(text(), 'Użytkownik z tym e-mail jest już zarejestrowany')]").size(),
                1, "Error message isn't displayed!");
    }

    @Test(priority = 4)
    public void submittingBlankRegForm() {
        authPage.setBlankValuesToRegForm()
                .submitInvalRegForm()
                .customWaitForPerformingJS();
        WebElement[] regInputs = {authPage.nameField, authPage.lastNameField, authPage.emailField,
                authPage.passwordField, authPage.passConfirmField};
        try {
            for (WebElement el : regInputs)
                assertTrue(authPage.fieldBorderIsRed(el), "Blank field " + el.toString() + " doesn't have red color!");
        } catch (NoSuchElementException e) {
            authPage = authPage.goToNewRegPage();
            throw new AssertionError("Blank registration form was submitted");
        }
    }

    @Test(priority = 4)
    public void equalityValidationOfPasswordsWhileEditingMainPassField() throws InterruptedException {
        authPage.fillRegFormWithValidData()
                .inputToPasswordField(password + "a")
                .moveFromAField(authPage.passwordField);   //этот шаг необязательный, но селениум слишком быстро проверяет цвет и приходится что то еще сделать))
        assertTrue(authPage.fieldBorderIsRed(authPage.passConfirmField));
        authPage.submitInvalRegForm()
                .waitForReaction();
        assertEquals(authPage.findElementsByXPath("//*[contains(text(), 'Hasła wprowadzone nie pasują do siebie!')]").size(),
                1, "Error message isn't displayed!");
    }

    @Test(priority = 4)
    public void accessibilityOfConcessionToEmailsText() {
        authPage.clickMarketingTerms();
        assertEquals(authPage.findElementsByXPath("//md-dialog[@aria-label='Wyrażam zgodę ...']").size(), 1, "Terms aren't opened!");
        assertTrue(authPage.findWithXPath("//md-dialog[@aria-label='Wyrażam zgodę ...']").isDisplayed(), "Terms aren't displayed!");
        authPage.closeDialogWindow();
        authPage.waitForClosingTerms();
    }

    @Test(priority = 4)
    public void accessibilityOfARegTermsText() {
        authPage.clickRegTerms()
                .waitForOpeningTerms();
        assertEquals(authPage.findElementsByXPath("//md-dialog[@aria-label='Potwierdzam, że ...']").size(),
                1, "Terms aren't opened!");
        assertTrue(authPage.findWithXPath("//md-dialog[@aria-label='Potwierdzam, że ...']").isDisplayed(),
                "Terms aren't displayed!");
        authPage.closeDialogWindow();
        authPage.waitForClosingTerms();
    }

    @Test(priority = 4)
    public void submittingRegFormWithUnmarkedTermsCheckbox() throws InterruptedException {
        authPage.fillRegFormWithValidData()
                .unmarkRegCheckbox()
                .submitInvalRegForm()
                .waitForReaction();
        try {
            assertTrue(authPage.fieldIsInvalid(authPage.termsCheckBox), "Unmarked checkbox has valid state!");
            assertTrue(authPage.elementIsRed(authPage.linkRegTerms),
                    "The link to the terms near to invalid checkbox isn't highlighted with red color!");
        } catch (NoSuchElementException ex) {
            authPage.goBack();
            throw new AssertionError("Unfilled registration form is submitted", ex);
        }
    }

    @Test(priority = 4)
    public void validationOfPasswordConfirmationField() {
        authPage.inputToPasswordField(password)
                .inputToPassConfirmField(password + "a")
                .moveFromAField(authPage.passConfirmField);
        assertTrue(authPage.fieldIsInvalid(authPage.passConfirmField), "Password confirmation field is valid while its value isn't equal to password field!");
        assertTrue(authPage.fieldBorderIsRed(authPage.passConfirmField), "Border of invalid field isn't highlighted with red color!");
        authPage.inputToPassConfirmField(password)
                .moveFromAField(authPage.passConfirmField);
        assertFalse(authPage.fieldIsInvalid(authPage.passConfirmField), "Password confirmation field is invalid while its value is equal to password field!");
        assertFalse(authPage.fieldBorderIsRed(authPage.passConfirmField), "Border of valid field is highlighted with red color!");
    }

    @Test(priority = 4)
    public void positiveValidationOfPasswordField() {
        authPage.inputToPasswordField("1q\\%[`")
                .moveFromAField(authPage.passwordField);
        assertEquals(authPage.getValue(authPage.passwordField), "1q\\%[`", "Wrong data input to password field!");
        assertFalse(authPage.fieldIsInvalid(authPage.passwordField), "Permissible characters aren't allowed at the password field!");
        assertFalse(authPage.fieldBorderIsRed(authPage.passwordField), "Border of valid field is highlighted with red color!");
    }

    @Test(priority = 4, dataProvider = "passwordValidation", dataProviderClass = DataProviders.class)
    public void negativeValidationOfPasswordField(String password) {
        authPage.inputToPasswordField(password)
                .moveFromAField(authPage.passwordField);
        assertEquals(authPage.getValue(authPage.passwordField), password, "Wrong data input to password field!");
        assertTrue(authPage.fieldIsInvalid(authPage.passwordField), "Impermissible characters are allowed at the password field!");
        assertTrue(authPage.fieldBorderIsRed(authPage.passwordField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 4)
    //при передаче на бэк срабатывает трим - обрезка пробелов, поэтому их ввод в начале конце допустим
    public void enteringEmailWithSpacesAtTheBeginning() {
        authPage.inputToEmailField("   a.paskhin1@gmail.com")
                .moveFromAField(authPage.emailField);
        assertEquals(authPage.getValue(authPage.emailField), "a.paskhin1@gmail.com", "Wrong data input to email field!");
        assertFalse(authPage.fieldIsInvalid(authPage.emailField), "Permissible characters aren't allowed at the email field!");
        assertFalse(authPage.fieldBorderIsRed(authPage.emailField), "Border of valid field is highlighted with red color!");
    }

    @Test(priority = 4)
    //при передаче на бэк срабатывает трим - обрезка пробелов, поэтому их ввод в начале конце допустим
    public void enteringEmailWithSpacesAtTheEnd() {
        authPage.inputToEmailField("a.paskhin1@gmail.com  ")
                .moveFromAField(authPage.emailField);
        assertEquals(authPage.getValue(authPage.emailField), "a.paskhin1@gmail.com", "Wrong data input to email field!");
        assertFalse(authPage.fieldIsInvalid(authPage.emailField), "Permissible characters aren't allowed at the email field!");
        assertFalse(authPage.fieldBorderIsRed(authPage.emailField), "Border of valid field is highlighted with red color!");
    }

    @Test(priority = 4)
    //видимо селениум видоизменяет кириллицу после "@" в малопонятные символы, поэтому будем считать, что ожидаем вводимую букву увидеть такой, как написано в "expected"
    public void positiveValidationOfEmailField() {
        authPage.inputToEmailField("a.paskhin-@gmail-ы.coms")
                .moveFromAField(authPage.emailField);
        assertEquals(authPage.getValue(authPage.emailField), "a.paskhin-@xn--gmail--ntf.coms", "Wrong data input to email field!");
        assertFalse(authPage.fieldIsInvalid(authPage.emailField), "Permissible characters aren't allowed at the email field!");
        assertFalse(authPage.fieldBorderIsRed(authPage.emailField), "Border of valid field is highlighted with red color!");
    }

    @Test(priority = 4, dataProvider = "emailValidation", dataProviderClass = DataProviders.class)
    public void negativeValidationOfEmailField(String text) {
        authPage.inputToEmailField(text)
                .moveFromAField(authPage.emailField);
        assertTrue(authPage.fieldIsInvalid(authPage.emailField), "Impermissible characters are allowed at the email field!");
        assertTrue(authPage.fieldBorderIsRed(authPage.emailField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 4)
    public void positiveValidationWhileUsingHyphenInLastNameField() {
        authPage.inputToLastName("a-a")
                .moveFromAField(authPage.lastNameField);
        assertFalse(authPage.fieldIsInvalid(authPage.lastNameField), "Hyphen isn't allowed in the lastName field!");
        assertFalse(authPage.fieldBorderIsRed(authPage.lastNameField), "Border of valid field is highlighted with red color!");
    }

    @Test(priority = 4, dataProvider = "UsingOfHyphen", dataProviderClass = DataProviders.class)
    public void negativeValidationWhileUsingHyphenInLastNameField(String text) {
        authPage.inputToLastName(text)
                .moveFromAField(authPage.lastNameField);
        assertTrue(authPage.fieldIsInvalid(authPage.lastNameField), "Undisguised hyphen is allowed in the lastName field!");
        assertTrue(authPage.fieldBorderIsRed(authPage.lastNameField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 4)
    public void enteringNonLatinAndSpecCharsToLastNameField() {
        authPage.inputToLastName("іыцжч!@.\"є'=+&")
                .moveFromAField(authPage.lastNameField);
        assertEquals(authPage.getValue(authPage.lastNameField), "", "Something except latin letters is entered to the lastName field!");
        assertTrue(authPage.fieldIsInvalid(authPage.lastNameField));
        assertTrue(authPage.fieldBorderIsRed(authPage.lastNameField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 4)
    public void positiveValidationWhileUsingHyphenInNameField() {
        authPage.inputToName("An-Mar")
                .moveFromAField(authPage.nameField);
        assertFalse(authPage.fieldIsInvalid(authPage.nameField), "Hyphen isn't allowed in the name field!");
        assertFalse(authPage.fieldBorderIsRed(authPage.nameField), "Border of valid field is highlighted with red color!");
    }

    @Test(priority = 4, dataProvider = "UsingOfHyphen", dataProviderClass = DataProviders.class)
    public void negativeValidationWhileUsingHyphenInNameField(String text) {
        authPage.inputToName(text)
                .moveFromAField(authPage.nameField);
        assertTrue(authPage.fieldIsInvalid(authPage.nameField), "Undisguised hyphen is allowed in the name field!");
        assertTrue(authPage.fieldBorderIsRed(authPage.nameField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 4)
    public void enteringNonLatinAndSpecCharsToNameField() {
        authPage.inputToName("іыцжч!@.\"є'=+&")
                .moveFromAField(authPage.nameField);
        assertEquals(authPage.getValue(authPage.nameField), "", "Something except latin letters is entered to the name field!");
        assertTrue(authPage.fieldIsInvalid(authPage.nameField));
        assertTrue(authPage.fieldBorderIsRed(authPage.nameField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 4)
    public void concessionToEmailsCheckboxIsMarkedByDefault() {
        assertTrue(authPage.checkboxIsMarked(authPage.marketingCheckbox),
                "Marketing checkbox isn't marked by default!");
    }

    @Test(priority = 3)
    public void successfulSubmittingPdlForm() throws SQLException {
        authPage = mainPage.submitAnUnregisteredNumberThroughPDLForm();
        int countVisElems = 0;
        for (WebElement el : mainPage.findElementsByXPath("//input")) {
            if (el.isDisplayed()) countVisElems++;
        }
        assertEquals(countVisElems, 5, "Not each input element is displayed!");
    }

    @Test(priority = 2)
    public void uncheckedCheckbox() throws InterruptedException {
        mainPage.inputToPDLPhone(regPhone)
                .uncheckPDLCheckbox()
                .submitInvalidPDLForm()
                .waitForReaction();
        try {
            assertTrue(mainPage.fieldWithPDLCheckboxIsInvalid(), "Unmarked checkbox has valid state!");
            assertTrue(mainPage.elementIsRed(mainPage.termsLinkInPDL),
                    "The link to the terms near to invalid checkbox isn't highlighted with red color!");
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Invalid PDL form is submitted", ex);
        }
    }

    @Test(priority = 2)
    public void submittingWhenPhoneIsBlank() throws InterruptedException {
        mainPage.markPDLCheckbox()
                .inputToPDLPhone("")
                .submitInvalidPDLForm()
                .waitForReaction();
        try {
            assertTrue(mainPage.inputIsInvalid(mainPage.pdlPhoneInput));
            mainPage.submitInvalidPDLForm()
                    .waitForReaction();
            assertTrue(mainPage.fieldBorderIsRed(mainPage.pdlPhoneInput)
                            || mainPage.elementIsRed(mainPage.findWithXPath("//*[contains(text(), 'Telefon komórkowy')]")),
                    "Border of invalid field isn't highlighted with red color!");
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Invalid PDL form is submitted", ex);
        }
    }

    @Test(priority = 2)
    //в поле для номера есть маска, поэтому оно всегда имеет непустой атрибут "value"
    public void incompletePhoneNumber() throws InterruptedException {
        mainPage.inputToPDLPhone("22222222");
        String def = mainPage.getValueFromPDLPhoneInput();
        mainPage.markPDLCheckbox()
                .submitInvalidPDLForm()
                .waitForReaction();
        try {
            assertTrue(mainPage.inputIsInvalid(mainPage.pdlPhoneInput));
            assertTrue(mainPage.elementIsRed(mainPage.pdlPhoneInput),
                    "Invalid field isn't highlighted with red color!");
            assertEquals(mainPage.getValueFromPDLPhoneInput(), def, "Digits are deleted from input");
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Invalid PDL form is submitted", ex);
        }
    }

    @Test(priority = 2)
    public void enteringLettersAndSymbolsToPhone() {
        String def = mainPage.waitPhonePdlInputIsAccessible()
                .getValueFromPDLPhoneInput();
        mainPage.inputToPDLPhone("qwe ы!@-");
        assertEquals(mainPage.getValueFromPDLPhoneInput(), def, "Letters are inputted to phone field!");
        mainPage.markPDLCheckbox()
                .submitInvalidPDLForm()
                .customWaitForPerformingJS();
        try {
            assertTrue(mainPage.inputIsInvalid(mainPage.pdlPhoneInput));
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Invalid PDL form is submitted", ex);
        }
    }

    //здесь когда поле ввода приобретает зеленый цвет - оно становится в фокусе
    //тест должен выполнятся с нетронутым инпутом телефона, поэтому все тесты со взаимодествием с инпутом имеют приоритет выше
    @Test(priority = 1)
    public void submittingEmptyPdlForm() throws InterruptedException {
        mainPage.uncheckPDLCheckbox()
//                .inputToPDLPhone("")
                .submitInvalidPDLForm()
                .waitForAngularRequestsToFinish();
        try {
            assertTrue(mainPage.inputIsInvalid(mainPage.pdlPhoneInput));
            assertTrue(mainPage.elementIsGreen(mainPage.pdlPhoneInput),
                    "PDL input isn't focused after first submitting blank PDL form!");
            mainPage.submitInvalidPDLForm()
                    .waitForReaction();
            assertTrue(mainPage.fieldBorderIsRed(mainPage.pdlPhoneInput) ||
                            mainPage.elementIsRed(mainPage.findWithXPath("//*[contains(text(), 'Telefon komórkowy')]")),
                    "Border of invalid field isn't highlighted with red color!");
            assertTrue(mainPage.fieldWithPDLCheckboxIsInvalid(), "Unmarked checkbox has valid state!");
            assertTrue(mainPage.elementIsRed(mainPage.termsLinkInPDL),
                    "The link to the terms near to invalid checkbox isn't highlighted with red color!");
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Invalid PDL form is submitted", ex);
        }
    }

    @Test(priority = 1)
    public void interactionWithTheLinkLoanInfo() {
        mainPage.clickLoanInfo();
        try {
            assertTrue(mainPage.mdDialogOfLoanInfo.isDisplayed(), "Loan info isn't displayed!");
            mainPage.closeDialogWindow();
            mainPage.waitForClosingLoanInfo();
        } catch (NoSuchElementException ex) {
            throw new AssertionError("Info about the loans isn't opened!", ex);
        }
    }

    @Test(priority = 1)
    public void accessibilityOfAnAccessToPersDataText() {
        mainPage.clickTheTermsInPDLForm();
        try {
            assertTrue(mainPage.mdDialogOfAccessToPersData.isDisplayed(), "Terms aren't displayed!");
            mainPage.closeDialogWindow();
            mainPage.waitForClosingTerms();
        } catch (NoSuchElementException ex) {
            throw new AssertionError("Terms aren't opened!", ex);
        }
    }

    @Test(priority = 1)
    public void inscriptionInTheSubmitButtonsIfAuthorizationIsAbsent() {
        assertEquals(mainPage.startPdlProcessButton.getText(), "DALEJ");
        assertEquals(mainPage.startConsProcessButton.getText(), "DALEJ");
    }


    @AfterSuite
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
