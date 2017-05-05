import io.github.bonigarcia.wdm.*;
import net.lightbody.bmp.proxy.ProxyServer;
import org.openqa.selenium.*;
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

import java.util.concurrent.TimeUnit;

/**
 * Created by user on 09.03.2017.
 */
@Test
public class Registration {
    static String regNumber = "222222220";
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
        options.timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        options.window().maximize();
        mainPage = new MainPage(driver);
//        regPage = mainPage.submitAnUnregNumber();
    }

    @Test(priority = 3, dataProvider = "emailValidation", dataProviderClass = DataProviders.class)
    public void negativeValidationOfEmailField(String text) {
        regPage.inputToEmailField(text)
                .moveFromAField(regPage.emailField);
        Assert.assertTrue(regPage.fieldIsInvalid(regPage.emailField), "Impermissible characters are allowed at the email field!");
        Assert.assertTrue(regPage.fieldBorderIsRed(regPage.emailField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 3)
    public void positiveValidationWhileUsingHyphenInLastNameField() {
        regPage.inputToLastName("a-a")
                .moveFromAField(regPage.lastNameField);
        Assert.assertFalse(regPage.fieldIsInvalid(regPage.lastNameField), "Hyphen isn't allowed in the surname field!");
        Assert.assertFalse(regPage.fieldBorderIsRed(regPage.lastNameField), "Border of valid field is highlighted with red color!");
    }

    @Test(priority = 3, dataProvider = "UsingOfHyphen", dataProviderClass = DataProviders.class)
    public void negativeValidationWhileUsingHyphenInLastNameField(String text) {
        regPage.inputToLastName(text)
                .moveFromAField(regPage.lastNameField);
        Assert.assertTrue(regPage.fieldIsInvalid(regPage.lastNameField), "Undisguised hyphen is allowed in the surname field!");
        Assert.assertTrue(regPage.fieldBorderIsRed(regPage.lastNameField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 3)
    public void enteringNonLatinAndSpecCharsToLastNameField() {
        regPage.inputToLastName("іыцжч!@.\"є'=+&")
                .moveFromAField(regPage.lastNameField);
        Assert.assertEquals(regPage.getValue(regPage.lastNameField), "", "Something except latin letters is entered to the surname field!");
        Assert.assertTrue(regPage.fieldIsInvalid(regPage.lastNameField));
        Assert.assertTrue(regPage.fieldBorderIsRed(regPage.lastNameField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 3)
    public void positiveValidationWhileUsingHyphenInNameField() {
        regPage.inputToName("An-Mar")
                .moveFromAField(regPage.nameField);
        Assert.assertFalse(regPage.fieldIsInvalid(regPage.nameField), "Hyphen isn't allowed in the name field!");
        Assert.assertFalse(regPage.fieldBorderIsRed(regPage.nameField), "Border of valid field is highlighted with red color!");
    }

    @Test(priority = 3, dataProvider = "UsingOfHyphen", dataProviderClass = DataProviders.class)
    public void negativeValidationWhileUsingHyphenInNameField(String text) {
        regPage.inputToName(text)
                .moveFromAField(regPage.nameField);
        Assert.assertTrue(regPage.fieldIsInvalid(regPage.nameField), "Undisguised hyphen is allowed in the name field!");
        Assert.assertTrue(regPage.fieldBorderIsRed(regPage.nameField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 2)
    public void enteringNonLatinAndSpecCharsToNameField() {
        regPage = mainPage.submitAnUnregNumber();
        regPage.inputToName("іыцжч!@.\"є'=+&")
                .moveFromAField(regPage.nameField);
        Assert.assertEquals(regPage.getValue(regPage.nameField), "", "Something except latin letters is entered to the name field!");
        Assert.assertTrue(regPage.fieldIsInvalid(regPage.nameField));
        Assert.assertTrue(regPage.fieldBorderIsRed(regPage.nameField), "Border of invalid field isn't highlighted with red color!");
    }

    @Test(priority = 1)
    public void uncheckedCheckbox() throws InterruptedException {
        mainPage.inputToPhone(regNumber)
                .uncheckRegChBox()
                .submitInvalRegForm()
                .waitForReaction();
        try {
            Assert.assertTrue(mainPage.fieldWithCheckboxIsInvalid());
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Registration form is submitted", ex);
        }
    }

    @Test(priority = 1)
    public void submittingWhenPhoneIsBlank() throws InterruptedException {
        mainPage.markRegCheckbox()
                .inputToPhone("")
                .submitInvalRegForm()
                .waitForReaction();
        try {
            Assert.assertTrue(mainPage.inputIsInvalid());
            mainPage.submitInvalRegForm()
                    .waitForReaction();
            Assert.assertTrue(mainPage.fieldBorderIsRed(mainPage.input), "Border of invalid field isn't highlighted with red color!");
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Registration form is submitted", ex);
        }
    }

    @Test(priority = 1)
    //в поле для номера есть маска, поэтому оно всегда имеет непустой атрибут "value"
    public void incompletePhoneNumber() throws InterruptedException {
        mainPage.inputToPhone("22222222");
        String def = mainPage.getValueFromPhoneInput();
        mainPage.markRegCheckbox()
                .submitInvalRegForm()
                .waitForReaction();
        try {
            Assert.assertTrue(mainPage.inputIsInvalid());
            Assert.assertEquals(mainPage.getValueFromPhoneInput(), def, "Digits are deleted from input");
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Registration form is submitted", ex);
        }
    }

    @Test(priority = 1)
    public void enteringLettersAndSymbolsToPhone() throws InterruptedException {
        String def = mainPage.getValueFromPhoneInput();
        mainPage.inputToPhone("qwe ы!@-");
        Assert.assertEquals(mainPage.getValueFromPhoneInput(), def, "Letters are inputted to phone field!");
        mainPage.markRegCheckbox()
                .submitInvalRegForm()
                .waitForReaction();
        try {
            Assert.assertTrue(mainPage.inputIsInvalid());
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Registration form is submitted", ex);
        }
    }

    @Test(priority = 1)
    public void interactionWithTheLinkLoanInfo() {
        mainPage.clickLoanInfo();
        try {
            Assert.assertTrue(mainPage.mdDialogOfLoanInfo.isDisplayed(), "Loan info isn't displayed!");
            mainPage.closeDialogWindow()
                    .waitForClosingLoanInfo();
        } catch (NoSuchElementException ex) {
            throw new AssertionError("Info about the loans isn't opened!", ex);
        }
    }

    @Test(priority = 1)
    public void accessibilityOfAServiceTermsText() {
        mainPage.clickTheTerms();
        try {
            Assert.assertTrue(mainPage.mdDialogOfTerms.isDisplayed(), "Terms aren't displayed!");
            mainPage.closeDialogWindow()
                    .waitForClosingTerms();
        } catch (NoSuchElementException ex) {
            throw new AssertionError("Terms aren't opened!", ex);
        }
    }


//    @AfterClass
//    public void teardown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }

}
