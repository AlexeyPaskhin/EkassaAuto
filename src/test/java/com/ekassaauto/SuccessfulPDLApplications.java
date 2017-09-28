package com.ekassaauto;

import com.ekassaauto.PageObjects.*;
import com.ekassaauto.database.PersistenceManager;
import com.ekassaauto.database.dao.*;
import com.ekassaauto.database.entities.CpaShadowClientInformationsEntity;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import javax.persistence.EntityManager;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

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

    @BeforeSuite
    public void createDBConnection() {
        EntityManager entityManager = (new PersistenceManager()).getEntityManager();
        userCredentialsDAO = new UserCredentialsDAO(entityManager);
        plainUsersDAO = new PlainUsersDAO(entityManager);
        sentSmsDAO = new SentSmsDAO(entityManager);
        instWormCacheDAO = new InstWormCacheDAO(entityManager);
        cpaShadowClientInformationsDAO = new CpaShadowClientInformationsDAO(entityManager);
    }

//    @BeforeClass
////    @Parameters(value = {"browser"})
//    public void preparation() throws SQLException {
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
////        if (browser.equalsIgnoreCase("chrome")) {
//            ChromeDriverManager.getInstance().setup();
//            driver = new ChromeDriver();
////        } else if (browser.equalsIgnoreCase("firefox")) {
////            FirefoxDriverManager.getInstance().setup();
////            driver = new FirefoxDriver();
////        }
//        options = driver.manage();
//        options.timeouts().implicitlyWait(2, TimeUnit.SECONDS);
//        try {
//            options.window().maximize();  //из-за бага в фаерфоксе
//        } catch (WebDriverException e) {
//            System.out.println("The bug while maximizing: " + e.getMessage() + "\nEnd of message");
//        }
//        mainPage = new MainPage(driver);

//        mainPage.switchToConsolidationForm();
//        authPage = mainPage.passPdlFormInUnauthorizedState();

//    }

    @Test(priority = 15)
    public void firstAcceptableApplicationOnNewAccount() throws SQLException {
        authPage = mainPage.passPdlFormInUnauthorizedState();
        aboutMePage = authPage.submitAuthFormForRegistrationWithVerifiedData();
        aboutMePage.cleanInstWormCache(name, pesel, lastName, bankAccount);
        pdlOfferPage = aboutMePage.submitAboutMePageWithBasicAcceptableData();
        bankAccountVerificationPage = pdlOfferPage.passingPdlOfferPageWithDefaultProposalWithoutBankCache(regPhone);
        congratulationPage = bankAccountVerificationPage.successfulPassingInstantorVerification();
        assertTrue(congratulationPage.congratsTitle.isDisplayed());
    }

    @Test(priority = 15, dependsOnMethods = "firstAcceptableApplicationOnNewAccount")
    public void secondAcceptableApplicationOnNewAccountWithInstantorCache() {
        aboutMePage = mainPage.startNewPdlProcess();
        pdlOfferPage = aboutMePage.submitPrefilledAboutMePageAfterFillindAcceptedRequiredData();
        congratulationPage = pdlOfferPage.passingPdlOfferPageWithDefaultProposalWithSuccessfulBankCache(regPhone);
        assertTrue(congratulationPage.congratsTitle.isDisplayed());
    }

    @Test
    public void postRequest() throws ParseException {


        try(CloseableHttpClient client = HttpClientBuilder.create().setConnectionManagerShared(true).build()){

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("C:\\Users\\user\\IdeaProjects\\EkassaAuto\\src\\test\\resources\\test.json"));
            JSONObject data = (JSONObject)obj;

            HttpUriRequest request = RequestBuilder.create("POST")
                    .setUri("http://test.ekassa.com/rest/cpa/partnerClientData")
                    .setHeader("Content-Type", "application/json; charset=utf-8")
                    .setHeader("authorization", "Basic ZWthc3NhdXNlcjpUcmZjY2YwOTg=")
                    .setEntity(new StringEntity(data.toString(), "utf-8"))
                    .build();

            HttpResponse response = client.execute(request);
            System.out.println("RISK POLICY CHECK RESPONSE: " + response.getStatusLine().getStatusCode());
            if(response.getStatusLine().getStatusCode() == 200){
                JSONObject result = (JSONObject) parser.parse(IOUtils.toString(response.getEntity().getContent(), "utf-8"));
                System.out.println("cpa_id: " + result.get("unique_partner_id"));
//                JSONObject reply = response.getEntity().writeTo(JSONObject);
//                String procInst = (String) responseEntity.get("id");
//                ActHistoryVariablesEntity decRes = actHistoryVariableDao.getProcessVariable(procInst, "decRes");
//                ActHistoryVariablesEntity activePDL = actHistoryVariableDao.getProcessVariable(procInst, "activepdl");
//                clientId.setActivePDL(activePDL.getLongValue() == 1);
//                if (activePDL.getLongValue() == 1){
//                    clientId.setPartnerId(EKASSA_PARTNER_ID);
//                    clientId.setEnable(true);
//                }
//                return decRes != null && decRes.getLongValue() >= 0;
            }

        } catch (IOException e) {
        }
    }

    public static <T>  T getObjectFromJsonFile(String path, Class<T> type) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        Gson gson = new Gson();
        return gson.fromJson(new InputStreamReader(inputStream), type);
    }
    @Test(priority = 16)
    public void cpaPdlProcessOfRegisteredClient() {
        mainPage.logOut();
        CpaShadowClientInformationsEntity cpaEntity = cpaShadowClientInformationsDAO.getExistingCpaClientInformationsEntity();
        cpaShadowClientInformationsDAO.setFieldsOfCpaEntityForSuccessfulPdl(cpaEntity);
        System.out.println(cpaEntity.getId());
        pdlOfferPage = mainPage.goToCpaProcessWithAutoLogin(cpaEntity.getId());
        congratulationPage = pdlOfferPage.passingPdlOfferPageWithDefaultProposalWithSuccessfulBankCache(cpaEntity.getPhone());
        assertTrue(congratulationPage.congratsTitle.isDisplayed());
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
