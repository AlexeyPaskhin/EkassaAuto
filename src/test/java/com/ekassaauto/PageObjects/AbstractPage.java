package com.ekassaauto.PageObjects;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.ekassaauto.Registration.instWormCacheDAO;
import static com.ekassaauto.Registration.password;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 14.03.2017.
 */
public abstract class AbstractPage {
    protected WebDriver driver;
    private NgWebDriver ngWebDriver;
    WebDriverWait explWait;
    JavascriptExecutor jseDriver;
    Actions actions;

    private static final String myProfileLinkLocator = "//a[@href='#/profile']";
    private static final String logOutButtonLocator = "//a[@ng-click='logout()']";
    private static final String confirmLogOutButtonLocator = "//button[@ng-click='dialog.hide()']";
    private static final String loaderLocator = "//*[text()='Prosimy doczekać się następnego ekranu']";

    @FindBy(xpath = "//ul[@ng-controller='BreadcrumbsCtrl']") public WebElement breadcrumbs;
    @FindBy(xpath = myProfileLinkLocator) WebElement myProfileLink;
    @FindBy(xpath = logOutButtonLocator) WebElement logOutButton;
    @FindBy(xpath = confirmLogOutButtonLocator) WebElement confirmLogOutButton;
    @FindBy(xpath = loaderLocator) WebElement loader;
    public AbstractPage (WebDriver driver) {
        this.driver = driver;
        explWait = new WebDriverWait(driver, 10);
        this.jseDriver = (JavascriptExecutor) driver;
        ngWebDriver = new NgWebDriver(jseDriver);
        actions = new Actions(driver);
        driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
        PageFactory.initElements(driver, this);
        waitForLoaderIsAbsent();
        waitForAngularRequestsToFinish();
    }

    //works incorrect
    AbstractPage setToFieldWithJS(WebElement field, String value) {
        jseDriver.executeScript("arguments[1].value = arguments[0]; ", value, field);
        return this;
    }

    AbstractPage selectFromListBoxByValueWithPuttingToMap(WebElement listBox, String value, Map<WebElement, String> compareMap) {
        listBox.click();
        explWait.until(elementToBeClickable(By.xpath("//div[@aria-hidden='false']//md-option[@value='" + value + "']")));
        listBox.findElement(By.xpath("//div[@aria-hidden='false']//md-option[@value='" + value + "']")).click();
//        actions.moveToElement(listBox).click().build().perform();
        //есть дублирующиеся варианты в разных листбоксах на странице, поэтому с помощью @aria-hidden='false'
        //выбираем только те элементы, что видимы на странице(там, где листбокс открыт)

        compareMap.put(listBox, value);
        return this;
    }

    public AbstractPage checkedSetWithPuttingToMap(WebElement field, String value, Map<WebElement, String> compareMap) {
        compareMap.put(field, value);
        int countOfRewriting = -1;
        do {
            explWait.until(elementToBeClickable(field));
            field.click();
            field.clear();
            field.click();
            field.sendKeys(value);
            countOfRewriting ++; //at the first inputting number of rewrites = 0
        } while (!getValue(field).equals(value));
        if (countOfRewriting != 0) System.out.println("Count of rewrites to input " + field + " : " + countOfRewriting);
        return this;
    }

    //в поле почтового индекса маска, и надо сравнивать значения с учетом нее
    AbstractPage checkedSetToPostalCodeFieldWithPuttingToMap(WebElement field, String postalCode, Map<WebElement, String> compareMap) {
        compareMap.put(field, postalCode);
        do {
            field.click();
            field.clear();
            field.click();
            field.sendKeys(postalCode);
        } while (!getValue(field).equals(postalCode.substring(0,2) + "-" + postalCode.substring(2,5)));
        return this;
    }

    //в поле телефона маска, и надо сравнивать значения с учетом нее
    AbstractPage checkedSetToPhoneFieldWithPuttingToMap(WebElement field, String phone, Map<WebElement, String> compareMap) {
        compareMap.put(field, phone);
        do {
            field.click();
            field.clear();
            field.click();
            field.sendKeys(phone);
        } while (!getValue(field).equals("(+48) " + phone.substring(0,2) + " " + phone.substring(2,5) + "-" + phone.substring(5,7)
                + "-" + phone.substring(7,9)));
        return this;
    }

