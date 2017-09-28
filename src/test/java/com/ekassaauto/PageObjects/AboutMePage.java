package com.ekassaauto.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.ekassaauto.Registration.*;

/**
 * Created by user on 06.07.2017.
 */
public class AboutMePage extends AbstractPage {
    private Form aboutMeForm;
//    private Map<String, String> dataForFillingAboutMePageMap;
    private Map<WebElement, String> dataForFillingAboutMePageMap;

    @CacheLookup @FindBy(xpath = "//input[@name='pesel']") public WebElement peselField;
    @CacheLookup @FindBy(xpath = "//input[@name='social-number']") public WebElement socialNumberField;
    @CacheLookup @FindBy(xpath = "//input[@name='account']") public WebElement bankAccountField;
    @CacheLookup @FindBy(xpath = "//input[@name='mail']") public WebElement emailField;
    @CacheLookup @FindBy(xpath = "//md-select[@placeholder='Stan cywilny']") public WebElement maritalStatusListbox;
    @CacheLookup @FindBy(xpath = "//md-select[@placeholder='Dzieci w wieku do 21 lat']") public WebElement dependentsQuantityListbox;
    @CacheLookup @FindBy(xpath = "//md-select[@placeholder='Wykształcenie']") public WebElement educationListbox;
    @CacheLookup @FindBy(xpath = "//md-select[@placeholder='Wykonywany zawód']") public WebElement occupationTypeListbox;
    @CacheLookup @FindBy(xpath = "//input[@ng-model='user.existPmt']") public WebElement currentDebtField;
    @CacheLookup @FindBy(xpath = "//md-select[@placeholder='Osoba kontaktowa']") public WebElement contactPersonListbox;
    @CacheLookup @FindBy(xpath = "//input[@ng-model='contactPersonPhone']") public WebElement contactPersonPhoneField;
    @CacheLookup @FindBy(xpath = "//md-select[@placeholder='Nieruchomość, w której zamieszkujesz']") public WebElement propertyOwnListbox;
    @CacheLookup @FindBy(xpath = "//input[@ng-model='user.postalCode']") public WebElement postalCodeField;
    @CacheLookup @FindBy(xpath = "//md-select[@placeholder='Województwo']") public WebElement livRegionListbox;
    @CacheLookup @FindBy(xpath = "//input[@ng-model='user.city']") public WebElement livCityField;
    @CacheLookup @FindBy(xpath = "//input[@ng-model='user.street']") public WebElement livStreetField;
    @CacheLookup @FindBy(xpath = "//input[@ng-model='user.building']") public WebElement livBuildingField;
    @CacheLookup @FindBy(xpath = "//input[@ng-model='user.flat']") public WebElement livApartmentField;
    @CacheLookup @FindBy(xpath = "//md-select[@placeholder='Zatrudnienia']") public WebElement empType1Listbox;
    @CacheLookup @FindBy(xpath = "//md-select[@placeholder='Okres pracy w podanego pracodawcy']") public WebElement workExpirience1Listbox;
    @CacheLookup @FindBy(xpath = "//input[@ng-model='job.income']") public WebElement netIncome1Field;
    @CacheLookup @FindBy(xpath = "//input[@ng-model='job.employer']") public WebElement empName1Field;
    @CacheLookup @FindBy(xpath = "//md-select[@placeholder='Województwo firmy']") public WebElement empRegion1Listbox;
    @CacheLookup @FindBy(xpath = "//input[@ng-model='job.phone']") public WebElement empPhone1Field;
    @CacheLookup @FindBy(xpath = "//md-select[@placeholder='Termin ukończenia umowy']") public WebElement tempContrExpTime1Listbox;
    @CacheLookup @FindBy(xpath = "//button[@type='submit']") private WebElement submitButton;
    //@CacheLookup здесь из за того что используем элементы в мапе тогда, когда страница AboutMe уже не открыта.и надо чтоб селениум не искал их

    public AboutMePage(WebDriver driver) {
        super(driver);
        initPageElements();
    }

    private void initPageElements() {
        aboutMeForm = new Form(findWithXPath("//form[@name='workForm']"));
        dataForFillingAboutMePageMap = new HashMap<>();
    }

