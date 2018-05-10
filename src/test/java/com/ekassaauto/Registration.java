package com.ekassaauto;

import com.ekassaauto.PageObjects.*;
import com.ekassaauto.database.PersistenceManager;
import com.ekassaauto.database.dao.aui.*;
import com.ekassaauto.database.dao.risk.BoDealsDAO;
import com.ekassaauto.database.entities.aui.PlainUserEntity;
import io.github.bonigarcia.wdm.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

/**
 * Created by user on 09.03.2017.
 */
@Test
public class Registration {
    protected WebDriver driver;
    public static String regPhone = "222222218", name = "Alex", lastName = "Paskhin", pesel = "83010138862",
            email = "lubi@eth2btc.info", password = "111111a", socialNumber = "ATC339884", currentDebt = "10",
            bankAccount = "77777777777777777777777775", contactPersonPhone = "123456789", postalCode = "00000",
            testString = "shutĄąĆćĘę-ŁłŃńÓóŚśŹźŻ", netIncome1 = "3800", empPhone1 = "987654321", instantorTestNik = "fake60!";
    private WebDriver.Options options;
    private MainPage mainPage;
    private AuthPage authPage;
    private AboutMePage aboutMePage;
    private PdlOfferPage pdlOfferPage;
    private BankAccountVerificationPage bankAccountVerificationPage;
    private CongratulationPage congratulationPage;
    private MyProfilePage myProfilePage;
    static SmsVerificationPage smsVerificationPage;
    public static UserCredentialsDAO userCredentialsDAO;
    public static SentSmsDAO sentSmsDAO;
    static PlainUsersDAO plainUsersDAO;
    public static InstWormCacheDAO instWormCacheDAO;
    static CpaShadowClientInformationsDAO cpaShadowClientInformationsDAO;
    static CpaClientCasheDAO cpaClientCasheDAO;
    static BMOutgoingPaymentDAO bmOutgoingPaymentDAO;

    static BoDealsDAO boDealsDAO;

    @BeforeSuite
    public void createDBConnections() {
        EntityManager auiEntityManager = (new PersistenceManager()).getAuiEntityManager();
        userCredentialsDAO = new UserCredentialsDAO(auiEntityManager);
        plainUsersDAO = new PlainUsersDAO(auiEntityManager);
        sentSmsDAO = new SentSmsDAO(auiEntityManager);
        instWormCacheDAO = new InstWormCacheDAO(auiEntityManager);
        cpaShadowClientInformationsDAO = new CpaShadowClientInformationsDAO(auiEntityManager);
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
        try {
            options.window().maximize();  //из-за бага в фаерфоксе
        } catch (WebDriverException e) {
            System.out.println("The bug while maximizing: " + e.getMessage() + "\nEnd of message");
        }
        mainPage = new MainPage(driver);

//        mainPage.switchToConsolidation();

//        authPage = mainPage.startSmallPdlInUnauthorizedState();
//        aboutMePage = authPage.submitAuthFormForRegistrationWithVerifiedData();
//        mainPage = new MainPage(driver).switchToConsolidation();

    }


    @Test(priority = 16)
    public void submitEventForEnterKeyInAuthorizedStateAtConsolidationForm() {
        mainPage = new MainPage(driver)
                .switchToConsolidation();
        mainPage.markConsolidationCheckbox()
                .submitFormWithEnterKeyThroughSpecificField(mainPage.consolidationTrancheInput);
        assertFalse(driver.getCurrentUrl().contains("employment"), "Submitting with the 'Enter' key was successful!");

        mainPage.markConsolidationCheckbox()
                .submitFormWithEnterKeyThroughSpecificField(mainPage.consolidationPaymentInput);
        assertFalse(driver.getCurrentUrl().contains("employment"), "Submitting with the 'Enter' key was successful!");
    }

    @Test(priority = 15, dependsOnMethods = "registrationThroughConsForm")
    public void inscriptionInTheConsolidationSubmitButtonAfterCreationConsolidationProcess() {
        mainPage = new MainPage(driver)
                .switchToConsolidation();
        mainPage.waitForAngularRequestsToFinish();
        assertEquals(mainPage.findElementsByXPath(MainPage.goToNextTaskConsolidationButtonLocator).size(), 1,
                "goToNextTask consolidation button isn't displayed after creation pdl process!");
        assertEquals(mainPage.goToNextTaskConsolidationButton.getText(), "KOLEJNY KROK");
    }