    public String getValue(WebElement field) {
        return field.getAttribute("value");
    }

    public boolean inputIsInvalid(WebElement input) {
        return input.getAttribute("aria-invalid").equals("true");
    }

    public AbstractPage selectFromListBoxByText(WebElement listBox, String textInOption) {
        listBox.click();
        listBox.findElement(By.xpath("//div[@aria-hidden='false']//md-option/div[contains(text(), '"
                + textInOption + "')]/..")).sendKeys(Keys.RETURN);  //тоже какой-то md-backdrop оверлей, приходится костылить
        //есть дублирующиеся варианты в разных листбоксах на странице, поэтому с помощью @aria-hidden='false'
        //выбираем только те элементы, что видимы на странице(там, где листбокс открыт)
        return this;
    }

    AbstractPage scrollToElement(WebElement element) {
        jseDriver.executeScript("arguments[0].scrollIntoView(true);", element);
        explWait.until(visibilityOf(element));
        return this;
    }

    public MyProfilePage goToMyProfile() {
        myProfileLink.click();
        return new MyProfilePage(driver);
    }

    public MyProfilePage goToMyProfileHidingNewPassPopUp() {

        myProfileLink.click();
        if (oneElementIsPresentAtAPage(MyProfilePage.newPassFieldLocator)) {
            new MyProfilePage(driver).setNewPasswordAtPopUp(password);
        }
        return new MyProfilePage(driver);
    }

    public AuthPage goToNewAuthPage() {
        logOut();
        return new MainPage(driver).startSmallPdlInUnauthorizedState();
    }

    public WebElement findWithXPath(String text) {
        WebElement el = driver.findElement(By.xpath(text));
        return el;
    }

    public List<WebElement> findElementsByXPath(String locator) {
        return driver.findElements(By.xpath(locator));
    }

    WebElement findWithCSS(String text) {
        WebElement el = driver.findElement(By.cssSelector(text));
        return el;
    }

    public boolean fieldIsInvalid(WebElement field){
        return field.getAttribute("aria-invalid").equals("true");
    }

    public AbstractPage moveFromAField(WebElement field) {
        field.click();
        field.sendKeys(Keys.TAB);
        return this;
    }

    public AbstractPage closeDialogWindow() {
        findWithXPath("//body").sendKeys(Keys.ESCAPE);
        return this;
    }

    public boolean fieldBorderIsRed(WebElement field) {
        System.out.println(field.getCssValue("border-color"));
        return field.getCssValue("border-color").contains("255, 0, 0")
                || field.getCssValue("border-color").contains("221, 44, 0");
    }

    public boolean elementIsRed(WebElement element) {
        System.out.println(element.getCssValue("color"));
        return element.getCssValue("color").contains("255, 0, 0")
                || element.getCssValue("color").contains("221, 44, 0");
    }

    public void goBack() {
        driver.navigate().back();
    }

    public boolean elementIsGreen(WebElement element) {
//                System.out.println(element.getCssValue("color"));
        return element.getCssValue("color").contains("67, 160, 71");
    }

    public boolean checkboxIsMarked(WebElement checkbox) {
        return checkbox.getAttribute("aria-checked").equals("true");
    }

    public void customWaitForPerformingJS() {
        try {   // try - на случай, если js в каком то кейсе не начал выполнятся, попытка перехода на другую страницу не произвелась
            new WebDriverWait(driver, 1).until(numberOfElementsToBe(By.xpath("//div[@class='preloader ng-scope']"), 1));
        } catch (TimeoutException e) {
            return;
        }
        explWait.until(numberOfElementsToBe(By.xpath("//div[@class='preloader ng-scope']"), 0));
    }

