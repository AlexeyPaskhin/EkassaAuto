package com.ekassaauto.PageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import javax.sound.midi.Track;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static com.ekassaauto.Registration.*;

/**
 * Created by user on 10.03.2017.
 */
public class MainPage extends AbstractPage {
    private Form pdlMainForm;
    private Form consolidationMainForm;

    public static final String goToNextTaskConsolidationButtonLocator = "//*[@ng-click='$ctrl.goToNextTask(true)']";
    public static final String startSmallPdlProcessButtonLocator = "(//a[@ng-click='vm.submit()'])[1]";
    public static final String startLargePdlProcessButtonLocator = "(//a[@ng-click='vm.submit()'])[2]";
    public static final String startConsProcessButtonLocator = "(//a[@ng-click='vm.submit()'])[3]";
    public static final String smallPdlAgreementsCheckboxLocator = "//label[@for='agreed1']";
    public static final String largePdlAgreementsCheckboxLocator = "//label[@for='agreed2']";
    public static final String termsLinkInSmallPDLLocator = "(//*[@ng-click='vm.showAgreements($event)'])[1]";
    public static final String termsLinkInLargePDLLocator = "(//*[@ng-click='vm.showAgreements($event)'])[2]";
    public static final String mdDialogOfAccessToPersDataLocator = "//md-dialog[@aria-label='Potwierdzam, że ...']";
    public static final String largePdlTabLocator = "//div[@ng-click='vm.enableProduct(vm.secondProduct);']";
    public static final String smallPdlTabLocator = "//div[@ng-click='vm.enableProduct(vm.firstProduct)']";
    public static final String consolidationTabLocator = "//div[@ng-click='vm.enableProduct(vm.thirdProduct)']";
    public static final String linkSmallLoanInfoLocator = "//*[@ng-click='vm.chooseApplication($event, vm.firstProduct)']";
    public static final String linkLargeLoanInfoLocator = "//*[@ng-click='vm.chooseApplication($event, vm.secondProduct)']";

    public static final String mdDialogOfSmallLoanInfoLocator = "//md-dialog[@aria-label='Niewielka suma ...']";
    public static final String mdDialogOfLargeLoanInfoLocator = "//*[@id='choosesSecondProductWithTopup']/md-dialog[@aria-label='Większa kwota ...']";
    public static final String mdDialogOfConsolidationInfoLocator = "//*[@id='choosesThirdProductWithoutTopup']/md-dialog[@aria-label='Większa kwota ...']";
    public static final String smallPdlAmountInputLocator = "//input[@ng-model='vm.firstProduct.amount']";
    public static final String largePdlAmountInputLocator = "//input[@ng-model='vm.secondProduct.amount']";

