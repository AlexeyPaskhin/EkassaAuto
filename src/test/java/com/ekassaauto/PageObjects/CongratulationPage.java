package com.ekassaauto.PageObjects;

import com.ekassaauto.PageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;


/**
 * Created by user on 15.08.2017.
 */
public class CongratulationPage extends AbstractPage {
    @FindBy(xpath = "//h2[text()='GRATULUJEMY!']") public WebElement congratsTitle;

    public CongratulationPage(WebDriver driver) {
        super(driver);
        exitFromAFrame(); //на случай прохождения инстантора, выход с его фрейма
        waitForCongratsPage();
    }

    public void waitForCongratsPage() {
        new WebDriverWait(driver,30).until(visibilityOfElementLocated(By.xpath("//h2[text()='GRATULUJEMY!']")));
    }
}
