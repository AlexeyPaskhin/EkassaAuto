import com.google.sitebricks.http.As;
import io.github.bonigarcia.wdm.*;
import net.lightbody.bmp.proxy.ProxyServer;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

/**
 * Created by user on 09.03.2017.
 */
@Test
public class Registration {
    static String regNumber = "222222220", name = "Alex", surname = "Paskhin", email = "a.paskhin1@gmail.com", password = "111111a";
    WebDriver.Options options;
    private WebDriver driver;
    private MainPage mainPage;
    private RegPage regPage;

    @BeforeClass
    public void preparation() {
//        String property = System.getProperty("user.dir") + "/drivers/chromedriver.exe";
//        System.setProperty("webdriver.chrome.driver", property);
        ChromeDriverManager.getInstance().setup();
//        InternetExplorerDriverManager.getInstance().setup();
//        OperaDriverManager.getInstance().forceCache().setup();
//        EdgeDriverManager.getInstance().setup();
//        PhantomJsDriverManager.getInstance().setup();
//        FirefoxDriverManager.getInstance().setup();
//        ProxyServer server = new ProxyServer(4444);
//        server.start();
//        server.autoBasicAuthorization("test.ekassa.com", "ekassauser", "Trfccf098");
//        Proxy proxy = server.seleniumProxy();
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability(CapabilityType.PROXY, proxy);
//        driver = new ChromeDriver(capabilities);
//        driver = new FirefoxDriver();
        driver = new ChromeDriver();
        options = driver.manage();
        options.timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        options.window().maximize();
        mainPage = new MainPage(driver);
//        regPage = mainPage.submitAnUnregNumber();
    }

    @Test(priority = 3)
    public void submittingBlankRegForm() {
        regPage.setBlankValuesToRegForm()
                .submitInvalRegForm();
        WebElement[] regInputs = {regPage.nameField, regPage.lastNameField, regPage.emailField, regPage.passwordField, regPage.passConfirmField};
        try{
            for (WebElement el : regInputs)
                assertTrue(regPage.fieldBorderIsRed(el));
        } catch (NoSuchElementException e) {
            mainPage = new MainPage(driver);
            regPage = mainPage.submitAnUnregNumber();
            throw new AssertionError("Blank registration form was submitted");
        }
    }

    @Test(priority = 3)
    public void equalityValidationOfPasswordsWhileEditingMainPassField() {
        regPage.fillRegFormWithValidData()
                .inputToPasswordField(password+"a")
                .moveFromAField(regPage.passwordField);   //этот шаг необязательный, но селениум слишком быстро проверяет цвет и приходится что то еще сделать))
        assertTrue(regPage.fieldBorderIsRed(regPage.passConfirmField));
        regPage.submitInvalRegForm();
        assertEquals(regPage.findElementsByXPath("//*[contains(text(), 'Hasła wprowadzone nie pasują do siebie!')]").size(), 1, "Error message isn't displayed!");
    }

    @Test(priority = 3)
    public void accessibilityOfConcessionToEmailsText() {
        regPage.clickMarketingTerms();
        assertEquals(regPage.findElementsByXPath("//md-dialog[@aria-label='Wyrażam zgodę ...']").size(),  1,"Terms aren't opened!");
        assertTrue(regPage.findWithXPath("//md-dialog[@aria-label='Wyrażam zgodę ...']").isDisplayed(), "Terms aren't displayed!");
        regPage.closeDialogWindow();
        regPage.waitForClosingTerms();
    }

    @Test(priority = 3)
    public void accessibilityOfARegTermsText() {
        regPage.clickRegTerms();
        assertEquals(regPage.findElementsByXPath("//md-dialog[@aria-label='Potwierdzam, że ...']").size(), 1,"Terms aren't opened!");
        assertTrue(regPage.findWithXPath("//md-dialog[@aria-label='Potwierdzam, że ...']").isDisplayed(), "Terms aren't displayed!");
        regPage.closeDialogWindow();
        regPage.waitForClosingTerms();
    }

    @Test(priority = 3)
    public void submittingRegFormWithUnmarkedTermsCheckbox() throws InterruptedException {
        regPage.fillRegFormWithValidData()
                .unmarkRegCheckbox()
                .submitInvalRegForm()
                .waitForReaction();
        try {
            assertTrue(regPage.fieldIsInvalid(regPage.termsCheckBox), "Unmarked checkbox has valid state!");
            assertTrue(regPage.elementIsRed(regPage.linkRegTerms), "linkRegTerms near to invalid checkbox isn't highlighted with red color!");
        } catch (NoSuchElementException ex) {
            regPage.goBack();
            throw new AssertionError("Unfilled registration form is submitted", ex);
        }
    }