    @Test(priority = 14, dependsOnMethods = {"successfulSubmittingConsolidationForm"
            , "inscriptionInTheConsolidationSubmitButtonBeforeCreationConsolidationProcess"})
    public void registrationThroughConsForm() throws SQLException {
        aboutMePage = authPage.submitAuthFormForRegistrationWithVerifiedData();
        aboutMePage.cleanInstWormCache(name, pesel, lastName, bankAccount);  //in order to not to receive -220050 decline due to absence of bik credits
        aboutMePage.fillInitiallyBlankAboutMePageWithAcceptableData()
                .submitAboutMePageWithoutBankCache();
        assertEquals(userCredentialsDAO.getUserByPhone(regPhone).size(), 1,
                "The client isn't registered after passing aboutMe page!");
    }

//    @Test(priority = 12)
//    public void enteringLettersAndSymbolsToConsolidationPhone() {
//        String def = mainPage.getValueFromConsolidationPhoneInput();
//        mainPage.inputToConsolidationPhone("qwe ы!@-");
//        assertEquals(mainPage.getValueFromConsolidationPhoneInput(), def, "Letters are inputted to phone field!");
//        mainPage.markConsolidationCheckbox()
//                .submitInvalidConsolidationForm()
//                .customWaitForPerformingJS();
//        try {
//            assertTrue(mainPage.inputIsInvalid(mainPage.consolidationPhoneInput));
//        } catch (NoSuchElementException ex) {
//            mainPage = new MainPage(driver);
//            throw new AssertionError("Invalid consolidation form is submitted", ex);
//        }
//    }

//    @Test(priority = 12)
//    //в поле для номера есть маска, поэтому оно всегда имеет непустой атрибут "value"
//    public void incompleteConsolidationPhoneNumber() {
//        mainPage.inputToConsolidationPhone("22222222");
//        String def = mainPage.getValueFromConsolidationPhoneInput();
//        mainPage.markConsolidationCheckbox()
//                .submitInvalidConsolidationForm()
//                .customWaitForPerformingJS();
//        try {
//            assertTrue(mainPage.inputIsInvalid(mainPage.consolidationPhoneInput));
//            assertTrue(mainPage.elementIsRed(mainPage.consolidationPhoneInput),
//                    "Invalid field isn't highlighted with red color!");
//            assertEquals(mainPage.getValueFromConsolidationPhoneInput(), def, "Digits are deleted from input");
//        } catch (NoSuchElementException ex) {
//            mainPage = new MainPage(driver);
//            throw new AssertionError("Invalid consolidation form is submitted", ex);
//        }
//    }

//    @Test(priority = 12)
//    public void submittingConsolidationMainFormWhenPhoneIsBlank() {
//        mainPage.markConsolidationCheckbox()
//                .inputToConsolidationPhone("")
//                .submitInvalidConsolidationForm()
//                .customWaitForPerformingJS();
//        try {
//            assertTrue(mainPage.inputIsInvalid(mainPage.consolidationPhoneInput));
//            mainPage.submitInvalidConsolidationForm()
//                    .customWaitForPerformingJS();
//            assertTrue(mainPage.elementIsRed(mainPage.consolidationPhoneInput) ||
//                            mainPage.elementIsRed(mainPage.findWithXPath("(//*[contains(text(), 'Telefon komórkowy')])[2]")),
//                    "Invalid field isn't highlighted with red color!");
//        } catch (NoSuchElementException ex) {
//            mainPage = new MainPage(driver);
//            throw new AssertionError("Invalid consolidation form is submitted", ex);
//        }
//    }

    @Test(priority = 13, dependsOnMethods = "inscriptionInTheConsolidationSubmitButtonBeforeCreationConsolidationProcess")
    public void successfulSubmittingConsolidationForm() {
        mainPage.logOut();
        authPage = mainPage.passConsolidationForm();
        int countVisElems = 0;
        for (WebElement el : mainPage.findElementsByXPath("//input")) {
            if (el.isDisplayed()) countVisElems++;
        }
        assertTrue(driver.getCurrentUrl().contains("/#/auth"), "The auth page isn't loaded after " +
                "consolidation process was started without authorization!");
        assertEquals(countVisElems, 3, "Not each input element is displayed!");
//        assertEquals(authPage.findElementsByXPath("//ul[@ng-controller='BreadcrumbsCtrl']").size(), 1,
//                "There are no breadcrumbs at the auth page!");
    }