    public void waitForReceivinginstantorReport(String firstName, String pesel, String secondName, String accountNumber) {
        new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) driver -> {
            try {

                return instWormCacheDAO.instWormCacheForPdlPresent(firstName, pesel, secondName, accountNumber);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    public void cleanInstWormCache(String name, String pesel, String lastName, String bankAccount) throws SQLException {
        if (instWormCacheDAO.instWormCacheForPdlPresent(name, pesel, lastName, bankAccount)) {
            instWormCacheDAO.deleteInstWormCache(name, pesel, lastName, bankAccount);
        }
    }

    public AbstractPage waitForAngularRequestsToFinish() {
        ngWebDriver.waitForAngularRequestsToFinish();
        return this;
    }

    public AbstractPage waitForLoaderIsAbsent() {
        new WebDriverWait(driver, 30).until(numberOfElementsToBe(By.xpath(loaderLocator), 0));
        return this;
    }

    public void printURL() {
        System.out.println(driver.getCurrentUrl());
    }

    public PdlOfferPage goToCpaProcessWithAutoLoginAndSkipPersonalData(Long id) {
        driver.navigate().to("http://ekassa.pl");
        URL url = null;
        try {
            url = new URL("http://test.ekassa.com/#/?cpa_id=" + id);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.navigate().to(url);
//        driver.navigate().refresh();
        waitForAngularRequestsToFinish();
        return new PdlOfferPage(driver);
    }
    public AboutMePage goToCpaProcessWithAutoLoginWithoutSkipPersonalData(Long cpaId) {
        driver.navigate().to("http://google.com");
        driver.navigate().to("http://test.ekassa.com/#/?cpa_id=" + cpaId);
//        driver.get("http://test.ekassa.com/#/?cpa_id=" + cpaId);
//        driver.navigate().refresh();
        waitForAngularRequestsToFinish();
        return new AboutMePage(driver);
    }

    public AuthPage goToCpaProcessWithoutAutoLogin(Long cpaId) {
        driver.navigate().to("http://google.com.ua");
        driver.navigate().to("http://test.ekassa.com/#/?cpa_id=" + cpaId);
        waitForAngularRequestsToFinish();
        return new AuthPage(driver);
    }

    public AbstractPage submitFormWithEnterKeyThroughSpecificField(WebElement field) {
        field.click();
        field.sendKeys(Keys.ENTER);
        waitForAngularRequestsToFinish();
        return this;
    }

    public AbstractPage exitFromAFrame() {
        driver.switchTo().defaultContent();
        return this;
    }

    public Boolean oneElementIsPresentAtAPage(String xPathLocator) {
        return findElementsByXPath(xPathLocator).size() == 1;
    }
    public Boolean oneOrMoreElementsArePresentAtAPage(String xPathLocator) {
        return findElementsByXPath(xPathLocator).size() >= 1;
    }
    public Boolean elementIsAbsentAtAPage(String xPathLocator) {
        return findElementsByXPath(xPathLocator).size() == 0;
    }

    public MainPage logOut() {
        new MainPage(driver);
        waitForAngularRequestsToFinish();
        if (oneOrMoreElementsArePresentAtAPage(myProfileLinkLocator) && myProfileLink.isDisplayed()) {
            clickOnLogOutButton();
            return confirmLogOut();
                    /*goToMyProfileHidingNewPassPopUp()  //in case we enter first time to personal account
                    .clickOnLogOutButton();*/
        }
        else return new MainPage(driver);
    }

    public void clickOnLogOutButton() {
        logOutButton.click();
//        waitForAngularRequestsToFinish();
    }

    private MainPage confirmLogOut() {
        confirmLogOutButton.click();
        waitForAngularRequestsToFinish();
        return new MainPage(driver);
    }

    public AboutMePage startNewPdlProcessViaMyProfile() {
        return goToMyProfileHidingNewPassPopUp()
                .clickNewPdlButton();
    }

    public AbstractPage markCheckBox(WebElement chBox) {
        if (chBox.getAttribute("aria-checked").equals("false"))
            chBox.click();
        return this;
    }

    public AbstractPage set(WebElement field, String value) {
        field.clear();
        field.click();
        field.sendKeys(value);
        return this;
    }


}