    @Test(priority = 3)
    public void validationOfPasswordConfirmationField() {
        regPage.inputToPasswordField(password)
                .inputToPassConfirmField(password + "a")
                .moveFromAField(regPage.passConfirmField);
        assertTrue(regPage.fieldIsInvalid(regPage.passConfirmField), "Password confirmation field is valid while its value isn't equal to password field!");
        assertTrue(regPage.fieldBorderIsRed(regPage.passConfirmField), "Border of invalid field isn't highlighted with red color!");
        regPage.inputToPassConfirmField(password)
                .moveFromAField(regPage.passConfirmField);
        assertFalse(regPage.fieldIsInvalid(regPage.passConfirmField), "Password confirmation field is invalid while its value is equal to password field!");
        assertFalse(regPage.fieldBorderIsRed(regPage.passConfirmField), "Border of valid field is highlighted with red color!");
    }

    @Test(priority = 3)
    public void positiveValidationOfPasswordField() {
        regPage.inputToPasswordField("1q\\%[`")
                .moveFromAField(regPage.passwordField);
        assertEquals(regPage.getValue(regPage.passwordField), "1q\\%[`", "Wrong data input to password field!");
        assertFalse(regPage.fieldIsInvalid(regPage.passwordField), "Permissible characters aren't allowed at the password field!");
        assertFalse(regPage.fieldBorderIsRed(regPage.passwordField), "Border of valid field is highlighted with red color!");
    }

