package com.ekassaauto;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.sql.SQLException;

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
        initPageElements();
    }

    private void initPageElements() {
            pdlOfferForm = new Form(findWithXPath("//form[@name='paydayOffer']"));
    }


    CongratulationPage passingPdlOfferPageWithDefaultProposalWithSuccessfulBankCache() {
        pdlOfferPage.selectDefaultProposal()
                .agreeWithTheTerms()
                .enterSmsCode()
                .submitPdlOfferForm()
                .waitForAngularRequestsToFinish();
//        if (instWormCacheDAO.instWormCacheForPdlPresent(regPhone) && instWormCacheDAO.instWormCacheIsSuccessful(regPhone))  -  это должно быть тестовой логикой
            return new CongratulationPage(driver);
    }

    RejectPage passingPdlOfferPageWithDefaultProposalWithFailedBankCache() {
        pdlOfferPage.selectDefaultProposal()
                .agreeWithTheTerms()
                .enterSmsCode()
                .submitPdlOfferForm()
                .waitForAngularRequestsToFinish();
//        if (instWormCacheDAO.instWormCacheForPdlPresent(regPhone) && !instWormCacheDAO.instWormCacheIsSuccessful(regPhone))  -  это должно быть тестовой логикой
            return new RejectPage(driver);
        }

    BankAccountVerificationPage passingPdlOfferPageWithDefaultProposalWithoutBankCache() {
        pdlOfferPage.selectDefaultProposal()
                .agreeWithTheTerms()
                .enterSmsCode()
                .submitPdlOfferForm()
                .waitForAngularRequestsToFinish();
        return new BankAccountVerificationPage(driver);
    }

    PdlOfferPage submitPdlOfferForm() {
        pdlOfferForm.submit(moveForwardButton);
        return this;
    }

    PdlOfferPage enterSmsCode() {
        explWait.until(visibilityOf(pdlOfferSmsCodeInput));
        pdlOfferForm.set(pdlOfferSmsCodeInput, sentSmsDAO.getSmsCodeByPhone(regPhone));
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