    @Test(priority = 12)
    public void interactionWithTheLinkConsolidationInfo() {
        mainPage.switchToConsolidation()
                .clickConsolidationInfo();
        try {
            assertTrue(mainPage.mdDialogOfConsolidationInfo.isDisplayed(),
                    "Consolidation info isn't displayed!");
            mainPage.closeDialogWindow();
            mainPage.waitForClosingConsolidationInfo();
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver).switchToConsolidation();
            throw new AssertionError("Info about the loans isn't opened!", ex);
        }
    }

    @Test(priority = 12)
    public void accessibilityOfAnAccessToPersDataTextInConsolidationForm() {
        mainPage.switchToConsolidation()
                .clickTheTermsInConsolidationForm();
        try {
            assertTrue(mainPage.mdDialogOfAccessToPersData.isDisplayed(), "Terms aren't displayed!");
            mainPage.closeDialogWindow();
            mainPage.waitForClosingTerms();
        } catch (NoSuchElementException ex) {
            throw new AssertionError("Terms aren't opened!", ex);
        }
    }

    @Test(priority = 12, dependsOnMethods = "inscriptionInTheConsolidationSubmitButtonBeforeCreationConsolidationProcess")
    public void uncheckedConsolidationCheckbox() {
        mainPage.switchToConsolidation()
                .unmarkConsolidationCheckbox()
                .submitInvalidConsolidationForm();
        assertFalse(driver.getCurrentUrl().contains("employment"), "Invalid consolidation form is submitted");
        assertTrue(mainPage.elementIsRed(mainPage.termsLinkInConsolidation),
                "The link to the terms near to invalid checkbox isn't highlighted with red color! It's highlighted with color: "
         + mainPage.termsLinkInConsolidation.getCssValue("color"));
    }

    @Test(priority = 12)
    public void inscriptionInTheConsolidationSubmitButtonBeforeCreationConsolidationProcess() {
        mainPage = mainPage.logOut()
                .switchToConsolidation();
        mainPage.waitForAngularRequestsToFinish();
        assertEquals(mainPage.findElementsByXPath(MainPage.startConsProcessButtonLocator).size(), 1,
                "The 'start consolidation process' button isn't displayed after creation pdl process" +
                        " and logging out instead of goToNextTask button!");
        assertEquals(mainPage.startConsProcessButton.getText(), "DALEJ");
    }

    @Test(priority = 11)
    public void submitEventForEnterKeyInUnauthorizedStateAtConsolidationForm() {
        mainPage.logOut()
                .switchToConsolidation()
                .markConsolidationCheckbox()
                .submitFormWithEnterKeyThroughSpecificField(mainPage.consolidationTrancheInput);
        assertFalse(driver.getCurrentUrl().contains("employment"),"Submitting with the 'Enter' key was successful!");

        mainPage.markConsolidationCheckbox()
                .submitFormWithEnterKeyThroughSpecificField(mainPage.consolidationPaymentInput);
        assertFalse(driver.getCurrentUrl().contains("employment"),"Submitting with the 'Enter' key was successful!");
    }

    //здесь когда поле ввода приобретает зеленый цвет - оно становится в фокусе
    //тест должен выполнятся с нетронутым инпутом телефона, поэтому все тесты со взаимодествием с инпутом имеют приоритет выше
