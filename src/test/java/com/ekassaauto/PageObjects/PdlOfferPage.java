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

    @FindBy(xpath = "//md-checkbox[@aria-label='Zgadzam się z warunkami']") WebElement agreementsCheckBox;
    @FindBy(xpath = "//span[contains(text(), 'Zgadzam z Warunkami')]/..") WebElement agreedWithTheTermsButton;
    @FindBy(xpath = "//input[@ng-model='smsCodeEntered2']") WebElement pdlOfferSmsCodeInput;
    @FindBy(xpath = "//span[contains(text(), 'Dalej')]/..") WebElement moveForwardButton;
    @FindBy(xpath = "//md-radio-button[@aria-label='Polecamy']") WebElement defaultProposalRadioButton;

    public PdlOfferPage(WebDriver driver) {
        super(driver);
//        initPageElements();
    }

    private void initPageElements() {
//            pdlOfferForm = new Form(findWithXPath("//form[@name='paydayOffer']"));
    }


    public CongratulationPage passingPdlOfferPageWithDefaultProposalWithSuccessfulBankCache(String phone) {
        selectDefaultProposal()
                .agreeWithTheTerms()
                .enterSmsCode(phone)
                .submitPdlOfferForm()
                .waitForAngularRequestsToFinish();
//        if (instWormCacheDAO.instWormCacheForPdlPresent(phone) && instWormCacheDAO.instWormCacheIsSuccessful(phone))  -  это должно быть тестовой логикой
            return new CongratulationPage(driver);
    }

    RejectPage passingPdlOfferPageWithDefaultProposalWithFailedBankCache(String phone) {
        selectDefaultProposal()
                .agreeWithTheTerms()
                .enterSmsCode(phone)
                .submitPdlOfferForm()
                .waitForAngularRequestsToFinish();
//        if (instWormCacheDAO.instWormCacheForPdlPresent(phone) && !instWormCacheDAO.instWormCacheIsSuccessful(phone))  -  это должно быть тестовой логикой
            return new RejectPage(driver);
        }

    public BankAccountVerificationPage passingPdlOfferPageWithDefaultProposalWithoutBankCache(String phone) {
        selectDefaultProposal()
                .agreeWithTheTerms()
                .enterSmsCode(phone)
                .submitPdlOfferForm()
                .waitForAngularRequestsToFinish();
        return new BankAccountVerificationPage(driver);
    }

    PdlOfferPage submitPdlOfferForm() {
        pdlOfferForm.submit(moveForwardButton);
        return this;
    }

    PdlOfferPage enterSmsCode(String phone) {
        explWait.until(visibilityOf(pdlOfferSmsCodeInput));
        pdlOfferForm.set(pdlOfferSmsCodeInput, sentSmsDAO.getSmsCodeByPhone(phone));
        return this;
    }

    PdlOfferPage agreeWithTheTerms() {
        pdlOfferForm.markCheckBox(agreementsCheckBox)
                .submit(agreedWithTheTermsButton);
        return this;
    }

    PdlOfferPage selectDefaultProposal() {
        pdlOfferForm.markRadioButton(defaultProposalRadioButton);
        return this;
    }
}
