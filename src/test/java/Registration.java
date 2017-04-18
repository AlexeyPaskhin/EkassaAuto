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

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.nonNull;

/**
 * Created by user on 09.03.2017.
 */
@Test
public class Registration {
    private WebDriver driver;
    private MainPage mainPage;
    private RegPage regPage;
    WebDriver.Options options;
    WebDriverWait explWait;
    private static String REGNUMBER = "222222220";

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
        explWait = new WebDriverWait(driver, 10);
        mainPage = new MainPage(driver);
    }

    @Test
    public void uncheckedCheckbox() {
        mainPage.inputToPhone(REGNUMBER)
                .uncheckRegChBox()
                .submitInvalRegForm();
        Assert.assertTrue(mainPage.fieldWithCheckboxIsInvalid());
    }

    @Test
    public void submittingWhenPhoneIsBlank() throws InterruptedException {
        mainPage.markRegCheckbox()
                .inputToPhone("")
                .submitInvalRegForm()
                .waitForReaction();
        try {
            Assert.assertTrue(mainPage.inputIsInvalid());
        } catch (NoSuchElementException ex) {
            mainPage = new MainPage(driver);
            throw new AssertionError("Registration form is submitted", ex);
        }
    }

    @Test
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

    @Test
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
//    @Test
//    public void accessibilityOfAServiceTermsText() {
//        mainPage.clickTheTerms();
//        Assert.assertNotNull(mainPage.frameOfTerms, "Terms aren't opened!");
//        mainPage.exitFromTerms();
//    }

//    @Test (dependsOnMethods = "enteringLettersAndSymbolsToPhone")
//    public void next() throws IOException {
////        explWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='footer__contacts layout-wrap layout-align-center-stretch layout-row']//div[3]//a")));
//        mainPage.findWithXPath("//*[@class='footer__contacts layout-wrap layout-align-center-stretch layout-row']//div[3]//a").click();
//    }

//    @AfterClass
//    public void teardown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
}
