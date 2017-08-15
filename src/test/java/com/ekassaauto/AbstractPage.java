package com.ekassaauto;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.ekassaauto.Registration.mainPage;
import static com.ekassaauto.Registration.regPage;
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

    @FindBy(xpath = "//a[contains(text(), 'MOJE PROFIL')]") WebElement myProfileLink;
//    @FindBy(xpath = "//div[@class='preloader ng-scope']") WebElement loader;
    public AbstractPage (WebDriver driver) {
        this.driver = driver;
        explWait = new WebDriverWait(driver, 10);
        this.jseDriver = (JavascriptExecutor) driver;
        ngWebDriver = new NgWebDriver(jseDriver);
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
        PageFactory.initElements(driver, this);
    }

    AbstractPage scrollToElement(WebElement element) {
        jseDriver.executeScript("arguments[0].scrollIntoView(true);", element);
        explWait.until(visibilityOf(element));
        return this;
    }

    MyProfilePage goToMyProfile() {
        myProfileLink.click();
        return new MyProfilePage(driver);
    }

    public RegPage goToNewRegPage() {
        mainPage = new MainPage(driver);
        regPage = mainPage.submitAnUnregisteredNumberThroughPDLForm();
        return new RegPage(driver);
    }

    WebElement findWithXPath(String text) {
        WebElement el = driver.findElement(By.xpath(text));
        return el;
    }

    List<WebElement> findElementsByXPath(String locator) {
        return driver.findElements(By.xpath(locator));
    }

    WebElement findWithCSS(String text) {
        WebElement el = driver.findElement(By.cssSelector(text));
        return el;
    }

    public boolean fieldIsInvalid(WebElement field){
        return field.getAttribute("class").contains("invalid");
    }

    public AbstractPage moveFromAField(WebElement field) {
        field.click();
        field.sendKeys(Keys.TAB);
        return this;
    }

    AbstractPage closeDialogWindow() {
        findWithXPath("//body").sendKeys(Keys.ESCAPE);
        return this;
    }

    public boolean fieldBorderIsRed(WebElement field) {
        return field.getCssValue("border-color").equals("rgb(221, 44, 0)");
    }

    public boolean elementIsRed(WebElement element) {
//        System.out.println(element.getCssValue("color"));
        return element.getCssValue("color").contains("255, 0, 0");
    }

    void goBack() {
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

    AbstractPage waitForAngularRequestsToFinish() {
        ngWebDriver.waitForAngularRequestsToFinish();
        return this;
    }
}
