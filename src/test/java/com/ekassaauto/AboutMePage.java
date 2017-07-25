package com.ekassaauto;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

/**
 * Created by user on 06.07.2017.
 */
public class AboutMePage extends AbstractPage {
    private Form smsCodeForm;

    @FindBy(xpath = "//ul[@ng-controller='BreadcrumbsCtrl']") WebElement breadcrumbs;

    public AboutMePage(WebDriver driver) {
        super(driver);
        initPageElements();
    }

    private void initPageElements() {
        smsCodeForm = new Form(findWithXPath("//form[@name='workForm']"));
    }
}


