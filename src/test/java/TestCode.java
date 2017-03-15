import io.github.bonigarcia.wdm.*;
import net.lightbody.bmp.proxy.ProxyServer;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
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
public class TestCode {
    ProxyServer server;
    private WebDriver driver;
    private MainPage mainPage;
    WebDriver.Options options;
    WebDriverWait explWait;
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

        server = new ProxyServer(4444);
        server.start();
        server.autoBasicAuthorization(".com", "ekassauser", "Trfccf098");
        Proxy proxy = server.seleniumProxy();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, proxy);

        driver = new ChromeDriver(capabilities);
        options=driver.manage();
        options.timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://test.ekassa.com");
        options.window().maximize();
        explWait = new WebDriverWait(driver, 10);

    }

    @Test
    public void sampleTest() {
        mainPage = new MainPage(driver);
        mainPage.markCheckbox().submitRegForm();
        Assert.assertNotNull(mainPage.findWithXPath("//*[@class='md-block md-input-has-placeholder ng-animate md-input-invalid']"));
    }

    @Test (dependsOnMethods = "sampleTest")
    public void next() {
//        explWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='footer__contacts layout-wrap layout-align-center-stretch layout-row']//div[3]//a")));
        mainPage.findWithXPath("//*[@class='footer__contacts layout-wrap layout-align-center-stretch layout-row']//div[3]//a").click();
    }

//    @AfterClass
//    public void teardown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
}
