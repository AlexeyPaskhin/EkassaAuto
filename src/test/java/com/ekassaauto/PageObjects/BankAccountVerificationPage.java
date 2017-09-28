package com.ekassaauto.PageObjects;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.ekassaauto.Registration.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

/**
 * Created by user on 15.08.2017.
 */
public class BankAccountVerificationPage extends AbstractPage {
//    @FindBy(xpath = "//div[@class='worm']") Form wormyForm;
    private Form wormyForm;
    private Form instantorForm;

    @FindBy(xpath = "//md-select[@placeholder='Wybierz swój bank']") WebElement wormyListBox;
    @FindBy(xpath = "//iframe[@name='instantor']") WebElement instantorFrame;
    @FindBy(xpath = "//button[@data-loading-text='Ładowanie…']") WebElement instantorListBox;
    @FindBy(xpath = "//ul[@style='display: block;']//li/*[contains(text(), 'Bank Zachodni WBK S.A.')]/..")
    WebElement bankZachodniInstantorListBoxOption;
    @FindBy(xpath = "//*[@id='input_nik']") WebElement instantorNikInput;
    @FindBy(xpath = "//input[@type='submit' and @value='DALEJ']") WebElement instantorSubmitButton;



    public BankAccountVerificationPage(WebDriver driver) {
        super(driver);
    }

    public CongratulationPage successfulPassingInstantorVerification() {
        if (wormyListBox.isDisplayed()) {  //если есть вурми проходим сквозь него к инстантору
            selectFromListBoxByText(wormyListBox, OptionsInWormyListBox.Inne.getValue());
        }
        explWait.until(frameToBeAvailableAndSwitchToIt(instantorFrame));
        selectBankZachodhiAtInstantor();
        instantorForm = new Form (findWithXPath("//form[@class='f1 js-bank-credentials']"));
        instantorForm.set(instantorNikInput, instantorTestNik)
                .submit(instantorSubmitButton);
        waitForAngularRequestsToFinish();
        return new CongratulationPage(driver);
    }

    private BankAccountVerificationPage selectBankZachodhiAtInstantor() {
        instantorListBox.click();
        bankZachodniInstantorListBoxOption.click();
        waitForAngularRequestsToFinish();
        return this;
    }

    private enum OptionsInWormyListBox {
        WybierzSwójBank("Wybierz swój bank"), BankPekaoSA("Bank Pekao SA"), BankZachodniWBKSA("Bank Zachodni WBK SA"),
        INGBankŚląski("ING Bank Śląski"), mBank("mBank"), PKOBankPolski("PKO Bank Polski"), AliorBank("Alior Bank"),
        CreditAgricole("Credit Agricole"), GetinBank("Getin Bank"), BankPocztowy("Bank pocztowy"),
        BankBGZBNPParibasSA("Bank BGZ BNP Paribas S.A."), Inteligo("Inteligo"), RaiffeisenPolbank("Raiffeisen Polbank"),
        EuroBank("Euro Bank"), BankHandlowy("Bank Handlowy"), Inne("Inne");
        private final String value;

        OptionsInWormyListBox(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }


    public void main1(String[] args) {
        List<WebElement> wormyListBox1 = null;
        Predicate<WebElement> predicate = webElement -> webElement != null && webElement.isDisplayed();
        List<WebElement> el = wormyListBox1.stream().filter(predicate::test).collect(Collectors.toList());

        el.stream()
                .filter(WebElement::isDisplayed)
                .collect(Collectors.toList());  //фильтровать элементы - отбирать только видимые

        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:sss");
        System.out.println(dtf.getLocale());
//        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPate("yyyy-mm-dd'T'hh:mm:ss");
        DateTime dateTime = new DateTime(DateTime.parse("2017-05-24 17:48:53", dtf));
//        DateTime dateTime = new DateTime(2017, 8, 24,12,0);
        Days days = Days.daysBetween(new DateTime().withTimeAtStartOfDay(), dateTime.withTimeAtStartOfDay());
        days.getDays();
        System.out.println(days.getDays());
        System.out.println(new DateTime());
        System.out.println(dateTime);
        Months months = Months.monthsBetween(new DateTime().withTimeAtStartOfDay(), dateTime.withTimeAtStartOfDay());
        System.out.println(months.getMonths());
        System.out.println(new DateTime());
        System.out.println(dateTime);
    }


}
