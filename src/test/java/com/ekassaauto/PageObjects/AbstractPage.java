package com.ekassaauto.PageObjects;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 14.03.2017.
 */
public abstract class AbstractPage {
    protected WebDriver driver;
    private NgWebDriver ngWebDriver;
    WebDriverWait explWait;
    JavascriptExecutor jseDriver;

    @FindBy(xpath = "//ul[@ng-controller='BreadcrumbsCtrl']") public WebElement breadcrumbs;
    @FindBy(xpath = "//a[contains(text(), 'MÓJ PROFIL')]") WebElement myProfileLink;
//    @FindBy(xpath = "//div[@class='preloader ng-scope']") WebElement loader;
    public AbstractPage (WebDriver driver) {
        this.driver = driver;
        explWait = new WebDriverWait(driver, 10);
        this.jseDriver = (JavascriptExecutor) driver;
        ngWebDriver = new NgWebDriver(jseDriver);
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
        PageFactory.initElements(driver, this);
    }

    //works incorrect
    AbstractPage setToFieldWithJS(WebElement field, String value) {
        jseDriver.executeScript("arguments[1].value = arguments[0]; ", value, field);
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

    public AuthPage goToNewAuthPage() {
        logOut();
        return new MainPage(driver).passPdlFormInUnauthorizedState();
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
//        System.out.println(element.getCssValue("color"));
        return element.getCssValue("color").contains("255, 0, 0");
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

    public AbstractPage waitForAngularRequestsToFinish() {
        ngWebDriver.waitForAngularRequestsToFinish();
        return this;
    }

    public PdlOfferPage goToCpaProcessWithAutoLogin(Long id) {

        driver.get("http://test.ekassa.com/#/?cpa_id=" + id);
        driver.navigate().refresh();
        waitForAngularRequestsToFinish();
        return new PdlOfferPage(driver);
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

    public MainPage logOut() {
        if (myProfileLink.isDisplayed()) {
            return goToMyProfile()
                    .clickOnLogOutButton();
        }
        else return new MainPage(driver);
    }

    public AboutMePage startNewPdlProcess() {
        return goToMyProfile()
                .clickNewPdlButton();
    }
}
