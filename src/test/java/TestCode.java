import io.github.bonigarcia.wdm.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by user on 09.03.2017.
 */
public class TestCode {
    @BeforeClass
//    @Test
    public void preparation() {
//        String property = System.getProperty("user.dir") + "/drivers/chromedriver.exe";
//        System.setProperty("webdriver.chrome.driver", property);

        ChromeDriverManager.getInstance().setup();
        InternetExplorerDriverManager.getInstance().setup();
        OperaDriverManager.getInstance().setup();
        EdgeDriverManager.getInstance().setup();
        PhantomJsDriverManager.getInstance().setup();
        FirefoxDriverManager.getInstance().setup();ChromeDriverManager.getInstance().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://ekassa.com/#/");
    }

    @Test
    public void sampleTest() {
        System.out.println("hello world");
    }
}
