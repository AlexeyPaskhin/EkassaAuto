package com.ekassaauto;

import com.ekassaauto.Form;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by user on 14.03.2017.
 */
public class MyProfilePage extends AbstractPage {
    private Form personalInfoForm;

    @FindBy(xpath = "//button[@ng-click='logout()']") WebElement logOutButton;

    public MyProfilePage(WebDriver driver) {
        super(driver);
        initPageElements();
    }

    private void initPageElements() {
        personalInfoForm = new Form(findWithXPath("//form[@name='personalInfo']"));
    }

    MainPage logOut() {
        logOutButton.click();
        return new MainPage(driver);
    }
}
