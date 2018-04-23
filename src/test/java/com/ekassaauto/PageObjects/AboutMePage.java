package com.ekassaauto.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

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

    public static final String loanAmountFieldLocator = "//input[@name='loanAmount']";
    public static final String loanTenorFieldLocator = "//input[@name='loanTenor']";
    public static final String workExperience1ListboxLocator = "//md-select[@placeholder='Okres pracy w podanego pracodawcy']";
    public static final String firstNameFieldLocator = "//input[@name='firstName']";
    public static final String lastNameFieldLocator = "//input[@name='lastName']";
    public static final String peselFieldLocator = "//input[@name='pesel']";
    public static final String socialNumberFieldLocator = "//input[@name='socialNumber']";
    public static final String bankAccountFieldLocator = "//input[@name='account']";
    public static final String emailFieldLocator = "//input[@name='email']";
    public static final String maritalStatusListboxLocator = "//md-select[@placeholder='Stan cywilny']";
    public static final String dependentsQuantityListboxLocator = "//md-select[@placeholder='Dzieci w wieku do 21 lat']";
    public static final String educationListboxLocator = "//md-select[@placeholder='Wykształcenie']";
    public static final String occupationTypeListboxLocator = "//md-select[@placeholder='Wykonywany zawód']";
    public static final String currentDebtFieldLocator = "//input[@name='existPmt']";
//    public static final String contactPersonListboxLocator = "//md-select[@placeholder='Osoba kontaktowa']";
    public static final String propertyOwnListboxLocator = "//md-select[@placeholder='Nieruchomość, w której zamieszkujesz']";
    public static final String postalCodeFieldLocator = "//input[@name='postalCode']";
    public static final String livRegionListboxLocator = "//md-select[@placeholder='Województwo']";
    public static final String livCityFieldLocator = "//input[@name='city']";
    public static final String livStreetFieldLocator = "//input[@name='street']";
    public static final String livBuildingFieldLocator = "//input[@name='building']";
    public static final String livApartmentFieldLocator = "//input[@name='flat']";
    public static final String empType1ListboxLocator = "//md-select[@placeholder='Zatrudnienia']";
    public static final String netIncome1FieldLocator = "//input[@ng-model='job.income']";
    public static final String empName1FieldLocator = "//input[@ng-model='job.employer']";
    public static final String empRegion1ListboxLocator = "//md-select[@placeholder='Województwo firmy']";
    public static final String empPhone1FieldLocator = "//input[@ng-model='job.phone']";
    public static final String tempContrExpTime1ListboxLocator = "//md-select[@placeholder='Termin ukończenia umowy']";
    public static final String submitButtonLocator = "//button[@type='submit']";

    @FindBy(xpath = loanAmountFieldLocator) public WebElement loanAmountField;
    @FindBy(xpath = loanTenorFieldLocator) public WebElement loanTenorField;
    @FindBy(xpath = firstNameFieldLocator) public WebElement firstNameField;
    @FindBy(xpath = lastNameFieldLocator) public WebElement lastNameField;
    @CacheLookup @FindBy(xpath = peselFieldLocator) public WebElement peselField;
    @CacheLookup @FindBy(xpath = socialNumberFieldLocator) public WebElement socialNumberField;
    @CacheLookup @FindBy(xpath = bankAccountFieldLocator) public WebElement bankAccountField;
    @CacheLookup @FindBy(xpath = emailFieldLocator) public WebElement emailField;
    @CacheLookup @FindBy(xpath = maritalStatusListboxLocator) public WebElement maritalStatusListbox;
    @CacheLookup @FindBy(xpath = dependentsQuantityListboxLocator) public WebElement dependentsQuantityListbox;
    @CacheLookup @FindBy(xpath = educationListboxLocator) public WebElement educationListbox;
    @CacheLookup @FindBy(xpath = occupationTypeListboxLocator) public WebElement occupationTypeListbox;
    @CacheLookup @FindBy(xpath = currentDebtFieldLocator) public WebElement currentDebtField;
