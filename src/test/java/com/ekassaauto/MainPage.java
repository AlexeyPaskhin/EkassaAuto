package com.ekassaauto;

import com.ekassaauto.database.entities.UserCredential;
import org.openqa.selenium.*;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.sql.SQLException;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static com.ekassaauto.Registration.*;

/**
 * Created by user on 10.03.2017.
 */
public class MainPage extends AbstractPage {
    private Form pdlRegForm;
    private Form consolidationRegForm;

    @FindBy(xpath = "//input[@name='phone']") protected WebElement pdlPhoneInput;
    @FindBy(xpath = "(//input[@name='phone'])[2]") protected WebElement consolidationPhoneInput;
    @FindBy(xpath = "//*[@type='submit']") WebElement submitPDLButton;
    @FindBy(xpath = "(//*[@type='submit'])[2]") WebElement submitConsButton;
    @FindBy(xpath = "//md-dialog[@aria-label='Potwierdzam, że ...']") protected WebElement mdDialogOfAccessToPersData;
    @CacheLookup
    @FindBy(xpath = "//md-dialog[@aria-label='Chwilówka w ...']") protected WebElement mdDialogOfLoanInfo;
    @CacheLookup
    @FindBy(xpath = "//md-dialog[@aria-label='Kredyt konsolidacyjny ...']") protected WebElement mdDialogOfConsolidationInfo;
    @FindBy(xpath = "//md-checkbox[@ng-model='agreedToConditions']") private WebElement pdlRegCheckbox;
    @FindBy(xpath = "(//md-checkbox[@ng-model='agreedToConditions'])[2]") private WebElement consolidationRegCheckbox;
    @FindBy(xpath = "//span[@ng-click='showAgreements($event)']") protected WebElement termsLinkInPDL;
    @FindBy(xpath = "(//span[@ng-click='showAgreements($event)'])[2]") protected WebElement termsLinkInConsolidation;
    @FindBy(xpath = "//span[@ng-click='showLoanInfo($event)']") private WebElement linkLoanInfo;
    @FindBy(xpath = "//div[@ng-click='showConsolidationInfo($event)']") private WebElement linkConsolidationInfo;
    @FindBy(xpath = "//div[text()='Kredyt konsolidacyjny']") WebElement consolidationOverlay;


    MainPage(WebDriver driver) {
        super(driver);
        driver.get("http://test.ekassa.com");
        waitForOpennessOfPDLCalc();
        if (!"Pożyczki na raty: chwilówki ratalne online - Ekassa".equals(driver.getTitle())) {
            throw new IllegalStateException("This is not the main page");
        }
        initPageElements();
    }

    private void initPageElements() {
        pdlRegForm = new Form(findWithXPath("//form"));
        consolidationRegForm = new Form(findWithXPath("(//form)[2]"));
    }

    MainPage submitInvalidPDLForm() {
        pdlRegForm.submit(submitPDLButton);
        return this;
    }

    MainPage submitInvalidConsolidationForm() {
        pdlRegForm.submit(submitConsButton);
        return this;
    }


