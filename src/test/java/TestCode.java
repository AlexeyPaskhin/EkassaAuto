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
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by user on 09.03.2017.
 */
public class TestCode extends MainPage{

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
        server.autoBasicAuthorization("", "ekassauser", "Trfccf098");
        Proxy proxy = server.seleniumProxy();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, proxy);

        driver = new ChromeDriver(capabilities);
        options=driver.manage();
        options.timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        options.window().maximize();

    }

    @Test
    public void sampleTest() {
        goToMainPage();
        markCheckbox();
        submitRegForm();
        Assert.assertNotNull(findWithXPath("//*[@class='md-block md-input-has-placeholder ng-animate md-input-invalid']"));
    }

//    @Test
//    public void next() {
//        findWithXPath("//*[@class='footer__contacts layout-wrap layout-align-center-stretch layout-row']//div[3]//a").click();
//    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
