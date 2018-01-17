package com.ekassaauto.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

/**
 * Created by user on 14.03.2017.
 */
public class MyProfilePage extends AbstractPage {
    private Form personalInfoForm;

    public static final String newPassFieldLocator = "//input[@name='newPassword']";

    @FindBy(xpath = "//button[@ng-click='logout()']") WebElement logOutButton;
    @FindBy(xpath = "//button[@ng-click='restartProcess(false)']") WebElement newPdlButton;
    @FindBy(xpath = newPassFieldLocator) WebElement newPasswordField;
    @FindBy(xpath = "//input[@name='newPasswordConfirmation']") WebElement newPasswordConfirmationField;
    @FindBy(xpath = "//form[@name='setCustomPassword']//button[@type='submit']") WebElement submitNewPasswordButton;

    public MyProfilePage(WebDriver driver) {
        super(driver);
        waitForLoadingMyProfilePage();
//        initPageElements();  фокус с формы личных данных при переходе в кабинет переместился на другую вкладку
    }

    private void waitForLoadingMyProfilePage() {
        explWait.until(elementToBeClickable(newPdlButton));
    }

    private void initPageElements() {
        personalInfoForm = new Form(findWithXPath("//form[@name='personalInfo']"));
    }

    public MainPage clickOnLogOutButton() {
        logOutButton.click();
        return new MainPage(driver);
    }

    public AboutMePage clickNewPdlButton() {
        newPdlButton.click();
        waitForAngularRequestsToFinish();
        return new AboutMePage(driver);
    }

    public MyProfilePage setNewPasswordAtPopUp(String password) {
            newPasswordField.sendKeys(password);
            newPasswordConfirmationField.sendKeys(password);
            submitNewPasswordButton.click();
            explWait.until(numberOfElementsToBe(By.xpath(newPassFieldLocator), 0));
        return this;
    }
}
