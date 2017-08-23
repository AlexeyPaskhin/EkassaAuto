package com.ekassaauto;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by user on 15.08.2017.
 */
public class CongratulationPage extends AbstractPage {
    @FindBy(xpath = "//h2[text()='GRATULUJEMY!']") WebElement congratsTitle;

    public CongratulationPage(WebDriver driver) {
        super(driver);
    }
}
