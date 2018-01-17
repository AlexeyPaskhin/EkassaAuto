package com.ekassaauto.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.Map;

/**
 * Created by user on 12.04.2017.
 */
public class Form extends AbstractElement {
    public Form(WebElement element) {
        super(element);
    }

    Form selectFromListBoxByValue(WebElement listBox, String value) {
        listBox.click();
        listBox.findElement(By.xpath("//div[@aria-hidden='false']//md-option[@value='" + value + "']")).click();
        //есть дублирующиеся варианты в разных листбоксах на странице, поэтому с помощью @aria-hidden='false'
        //выбираем только те элементы, что видимы на странице(там, где листбокс открыт)
        return this;
    }

    Form selectFromListBoxByValueWithPuttingToMap(WebElement listBox, String value, Map<WebElement, String> compareMap) {
        listBox.click();
        listBox.findElement(By.xpath("//div[@aria-hidden='false']//md-option[@value='" + value + "']")).click();
        //есть дублирующиеся варианты в разных листбоксах на странице, поэтому с помощью @aria-hidden='false'
        //выбираем только те элементы, что видимы на странице(там, где листбокс открыт)

        compareMap.put(listBox, value);
        return this;
    }

    public Form set(String xplocator, String value) {
        findWithXPath(xplocator).click();
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
        field.click();
        field.clear();
        field.click();
        field.sendKeys(value);
        return this;
    }

    public Form checkedSetWithPuttingToMap(WebElement field, String value, Map<WebElement, String> compareMap) {
        compareMap.put(field, value);
        int countOfRewriting = -1;
        do {
            field.click();
            field.clear();
            field.click();
            field.sendKeys(value);
            countOfRewriting ++; //at the first inputting number of rewrites = 0
        } while (!getFieldValue(field).equals(value));
        if (countOfRewriting != 0) System.out.println("Count of rewrites to input " + field + " : " + countOfRewriting);
        return this;
    }

    //в поле телефона маска, и надо сравнивать значения с учетом нее
    public Form checkedSetToPhoneFieldWithPuttingToMap(WebElement field, String phone, Map<WebElement, String> compareMap) {
        compareMap.put(field, phone);
        do {
            field.click();
            field.clear();
            field.click();
            field.sendKeys(phone);
        } while (!getFieldValue(field).equals("(+48) " + phone.substring(0,2) + " " + phone.substring(2,5) + "-" + phone.substring(5,7)
        + "-" + phone.substring(7,9)));
        return this;
    }

    //в поле почтового индекса маска, и надо сравнивать значения с учетом нее
    public Form checkedSetToPostalCodeFieldWithPuttingToMap(WebElement field, String postalCode, Map<WebElement, String> compareMap) {
        compareMap.put(field, postalCode);
        do {
            field.click();
            field.clear();
            field.click();
            field.sendKeys(postalCode);
        } while (!getFieldValue(field).equals(postalCode.substring(0,2) + "-" + postalCode.substring(2,5)));
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
    public Form markCheckBoxWithOverlay(WebElement chBox) {
        if (chBox.getAttribute("aria-checked").equals("false"))
            chBox.sendKeys(Keys.RETURN);
        return this;
    }

    public Form uncheck(WebElement chBox) {
        if (chBox.getAttribute("aria-checked").equals("true"))
            chBox.click();
        return this;
    }

    Form unmarkLabelCheckbox(WebElement labelCheckbox) {
        if (findWithXPath("//input[@id='" + labelCheckbox.getAttribute("for") + "']").isSelected()) {
            labelCheckbox.click();
        }
        return this;
    }

    public Form markLabelCheckbox(WebElement labelCheckbox) {
//        try {
            if (!
                    findWithXPath("//input[@id='" +
                    labelCheckbox.getAttribute("for") + "']")
                    .isSelected()) {
                labelCheckbox.click();
            }
//        } catch (WebDriverException e) {
//            System.out.println("Checkbox was not clickable due to overlay");

//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e1) {
//                e1.printStackTrace();
//            }
//
//            markLabelCheckbox(labelCheckbox);
//        }
        return this;
    }

    void submit() {
        context.submit();
    }

    void submit(WebElement submitButton) {
        try {
            submitButton.click();
        } catch (WebDriverException e) {
            this.submit(); //At the main page submit button sometimes isn't clickable due to overlay
        }
    }

    Form markRadioButton(WebElement radioButton) {
        if (radioButton.getAttribute("aria-checked").equals("false"))
            radioButton.click();
        return this;
    }
}
