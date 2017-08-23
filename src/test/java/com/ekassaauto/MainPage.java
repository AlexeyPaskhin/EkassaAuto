package com.ekassaauto;

import org.openqa.selenium.*;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static com.ekassaauto.Registration.*;

/**
 * Created by user on 10.03.2017.
 */
public class MainPage extends AbstractPage {
    private Form pdlMainForm;
    private Form consolidationMainForm;

    @FindBy(xpath = "//input[@name='phone']") protected WebElement pdlPhoneInput;
    @FindBy(xpath = "(//input[@name='phone'])[2]") protected WebElement consolidationPhoneInput;
    @FindBy(xpath = "//button[@type='submit']") WebElement startPdlProcessButton;
    @FindBy(xpath = "(//button[@type='submit'])[2]") WebElement startConsProcessButton;
    @FindBy(xpath = "//*[@ng-click='goToNextTask(false)']") WebElement goToNextTaskPdlButton;
    @FindBy(xpath = "//*[@ng-click='goToNextTask(true)']") WebElement goToNextTaskConsolidationButton;
    @FindBy(xpath = "//md-dialog[@aria-label='Potwierdzam, że ...']") protected WebElement mdDialogOfAccessToPersData;
    @CacheLookup
    @FindBy(xpath = "//md-dialog[@aria-label='Chwilówka w ...']") protected WebElement mdDialogOfLoanInfo;
    @CacheLookup
    @FindBy(xpath = "//md-dialog[@aria-label='Kredyt konsolidacyjny ...']") protected WebElement mdDialogOfConsolidationInfo;
    @FindBy(xpath = "//md-checkbox[@ng-model='agreedToConditions']") private WebElement pdlMainScreenCheckbox;
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
        pdlMainForm = new Form(findWithXPath("//form"));
        consolidationMainForm = new Form(findWithXPath("(//form)[2]"));
    }

    MainPage submitInvalidPDLForm() {
        pdlMainForm.submit(startPdlProcessButton);
        return this;
    }

    MainPage submitInvalidConsolidationForm() {
        pdlMainForm.submit(startConsProcessButton);
        return this;
    }

    AuthPage submitPdlFormInUnauthorizedState() {
        //todo Add checking on presence of log in
        pdlMainForm.markCheckBoxWithOverlay(pdlMainScreenCheckbox)
                .submit(startPdlProcessButton);
        waitForAngularRequestsToFinish();
        return new AuthPage(driver);
    }

    @Deprecated
    AuthPage submitAnUnregisteredNumberThroughPDLForm() {
//        try {
//            List<UserCredential> requestedUserCredentials = userCredentialsDAO.getUserByPhone(regPhone);
//            if (requestedUserCredentials.size() == 1) {
//                userCredentialsDAO.deleteUserByPhone(regPhone);
//            } else if (requestedUserCredentials.size() > 1) {
//                throw new AssertionError("There are more than 1 unique phone number located in the usercredentials table!");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        //todo удалять процессы так же с камунды с данным номером
        pdlMainForm.setToFieldWithOverlay(pdlPhoneInput, regPhone)
                .markCheckBox(pdlMainScreenCheckbox)
                .submit(startPdlProcessButton);

        customWaitForPerformingJS();

////        explWait.until(presenceOfElementLocated(By.xpath("//pdlPhoneInput[@name='name']")));
//        explWait.until(elementToBeClickable(By.xpath("//pdlPhoneInput[@name='name']")));
        return new AuthPage(driver);
    }

    @Deprecated
    AuthPage submitAnUnregisteredNumberThroughConsolidationForm() {
//        try {
//            List<UserCredential> requestedUserCredentials = userCredentialsDAO.getUserByPhone(regPhone);
//            if (requestedUserCredentials.size() == 1) {
//                userCredentialsDAO.deleteUserByPhone(regPhone);
//            } else if (requestedUserCredentials.size() > 1) {
//                throw new AssertionError("There are more than 1 unique phone number located in the usercredentials table!");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        //todo удалять процессы так же с камунды с данным номером
        consolidationMainForm.setToFieldWithOverlay(consolidationPhoneInput, regPhone)
                .markCheckBox(consolidationRegCheckbox)
                .submit(startConsProcessButton);

//        customWaitForPerformingJS();
        waitForAngularRequestsToFinish();
        return new AuthPage(driver);
    }

    PasswordPage submitExistUserRegForm() {
        pdlMainForm.submit(startPdlProcessButton);
        return new PasswordPage(driver);
    }

    MainPage markPDLCheckbox() {
        pdlMainForm.markCheckBox(pdlMainScreenCheckbox);
        return this;
    }
    MainPage markConsolidationCheckbox() {
        consolidationMainForm.markCheckBox(consolidationRegCheckbox);
        return this;
    }

    MainPage uncheckPDLCheckbox() {
        pdlMainForm.uncheck(pdlMainScreenCheckbox);
        return this;
    }

    MainPage uncheckConsolidationCheckbox() {
        consolidationMainForm.uncheck(consolidationRegCheckbox);
        return this;
    }

    MainPage inputToPDLPhone(String data) {
//        pdlPhoneInput.sendKeys(data);
        pdlMainForm.setToFieldWithOverlay(pdlPhoneInput, data);
        return this;
    }

    MainPage inputToConsolidationPhone(String data) {
        pdlMainForm.setToFieldWithOverlay(consolidationPhoneInput, data);
        return this;
    }

    String getValueFromPDLPhoneInput() {
        return pdlMainForm.getFieldValue(pdlPhoneInput);
    }

    String getValueFromConsolidationPhoneInput() {
        return consolidationMainForm.getValueFromFieldAtFormWIthOverlay(consolidationPhoneInput);
    }

    boolean inputIsInvalid(WebElement input) {
        return input.getAttribute("class").contains("invalid");
    }

    boolean fieldWithPDLCheckboxIsInvalid() {
        return pdlMainForm.getElementClass(termsLinkInPDL).contains("error");
    }

    boolean fieldWithConsolidationCheckboxIsInvalid() {
        return consolidationMainForm.getElementClass(termsLinkInConsolidation).contains("error");
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
        explWait.until(or(elementToBeClickable(startPdlProcessButton),
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
        explWait.until(elementToBeClickable(startPdlProcessButton));
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