//    @Test(priority = 11)
//    public void submittingEmptyConsolidationForm() {
//                mainPage.logOut();
//        mainPage.switchToConsolidation()
//                .uncheckConsolidationCheckbox()
////                .inputToConsolidationPhone("")
//                .submitInvalidConsolidationForm()
//                .customWaitForPerformingJS();
//        try {
//            assertTrue(mainPage.inputIsInvalid(mainPage.consolidationPhoneInput));
//            assertTrue(mainPage.elementIsGreen(mainPage.consolidationPhoneInput),
//                    "Consolidation input isn't focused after first submitting blank consolidation form!");
//            mainPage.submitInvalidConsolidationForm()
//                    .customWaitForPerformingJS();
//
//            assertTrue(mainPage.fieldBorderIsRed(mainPage.consolidationPhoneInput) ||
//                            mainPage.elementIsRed(mainPage.findWithXPath("(//*[contains(text(), 'Telefon komórkowy')])[2]")),
//                    "Border of invalid field isn't highlighted with red color!");
//            assertTrue(mainPage.fieldWithConsolidationCheckboxIsInvalid(), "Unmarked checkbox has valid state!");
//            assertTrue(mainPage.elementIsRed(mainPage.termsLinkInConsolidation),
//                    "The link to the terms near to invalid checkbox isn't highlighted with red color!");
//        } catch (NoSuchElementException ex) {
//            mainPage = new MainPage(driver);
//            throw new AssertionError("Invalid consolidation form is submitted", ex);
//        }
//    }

    @Test(priority = 10)
    public void submitEventForEnterKeyInAuthorizedStateAtPdlForm() {
        mainPage.markSmallPDLCheckbox()
                .submitFormWithEnterKeyThroughSpecificField(mainPage.smallPdlAmountInput);
        assertFalse(driver.getCurrentUrl().contains("employment"),
                "Submitting with the 'Enter' key was successful!"); //checking we stayed at the main page

//        mainPage.markSmallPDLCheckbox()
//                .submitFormWithEnterKeyThroughSpecificField(mainPage.pdlTermInput);
//        assertFalse(driver.getCurrentUrl().contains("employment"),
//                "Submitting with the 'Enter' key was successful!"); //checking we stayed at the main page
    }

    @Test(priority = 9, dependsOnMethods = "submittingRegFormWithUnmarkedConcessionToEmailsCheckbox")
    public void inscriptionInThePdlSubmitButtonAfterCreationPdlProcess() {
        mainPage = new MainPage(driver);
        mainPage.waitForAngularRequestsToFinish();
        assertEquals(mainPage.findElementsByXPath(mainPage.goToNextTaskPdlButtonLocator).size(), 1,
                "goToNextTask Pdl Button isn't displayed after creation pdl process!");
        assertEquals(mainPage.goToNextTaskPdlButton.getText(), "KOLEJNY KROK");
    }

    @Test(priority = 8)
    public void submittingRegFormWithUnmarkedConcessionToEmailsCheckbox() {
        mainPage.logOut()
                .startSmallPdlInUnauthorizedState()
                .unmarkMarketingCheckbox()
                .submitAuthFormForRegistrationWithVerifiedData()
                .submitAboutMePageWithBasicAcceptableData();
        assertFalse(userCredentialsDAO.getStateOfMarketingDistributionByPhone(regPhone),
                "The 'false' value isn't set to relevant field in the plainUsers table after registration a user" +
                        " with unmarked marketing checkbox!");
    }

    @Test(priority = 7, dependsOnMethods = {"passingInitiallyBlankAboutMeScreenForRegistration","successfulSubmittingAuthFormForRegistration",
    "successfulSubmittingPdlForm"})
    public void enteringFirstTimeToPersonalAccount() {
        myProfilePage = pdlOfferPage.goToMyProfile();
        assertTrue(userCredentialsDAO.getStateOfFirstTimeEnteredByPhone(regPhone),
                "The 'true' value isn't set at the plainUsers table at the firsttimeentered field immediately after registration");
        assertEquals(myProfilePage.findElementsByXPath("//md-dialog[@aria-label='Rekomendujemy zmianę ...']").size(),
                1, "The dialog window for setting a password isn't present while first entering to personal profile!");
        assertTrue(myProfilePage.oneElementIsPresentAtAPage("//h4[text()='Rekomendujemy zmianę hasła w wygodnym dla Ciebie.']"),
                "The dialog window for setting a password isn't displayed!");

        myProfilePage.setNewPasswordAtPopUp(password);
        assertEquals(myProfilePage.findElementsByXPath("//md-dialog[@aria-label='Rekomendujemy zmianę ...']").size(),
                0, "The dialog window for setting a password isn't hided after new pass was set!");
        //todo to bound with testLink
    }

    @Test(priority = 7)
    public void registrationWithMarkedConcessionToEmailsCheckbox() {
        assertTrue(userCredentialsDAO.getStateOfMarketingDistributionByPhone(regPhone),
                "The 'true' value isn't set in the plainUsers table after registration a user with marked marketing checkbox!");
    }

    @Test(priority = 6, dependsOnMethods = "passingInitiallyBlankAboutMeScreenForRegistration")
    public void recordingClientInfoToPlainUsersTable() throws SQLException {
        Map enteredClientInfo = aboutMePage.getDataForFillingAboutMePageMap();
        PlainUserEntity dbClientData = userCredentialsDAO.getUserByPhone(regPhone).get(0).getPlainUserEntity();

        assertEquals(dbClientData.getFirstName(), name, "Wrong data was recorded into Plainuser table after registration!");
        assertEquals(dbClientData.getLastName(), lastName, "Wrong data was recorded into Plainuser table after registration!");

        assertEquals(dbClientData.getPesel(), enteredClientInfo.get(aboutMePage.peselField), "Wrong data was recorded into Plainuser table after registration!");
        assertEquals(dbClientData.getSocialNumber(), enteredClientInfo.get(aboutMePage.socialNumberField), "Wrong data was recorded into Plainuser table after registration!");
        assertEquals(dbClientData.getAccount(), enteredClientInfo.get(aboutMePage.bankAccountField), "Wrong data was recorded into Plainuser table after registration!");
        assertEquals(dbClientData.getEmail(), enteredClientInfo.get(aboutMePage.emailField), "Wrong data was recorded into Plainuser table after registration!");
        assertEquals(dbClientData.getMaritalStatus(), enteredClientInfo.get(aboutMePage.maritalStatusListbox), "Wrong data was recorded into Plainuser table after registration!");
        assertEquals(dbClientData.getChildren().toString(), enteredClientInfo.get(aboutMePage.dependentsQuantityListbox), "Wrong data was recorded into Plainuser table after registration!");
        assertEquals(dbClientData.getEducation(), enteredClientInfo.get(aboutMePage.educationListbox), "Wrong data was recorded into Plainuser table after registration!");
        assertEquals(dbClientData.getOccupationtype(), enteredClientInfo.get(aboutMePage.occupationTypeListbox), "Wrong data was recorded into Plainuser table after registration!");
//        assertEquals(dbClientData., enteredClientInfo.get(aboutMePage.currentDebtField)); поле не записывается в базу
//        assertEquals(dbClientData.getPesel(), enteredClientInfo.get(aboutMePage.contactPersonListbox)); поле не записывается в базу
//        assertEquals(dbClientData.getPesel(), enteredClientInfo.get(aboutMePage.contactPersonPhoneField)); поле не записывается в базу
        assertEquals(dbClientData.getPropertyOwn(), enteredClientInfo.get(aboutMePage.propertyOwnListbox), "Wrong data was recorded into Plainuser table after registration!");
        assertEquals(dbClientData.getPostalCode(), enteredClientInfo.get(aboutMePage.postalCodeField), "Wrong data was recorded into Plainuser table after registration!");
        assertEquals(dbClientData.getLivRegion(), enteredClientInfo.get(aboutMePage.livRegionListbox), "Wrong data was recorded into Plainuser table after registration!");
        assertEquals(dbClientData.getLivСity(), enteredClientInfo.get(aboutMePage.livCityField), "Wrong data was recorded into Plainuser table after registration!");
        assertEquals(dbClientData.getLivStreet(), enteredClientInfo.get(aboutMePage.livStreetField), "Wrong data was recorded into Plainuser table after registration!");
        assertEquals(dbClientData.getLivBuilding(), enteredClientInfo.get(aboutMePage.livBuildingField), "Wrong data was recorded into Plainuser table after registration!");
        assertEquals(dbClientData.getLivApartment(), enteredClientInfo.get(aboutMePage.livApartmentField), "Wrong data was recorded into Plainuser table after registration!");
        assertEquals(dbClientData.getEmploymentType(), enteredClientInfo.get(aboutMePage.empType1Listbox), "Wrong data was recorded into Plainuser table after registration!");
        assertEquals(dbClientData.getWorkExperience().toString(), enteredClientInfo.get(aboutMePage.workExpirience1Listbox), "Wrong data was recorded into Plainuser table after registration!");
        assertEquals(dbClientData.getIncome().toString(), enteredClientInfo.get(aboutMePage.netIncome1Field), "Wrong data was recorded into Plainuser table after registration!");
        assertEquals(dbClientData.getEmployer(), enteredClientInfo.get(aboutMePage.empName1Field), "Wrong data was recorded into Plainuser table after registration!");
        assertEquals(dbClientData.getEmployerRegion(), enteredClientInfo.get(aboutMePage.empRegion1Listbox), "Wrong data was recorded into Plainuser table after registration!");
        assertEquals(dbClientData.getEmployerPhone(), enteredClientInfo.get(aboutMePage.empPhone1Field), "Wrong data was recorded into Plainuser table after registration!");

    }

    @Test(priority = 5, dependsOnMethods = "successfulSubmittingAuthFormForRegistration")
    public void passingInitiallyBlankAboutMeScreenForRegistration() throws SQLException {
//        aboutMePage.fillAboutMePageWithBasicAcceptableData();
//        aboutMePage.getValue(aboutMePage.peselField);
        pdlOfferPage = aboutMePage.submitAboutMePageWithBasicAcceptableData();
        assertTrue(pdlOfferPage.oneElementIsPresentAtAPage("//article[@ng-if='$ctrl.topupPackage']"));
        assertTrue(pdlOfferPage.findWithXPath("//article[@ng-if='$ctrl.topupPackage']").isDisplayed(),
                "The top-up article isn't displayed, pdlOfferPage isn't loaded!");
        assertEquals(userCredentialsDAO.getUserByPhone(regPhone).size(), 1,
                "The client isn't registered after passing aboutMe page!");
    }

    @Test(priority = 4, dependsOnMethods = "successfulSubmittingPdlForm")
    public void successfulSubmittingAuthFormForRegistration() {
        aboutMePage = authPage.submitAuthFormForRegistrationWithVerifiedData();
        assertTrue(aboutMePage.breadcrumbs.isDisplayed(),
                "Page 'About me' isn't displayed after submitting auth form with correct data!");
    }

    @Test(priority = 3)
    public void submittingBlankAuthForm() {
        authPage.setBlankValuesToAuthForm()
                .markTermsCheckbox()
                .submitInvalRegForm()
                .waitForAngularRequestsToFinish();
        WebElement[] regInputs = {authPage.nameField, authPage.lastNameField, authPage.phoneField};
        try {
            for (WebElement el : regInputs)
                assertTrue(authPage.fieldBorderIsRed(el), "Blank field " + el.toString() + " doesn't have red color!");
        } catch (NoSuchElementException e) {
            authPage = authPage.goToNewAuthPage();
            throw new AssertionError("Blank registration form was submitted");
        }
    }

    @Test(priority = 3)
    public void accessibilityOfConcessionToEmailsText() {
        authPage.clickMarketingTerms();
        assertEquals(authPage.findElementsByXPath("//md-dialog[@aria-label='Wyrażam zgodę ...']").size(), 1, "Terms aren't opened!");
        assertTrue(authPage.findWithXPath("//md-dialog[@aria-label='Wyrażam zgodę ...']").isDisplayed(), "Terms aren't displayed!");
        authPage.closeDialogWindow();
        authPage.waitForClosingTerms();
    }

    @Test(priority = 3)
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

    @Test(priority = 3, dependsOnMethods = "successfulSubmittingPdlForm")
    public void submittingRegFormWithUnmarkedTermsCheckbox() {
        authPage.fillAuthFormForRegistrationWithValidData()
                .unmarkAuthCheckbox()
                .submitInvalRegForm()
                .waitForAngularRequestsToFinish();
        try {
            assertTrue(authPage.fieldIsInvalid(authPage.termsCheckBox), "Unmarked checkbox has valid state!");
            assertTrue(authPage.elementIsRed(authPage.linkRegTerms),
                    "The link to the terms near to invalid checkbox isn't highlighted with red color!");
        } catch (NoSuchElementException ex) {
            authPage.goToNewAuthPage();
            throw new AssertionError("Not completely filled registration form was submitted!", ex);
        }
    }

