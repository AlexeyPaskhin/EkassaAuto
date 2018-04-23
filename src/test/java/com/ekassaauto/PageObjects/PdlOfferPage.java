package com.ekassaauto.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.ekassaauto.Registration.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;


/**
 * Created by user on 14.08.2017.
 */
public class PdlOfferPage extends AbstractPage {
    private Form pdlOfferForm;
    private static final String selectTopUpButtonLocator = "//button[@ng-click='$ctrl.proceedToContracts($ctrl.topupPackage)']";
    private static final String pdlOfferSmsCodeInputLocator = "//input[@ng-model='$ctrl.sms.code']";
    private static final String moveForwardButtonLocator = "//button[@ng-disabled='!$ctrl.contractsChecked']";

    @FindBy(xpath = "//md-checkbox[@aria-label='Zgadzam się z warunkami']") WebElement contractsCheckBox;
    @FindBy(xpath = "//span[contains(text(), 'Zgadzam z Warunkami')]/..") WebElement agreedWithTheTermsButton;
    @FindBy(xpath = pdlOfferSmsCodeInputLocator) WebElement pdlOfferSmsCodeInput;
    @FindBy(xpath = moveForwardButtonLocator) WebElement moveForwardButton;
    @FindBy(xpath = "//md-radio-button[@aria-label='Polecamy']") WebElement defaultProposalRadioButton;
    @FindBy(xpath = selectTopUpButtonLocator) WebElement selectTopUpButton;
    @FindBy(xpath = "//md-checkbox[@name='agreedToConditions']") public WebElement termsCheckBox;

    public PdlOfferPage(WebDriver driver) {
        super(driver);
//        initPageElements();
        printURL();
    }

    private void initPageElements() {
//            pdlOfferForm = new Form(findWithXPath("//form[@name='paydayOffer']"));
    }


    public CongratulationPage passPdlOfferPageSelectingTopUpWithSuccessfulBankCache(String phone) {
        selectTopUp()
                .agreeWithTheContracts()
                .enterSmsCode(phone)
                .clickMoveForwardButton()
                .waitForAngularRequestsToFinish();
//        if (instWormCacheDAO.instWormCacheForPdlPresent(phone) && instWormCacheDAO.instWormCacheIsSuccessful(phone))  -  это должно быть тестовой логикой
            return new CongratulationPage(driver);
    }

    RejectPage passPdlOfferPageWithDefaultProposalWithFailedBankCache(String phone) {
        selectTopUp()
                .agreeWithTheContracts()
                .enterSmsCode(phone)
                .clickMoveForwardButton()
                .waitForAngularRequestsToFinish();
//        if (instWormCacheDAO.instWormCacheForPdlPresent(phone) && !instWormCacheDAO.instWormCacheIsSuccessful(phone))  -  это должно быть тестовой логикой
            return new RejectPage(driver);
        }

    public BankAccountVerificationPage passPdlOfferPageSelectingTopUpWithoutBankCache(String phone) {
        selectTopUp()
                .agreeWithTheContracts()
                .enterSmsCode(phone)
                .clickMoveForwardButton()
                .waitForAngularRequestsToFinish();
        return new BankAccountVerificationPage(driver);
    }

    public BankAccountVerificationPage passCpaPdlOfferPageSelectingTopUpWithoutBankCache(String phone) {
        markTermsCheckbox()
                .selectTopUp()
                .agreeWithTheContracts()
                .enterSmsCode(phone)
                .clickMoveForwardButton()
                .waitForAngularRequestsToFinish();
        return new BankAccountVerificationPage(driver);
    }

    PdlOfferPage clickMoveForwardButton() {
        moveForwardButton.click();
        return this;
    }

    PdlOfferPage enterSmsCode(String phone) {
        explWait.until(visibilityOf(pdlOfferSmsCodeInput));
        set(pdlOfferSmsCodeInput, sentSmsDAO.getSmsCodeByPhone(phone));
        return this;
    }

    PdlOfferPage agreeWithTheContracts() {

        markCheckBox(contractsCheckBox);
        agreedWithTheTermsButton.click();
        return this;
    }

    PdlOfferPage selectTopUp() {
        selectTopUpButton.click();
        return this;
    }

    public PdlOfferPage markTermsCheckbox() {
        markCheckBox(termsCheckBox);
//        termsCheckBox.click();
        return this;
    }
}