//    @CacheLookup @FindBy(xpath = contactPersonListboxLocator) public WebElement contactPersonListbox;
//    @CacheLookup @FindBy(xpath = "//input[@name='contactPersonPhone']") public WebElement contactPersonPhoneField;
    @CacheLookup @FindBy(xpath = propertyOwnListboxLocator) public WebElement propertyOwnListbox;
    @CacheLookup @FindBy(xpath = postalCodeFieldLocator) public WebElement postalCodeField;
    @CacheLookup @FindBy(xpath = livRegionListboxLocator) public WebElement livRegionListbox;
    @CacheLookup @FindBy(xpath = livCityFieldLocator) public WebElement livCityField;
    @CacheLookup @FindBy(xpath = livStreetFieldLocator) public WebElement livStreetField;
    @CacheLookup @FindBy(xpath = livBuildingFieldLocator) public WebElement livBuildingField;
    @CacheLookup @FindBy(xpath = livApartmentFieldLocator) public WebElement livApartmentField;
    @CacheLookup @FindBy(xpath = empType1ListboxLocator) public WebElement empType1Listbox;
    @CacheLookup @FindBy(xpath = workExperience1ListboxLocator) public WebElement workExpirience1Listbox;
    @CacheLookup @FindBy(xpath = netIncome1FieldLocator) public WebElement netIncome1Field;
    @CacheLookup @FindBy(xpath = empName1FieldLocator) public WebElement empName1Field;
    @CacheLookup @FindBy(xpath = empRegion1ListboxLocator) public WebElement empRegion1Listbox;
    @CacheLookup @FindBy(xpath = empPhone1FieldLocator) public WebElement empPhone1Field;
    @CacheLookup @FindBy(xpath = tempContrExpTime1ListboxLocator) public WebElement tempContrExpTime1Listbox;
    @CacheLookup @FindBy(xpath = submitButtonLocator) private WebElement submitButton;
    //@CacheLookup здесь из за того что используем элементы в мапе тогда, когда страница AboutMe уже не открыта.и надо чтоб селениум не искал их

    public AboutMePage(WebDriver driver) {
        super(driver);
        if (driver.getCurrentUrl().contains("/#/employment/")) {
            initPageElements();
            printURL();
        }
    }

    private void initPageElements() {
        aboutMeForm = new Form(findWithXPath("//form[@name='$ctrl.form.aboutMeForm']"));
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

    public PdlOfferPage passPrefilledAboutMePageGottenFromMyProfile() {
        aboutMeForm.set(currentDebtField, currentDebt)
//                .selectFromListBoxByValue(contactPersonListbox, ContactPersons.WifeOrHusband.getValue())
//                .set(contactPersonPhoneField, contactPersonPhone)
                .set(loanAmountField, "2000")
                .set(loanTenorField, "12")
                .submit(submitButton);
        waitForAngularRequestsToFinish();
        return new PdlOfferPage(driver);
    }
    public PdlOfferPage passPrefilledAboutMePageGottenFromMainPage() {
        aboutMeForm.set(currentDebtField, currentDebt)
//                .selectFromListBoxByValue(contactPersonListbox, ContactPersons.WifeOrHusband.getValue())
//                .set(contactPersonPhoneField, contactPersonPhone)
                .submit(submitButton);
        waitForAngularRequestsToFinish();
        return new PdlOfferPage(driver);
    }

    public PdlOfferPage submitAboutMePageWithBasicAcceptableData() {
        fillInitiallyBlankAboutMePageWithAcceptableData();
        aboutMeForm.submit(submitButton);
        waitForAngularRequestsToFinish();

        return new PdlOfferPage(driver);
    }

    public BankAccountVerificationPage submitAboutMePageWithoutBankCache() {
        aboutMeForm.submit(submitButton);
        waitForAngularRequestsToFinish();

        return new BankAccountVerificationPage(driver);
    }

    public AboutMePage submitInvalidAboutMePage() {
        submitButton.click();
        waitForAngularRequestsToFinish();
        return this;
    }

    public AboutMePage fillInitiallyBlankAboutMePageWithAcceptableData() {
        checkedSetWithPuttingToMap(peselField, pesel, dataForFillingAboutMePageMap)
                .checkedSetWithPuttingToMap(socialNumberField, socialNumber, dataForFillingAboutMePageMap)
                .checkedSetWithPuttingToMap(bankAccountField, bankAccount, dataForFillingAboutMePageMap)
                .checkedSetWithPuttingToMap(emailField, email, dataForFillingAboutMePageMap)
                .selectFromListBoxByValueWithPuttingToMap(maritalStatusListbox, MaritalStatuses.Married.getValue(), dataForFillingAboutMePageMap)
                .selectFromListBoxByValueWithPuttingToMap(dependentsQuantityListbox, DependentsQuantities.None.getValue(), dataForFillingAboutMePageMap)
                .selectFromListBoxByValueWithPuttingToMap(educationListbox, Education.SecondaryEducation.getValue(), dataForFillingAboutMePageMap)
                .selectFromListBoxByValueWithPuttingToMap(occupationTypeListbox, OccupationTypes.ItSpecialist.getValue(), dataForFillingAboutMePageMap)
                .checkedSetWithPuttingToMap(currentDebtField, currentDebt, dataForFillingAboutMePageMap)
//                .selectFromListBoxByValueWithPuttingToMap(contactPersonListbox, ContactPersons.WifeOrHusband.getValue(), dataForFillingAboutMePageMap)
//                .checkedSetToPhoneFieldWithPuttingToMap(contactPersonPhoneField, contactPersonPhone, dataForFillingAboutMePageMap)
                .selectFromListBoxByValueWithPuttingToMap(propertyOwnListbox, PropertyOwn.HomeOwner.getValue(), dataForFillingAboutMePageMap)
                .checkedSetToPostalCodeFieldWithPuttingToMap(postalCodeField, postalCode, dataForFillingAboutMePageMap)
                .selectFromListBoxByValueWithPuttingToMap(livRegionListbox, Regions.PodkarpackieWojewództwo.getValue(), dataForFillingAboutMePageMap)
                .checkedSetWithPuttingToMap(livCityField, testString, dataForFillingAboutMePageMap)
                .checkedSetWithPuttingToMap(livStreetField, testString, dataForFillingAboutMePageMap)
                .checkedSetWithPuttingToMap(livBuildingField, testString, dataForFillingAboutMePageMap)
                .checkedSetWithPuttingToMap(livApartmentField, testString, dataForFillingAboutMePageMap)
                .selectFromListBoxByValueWithPuttingToMap(empType1Listbox, EmploymentTypes.FullTimeEmployed.getValue(), dataForFillingAboutMePageMap);
        selectFromListBoxByValueWithPuttingToMap(workExpirience1Listbox, TermsOfWorkExperience.From1To2Years.getValue(), dataForFillingAboutMePageMap);
        checkedSetToPhoneFieldWithPuttingToMap(empPhone1Field, empPhone1, dataForFillingAboutMePageMap)
                .selectFromListBoxByValueWithPuttingToMap(empRegion1Listbox, Regions.PodkarpackieWojewództwo.getValue(), dataForFillingAboutMePageMap)
                .checkedSetWithPuttingToMap(empName1Field, testString, dataForFillingAboutMePageMap)
                .checkedSetWithPuttingToMap(netIncome1Field, netIncome1, dataForFillingAboutMePageMap);
        return this;
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

    public enum EmploymentTypes {
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
