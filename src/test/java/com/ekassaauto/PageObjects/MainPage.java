package com.ekassaauto.PageObjects;

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

    @FindBy(xpath = "(//input[@name='phone'])[2]")
    public WebElement consolidationPhoneInput;
    @FindBy(xpath = "//button[@type='submit']")
    public WebElement startPdlProcessButton;
    @FindBy(xpath = "(//button[@type='submit'])[2]")
    public WebElement startConsProcessButton;
    @FindBy(xpath = "//*[@ng-click='goToNextTask(false)']")
    public WebElement goToNextTaskPdlButton;
    @FindBy(xpath = "//*[@ng-click='goToNextTask(true)']")
    public WebElement goToNextTaskConsolidationButton;
    @FindBy(xpath = "//md-dialog[@aria-label='Potwierdzam, że ...']")
    public WebElement mdDialogOfAccessToPersData;
    @CacheLookup
    @FindBy(xpath = "//md-dialog[@aria-label='Chwilówka w ...']")
    public WebElement mdDialogOfLoanInfo;
    @CacheLookup
    @FindBy(xpath = "//md-dialog[@aria-label='Pożyczka konsolidacyjna ...']")
    public WebElement mdDialogOfConsolidationInfo;
    @FindBy(xpath = "//md-checkbox[@name='agreedToConditions']") private WebElement pdlMainScreenCheckbox;
    @FindBy(xpath = "(//md-checkbox[@name='agreedToConditions'])[2]") private WebElement consolidationRegCheckbox;
    @FindBy(xpath = "//span[@ng-click='$ctrl.showAgreements($event)']") public WebElement termsLinkInPDL;
    @FindBy(xpath = "(//span[@ng-click='$ctrl.showAgreements($event)'])[2]") public WebElement termsLinkInConsolidation;
    @FindBy(xpath = "//span[@ng-click='$ctrl.showLoanInfo($event)']") private WebElement linkLoanInfo;
    @FindBy(xpath = "(//span[@ng-click='$ctrl.showConsolidationInfo($event)'])[2]") private WebElement linkConsolidationInfo;
    @FindBy(xpath = "//span[text()='Pożyczka konsolidacyjna']") WebElement consolidationOverlay;
    @FindBy(xpath = "//input[@ng-model='$ctrl.loan.amount']") public WebElement pdlAmountInput;
    @FindBy(xpath = "//input[@ng-model='$ctrl.loan.term']") public WebElement pdlTermInput;
    @FindBy(xpath = "//input[@ng-model='$ctrl.consolidation.totalPaymentRequested']") public WebElement consolidationTrancheInput;
    @FindBy(xpath = "//input[@ng-model='$ctrl.consolidation.amount']") public WebElement consolidationPaymentInput;


    public MainPage(WebDriver driver) {
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

    public MainPage submitInvalidPDLForm() {
        pdlMainForm.submit(startPdlProcessButton);
        return this;
    }

    public MainPage submitInvalidConsolidationForm() {
        pdlMainForm.submit(startConsProcessButton);
        waitForAngularRequestsToFinish();
        return this;
    }

    public AuthPage passPdlFormInUnauthorizedState() {
        pdlMainForm.markCheckBoxWithOverlay(pdlMainScreenCheckbox)
                .submit(startPdlProcessButton);
        waitForAngularRequestsToFinish();
        return new AuthPage(driver);
    }

    public AuthPage passConsolidationFormInUnauthorizedState() {
        pdlMainForm.markCheckBoxWithOverlay(consolidationRegCheckbox)
                .submit(startConsProcessButton);
        waitForAngularRequestsToFinish();
        return new AuthPage(driver);
    }

    @Deprecated
    public AuthPage submitAnUnregisteredNumberThroughPDLForm() {
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
//        pdlMainForm.setToFieldWithOverlay(pdlPhoneInput, regPhone)
//                .markCheckBox(pdlMainScreenCheckbox)
//                .submit(startPdlProcessButton);

        customWaitForPerformingJS();

////        explWait.until(presenceOfElementLocated(By.xpath("//pdlPhoneInput[@name='name']")));
//        explWait.until(elementToBeClickable(By.xpath("//pdlPhoneInput[@name='name']")));
        return new AuthPage(driver);
    }

    @Deprecated
    public AuthPage submitAnUnregisteredNumberThroughConsolidationForm() {
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

    public MainPage markConsolidationCheckbox() {
        consolidationMainForm.markCheckBox(consolidationRegCheckbox);
        return this;
    }

    public MainPage unMarkPDLCheckbox() {
        pdlMainForm.uncheck(pdlMainScreenCheckbox);
        return this;
    }

    public MainPage markPDLCheckbox() {
        pdlMainForm.markCheckBoxWithOverlay(pdlMainScreenCheckbox);
        return this;
    }

    public MainPage uncheckConsolidationCheckbox() {
        consolidationMainForm.uncheck(consolidationRegCheckbox);
        return this;
    }

    public MainPage inputToConsolidationPhone(String data) {
        pdlMainForm.setToFieldWithOverlay(consolidationPhoneInput, data);
        return this;
    }

    public String getValueFromConsolidationPhoneInput() {
        return consolidationMainForm.getValueFromFieldAtFormWIthOverlay(consolidationPhoneInput);
    }

    public boolean fieldWithPDLCheckboxIsInvalid() {
        return pdlMainForm.getElementClass(termsLinkInPDL).contains("error");
    }

    public boolean fieldWithConsolidationCheckboxIsInvalid() {
        return consolidationMainForm.getElementClass(termsLinkInConsolidation).contains("error");
    }

    public MainPage clickTheTermsInPDLForm() {
        termsLinkInPDL.sendKeys(Keys.RETURN);     //здесь замена .click-у, потому что клик не работает всегда адекватно
        //из-за оверлея формы дивом, и хром думает,что элементы некликабельны, хоть явное ожидание и добавлено
        explWait.until(presenceOfElementLocated(By.xpath("//md-dialog[@aria-label='Potwierdzam, że ...']")));
        return this;
    }
    public MainPage clickTheTermsInConsolidationForm() {
        termsLinkInConsolidation.sendKeys(Keys.RETURN);     //здесь замена .click-у, потому что клик не работает всегда адекватно
        //из-за оверлея формы дивом, и хром думает,что элементы некликабельны, хоть явное ожидание и добавлено
        explWait.until(presenceOfElementLocated(By.xpath("//md-dialog[@aria-label='Potwierdzam, że ...']")));
        return this;
    }

    public MainPage clickLoanInfo() {
        linkLoanInfo.sendKeys(Keys.RETURN);
        explWait.until(presenceOfElementLocated(By.xpath("//md-dialog[@aria-label='Chwilówka w ...']")));
        return this;
    }
    public MainPage clickConsolidationInfo() {
        linkConsolidationInfo.sendKeys(Keys.RETURN);
        explWait.until(presenceOfElementLocated(By.xpath("//md-dialog[@aria-label='Pożyczka konsolidacyjna ...']")));
        return this;
    }

    public MainPage switchToConsolidationForm() {
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

    public MainPage waitForClosingTerms() {
        explWait.until(invisibilityOf(findWithXPath("//md-dialog[@aria-label='Potwierdzam, że ...']")));
        return this;
    }

    public MainPage waitForClosingLoanInfo() {
        explWait.until(invisibilityOf(mdDialogOfLoanInfo));
        return this;
    }
    public MainPage waitForClosingConsolidationInfo() {
        explWait.until(invisibilityOf(mdDialogOfConsolidationInfo));
        return this;
    }

    MainPage waitForOpennessOfPDLCalc() {
//        explWait.until(invisibilityOf((findWithXPath("//*[text()='Chwilówki']"))));
        explWait.until(elementToBeClickable(startPdlProcessButton));
        explWait.until(not(elementToBeClickable(By.xpath("//div[@class='first-task__calc-overlay']"))));
        return this;
    }

    MainPage waitForOpennessOfConsolidationCalc() {
        explWait.until(not(elementToBeClickable(By.xpath("(//div[@class='first-task__calc-overlay'])[2]"))));
//        explWait.until(invisibilityOf((findWithXPath("//*[text()='Kredyt konsolidacyjny']"))));
        explWait.until(elementToBeClickable(termsLinkInConsolidation));
        return this;
    }

//    MainPage waitPhonePdlInputIsAccessible() {
//        explWait.until(elementToBeClickable(pdlPhoneInput));
//        return this;
//    }


}
