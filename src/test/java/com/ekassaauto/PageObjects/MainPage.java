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

    public static final String goToNextTaskConsolidationButtonLocator = "//*[@ng-click='vm.goToNextTask(true)']";
    public static final String goToNextTaskPdlButtonLocator = "//*[@ng-click='vm.goToNextTask(false)']";
    public static final String startSmallPdlProcessButtonLocator = "//a[@ng-if='!vm.firstProduct.lastProcess || !vm.firstProduct.nextTaskEnabled']";
    public static final String startLargePdlProcessButtonLocator = "//a[@ng-if='!vm.secondProduct.lastProcess || !vm.secondProduct.nextTaskEnabled']";
    public static final String startConsProcessButtonLocator = "//a[@ng-if='!vm.thirdProduct.lastProcess || !vm.thirdProduct.nextTaskEnabled']";
    public static final String smallPdlAgreementsCheckboxLocator = "//label[@for='agreed1']";
    public static final String largePdlAgreementsCheckboxLocator = "//label[@for='agreed2']";
    public static final String consolidationRegCheckboxLocator = "//label[@for='agreed3']";
    public static final String termsLinkInSmallPDLLocator = "(//*[@ng-click='vm.showAgreements($event)'])[1]";
    public static final String termsLinkInLargePDLLocator = "(//*[@ng-click='vm.showAgreements($event)'])[2]";
    public static final String termsLinkInConsolidationLocator= "(//a[@ng-click='vm.showAgreements($event)'])[3]";
    public static final String mdDialogOfAccessToPersDataLocator = "//md-dialog[@aria-label='Potwierdzam, że ...']";
    public static final String largePdlTabLocator = "//div[@ng-click='vm.enableProduct(vm.secondProduct);']";
    public static final String smallPdlTabLocator = "//div[@ng-click='vm.enableProduct(vm.firstProduct)']";
    public static final String consolidationTabLocator = "//div[@ng-click='vm.enableProduct(vm.thirdProduct)']";
    public static final String linkSmallLoanInfoLocator = "//*[@ng-click='vm.chooseApplication($event, vm.firstProduct)']";
    public static final String linkLargeLoanInfoLocator = "//*[@ng-click='vm.chooseApplication($event, vm.secondProduct)']";
    public static final String linkConsolidationInfoLocator = "//a[@ng-click='vm.chooseApplication($event, vm.thirdProduct)']";

    public static final String mdDialogOfSmallLoanInfoLocator = "//md-dialog[@aria-label='Niewielka suma ...']";
    public static final String mdDialogOfLargeLoanInfoLocator = "//*[@id='choosesSecondProductWithTopup']/md-dialog[@aria-label='Większa kwota ...']";
    public static final String mdDialogOfConsolidationInfoLocator = "//*[@id='choosesThirdProductWithoutTopup']/md-dialog[@aria-label='Większa kwota ...']";
    public static final String smallPdlAmountInputLocator = "//input[@ng-model='vm.firstProduct.amount']";
    public static final String largePdlAmountInputLocator = "//input[@ng-model='vm.secondProduct.amount']";
    public static final String consolidationTrancheInputLocator = "//input[@ng-model='vm.thirdProduct.totalPaymentRequested']";
    public static final String consolidationPaymentInputLocator = "//input[@ng-model='vm.thirdProduct.amount']";

    @FindBy(xpath = largePdlTabLocator) public WebElement largePdlTab;
    @FindBy(xpath = smallPdlTabLocator) public WebElement smallPdlTab;
    @FindBy(xpath = consolidationTabLocator) public WebElement consolidationTab;
    @FindBy(xpath = "(//input[@name='phone'])[2]") public WebElement consolidationPhoneInput;
    @FindBy(xpath = startSmallPdlProcessButtonLocator) public WebElement startSmallPdlProcessButton;
    @FindBy(xpath = startLargePdlProcessButtonLocator) public WebElement startLargePdlProcessButton;
    @FindBy(xpath = startConsProcessButtonLocator) public WebElement startConsProcessButton;
    @FindBy(xpath = goToNextTaskPdlButtonLocator) public WebElement goToNextTaskPdlButton;
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
    @FindBy(xpath = consolidationRegCheckboxLocator) private WebElement consolidationRegCheckbox;
    @FindBy(xpath = termsLinkInSmallPDLLocator) public WebElement termsLinkInSmallPDL;
    @FindBy(xpath = termsLinkInLargePDLLocator) public WebElement termsLinkInLargePDL;
    @FindBy(xpath = termsLinkInConsolidationLocator) public WebElement termsLinkInConsolidation;
    @FindBy(xpath = linkSmallLoanInfoLocator) private WebElement linkSmallLoanInfo;
    @FindBy(xpath = linkLargeLoanInfoLocator) private WebElement linkLargeLoanInfo;
    @FindBy(xpath = linkConsolidationInfoLocator) private WebElement linkConsolidationInfo;
    @FindBy(xpath = "//span[text()='Pożyczka konsolidacyjna']") WebElement consolidationOverlay;
    @FindBy(xpath = smallPdlAmountInputLocator) public WebElement smallPdlAmountInput;
    @FindBy(xpath = largePdlAmountInputLocator) public WebElement largePdlAmountInput;
    @FindBy(xpath = "//input[@ng-model='$ctrl.loan.term']") public WebElement pdlTermInput;
    @FindBy(xpath = consolidationTrancheInputLocator) public WebElement consolidationTrancheInput;
    @FindBy(xpath = consolidationPaymentInputLocator) public WebElement consolidationPaymentInput;


    public MainPage(WebDriver driver) {
        super(driver);
        String testUrlEkassa = "http://test.ekassa.com";
        String prodUrlEkassa = "https://ekassa.pl";
        if (!driver.getCurrentUrl().equalsIgnoreCase("http://test.ekassa.com/#/")) {
            driver.get(testUrlEkassa);
        }
//        waitForOpennessOfPDLCalc();
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
        startConsProcessButton.click();
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

    public AuthPage passConsolidationForm() {
        switchToConsolidation()
                .markConsolidationCheckbox();
        startConsProcessButton.click();
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

    public MainPage unmarkSmallPDLCheckbox() {
        unmarkLabelCheckbox(smallPdlAgreementsCheckbox);
        return this;
    }

    public MainPage unmarkLargePDLCheckbox() {
        unmarkLabelCheckbox(largePdlAgreementsCheckbox);
        return this;
    }

    private void markLabelCheckbox(WebElement labelCheckbox) {
                if (!
                findWithXPath("//input[@id='" +
                        labelCheckbox.getAttribute("for") + "']")
                        .isSelected()) {
            labelCheckbox.click();
        }
    }
    private void unmarkLabelCheckbox(WebElement labelCheckbox) {
                if (findWithXPath("//input[@id='" +
                        labelCheckbox.getAttribute("for") + "']")
                        .isSelected()) {
            labelCheckbox.click();
        }
    }

    public MainPage markSmallPDLCheckbox() {
        markLabelCheckbox(smallPdlAgreementsCheckbox);

        return this;
    }

    public MainPage markLargePDLCheckbox() {
        markLabelCheckbox(largePdlAgreementsCheckbox);
        return this;
    }

    public MainPage markConsolidationCheckbox() {
        markLabelCheckbox(consolidationRegCheckbox);
        return this;
    }
   public MainPage unmarkConsolidationCheckbox() {
        unmarkLabelCheckbox(consolidationRegCheckbox);
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
        termsLinkInConsolidation.click();
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
        linkConsolidationInfo.click();
        explWait.until(presenceOfElementLocated(By.xpath("//md-dialog[@aria-label='Większa kwota ...']")));
        return this;
    }

    public MainPage switchToConsolidation() {
        waitForAngularRequestsToFinish();
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
//        explWait.until(elementToBeClickable(termsLinkInSmallPDL));
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
