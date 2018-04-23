package com.ekassaauto.PageObjects;

import org.testng.annotations.DataProvider;

/**
 * Created by user on 19.04.2017.
 */
public class DataProviders {
    @DataProvider(name = "UsingOfHyphen")
    public static Object[][] UsingOfHyphen() {
        return new Object[][] {
                {"-"},
                {"kek-"},
                {"-roock"},
        };
    }

    @DataProvider(name = "emailValidation")
    public static Object[][] emailNonValid() {
        return new Object[][]{
                {"a.paskhin1 @gmail.com"},
                {"a.paskhin1@@gmail.com"},
                {"a.paskhin1gmail.com"},
                {"a.paskhin1@gmailcom"},
                {"ыa.paskhin1@gmail.com"},
                {"apaskhin1.gmail@com"},
                {"a.paskhin1@gmail.commm"},
                {"a.paskhin1@gmail.c"},
                {"a.paskhin1@gmail..com"},
                {"a.paskhin1@gmail.сщь"},
                {"a.paskhin1@gmail.com-"}
        };
    }

    @DataProvider
    public static Object[][] passwordValidation() {
        return new Object[][]{
                {"1234q"},
                {"12345ыц"},
                {"qwerty"},
                {"123456"},
                {"123 456q"},
                {" 123456q"},
                {"123456q "}
        };
    }

    @DataProvider
    public static Object[][] invalidSmsCodes() {
        return new Object[][] {
                {""},
                {"abc1"},
                {"123"}
        };
    }

    @DataProvider
    private static Object[][] requiredFieldsForCpa() {
        return new Object[][]{
                {"amountRequested", AboutMePage.loanAmountFieldLocator},
                {"tenorRequested", AboutMePage.loanTenorFieldLocator},
                {"firstName", AboutMePage.firstNameFieldLocator},
                {"lastName", AboutMePage.lastNameFieldLocator},
                {"pesel", AboutMePage.peselFieldLocator},
                {"socialNumber", AboutMePage.socialNumberFieldLocator},
                {"maritalStatus", AboutMePage.maritalStatusListboxLocator},
                {"education", AboutMePage.educationListboxLocator},
                {"email", AboutMePage.emailFieldLocator},
                {"propertyOwn", AboutMePage.propertyOwnListboxLocator},
                {"empType1", AboutMePage.empType1ListboxLocator},
                {"netIncome1", AboutMePage.netIncome1FieldLocator},
                {"existPmt", AboutMePage.currentDebtFieldLocator},
                {"workExperience1", AboutMePage.workExperience1ListboxLocator},
                {"accountNumber", AboutMePage.bankAccountFieldLocator},
                {"livPostcode", AboutMePage.postalCodeFieldLocator},
                {"livCity", AboutMePage.livCityFieldLocator},
                {"livStreet", AboutMePage.livStreetFieldLocator},
                {"livBuilding", AboutMePage.livBuildingFieldLocator},
        };
    }

}