    public Map<WebElement, String> getDataForFillingAboutMePageMap() {
        return dataForFillingAboutMePageMap;
    }

    @Deprecated
    public AboutMePage fillAboutMePageWithBasicAcceptableData() {
        jseDriver.executeScript("arguments[0].setAttribute('value', '" + pesel +  "');", peselField);
        moveFromAField(peselField);
//        setToFieldWithJS(peselField, pesel);
        return this;
    }

    public PdlOfferPage submitPrefilledAboutMePageAfterFillindAcceptedRequiredData() {
        aboutMeForm.set(currentDebtField, currentDebt)
                .selectFromListBoxByValue(contactPersonListbox, ContactPersons.WifeOrHusband.getValue())
                .set(contactPersonPhoneField, contactPersonPhone)
                .submit(submitButton);
        waitForAngularRequestsToFinish();
        return new PdlOfferPage(driver);
    }

    public PdlOfferPage submitAboutMePageWithBasicAcceptableData() {
            aboutMeForm.checkedSetWithPuttingToMap(peselField, pesel, dataForFillingAboutMePageMap)
                    .checkedSetWithPuttingToMap(socialNumberField, socialNumber, dataForFillingAboutMePageMap)
                    .checkedSetWithPuttingToMap(bankAccountField, bankAccount, dataForFillingAboutMePageMap)
                    .checkedSetWithPuttingToMap(emailField, email, dataForFillingAboutMePageMap)
                    .selectFromListBoxByValueWithPuttingToMap(maritalStatusListbox, MaritalStatuses.Married.getValue(), dataForFillingAboutMePageMap)
                    .selectFromListBoxByValueWithPuttingToMap(dependentsQuantityListbox, DependentsQuantities.None.getValue(), dataForFillingAboutMePageMap)
                    .selectFromListBoxByValueWithPuttingToMap(educationListbox, Education.SecondaryEducation.getValue(), dataForFillingAboutMePageMap)
                    .selectFromListBoxByValueWithPuttingToMap(occupationTypeListbox, OccupationTypes.ItSpecialist.getValue(), dataForFillingAboutMePageMap)
                    .checkedSetWithPuttingToMap(currentDebtField, currentDebt, dataForFillingAboutMePageMap)
                    .selectFromListBoxByValueWithPuttingToMap(contactPersonListbox, ContactPersons.WifeOrHusband.getValue(), dataForFillingAboutMePageMap)
                    .checkedSetToPhoneFieldWithPuttingToMap(contactPersonPhoneField, contactPersonPhone, dataForFillingAboutMePageMap)
                    .selectFromListBoxByValueWithPuttingToMap(propertyOwnListbox, PropertyOwn.HomeOwner.getValue(), dataForFillingAboutMePageMap)
                    .checkedSetToPostalCodeFieldWithPuttingToMap(postalCodeField, postalCode, dataForFillingAboutMePageMap)
                    .selectFromListBoxByValueWithPuttingToMap(livRegionListbox, Regions.PodkarpackieWojewództwo.getValue(), dataForFillingAboutMePageMap)
                    .checkedSetWithPuttingToMap(livCityField, testString, dataForFillingAboutMePageMap)
                    .checkedSetWithPuttingToMap(livStreetField, testString, dataForFillingAboutMePageMap)
                    .checkedSetWithPuttingToMap(livBuildingField, testString, dataForFillingAboutMePageMap)
                    .checkedSetWithPuttingToMap(livApartmentField, testString, dataForFillingAboutMePageMap)
                    .selectFromListBoxByValueWithPuttingToMap(empType1Listbox, EmploymentTypes.FullTimeEmployed.getValue(), dataForFillingAboutMePageMap)
                    .selectFromListBoxByValueWithPuttingToMap(workExpirience1Listbox, TermsOfWorkExperience.From1To2Years.getValue(), dataForFillingAboutMePageMap)
                    .checkedSetWithPuttingToMap(empName1Field, testString, dataForFillingAboutMePageMap)
                    .checkedSetToPhoneFieldWithPuttingToMap(empPhone1Field, empPhone1, dataForFillingAboutMePageMap)
                    .checkedSetWithPuttingToMap(netIncome1Field, netIncome1, dataForFillingAboutMePageMap)
                    .selectFromListBoxByValueWithPuttingToMap(empRegion1Listbox, Regions.PodkarpackieWojewództwo.getValue(), dataForFillingAboutMePageMap)
                    .submit(submitButton);
        waitForAngularRequestsToFinish();

        return new PdlOfferPage(driver);
    }

