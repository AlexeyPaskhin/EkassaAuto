import io.github.bonigarcia.wdm.*;
import net.lightbody.bmp.proxy.ProxyServer;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 09.03.2017.
 */
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
        options.timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        options.window().maximize();
        driver.get("http://test.ekassa.com");
        explWait = new WebDriverWait(driver, 10);
        mainPage = new MainPage(driver);
    }

    @Test
    public void uncheckedCheckbox() {
        mainPage.inputToPhone(REGNUMBER)
                .uncheckRegChBox()
                .submitInvalRegForm();
        Assert.assertTrue(mainPage.fieldWithChecboxIsInvalid());
    }

    @Test
    public void submittingWhenPhoneIsBlank() {
        mainPage.inputToPhone("")
                .markRegCheckbox()
                .submitInvalRegForm();
        Assert.assertTrue(mainPage.inputIsInvalid());
    }

    @Test
    public void incompletePhoneNumber() {
        mainPage.inputToPhone("22222222");
        String def = mainPage.getValueFromPhoneInput();
        mainPage.markRegCheckbox()
                .submitInvalRegForm();
        Assert.assertTrue(mainPage.inputIsInvalid());
        Assert.assertEquals(mainPage.getValueFromPhoneInput(), def, "Digits are deleted from input");
    }

    @Test
    public void enteringLettersAndSymbolsToPhone() {
        String def = mainPage.getValueFromPhoneInput();
        mainPage.inputToPhone("qwe ы!@-");
        Assert.assertEquals(mainPage.getValueFromPhoneInput(), def, "Letters are inputted to phone field!");
        mainPage.markRegCheckbox()
                .submitInvalRegForm();
        Assert.assertTrue(mainPage.inputIsInvalid());
    }

//    @Test (dependsOnMethods = "enteringLettersAndSymbolsToPhone")
//    public void next() throws IOException {
////        explWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='footer__contacts layout-wrap layout-align-center-stretch layout-row']//div[3]//a")));
//        mainPage.findWithXPath("//*[@class='footer__contacts layout-wrap layout-align-center-stretch layout-row']//div[3]//a").click();
//    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
