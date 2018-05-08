package com.ekassaauto;

import com.ekassaauto.PageObjects.*;
import com.ekassaauto.database.PersistenceManager;
import com.ekassaauto.database.dao.aui.*;
import com.ekassaauto.database.dao.risk.BoDealsDAO;
import com.ekassaauto.database.entities.aui.CpaShadowClientInformationsEntity;
import com.google.gson.Gson;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import javax.persistence.EntityManager;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import static com.ekassaauto.Registration.*;
import static org.testng.Assert.*;

/**
 * Created by user on 25.07.2017.
 */
public class SuccessfulPDLApplications {
    protected WebDriver driver;
    WebDriver.Options options;
    private MainPage mainPage;
    private AuthPage authPage;
    private AboutMePage aboutMePage;
    private PdlOfferPage pdlOfferPage;
    private BankAccountVerificationPage bankAccountVerificationPage;
    private CongratulationPage congratulationPage;
    public static String chromeDriverPath = "/usr/bin/chromedriver";


    private void startBrowser() {
        ////        if (browser.equalsIgnoreCase("chrome")) {
//        ChromeDriverManager.getInstance().setup();
//        driver = new ChromeDriver();
//        } else if (browser.equalsIgnoreCase("firefox")) {
//            FirefoxDriverManager.getInstance().setup();
//            driver = new FirefoxDriver();
//        }

        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        System.out.println("CREATING A DRIVER!!!1");
        driver = new ChromeDriver();

        options = driver.manage();
        options.timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        try {
            options.window().maximize();  //из-за бага в фаерфоксе
        } catch (WebDriverException e) {
            System.out.println("The bug while maximizing: " + e.getMessage() + "\nEnd of message");
        }
    }

    @BeforeSuite
    public void createDBConnections() {
        PersistenceManager persistenceManager = new PersistenceManager();
        EntityManager auiEntityManager = persistenceManager.getAuiEntityManager();
        EntityManager riskEntityManager = persistenceManager.getRiskEntityManager();

        userCredentialsDAO = new UserCredentialsDAO(auiEntityManager);
        plainUsersDAO = new PlainUsersDAO(auiEntityManager);
        sentSmsDAO = new SentSmsDAO(auiEntityManager);
        instWormCacheDAO = new InstWormCacheDAO(auiEntityManager);
        cpaShadowClientInformationsDAO = new CpaShadowClientInformationsDAO(auiEntityManager);
        cpaClientCasheDAO = new CpaClientCasheDAO(auiEntityManager);
        bmOutgoingPaymentDAO = new BMOutgoingPaymentDAO(auiEntityManager);

        boDealsDAO = new BoDealsDAO(riskEntityManager);
    }

    @BeforeClass
//    @Parameters(value = {"browser"})
    public void preparation() throws SQLException {
////        String property = System.getProperty("user.dir") + "/drivers/chromedriver.exe";
////        System.setProperty("webdriver.chrome.driver", property);
////        InternetExplorerDriverManager.getInstance().setup();
////        OperaDriverManager.getInstance().forceCache().setup();
////        EdgeDriverManager.getInstance().setup();
////        PhantomJsDriverManager.getInstance().setup();
////        FirefoxDriverManager.getInstance().setup();
////        driver = new FirefoxDriver();
////        ProxyServer server = new ProxyServer(4444);
////        server.start();
////        server.autoBasicAuthorization("test.ekassa.com", "ekassauser", "Trfccf098");
////        Proxy proxy = server.seleniumProxy();
////        DesiredCapabilities capabilities = new DesiredCapabilities();
////        capabilities.setCapability(CapabilityType.PROXY, proxy);
////        driver = new ChromeDriver(capabilities);
////        driver = new OperaDriver();
//        cpaShadowClientInformationsDAO.getCpaClientById(12441L);
//        boDealsDAO.printId("92102107697");
        startBrowser();
        mainPage = new MainPage(driver);
//        mainPage.switchToConsolidation();
//        authPage = mainPage.startSmallPdlInUnauthorizedState();

    }

    @Test(enabled = false)
    public void createALotOfBMTransactionsWithDifferentPesel() {
        bmOutgoingPaymentDAO.createBMTransactionsWithDifferentPesel();
    }

    @Test(enabled = false)
    public void createDuplicateBMTransactions() {
        while (true) {
//        while (Calendar.getInstance().before("2017-11-24 17:10:00")) {
            bmOutgoingPaymentDAO.createDuplicateBMTransaction(66127910L);
        }
    }

    @Test(priority = 19)
    public void firstAcceptableApplicationOnNewAccount() throws SQLException {
        mainPage.logOut();
        authPage = mainPage.startSmallPdlInUnauthorizedState();
        aboutMePage = authPage.submitAuthFormForRegistrationWithVerifiedData();
        aboutMePage.cleanInstWormCache(name, pesel, lastName, bankAccount);
        pdlOfferPage = aboutMePage.submitAboutMePageWithBasicAcceptableData();
        bankAccountVerificationPage = pdlOfferPage.passPdlOfferPageSelectingTopUpWithoutBankCache(regPhone);
        congratulationPage = bankAccountVerificationPage.successfulPassingInstantorVerification();
        congratulationPage.printURL();
        assertTrue(congratulationPage.congratsTitle.isDisplayed());
    }

    @Test(priority = 20, dependsOnMethods = "firstAcceptableApplicationOnNewAccount")
    public void receivingSuccessfulInstantorReport() throws SQLException {
        mainPage.waitForReceivinginstantorReport(name, pesel, lastName, bankAccount);
        assertTrue(instWormCacheDAO.instWormCacheIsSuccessful(name, pesel, lastName, bankAccount));
    }

    @Test(priority = 20, dependsOnMethods = "receivingSuccessfulInstantorReport")
    public void secondAcceptableApplicationOnNewAccountHavingInstantorCache() {
        aboutMePage = mainPage.startNewPdlProcessViaMyProfile();
        pdlOfferPage = aboutMePage.passPrefilledAboutMePageGottenFromMyProfile();
        congratulationPage = pdlOfferPage.passPdlOfferPageSelectingTopUpWithSuccessfulBankCache(regPhone);
        assertTrue(congratulationPage.congratsTitle.isDisplayed());
    }



    public static <T> T getObjectFromJsonFile(String path, Class<T> type) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        Gson gson = new Gson();
        return gson.fromJson(new InputStreamReader(inputStream), type);
    }

    @Test(priority = 18, enabled = false)
    public void cpaPdlProcessOfRegisteredClient() {
        mainPage.logOut();
        CpaShadowClientInformationsEntity cpaEntity = cpaShadowClientInformationsDAO.getExistingCpaClientInformationsEntity();
        cpaShadowClientInformationsDAO.setFieldsOfCpaEntityForSuccessfulPdl(cpaEntity);
        System.out.println(cpaEntity.getId());
        pdlOfferPage = mainPage.goToCpaProcessWithAutoLoginAndSkipPersonalData(cpaEntity.getId());
        congratulationPage = pdlOfferPage.passPdlOfferPageSelectingTopUpWithSuccessfulBankCache(cpaEntity.getPhone());
        congratulationPage.printURL();
        assertTrue(congratulationPage.congratsTitle.isDisplayed());
    }


    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