    public BankAccountVerificationPage submitAboutMePageAtConsolProcessWithoutBankCache() {
        aboutMeForm.submit(submitButton);
        waitForAngularRequestsToFinish();

        return new BankAccountVerificationPage(driver);
    }

    public AboutMePage fillInitiallyBlankAboutMePageWithAcceptableData() {
        aboutMeForm.checkedSetWithPuttingToMap(peselField, pesel, dataForFillingAboutMePageMap)
                .checkedSetWithPuttingToMap(socialNumberField, socialNumber, dataForFillingAboutMePageMap)
                .checkedSetWithPuttingToMap(bankAccountField, bankAccount, dataForFillingAboutMePageMap)
                .checkedSetWithPuttingToMap(emailField, email, dataForFillingAboutMePageMap)
                .selectFromListBoxByValueWithPuttingToMap(maritalStatusListbox, MaritalStatuses.Married.getValue(), dataForFillingAboutMePageMap)
                .selectFromListBoxByValueWithPuttingToMap(dependentsQuantityListbox, DependentsQuantities.None.getValue(), dataForFillingAboutMePageMap)
                .selectFromListBoxByValueWithPuttingToMap(educationListbox, Education.SecondaryEducation.getValue(), dataForFillingAboutMePageMap)
                .selectFromListBoxByValueWithPuttingToMap(occupationTypeListbox, OccupationTypes.ItSpecialist.getValue(), dataForFillingAboutMePageMap)
                .checkedSetWithPuttingToMap(currentDebtField, currentDebt, dataForFillingAboutMePageMap)
                .selectFromListBoxByValueWithPuttingToMap(contactPersonListbox, ContactPersons.WifeOrHusband.getValue(), dataForFillingAboutMePageMap)
                .checkedSetToPhoneFieldWithPuttingToMap(contactPersonPhoneField, contactPersonPhone, dataForFillingAboutMePageMap)
                .selectFromListBoxByValueWithPuttingToMap(propertyOwnListbox, PropertyOwn.HomeOwner.getValue(), dataForFillingAboutMePageMap)
                .checkedSetToPostalCodeFieldWithPuttingToMap(postalCodeField, postalCode, dataForFillingAboutMePageMap)
                .selectFromListBoxByValueWithPuttingToMap(livRegionListbox, Regions.PodkarpackieWojewództwo.getValue(), dataForFillingAboutMePageMap)
                .checkedSetWithPuttingToMap(livCityField, testString, dataForFillingAboutMePageMap)
                .checkedSetWithPuttingToMap(livStreetField, testString, dataForFillingAboutMePageMap)
                .checkedSetWithPuttingToMap(livBuildingField, testString, dataForFillingAboutMePageMap)
                .checkedSetWithPuttingToMap(livApartmentField, testString, dataForFillingAboutMePageMap)
                .selectFromListBoxByValueWithPuttingToMap(empType1Listbox, EmploymentTypes.FullTimeEmployed.getValue(), dataForFillingAboutMePageMap)
                .selectFromListBoxByValueWithPuttingToMap(workExpirience1Listbox, TermsOfWorkExperience.From1To2Years.getValue(), dataForFillingAboutMePageMap)
                .checkedSetWithPuttingToMap(empName1Field, testString, dataForFillingAboutMePageMap)
                .checkedSetToPhoneFieldWithPuttingToMap(empPhone1Field, empPhone1, dataForFillingAboutMePageMap)
                .checkedSetWithPuttingToMap(netIncome1Field, netIncome1, dataForFillingAboutMePageMap)
                .selectFromListBoxByValueWithPuttingToMap(empRegion1Listbox, Regions.PodkarpackieWojewództwo.getValue(), dataForFillingAboutMePageMap);
        return this;
    }