//    @Test(priority = 3)
//    public void validationOfPasswordConfirmationField() {
//        authPage.inputToPasswordField(password)
//                .inputToPassConfirmField(password + "a")
//                .moveFromAField(authPage.passConfirmField);
//        assertTrue(authPage.fieldIsInvalid(authPage.passConfirmField), "Password confirmation field is valid while its value isn't equal to password field!");
//        assertTrue(authPage.fieldBorderIsRed(authPage.passConfirmField), "Border of invalid field isn't highlighted with red color!");
//        authPage.inputToPassConfirmField(password)
//                .moveFromAField(authPage.passConfirmField);
//        assertFalse(authPage.fieldIsInvalid(authPage.passConfirmField), "Password confirmation field is invalid while its value is equal to password field!");
//        assertFalse(authPage.fieldBorderIsRed(authPage.passConfirmField), "Border of valid field is highlighted with red color!");
//    }

//    @Test(priority = 3)
//    public void positiveValidationOfPasswordField() {
//        authPage.inputToPasswordField("1q\\%[`")
//                .moveFromAField(authPage.passwordField);
//        assertEquals(authPage.getValue(authPage.passwordField), "1q\\%[`", "Wrong data input to password field!");
//        assertFalse(authPage.fieldIsInvalid(authPage.passwordField), "Permissible characters aren't allowed at the password field!");
//        assertFalse(authPage.fieldBorderIsRed(authPage.passwordField), "Border of valid field is highlighted with red color!");
//    }

