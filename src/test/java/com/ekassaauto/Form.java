package com.ekassaauto;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * Created by user on 12.04.2017.
 */
public class Form extends AbstractElement {
    public Form(WebElement element) {
        super(element);
    }

    public Form set(String xplocator, String value) {
        findWithXPath(xplocator).clear();
        findWithXPath(xplocator).sendKeys(value);
        return this;
    }

    public Form setToFieldWithOverlay(WebElement field, String value) {
        field.clear();
        field.sendKeys(Keys.RETURN);  //обычный клик из за оверлея над пдл и консолид. формами забагован в хроме, это аналогичная ф-ция через клавиатуру
        field.sendKeys(value);
        return this;
    }

    public Form set(WebElement field, String value) {
        field.clear();
        field.click();
        field.sendKeys(value);
        return this;
    }

    public String getFieldValue(String xplocator) {
        return findWithXPath(xplocator).getAttribute("value");
    }

    public String getFieldValue(WebElement field) {
        field.click();
        return field.getAttribute("value");
    }
    public String getValueFromFieldAtFormWIthOverlay(WebElement field) {
        field.sendKeys(Keys.RETURN);
        return field.getAttribute("value");
    }

    public String getElementClass(WebElement element) {
        return element.getAttribute("class");
    }

    public Form markCheckBox(WebElement chBox) {
        if (chBox.getAttribute("aria-checked").equals("false"))
            chBox.click();
        return this;
    }

    public Form uncheck(WebElement chBox) {
        if (chBox.getAttribute("aria-checked").equals("true"))
            chBox.click();
        return this;
    }

    void submit() {
        context.submit();
    }

    void submit(WebElement submitButton) {
        submitButton.click();
    }
}
