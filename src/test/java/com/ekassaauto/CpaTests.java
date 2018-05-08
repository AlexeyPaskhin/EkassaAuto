package com.ekassaauto;

import com.ekassaauto.PageObjects.*;
import com.ekassaauto.database.PersistenceManager;
import com.ekassaauto.database.dao.aui.*;
import com.ekassaauto.database.dao.risk.BoDealsDAO;
import com.ekassaauto.database.entities.aui.CpaShadowClientInformationsEntity;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
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
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;

import javax.persistence.EntityManager;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.ekassaauto.Registration.*;
import static org.testng.Assert.*;

/**
 * Created by user on 19.02.2018.
 */
public class CpaTests {
    protected WebDriver driver;
    WebDriver.Options options;
    private MainPage mainPage;
    private AuthPage authPage;
    private AboutMePage aboutMePage;
    private PdlOfferPage pdlOfferPage;
    private BankAccountVerificationPage bankAccountVerificationPage;
    private CongratulationPage congratulationPage;
    private String regPhone = "222222217";

    private JSONParser parser = new JSONParser();

    private void startBrowser() {
        ////        if (browser.equalsIgnoreCase("chrome")) {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
//        } else if (browser.equalsIgnoreCase("firefox")) {
//            FirefoxDriverManager.getInstance().setup();
//            driver = new FirefoxDriver();
//        }
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
    public void preparation() {
        startBrowser();
        mainPage = new MainPage(driver);
    }

//    @Test
//    public void kek() throws AWTException {
////        new Actions(driver)
////                .keyDown(Keys.CONTROL)
////                .keyDown(Keys.getKeyFromUnicode('t'))
////                .keyUp(Keys.CONTROL)
////                .keyUp(Keys.getKeyFromUnicode('t'))
////                .build()
////                .perform();
//
////        driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, "T"));
//        Robot r = new Robot();
//        r.keyPress(KeyEvent.VK_CONTROL);
//        r.keyPress(KeyEvent.VK_T);
//        r.keyRelease(KeyEvent.VK_CONTROL);
//        r.keyRelease(KeyEvent.VK_T);
////To switch to the new tab
//        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
//        for (String a :
//                tabs) {
//            System.out.println(a);
//        }
//        driver.switchTo().window(tabs.get(1));
////To navigate to new link/URL in 2nd new tab
//        driver.get("http://facebook.com");
//        driver.navigate().refresh();
//    }

    @Test(invocationCount = 2)
    public void newCpaClientProcessWithMinOffer() throws ParseException, SQLException {
        mainPage.logOut();
        cpaClientCasheDAO.deleteAllCpaCache();
        userCredentialsDAO.deleteUserByPhone(regPhone);
        instWormCacheDAO.deleteInstWormCache(name, pesel, lastName, bankAccount);

        try (CloseableHttpClient client = HttpClientBuilder.create().setConnectionManagerShared(true).build()) {

            JSONObject data = (JSONObject) parser.parse(new FileReader("src/test/resources/cpaInfo.json"));
            data.put("phone", regPhone); data.put("empType1", AboutMePage.EmploymentTypes.Student.getValue());

            HttpUriRequest request = buildCpaPostRequest(data);

            HttpResponse response = client.execute(request);
            System.out.println("RISK POLICY CHECK RESPONSE: " + response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 200) {
                CpaShadowClientInformationsEntity newCpaClientEntity = getCpaEntryFromRestResponse(response);

                assertTrue(newCpaClientEntity.isAutoLogin(), "New client cpa entry with all required fields doesn't have auto login!");
                assertTrue(newCpaClientEntity.isSkipPersonalData(),
                        "New cpa client will not see the offer screen immediately but will see the 'AboutMe' screen!");
                assertTrue(newCpaClientEntity.isEnable(), "New client cpa entry isn't enabled!");

                pdlOfferPage = mainPage.goToCpaProcessWithAutoLoginAndSkipPersonalData(newCpaClientEntity.getId());
                assertTrue(pdlOfferPage.findElementsByXPath("//article").size() == 1,  //кол-во блоков офферов == 1
                        "Min Counter offer wasn't proposed! Or check the 'sumOfNetIncome' variable at this phone: " + regPhone);
                bankAccountVerificationPage = pdlOfferPage.passCpaPdlOfferPageSelectingTopUpWithoutBankCache(regPhone);
                congratulationPage = bankAccountVerificationPage.successfulPassingInstantorVerification();
                assertTrue(congratulationPage.congratsTitle.isDisplayed());
            } else throw new AssertionError("Rest request was failed!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dataProvider = "requiredFieldsForCpa", dataProviderClass = DataProviders.class
            ,dependsOnMethods = "newCpaClientProcessWithAllRequiredFields"
    )
    public void existingCpaClientWithOneMissedRequiredField(String parameter, String removedFieldLocator) throws SQLException, ParseException {
        mainPage.logOut();
        cpaClientCasheDAO.deleteAllCpaCache();
        instWormCacheDAO.deleteInstWormCache(name, pesel, lastName, bankAccount);

        try (CloseableHttpClient client = HttpClientBuilder.create().setConnectionManagerShared(true).build()) {

            JSONObject data = (JSONObject) parser.parse(new FileReader("src/test/resources/cpaInfo.json"));
            data.put("phone", regPhone);
            data.remove(parameter);

            HttpUriRequest request = buildCpaPostRequest(data);

            HttpResponse response = client.execute(request);
            System.out.println("RISK POLICY CHECK RESPONSE: " + response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 200) {
                CpaShadowClientInformationsEntity existingCpaClientEntity = getCpaEntryFromRestResponse(response);

                assertFalse(existingCpaClientEntity.isAutoLogin(), "Existing client cpa entry without all required fields has auto login!");
                assertFalse(existingCpaClientEntity.isSkipPersonalData(),
                        "Existing cpa client without all required data will see the offer screen immediately instead the 'AboutMe' screen!");
                assertTrue(existingCpaClientEntity.isEnable(), "New client cpa entry isn't enabled!");

                authPage = mainPage.goToCpaProcessWithoutAutoLogin(existingCpaClientEntity.getId());

                assertTrue(driver.getCurrentUrl().contains("/#/auth/"),
                        "The auth page isn't displayed for existing cpa client without all required data!");
                switch (parameter) {
                    case "firstName":
                        assertEquals(authPage.getValue(authPage.nameField), "",
                                "First name isn't empty for cpa-process of existing client after deleting responsive parameter!");
                        break;
                    case "lastName":
                        assertEquals(authPage.getValue(authPage.lastNameField), "",
                                "Last name isn't empty for cpa-process of existing client after deleting responsive parameter!");
                        break;
                    default:
                        aboutMePage = authPage.inputToSmsCodeField(sentSmsDAO.getSmsCodeByPhone(regPhone))
                                .markTermsCheckbox()
                                .submitValidRegForm();

                        assertTrue(!aboutMePage.elementIsAbsentAtAPage(removedFieldLocator) &&
                                        aboutMePage.findWithXPath(removedFieldLocator).isDisplayed(),
                                "Element " + removedFieldLocator + " ins't present at the 'Employment' page!");

                        switch (aboutMePage.findWithXPath(removedFieldLocator).getTagName()) {
                            case "input":
                                assertEquals(aboutMePage.getValue(aboutMePage.findWithXPath(removedFieldLocator)), "",
                                        "Removed field " + removedFieldLocator + " isn't empty!");
                                break;
                            case "md-select":
                                assertTrue(aboutMePage.inputIsInvalid(aboutMePage.findWithXPath(removedFieldLocator)),
                                        "Removed field " + removedFieldLocator + " isn't empty!");
                                break;
                            default:
                                throw new AssertionError("Wrong element is under testing. Check out the locator " +
                                        removedFieldLocator);
                        }

                        aboutMePage.submitInvalidAboutMePage();
                        assertTrue(driver.getCurrentUrl().contains("/#/employment/"), "Invalid AboutMe page was submitted!");
                        assertTrue(aboutMePage.elementIsRed(aboutMePage.findWithXPath(removedFieldLocator + "/../label")),
                                "Invalid field isn't highlighted with red color!");  //проверяем что лейбл на данном поле подсвечен

                        break;
                }
            } else throw new AssertionError("Rest request was failed!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void newCpaClientProcessWithAllRequiredFields() throws ParseException, SQLException {
        mainPage.logOut();
        cpaClientCasheDAO.deleteAllCpaCache();
        userCredentialsDAO.deleteUserByPhone(regPhone);
        instWormCacheDAO.deleteInstWormCache(name, pesel, lastName, bankAccount);

        try (CloseableHttpClient client = HttpClientBuilder.create().setConnectionManagerShared(true).build()) {

            JSONObject data = (JSONObject) parser.parse(new FileReader("src/test/resources/cpaInfo.json"));
            data.put("phone", regPhone); data.put("empType1", AboutMePage.EmploymentTypes.FullTimeEmployed.getValue());

            HttpUriRequest request = buildCpaPostRequest(data);

            HttpResponse response = client.execute(request);
            System.out.println("RISK POLICY CHECK RESPONSE: " + response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 200) {
                CpaShadowClientInformationsEntity newCpaClientEntity = getCpaEntryFromRestResponse(response);

                assertTrue(newCpaClientEntity.isAutoLogin(), "New client cpa entry with all required fields doesn't have auto login!");
                assertTrue(newCpaClientEntity.isSkipPersonalData(),
                        "New cpa client will not see the offer screen immediately but will see the 'AboutMe' screen!");
                assertTrue(newCpaClientEntity.isEnable(), "New client cpa entry isn't enabled!");

                pdlOfferPage = mainPage.goToCpaProcessWithAutoLoginAndSkipPersonalData(newCpaClientEntity.getId());
                assertTrue(pdlOfferPage.findElementsByXPath("//article").size() == 3,  //кол-во блоков офферов == 3
                        "Min Counter offer was proposed! Or check tha 'sumOfNetIncome' variable at this phone: " + regPhone);
                bankAccountVerificationPage = pdlOfferPage.passCpaPdlOfferPageSelectingTopUpWithoutBankCache(regPhone);
                congratulationPage = bankAccountVerificationPage.successfulPassingInstantorVerification();
                assertTrue(congratulationPage.congratsTitle.isDisplayed());
            } else {
                JSONObject result = (JSONObject) parser.parse(IOUtils.toString(response.getEntity().getContent(), "utf-8"));
                System.out.println(result.get("Error"));
//                for (Map.Entry<String, String> e:
//                     result.entrySet()) {
//
//                }throw new AssertionError("Rest request was failed!");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CpaShadowClientInformationsEntity getCpaEntryFromRestResponse(HttpResponse response) throws IOException, ParseException {
        JSONObject result = (JSONObject) parser.parse(IOUtils.toString(response.getEntity().getContent(), "utf-8"));
        Long cpaId = (Long) result.get("unique_partner_id");
        System.out.println("cpa_id=" + cpaId);
        return cpaShadowClientInformationsDAO.getCpaClientById(cpaId);
    }

    private HttpUriRequest buildCpaPostRequest(JSONObject data) {
        return RequestBuilder.create("POST")
                .setUri("http://test.ekassa.com/rest/cpa/partnerClientData")
                .setHeader("Content-Type", "application/json; charset=utf-8")
                .setHeader("authorization", "Basic ZWthc3NhdXNlcjpUcmZjY2YwOTg=")
                .setEntity(new StringEntity(data.toString(), "utf-8"))
                .build();
    }

    @Test
    public void newCpaClientProcessWithAllMissedRequiredFields() throws SQLException, ParseException {
        mainPage.logOut();
        cpaClientCasheDAO.deleteAllCpaCache();
        userCredentialsDAO.deleteUserByPhone(regPhone);
        instWormCacheDAO.deleteInstWormCache(name, pesel, lastName, bankAccount);

        try (CloseableHttpClient client = HttpClientBuilder.create().setConnectionManagerShared(true).build()) {

            JSONObject data = (JSONObject) parser.parse(new FileReader("src/test/resources/cpaInfo.json"));
            data.put("phone", regPhone);
            data.remove("amountRequested");
            data.remove("tenorRequested");
            data.remove("firstName");
            data.remove("lastName");
            data.remove("pesel");
            data.remove("socialNumber");
            data.remove("maritalStatus");
            data.remove("education");
            data.remove("email");
            data.remove("propertyOwn");
            data.remove("empType1");
            data.remove("netIncome1");
            data.remove("existPmt");
            data.remove("workExperience1");
            data.remove("accountNumber");
            data.remove("livPostcode");
            data.remove("livCity");
            data.remove("livStreet");
            data.remove("livBuilding");

            HttpUriRequest request = buildCpaPostRequest(data);

            HttpResponse response = client.execute(request);
            System.out.println("RISK POLICY CHECK RESPONSE: " + response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 200) {
                CpaShadowClientInformationsEntity newCpaClientEntity = getCpaEntryFromRestResponse(response);

                //todo - should be auto login true for normal logic
                assertFalse(newCpaClientEntity.isAutoLogin()/*, "New client cpa entry without all required fields has auto login!"*/);
                assertFalse(newCpaClientEntity.isSkipPersonalData(),
                        "New cpa client will see the offer screen immediately instead the 'AboutMe' screen!");
                assertTrue(newCpaClientEntity.isEnable(), "New client cpa entry isn't enabled!");

                aboutMePage = mainPage.goToCpaProcessWithAutoLoginWithoutSkipPersonalData(newCpaClientEntity.getId());

                assertTrue(driver.getCurrentUrl().contains("employment"), "The AboutMe page isn't displayed for filling invalid data!");
                assertEquals(aboutMePage.getValue(aboutMePage.peselField), "", "Removed field isn't empty!");
                assertTrue(aboutMePage.inputIsInvalid(aboutMePage.findWithXPath(AboutMePage.educationListboxLocator)),
                        "Removed field " + AboutMePage.educationListboxLocator + " isn't empty!");
                aboutMePage.submitInvalidAboutMePage();
                assertTrue(driver.getCurrentUrl().contains("/#/employment/"), "Invalid AboutMe page was submitted!");
            } else throw new AssertionError("Rest request was failed!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dataProvider = "requiredFieldsForCpa", dataProviderClass = DataProviders.class)
    public void newCpaClientProcessWithOneMissedRequiredField(String parameter, String removedFieldLocator) throws SQLException, ParseException {
        mainPage.logOut();
        cpaClientCasheDAO.deleteAllCpaCache();
        userCredentialsDAO.deleteUserByPhone(regPhone);
        instWormCacheDAO.deleteInstWormCache(name, pesel, lastName, bankAccount);

        try (CloseableHttpClient client = HttpClientBuilder.create().setConnectionManagerShared(true).build()) {

            JSONObject data = (JSONObject) parser.parse(new FileReader("src/test/resources/cpaInfo.json"));
            data.put("phone", regPhone);
            data.remove(parameter);

            HttpUriRequest request = buildCpaPostRequest(data);

            HttpResponse response = client.execute(request);
            System.out.println("RISK POLICY CHECK RESPONSE: " + response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 200) {
                CpaShadowClientInformationsEntity newCpaClientEntity = getCpaEntryFromRestResponse(response);

                //todo - should be auto login true for normal logic
                assertFalse(newCpaClientEntity.isAutoLogin(), "New client cpa entry without all required fields has auto login!");
                assertFalse(newCpaClientEntity.isSkipPersonalData(),
                        "New cpa client will see the offer screen immediately instead the 'AboutMe' screen!");
                assertTrue(newCpaClientEntity.isEnable(), "New client cpa entry isn't enabled!");

                aboutMePage = mainPage.goToCpaProcessWithAutoLoginWithoutSkipPersonalData(newCpaClientEntity.getId());

                assertTrue(driver.getCurrentUrl().contains("/#/employment/"), "The AboutMe page isn't displayed for filling invalid data!");

                assertTrue(!aboutMePage.elementIsAbsentAtAPage(removedFieldLocator) &&
                                aboutMePage.findWithXPath(removedFieldLocator).isDisplayed(),
                        "Element " + removedFieldLocator + " ins't present at the 'Employment' page!");

                switch (aboutMePage.findWithXPath(removedFieldLocator).getTagName()) {
                    case "input":
                        assertEquals(aboutMePage.getValue(aboutMePage.findWithXPath(removedFieldLocator)), "",
                                "Removed field " + removedFieldLocator + " isn't empty!");
                        break;
                    case "md-select":
                        assertTrue(aboutMePage.inputIsInvalid(aboutMePage.findWithXPath(removedFieldLocator)),
                                "Removed field " + removedFieldLocator + " isn't empty!");
                        break;
                    default:
                        throw new AssertionError("Wrong element is under testing. Check out the locator " +
                                removedFieldLocator);
                }

                aboutMePage.submitInvalidAboutMePage();
                assertTrue(driver.getCurrentUrl().contains("/#/employment/"), "Invalid AboutMe page was submitted!");
                assertTrue(aboutMePage.elementIsRed(aboutMePage.findWithXPath(removedFieldLocator + "/../label")),
                        "Invalid field isn't highlighted with red color!");  //проверяем что лейбл на данном поле подсвечен
            } else throw new AssertionError("Rest request was failed!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