//    @Test(priority = 3, dataProvider = "passwordValidation", dataProviderClass = DataProviders.class)
//    public void negativeValidationOfPasswordField(String password) {
//        authPage.inputToPasswordField(password)
//                .moveFromAField(authPage.passwordField);
//        assertEquals(authPage.getValue(authPage.passwordField), password, "Wrong data input to password field!");
//        assertTrue(authPage.fieldIsInvalid(authPage.passwordField), "Impermissible characters are allowed at the password field!");
//        assertTrue(authPage.fieldBorderIsRed(authPage.passwordField), "Border of invalid field isn't highlighted with red color!");
//    }


    @Test(priority = 3)
    public void positiveValidationWhileUsingHyphenInLastNameField() {
        authPage.inputToLastName("a-a")
                .moveFromAField(authPage.lastNameField);
        assertFalse(authPage.fieldIsInvalid(authPage.lastNameField), "Hyphen isn't allowed in the lastName field!");
        assertFalse(authPage.fieldBorderIsRed(authPage.lastNameField), "Border of valid field is highlighted with red color!");
    }

    @Test(priority = 3, dataProvider = "UsingOfHyphen", dataProviderClass = DataProviders.class)
    public void negativeValidationWhileUsingHyphenInLastNameField(String text) {
        authPage.inputToLastName(text)
                .moveFromAField(authPage.lastNameField);
        assertTrue(authPage.fieldIsInvalid(authPage.lastNameField), "Undisguised hyphen is allowed in the lastName field!");
        assertTrue(authPage.fieldBorderIsRed(authPage.lastNameField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 3)
    public void enteringNonLatinAndSpecCharsToLastNameField() {
        authPage.inputToLastName("іыцжч!@.\"є'=+&")
                .moveFromAField(authPage.lastNameField);
        assertEquals(authPage.getValue(authPage.lastNameField), "", "Something except latin letters is entered to the lastName field!");
        assertTrue(authPage.fieldIsInvalid(authPage.lastNameField));
        assertTrue(authPage.fieldBorderIsRed(authPage.lastNameField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 3)
    public void positiveValidationWhileUsingHyphenInNameField() {
        authPage.inputToName("All-Akb")
                .moveFromAField(authPage.nameField);
        assertFalse(authPage.fieldIsInvalid(authPage.nameField), "Hyphen isn't allowed in the name field!");
        assertFalse(authPage.fieldBorderIsRed(authPage.nameField), "Border of valid field is highlighted with red color!");
    }

    @Test(priority = 3, dataProvider = "UsingOfHyphen", dataProviderClass = DataProviders.class)
    public void negativeValidationWhileUsingHyphenInNameField(String text) {
        authPage.inputToName(text)
                .moveFromAField(authPage.nameField);
        assertTrue(authPage.fieldIsInvalid(authPage.nameField), "Undisguised hyphen is allowed in the name field!");
        assertTrue(authPage.fieldBorderIsRed(authPage.nameField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 3)
    public void enteringNonLatinAndSpecCharsToNameField() {
        authPage.inputToName("іыцжч!@.\"є'=+&")
                .moveFromAField(authPage.nameField);
        assertEquals(authPage.getValue(authPage.nameField), "", "Something except latin letters is entered to the name field!");
        assertTrue(authPage.fieldIsInvalid(authPage.nameField));
        assertTrue(authPage.fieldBorderIsRed(authPage.nameField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 3)
    public void submittingWhenPhoneIsBlank() {
        authPage.inputToPhoneField("")
                .inputToName(name)
                .inputToLastName(lastName)
                .markTermsCheckbox()
                .submitInvalRegForm()
                .waitForAngularRequestsToFinish();
        try {
            assertTrue(authPage.inputIsInvalid(authPage.phoneField));
            assertTrue(authPage.fieldBorderIsRed(authPage.phoneField)
                            || authPage.elementIsRed(authPage.findWithXPath("//*[contains(text(), 'Telefon komórkowy')]")),
                    "Border of invalid field isn't highlighted with red color!");
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Invalid PDL form is submitted", ex);
        }
    }

    @Test(priority = 3)
    //в поле для номера есть маска, поэтому оно всегда имеет непустой атрибут "value"
    public void incompletePhoneNumber() {
        authPage.inputToPhoneField("22222222");
        String def = authPage.getValueFromPDLPhoneInput();
        authPage.inputToName(name)
                .inputToLastName(lastName)
                .markTermsCheckbox()
                .submitInvalRegForm()
                .waitForAngularRequestsToFinish();
        try {
            assertTrue(authPage.inputIsInvalid(authPage.phoneField));
            assertTrue(authPage.elementIsRed(authPage.phoneField),
                    "Invalid field isn't highlighted with red color!");
            assertEquals(authPage.getValueFromPDLPhoneInput(), def, "Digits are deleted from input");
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Invalid PDL form is submitted", ex);
        }
    }

    @Test(priority = 3)
    public void enteringLettersAndSymbolsToPhone() {
        String def = authPage.getValueFromPDLPhoneInput();
        authPage.inputToPhoneField("qwe ы!@-/");
        assertEquals(authPage.getValueFromPDLPhoneInput(), def, "Letters are inputted to phone field!");
        authPage.inputToName(name)
                .inputToLastName(lastName)
                .markTermsCheckbox()
                .submitInvalRegForm()
                .waitForAngularRequestsToFinish();
        try {
            assertTrue(authPage.inputIsInvalid(authPage.phoneField));
            assertTrue(authPage.fieldBorderIsRed(authPage.phoneField), "Invalid field isn't highlighted with red color!");
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Invalid PDL form is submitted", ex);
        }
    }

    @Test(priority = 3)
    public void concessionToEmailsCheckboxIsMarkedByDefault() {
        assertTrue(authPage.checkboxIsMarked(authPage.marketingCheckbox),
                "Marketing checkbox isn't marked by default!");
    }

    @Test(priority = 2)
    public void successfulSubmittingPdlForm() {

        mainPage.logOut()
                .switchToSmallPdl();
        authPage = mainPage.startSmallPdlInUnauthorizedState();
        assertTrue(driver.getCurrentUrl().contains("/#/auth"), "The auth page isn't loaded after " +
                "pdl process was started without authorization!");
        int countVisElems = 0;
        for (WebElement el : mainPage.findElementsByXPath("//input")) {
            if (el.isDisplayed()) countVisElems++;
        }
        assertEquals(countVisElems, 3, "Not each input element is displayed!");
//        assertEquals(authPage.findElementsByXPath("//ul[@ng-controller='BreadcrumbsCtrl']").size(), 1,
//                "There are no breadcrumbs at the auth page!");
    }

    @Test(priority = 1)
    public void uncheckedCheckboxLargePdl() {
        mainPage.switchToLargePdl();
        mainPage.unmarkLargePDLCheckbox()
                .submitInvalidLargePDLForm()
                .waitForAngularRequestsToFinish();
        try {
            assertTrue(mainPage.fieldWithLargePDLCheckboxIsInvalid(), "Unmarked checkbox has valid state!");
            assertTrue(mainPage.elementIsRed(mainPage.termsLinkInLargePDL),
                    "The link to the terms near to invalid checkbox isn't highlighted with red color!");
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Invalid PDL form is submitted", ex);
        }
    }

    @Test(priority = 1)
    public void uncheckedCheckboxSmallPdl() {
        mainPage.switchToSmallPdl()
                .unmarkSmallPDLCheckbox()
                .submitInvalidSmallPDLForm()
                .waitForAngularRequestsToFinish();
        try {
            assertTrue(mainPage.fieldWithSmallPDLCheckboxIsInvalid(), "Unmarked checkbox has valid state!");
            assertTrue(mainPage.elementIsRed(mainPage.termsLinkInSmallPDL),
                    "The link to the terms near to invalid checkbox isn't highlighted with red color!");
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Invalid PDL form is submitted", ex);
        }
    }

    //здесь когда поле ввода приобретает зеленый цвет - оно становится в фокусе
    //тест должен выполнятся с нетронутым инпутом телефона, поэтому все тесты со взаимодествием с инпутом имеют приоритет выше
//    @Test(priority = 1)
//    public void submittingEmptyPdlForm() throws InterruptedException {
//        mainPage.unmarkSmallPDLCheckbox()
////                .inputToPhoneField("")
//                .submitInvalidSmallPDLForm()
//                .waitForAngularRequestsToFinish();
//        try {
////            assertTrue(mainPage.inputIsInvalid(mainPage.pdlPhoneInput));
////            assertTrue(mainPage.elementIsGreen(mainPage.pdlPhoneInput),
////                    "PDL input isn't focused after first submitting blank PDL form!");
////            mainPage.submitInvalidSmallPDLForm()
////                    .waitForReaction();
//            assertTrue(mainPage.fieldBorderIsRed(mainPage.pdlPhoneInput) ||
//                            mainPage.elementIsRed(mainPage.findWithXPath("//*[contains(text(), 'Telefon komórkowy')]")),
//                    "Border of invalid field isn't highlighted with red color!");
//            assertTrue(mainPage.fieldWithSmallPDLCheckboxIsInvalid(), "Unmarked checkbox has valid state!");
//            assertTrue(mainPage.elementIsRed(mainPage.termsLinkInSmallPDL),
//                    "The link to the terms near to invalid checkbox isn't highlighted with red color!");
//        } catch (NoSuchElementException ex) {
//            mainPage = new MainPage(driver);
//            throw new AssertionError("Invalid PDL form is submitted", ex);
//        }
//    }

    @Test(priority = 1)
    public void submitEventForEnterKeyInUnauthorizedStateAtPdlTabs() {
        mainPage.switchToSmallPdl()
                .markSmallPDLCheckbox()
                .submitFormWithEnterKeyThroughSpecificField(mainPage.smallPdlAmountInput);
        assertTrue(mainPage.oneElementIsPresentAtAPage(MainPage.smallPdlAmountInputLocator),
                "Submitting with the 'Enter' key was successful!"); //checking we stayed at the main page

        mainPage.switchToLargePdl()
                .markLargePDLCheckbox()
                .submitFormWithEnterKeyThroughSpecificField(mainPage.largePdlAmountInput);
        assertTrue(mainPage.oneElementIsPresentAtAPage(MainPage.largePdlAmountInputLocator),
                "Submitting with the 'Enter' key was successful!"); //checking we stayed at the main page
    }

    @Test(priority = 1)
    public void interactionWithTheLinkLargeLoanInfo() {
        mainPage.switchToLargePdl()
                .clickLargeLoanInfo();
        assertTrue(mainPage.oneElementIsPresentAtAPage(MainPage.mdDialogOfLargeLoanInfoLocator),
                "Info about the loans isn't opened at the Large Pdl tab!");
        assertTrue(mainPage.mdDialogOfLargeLoanInfo.isDisplayed(), "Loan info isn't displayed!");
        mainPage.closeDialogWindow();
        mainPage.waitForAngularRequestsToFinish();
//        mainPage.waitForClosingSmallLoanInfo();
    }

    @Test(priority = 1)
    public void interactionWithTheLinkSmallLoanInfo() {
        mainPage.switchToSmallPdl()
                .clickSmallLoanInfo();
        try {
            assertTrue(mainPage.mdDialogOfSmallLoanInfo.isDisplayed(), "Loan info isn't displayed!");
            mainPage.closeDialogWindow();
            mainPage.waitForClosingSmallLoanInfo();
        } catch (NoSuchElementException ex) {
            throw new AssertionError("Info about the loans isn't opened at the small Pdl tab!", ex);
        }
    }

    @Test(priority = 1)
    public void accessibilityOfAnAccessToPersDataText() {
        mainPage.clickTheTermsInSmallPDLForm();
        try {
            assertTrue(mainPage.mdDialogOfAccessToPersData.isDisplayed(), "Terms aren't displayed after click on " +
                    MainPage.termsLinkInSmallPDLLocator);
            mainPage.closeDialogWindow();
            mainPage.waitForClosingTerms();
        } catch (NoSuchElementException ex) {
            throw new AssertionError("Terms aren't opened after click on " + MainPage.termsLinkInSmallPDLLocator, ex);
        }
        mainPage.switchToLargePdl()
                .clickTheTermsInLargePDLForm();
        assertTrue(mainPage.oneElementIsPresentAtAPage(MainPage.mdDialogOfAccessToPersDataLocator),
                "Terms aren't opened after click on " + MainPage.termsLinkInLargePDLLocator);
        assertTrue(mainPage.mdDialogOfAccessToPersData.isDisplayed(), "Terms aren't displayed after click on " +
                MainPage.termsLinkInSmallPDLLocator);
        mainPage.closeDialogWindow();
        mainPage.waitForClosingTerms();
        mainPage.switchToSmallPdl();
    }

    @Test(priority = 1)
    public void inscriptionInTheSubmitButtonsIfAuthorizationIsAbsent() {
        mainPage.switchToSmallPdl();
        assertEquals(mainPage.startSmallPdlProcessButton.getText(), "DALEJ");
        mainPage.switchToLargePdl();
        assertEquals(mainPage.startLargePdlProcessButton.getText(), "DALEJ");
        mainPage.switchToConsolidation();
        assertEquals(mainPage.startConsProcessButton.getText(), "DALEJ");
        mainPage.switchToSmallPdl();
    }


    @AfterSuite
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