    RegPage submitAnUnregisteredNumberThroughPDLForm() {
        try {
            List<UserCredential> requestedUserCredentials = userCredentialsDAO.getUserByPhone(regPhone);
            if (requestedUserCredentials.size() == 1) {
                userCredentialsDAO.deleteUserByPhone(regPhone);
            } else if (requestedUserCredentials.size() > 1) {
                throw new AssertionError("There are more than 1 unique phone number located in the usercredentials table!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //todo удалять процессы так же с камунды с данным номером
        pdlRegForm.setToFieldWithOverlay(pdlPhoneInput, regPhone)
                .markCheckBox(pdlRegCheckbox)
                .submit(submitPDLButton);

        customWaitForPerformingJS();

////        explWait.until(presenceOfElementLocated(By.xpath("//pdlPhoneInput[@name='name']")));
//        explWait.until(elementToBeClickable(By.xpath("//pdlPhoneInput[@name='name']")));
        return new RegPage(driver);
    }

    RegPage submitAnUnregisteredNumberThroughConsolidationForm() {
        try {
            List<UserCredential> requestedUserCredentials = userCredentialsDAO.getUserByPhone(regPhone);
            if (requestedUserCredentials.size() == 1) {
                userCredentialsDAO.deleteUserByPhone(regPhone);
            } else if (requestedUserCredentials.size() > 1) {
                throw new AssertionError("There are more than 1 unique phone number located in the usercredentials table!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //todo удалять процессы так же с камунды с данным номером
        consolidationRegForm.setToFieldWithOverlay(consolidationPhoneInput, regPhone)
                .markCheckBox(consolidationRegCheckbox)
                .submit(submitConsButton);

//        customWaitForPerformingJS();
        waitForAngularRequestsToFinish();
        return new RegPage(driver);
    }

    PasswordPage submitExistUserRegForm() {
        pdlRegForm.submit(submitPDLButton);
        return new PasswordPage(driver);
    }

    MainPage markPDLCheckbox() {
        pdlRegForm.markCheckBox(pdlRegCheckbox);
        return this;
    }
    MainPage markConsolidationCheckbox() {
        consolidationRegForm.markCheckBox(consolidationRegCheckbox);
        return this;
    }

    MainPage uncheckPDLCheckbox() {
        pdlRegForm.uncheck(pdlRegCheckbox);
        return this;
    }

    MainPage uncheckConsolidationCheckbox() {
        consolidationRegForm.uncheck(consolidationRegCheckbox);
        return this;
    }

    MainPage inputToPDLPhone(String data) {
//        pdlPhoneInput.sendKeys(data);
        pdlRegForm.setToFieldWithOverlay(pdlPhoneInput, data);
        return this;
    }

    MainPage inputToConsolidationPhone(String data) {
        pdlRegForm.setToFieldWithOverlay(consolidationPhoneInput, data);
        return this;
    }

    String getValueFromPDLPhoneInput() {
        return pdlRegForm.getFieldValue(pdlPhoneInput);
    }

    String getValueFromConsolidationPhoneInput() {
        return consolidationRegForm.getValueFromFieldAtFormWIthOverlay(consolidationPhoneInput);
    }

    boolean inputIsInvalid(WebElement input) {
        return input.getAttribute("class").contains("invalid");
    }

    boolean fieldWithPDLCheckboxIsInvalid() {
        return pdlRegForm.getElementClass(termsLinkInPDL).contains("error");
    }

    boolean fieldWithConsolidationCheckboxIsInvalid() {
        return consolidationRegForm.getElementClass(termsLinkInConsolidation).contains("error");
    }

    MainPage clickTheTermsInPDLForm() {
        termsLinkInPDL.sendKeys(Keys.RETURN);     //здесь замена .click-у, потому что клик не работает всегда адекватно
        //из-за оверлея формы дивом, и хром думает,что элементы некликабельны, хоть явное ожидание и добавлено
        explWait.until(presenceOfElementLocated(By.xpath("//md-dialog[@aria-label='Potwierdzam, że ...']")));
        return this;
    }
    MainPage clickTheTermsInConsolidationForm() {
        termsLinkInConsolidation.sendKeys(Keys.RETURN);     //здесь замена .click-у, потому что клик не работает всегда адекватно
        //из-за оверлея формы дивом, и хром думает,что элементы некликабельны, хоть явное ожидание и добавлено
        explWait.until(presenceOfElementLocated(By.xpath("//md-dialog[@aria-label='Potwierdzam, że ...']")));
        return this;
    }

    MainPage clickLoanInfo() {
        linkLoanInfo.sendKeys(Keys.RETURN);
        explWait.until(presenceOfElementLocated(By.xpath("//md-dialog[@aria-label='Chwilówka w ...']")));
        return this;
    }
    MainPage clickConsolidationInfo() {
        linkConsolidationInfo.sendKeys(Keys.RETURN);
        explWait.until(presenceOfElementLocated(By.xpath("//md-dialog[@aria-label='Kredyt konsolidacyjny ...']")));
        return this;
    }

    MainPage switchToConsolidationForm() {
        if (consolidationOverlay.isDisplayed()){
            consolidationOverlay.click();
        }
        waitForOpennessOfConsolidationCalc();
        return this;
    }

    void waitForReaction() throws InterruptedException {
        Thread.sleep(1000);
        explWait.until(or(elementToBeClickable(submitPDLButton),
                elementToBeClickable(By.xpath("//input[@name='name']"))));
    }

    MainPage waitForClosingTerms() {
        explWait.until(invisibilityOf(findWithXPath("//md-dialog[@aria-label='Potwierdzam, że ...']")));
        return this;
    }

    MainPage waitForClosingLoanInfo() {
        explWait.until(invisibilityOf(mdDialogOfLoanInfo));
        return this;
    }
    MainPage waitForClosingConsolidationInfo() {
        explWait.until(invisibilityOf(mdDialogOfConsolidationInfo));
        return this;
    }

    MainPage waitForOpennessOfPDLCalc() {
//        explWait.until(invisibilityOf((findWithXPath("//*[text()='Chwilówki']"))));
        explWait.until(elementToBeClickable(termsLinkInPDL));
        return this;
    }

    MainPage waitForOpennessOfConsolidationCalc() {
        explWait.until(invisibilityOf((findWithXPath("//*[text()='Kredyt konsolidacyjny']"))));
        explWait.until(elementToBeClickable(termsLinkInConsolidation));
        return this;
    }

    MainPage waitPhonePdlInputIsAccessible() {
        explWait.until(elementToBeClickable(pdlPhoneInput));
        return this;
    }


}