    @FindBy(xpath = largePdlTabLocator) public WebElement largePdlTab;
    @FindBy(xpath = smallPdlTabLocator) public WebElement smallPdlTab;
    @FindBy(xpath = consolidationTabLocator) public WebElement consolidationTab;
    @FindBy(xpath = "(//input[@name='phone'])[2]") public WebElement consolidationPhoneInput;
    @FindBy(xpath = startSmallPdlProcessButtonLocator) public WebElement startSmallPdlProcessButton;
    @FindBy(xpath = startLargePdlProcessButtonLocator) public WebElement startLargePdlProcessButton;
    @FindBy(xpath = startConsProcessButtonLocator) public WebElement startConsProcessButton;
    @FindBy(xpath = "//*[@ng-click='$ctrl.goToNextTask(false)']")
    public WebElement goToNextTaskPdlButton;
    @FindBy(xpath = goToNextTaskConsolidationButtonLocator) public WebElement goToNextTaskConsolidationButton;
    @FindBy(xpath = mdDialogOfAccessToPersDataLocator) public WebElement mdDialogOfAccessToPersData;
    @CacheLookup
    @FindBy(xpath = mdDialogOfSmallLoanInfoLocator) public WebElement mdDialogOfSmallLoanInfo;
    @CacheLookup
    @FindBy(xpath = mdDialogOfLargeLoanInfoLocator) public WebElement mdDialogOfLargeLoanInfo;
    @CacheLookup
    @FindBy(xpath = mdDialogOfConsolidationInfoLocator) public WebElement mdDialogOfConsolidationInfo;
    @FindBy(xpath = smallPdlAgreementsCheckboxLocator) private WebElement smallPdlAgreementsCheckbox;
    @FindBy(xpath = largePdlAgreementsCheckboxLocator)
    public WebElement largePdlAgreementsCheckbox;
    @FindBy(xpath = "(//md-checkbox[@name='agreedToConditions'])[2]") private WebElement consolidationRegCheckbox;
    @FindBy(xpath = termsLinkInSmallPDLLocator) public WebElement termsLinkInSmallPDL;
    @FindBy(xpath = termsLinkInLargePDLLocator) public WebElement termsLinkInLargePDL;
    @FindBy(xpath = "(//span[@ng-click='$ctrl.showAgreements($event)'])[2]") public WebElement termsLinkInConsolidation;
    @FindBy(xpath = linkSmallLoanInfoLocator) private WebElement linkSmallLoanInfo;
    @FindBy(xpath = linkLargeLoanInfoLocator) private WebElement linkLargeLoanInfo;
    @FindBy(xpath = "//div[@ng-click='$ctrl.showConsolidationInfo($event)']") private WebElement linkConsolidationInfo;
    @FindBy(xpath = "//span[text()='Pożyczka konsolidacyjna']") WebElement consolidationOverlay;
    @FindBy(xpath = smallPdlAmountInputLocator) public WebElement smallPdlAmountInput;
    @FindBy(xpath = largePdlAmountInputLocator) public WebElement largePdlAmountInput;
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
        switchToSmallPdl();
//        jseDriver.executeScript("window.scrollBy(0,500)");
    }

    private void initPageElements() {
        pdlMainForm = new Form(findWithXPath("//section[@class='products']")); //Form tags were removed
        consolidationMainForm = new Form(findWithXPath("//section[@class='products']"));  //Form tags were removed
    }

    public MainPage submitInvalidSmallPDLForm() {
        pdlMainForm.submit(startSmallPdlProcessButton);
        return this;
    }

    public MainPage submitInvalidLargePDLForm() {
        pdlMainForm.submit(startLargePdlProcessButton);
        return this;
    }

    public MainPage submitInvalidConsolidationForm() {
        pdlMainForm.submit(startConsProcessButton);
        waitForAngularRequestsToFinish();
        return this;
    }

    public AuthPage startSmallPdlInUnauthorizedState() {
//        pdlMainForm.markLabelCheckbox(smallPdlAgreementsCheckbox)
//                .submit(startSmallPdlProcessButton);
        markSmallPDLCheckbox();
        submitInvalidSmallPDLForm();
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
//                .markCheckBox(smallPdlAgreementsCheckbox)
//                .submit(startSmallPdlProcessButton);

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
        pdlMainForm.submit(startSmallPdlProcessButton);
        return new PasswordPage(driver);
    }

    public MainPage markConsolidationCheckbox() {
        consolidationMainForm.markCheckBox(consolidationRegCheckbox);
        return this;
    }

    public MainPage unmarkSmallPDLCheckbox() {
        pdlMainForm.unmarkLabelCheckbox(smallPdlAgreementsCheckbox);
        return this;
    }

    public MainPage unmarkLargePDLCheckbox() {
        pdlMainForm.unmarkLabelCheckbox(largePdlAgreementsCheckbox);
        return this;
    }

    public MainPage markSmallPDLCheckbox() {
//        pdlMainForm.markLabelCheckbox(smallPdlAgreementsCheckbox);
        if (!
                findWithXPath("//input[@id='" +
                        smallPdlAgreementsCheckbox.getAttribute("for") + "']")
                        .isSelected()) {
            smallPdlAgreementsCheckbox.click();
        }
        return this;
    }

    public MainPage markLargePDLCheckbox() {
        pdlMainForm.markLabelCheckbox(largePdlAgreementsCheckbox);
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

    public boolean fieldWithSmallPDLCheckboxIsInvalid() {
        return pdlMainForm.getElementClass(termsLinkInSmallPDL).contains("error");
    }

    public boolean fieldWithLargePDLCheckboxIsInvalid() {
        return pdlMainForm.getElementClass(termsLinkInLargePDL).contains("error");
    }

    public boolean fieldWithConsolidationCheckboxIsInvalid() {
        return consolidationMainForm.getElementClass(termsLinkInConsolidation).contains("error");
    }

    public MainPage clickTheTermsInSmallPDLForm() {
        termsLinkInSmallPDL.click();
        explWait.until(presenceOfElementLocated(By.xpath("//md-dialog[@aria-label='Potwierdzam, że ...']")));
        return this;
    }

    public MainPage clickTheTermsInLargePDLForm() {
        termsLinkInLargePDL.click();
        explWait.until(presenceOfElementLocated(By.xpath("//md-dialog[@aria-label='Potwierdzam, że ...']")));
        return this;
    }

    public MainPage clickTheTermsInConsolidationForm() {
        termsLinkInConsolidation.sendKeys(Keys.RETURN);     //здесь замена .click-у, потому что клик не работает всегда адекватно
        //из-за оверлея формы дивом, и хром думает,что элементы некликабельны, хоть явное ожидание и добавлено
        explWait.until(presenceOfElementLocated(By.xpath(mdDialogOfAccessToPersDataLocator)));
        return this;
    }

    public MainPage clickSmallLoanInfo() {
        linkSmallLoanInfo.click();
        explWait.until(presenceOfElementLocated(By.xpath(mdDialogOfSmallLoanInfoLocator)));
        return this;
    }
    public MainPage clickLargeLoanInfo() {
        linkLargeLoanInfo.click();
        return this;
    }
    public MainPage clickConsolidationInfo() {
        linkConsolidationInfo.sendKeys(Keys.RETURN);
        explWait.until(presenceOfElementLocated(By.xpath("//md-dialog[@aria-label='Pożyczka konsolidacyjna ...']")));
        return this;
    }

    public MainPage switchToConsolidation() {
//        if (consolidationOverlay.isDisplayed()){
//            consolidationOverlay.click();
//        }
//        waitForOpennessOfConsolidationCalc();
        consolidationTab.click();
        return this;
    }

    void waitForReaction() throws InterruptedException {
        Thread.sleep(1000);
        explWait.until(or(elementToBeClickable(startSmallPdlProcessButton),
                elementToBeClickable(By.xpath("//input[@name='name']"))));
    }

    public MainPage waitForClosingTerms() {
        explWait.until(invisibilityOf(findWithXPath("//md-dialog[@aria-label='Potwierdzam, że ...']")));
        return this;
    }

    public MainPage waitForClosingSmallLoanInfo() {
        explWait.until(invisibilityOf(mdDialogOfSmallLoanInfo));
        return this;
    }
    public MainPage waitForClosingConsolidationInfo() {
        explWait.until(invisibilityOf(mdDialogOfConsolidationInfo));
        return this;
    }

    MainPage waitForOpennessOfPDLCalc() {
//        explWait.until(invisibilityOf((findWithXPath("//*[text()='Chwilówki']"))));
        waitForAngularRequestsToFinish();
        explWait.until(elementToBeClickable(termsLinkInSmallPDL));
//        explWait.until(not(elementToBeClickable(By.xpath("//div[@class='first-task__calc-overlay']"))));
        return this;
    }

    MainPage waitForOpennessOfConsolidationCalc() {
        explWait.until(not(elementToBeClickable(By.xpath("(//div[@class='first-task__calc-overlay'])[2]"))));
//        explWait.until(invisibilityOf((findWithXPath("//*[text()='Kredyt konsolidacyjny']"))));
        explWait.until(elementToBeClickable(termsLinkInConsolidation));
        return this;
    }

    public MainPage switchToLargePdl() {
        try {
            largePdlTab.click();
        } catch (WebDriverException e) {
            System.out.println("Element" + largePdlTabLocator + " was not clickable due to overlay");

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                switchToLargePdl();
            }
        }
        explWait.until(visibilityOfElementLocated(By.xpath(termsLinkInLargePDLLocator)));
        return this;
    }

    public MainPage switchToSmallPdl() {
        try {
            smallPdlTab.click();
        } catch (WebDriverException e) {
            System.out.println("Element" + smallPdlTabLocator + " was not clickable due to overlay");

            try {
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            switchToSmallPdl();
        }
        return this;
    }


//    MainPage waitPhonePdlInputIsAccessible() {
//        explWait.until(elementToBeClickable(pdlPhoneInput));
//        return this;
//    }


}