    public void cleanInstWormCache(String name, String pesel, String lastName, String bankAccount) throws SQLException {
        if (instWormCacheDAO.instWormCacheForPdlPresent(name, pesel, lastName, bankAccount)) {
            instWormCacheDAO.deleteInstWormCache(name, pesel, lastName, bankAccount);
        }
    }

//    public enum InputsAtTheAboutMePage {
//        PeselField("PeselField");
//        private final String value;
//
//        InputsAtTheAboutMePage(String value) {
//            this.value = value;
//        }
//        public String getValue() {
//            return value;
//        }
//    }

    enum TermsOfWorkExperience {
        LessThan2Months("1"), From3To6Months("3"), From7To12Months("7"), From1To2Years("12"), From3To5Years("36"),
        From6To10Years("72"), MoreThan11Years("132");
        private final String value;

        TermsOfWorkExperience(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

    enum EmploymentTypes {
        FullTimeEmployed("Full Time Employed"), PartTimeEmployed("Part Time Employed"), FixedTermContract("Fixed Term Contract"),
        SelfEmployed("Self Employed"), Student("Student"), HousewifeOrman("Housewife/man"),
        MaternityLeave("Maternity leave"), Unemployed("Unemployed");
        private final String value;

        EmploymentTypes(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

    enum Regions {
        PodkarpackieWojewództwo("Podkarpackie Województwo"), PomorskieWojewództwo("Pomorskie Województwo"),
        MazowieckieWojewództwo("Mazowieckie Województwo"), ZachodniopomorskieWojewództwo("Zachodniopomorskie Województwo"),
        OpolskieWojewództwo("Opolskie Województwo"), KujawskoPomorskieWojewództwo("Kujawsko-pomorskie Województwo"),
        ŚwiętokrzyskieWojewództwo("Świętokrzyskie Województwo"), ŚląskieWojewództwo("Śląskie Województwo"),
        MałopolskieWojewództwo("Małopolskie Województwo"), PodlaskieWojewództwo("Podlaskie Województwo"),
        LubelskieWojewództwo("Lubelskie Województwo"), ŁódzkieWojewództwo("Łódzkie Województwo"),
        WielkopolskieWojewództwo("Wielkopolskie Województwo"), DolnośląskieWojewództwo("Dolnośląskie Województwo"),
        WarmińskoMazurskieWojewództwo("Warmińsko-mazurskie Województwo"), LubuskieWojewództwo("Lubuskie Województwo");
        private final String value;

        Regions(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

    enum PropertyOwn {
        HomeOwner("Home Owner"), HomeOwnerWithMortgage("Home Owner with Mortgage"), Tenant("Tenant"),
        LivingWithParents("Living with Parents"), Other("Other");
        private final String value;

        PropertyOwn(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

    enum ContactPersons {
        WifeOrHusband("wife/husband"), FatherOrMother("father/mother"), BrotherOrSister("brother/sister"),
        Cohabitant("cohabitant"), Relative("relative"), Friend("friend"), Other("other");
        private final String value;

        ContactPersons(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

    enum OccupationTypes {
        LeadingPosition("Leading Position"), TeacherAndEducation("Teacher / education"),
        Doctor("Doctor / Other medical proffesions"), Layer("Advocate / Lawyer"), ItSpecialist("IT specialist / expert"),
        Engineer("Engineer / Technician and other middle staff"), Administrative("Administrative / State employee"),
        Salesman("Sales representative / Salesman"), PhysicalWorker("Physical worker"), Other("Other");
        private final String value;

        OccupationTypes(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

    enum Education {
        NoEducation("No education"), PrimaryEducation("Primary education"), VocationalEducation("Vocational education"),
        SecondaryEducation("Secondary education"), Licentiate("Licentiate (lower higher)"), HigherEducation("Higher education"),
        Degree("Degree"), AcademicResearch("Academic/research");
        private final String value;

        Education(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

    enum DependentsQuantities {
        None("0"), One("1"), Two("2"), Three("3"), Four("4"), MoreThan4("5");
        private final String value;

        DependentsQuantities(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

    enum MaritalStatuses {
        Single("Single"), Married("Married"), Widowed("Widowed"), Divorced("Divorced"), LivingWithPartner("Living with Partner");
        private final String value;

        MaritalStatuses(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }
}
