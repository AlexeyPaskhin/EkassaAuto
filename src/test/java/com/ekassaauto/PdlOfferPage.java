package com.ekassaauto;

import org.openqa.selenium.WebDriver;

/**
 * Created by user on 14.08.2017.
 */
public class PdlOfferPage extends AbstractPage {
    private Form pdlOfferForm;

    public PdlOfferPage(WebDriver driver) {
        super(driver);
        initPageElements();
    }

    private void initPageElements() {
        pdlOfferForm = new Form(findWithXPath("//form[@name='paydayOffer']"));
    }

}