    @Test(priority = 3, dataProvider = "passwordValidation", dataProviderClass = DataProviders.class)
    public void negativeValidationOfPasswordField(String password) {
        regPage.inputToPasswordField(password)
                .moveFromAField(regPage.passwordField);
        assertEquals(regPage.getValue(regPage.passwordField), password, "Wrong data input to password field!");
        assertTrue(regPage.fieldIsInvalid(regPage.passwordField), "Impermissible characters are allowed at the password field!");
        assertTrue(regPage.fieldBorderIsRed(regPage.passwordField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 3)
    //видимо селениум видоизменяет кириллицу после "@" в малопонятные символы, поэтому будем считать, что ожидаем вводимую букву увидеть такой, как написано в "expected"
    public void positiveValidationOfEmailField() {
        regPage.inputToEmailField("a.paskhin-@gmail-ы.coms")
                .moveFromAField(regPage.emailField);
        assertEquals(regPage.getValue(regPage.emailField), "a.paskhin-@xn--gmail--ntf.coms", "Wrong data input to email field!");
        assertFalse(regPage.fieldIsInvalid(regPage.emailField), "Permissible characters aren't allowed at the email field!");
        assertFalse(regPage.fieldBorderIsRed(regPage.emailField), "Border of valid field is highlighted with red color!");
    }

    @Test(priority = 3, dataProvider = "emailValidation", dataProviderClass = DataProviders.class)
    public void negativeValidationOfEmailField(String text) {
        regPage.inputToEmailField(text)
                .moveFromAField(regPage.emailField);
        assertTrue(regPage.fieldIsInvalid(regPage.emailField), "Impermissible characters are allowed at the email field!");
        assertTrue(regPage.fieldBorderIsRed(regPage.emailField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 3)
    public void positiveValidationWhileUsingHyphenInLastNameField() {
        regPage.inputToLastName("a-a")
                .moveFromAField(regPage.lastNameField);
        assertFalse(regPage.fieldIsInvalid(regPage.lastNameField), "Hyphen isn't allowed in the surname field!");
        assertFalse(regPage.fieldBorderIsRed(regPage.lastNameField), "Border of valid field is highlighted with red color!");
    }

    @Test(priority = 3, dataProvider = "UsingOfHyphen", dataProviderClass = DataProviders.class)
    public void negativeValidationWhileUsingHyphenInLastNameField(String text) {
        regPage.inputToLastName(text)
                .moveFromAField(regPage.lastNameField);
        assertTrue(regPage.fieldIsInvalid(regPage.lastNameField), "Undisguised hyphen is allowed in the surname field!");
        assertTrue(regPage.fieldBorderIsRed(regPage.lastNameField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 3)
    public void enteringNonLatinAndSpecCharsToLastNameField() {
        regPage.inputToLastName("іыцжч!@.\"є'=+&")
                .moveFromAField(regPage.lastNameField);
        assertEquals(regPage.getValue(regPage.lastNameField), "", "Something except latin letters is entered to the surname field!");
        assertTrue(regPage.fieldIsInvalid(regPage.lastNameField));
        assertTrue(regPage.fieldBorderIsRed(regPage.lastNameField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 3)
    public void positiveValidationWhileUsingHyphenInNameField() {
        regPage.inputToName("An-Mar")
                .moveFromAField(regPage.nameField);
        assertFalse(regPage.fieldIsInvalid(regPage.nameField), "Hyphen isn't allowed in the name field!");
        assertFalse(regPage.fieldBorderIsRed(regPage.nameField), "Border of valid field is highlighted with red color!");
    }

    @Test(priority = 3, dataProvider = "UsingOfHyphen", dataProviderClass = DataProviders.class)
    public void negativeValidationWhileUsingHyphenInNameField(String text) {
        regPage.inputToName(text)
                .moveFromAField(regPage.nameField);
        assertTrue(regPage.fieldIsInvalid(regPage.nameField), "Undisguised hyphen is allowed in the name field!");
        assertTrue(regPage.fieldBorderIsRed(regPage.nameField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 3)
    public void enteringNonLatinAndSpecCharsToNameField() {
        regPage.inputToName("іыцжч!@.\"є'=+&")
                .moveFromAField(regPage.nameField);
        assertEquals(regPage.getValue(regPage.nameField), "", "Something except latin letters is entered to the name field!");
        assertTrue(regPage.fieldIsInvalid(regPage.nameField));
        assertTrue(regPage.fieldBorderIsRed(regPage.nameField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 2)
    public void successfulSubmittingPdlForm() {
        regPage = mainPage.submitAnUnregNumber();
        int countVisElems = 0;
        for (WebElement el : mainPage.findElementsByXPath("//input")) {
            if (el.isDisplayed()) countVisElems ++;
        }
        assertEquals(countVisElems, 5, "Not each input element is displayed!");
        assertTrue(regPage.CheckboxIsMarked(regPage.marketingCheckbox));
    }

    @Test(priority = 1)
    public void uncheckedCheckbox() throws InterruptedException {
        mainPage.inputToPhone(regNumber)
                .uncheckPDLChBox()
                .submitInvalPDLForm()
                .waitForReaction();
        try {
            assertTrue(mainPage.fieldWithCheckboxIsInvalid(), "Unmarked checkbox has valid state!");
            assertTrue(mainPage.elementIsRed(mainPage.linkTerms), "linkRegTerms near to invalid checkbox isn't highlighted with red color!");
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("PDL form is submitted", ex);
        }
    }

    @Test(priority = 1)
    public void submittingWhenPhoneIsBlank() throws InterruptedException {
        mainPage.markPDLCheckbox()
                .inputToPhone("")
                .submitInvalPDLForm()
                .waitForReaction();
        try {
            assertTrue(mainPage.inputIsInvalid());
            mainPage.submitInvalPDLForm()
                    .waitForReaction();
            assertTrue(mainPage.fieldBorderIsRed(mainPage.input), "Border of invalid field isn't highlighted with red color!");
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Invalid PDL form is submitted", ex);
        }
    }

    @Test(priority = 1)
    //в поле для номера есть маска, поэтому оно всегда имеет непустой атрибут "value"
    public void incompletePhoneNumber() throws InterruptedException {
        mainPage.inputToPhone("22222222");
        String def = mainPage.getValueFromPhoneInput();
        mainPage.markPDLCheckbox()
                .submitInvalPDLForm()
                .waitForReaction();
        try {
            assertTrue(mainPage.inputIsInvalid());
            assertEquals(mainPage.getValueFromPhoneInput(), def, "Digits are deleted from input");
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Invalid PDL form is submitted", ex);
        }
    }

    @Test(priority = 1)
    public void enteringLettersAndSymbolsToPhone() throws InterruptedException {
        String def = mainPage.getValueFromPhoneInput();
        mainPage.inputToPhone("qwe ы!@-");
        assertEquals(mainPage.getValueFromPhoneInput(), def, "Letters are inputted to phone field!");
        mainPage.markPDLCheckbox()
                .submitInvalPDLForm()
                .waitForReaction();
        try {
            assertTrue(mainPage.inputIsInvalid());
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Invalid PDL form is submitted", ex);
        }
    }

    //здесь когда поле ввода приобретает зеленый цвет - оно становится в фокусе
    @Test(priority = 1)
    public void submittingEmptyPdlForm() throws InterruptedException {
        mainPage.uncheckPDLChBox()
                .inputToPhone("")
                .submitInvalPDLForm()
                .waitForReaction();
        try {
            assertTrue(mainPage.inputIsInvalid());
            assertTrue(mainPage.elementIsGreen(mainPage.input), "Input isn't focused!");
            mainPage.submitInvalPDLForm()
                    .waitForReaction();
            assertTrue(mainPage.fieldBorderIsRed(mainPage.input), "Border of invalid field isn't highlighted with red color!");
            assertTrue(mainPage.fieldWithCheckboxIsInvalid(), "Unmarked checkbox has valid state!");
            assertTrue(mainPage.elementIsRed(mainPage.linkTerms), "linkRegTerms near to invalid checkbox isn't highlighted with red color!");
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
        mainPage.clickTheTerms();
        try {
            assertTrue(mainPage.mdDialogOfAccessToPersData.isDisplayed(), "Terms aren't displayed!");
            mainPage.closeDialogWindow();
            mainPage.waitForClosingTerms();
        } catch (NoSuchElementException ex) {
            throw new AssertionError("Terms aren't opened!", ex);
        }
    }


    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
