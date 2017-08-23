package com.ekassaauto;

import com.ekassaauto.database.PersistenceManager;
import com.ekassaauto.database.dao.InstWormCacheDAO;
import com.ekassaauto.database.dao.PlainUsersDAO;
import com.ekassaauto.database.dao.SentSmsDAO;
import com.ekassaauto.database.dao.UserCredentialsDAO;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import static com.ekassaauto.Registration.*;

/**
 * Created by user on 25.07.2017.
 */
public class SuccessfulPDLApplications {

    @BeforeSuite
    public void createDBConnection() {
        EntityManager entityManager = (new PersistenceManager()).getEntityManager();
        userCredentialsDAO = new UserCredentialsDAO(entityManager);
        plainUsersDAO = new PlainUsersDAO(entityManager);
        sentSmsDAO = new SentSmsDAO(entityManager);
        instWormCacheDAO = new InstWormCacheDAO(entityManager);
    }

    @BeforeClass
    public void preparation() throws SQLException {
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

//        authPage = mainPage.submitPdlFormInUnauthorizedState();
//        aboutMePage = authPage.submitRegFormWithVerifiedData();
//        aboutMePage.cleanInstWormCache();
//        pdlOfferPage = aboutMePage.submitAboutMePageWithAcceptableData();
//        bankAccountVerificationPage = pdlOfferPage.passingPdlOfferPageWithDefaultProposalWithoutBankCache();
//        congratulationPage = bankAccountVerificationPage.successfulPassingInstantorVerification();

    }

    @Test(priority = 15)
    public void firstAcceptableApplicationOnNewAccount() throws SQLException {
        authPage = mainPage.submitPdlFormInUnauthorizedState();
        aboutMePage = authPage.submitRegFormWithVerifiedData();
        aboutMePage.cleanInstWormCache();
        pdlOfferPage = aboutMePage.submitAboutMePageWithAcceptableData();
        bankAccountVerificationPage = pdlOfferPage.passingPdlOfferPageWithDefaultProposalWithoutBankCache();
        congratulationPage = bankAccountVerificationPage.successfulPassingInstantorVerification();
    }

    @Test(priority = 16)
    public void cpaPdlProcessOfRegisteredClient() {
        congratulationPage.goToMyProfile()
                .logOut();

    }

    @AfterSuite
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
