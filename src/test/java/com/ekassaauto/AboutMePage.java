package com.ekassaauto;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.sql.SQLException;

import static com.ekassaauto.Registration.*;

/**
 * Created by user on 06.07.2017.
 */
public class AboutMePage extends AbstractPage {
    private Form aboutMeForm;

    @FindBy(xpath = "//ul[@ng-controller='BreadcrumbsCtrl']") WebElement breadcrumbs;
    @FindBy(xpath = "//input[@name='pesel']") WebElement peselField;
    @FindBy(xpath = "//input[@name='social-number']") WebElement socialNumberField;
    @FindBy(xpath = "//input[@name='account']") WebElement bankAccountField;
    @FindBy(xpath = "//input[@name='mail']") WebElement emailField;
    @FindBy(xpath = "//md-select[@placeholder='Stan cywilny']") WebElement maritalStatusListbox;
    @FindBy(xpath = "//md-select[@placeholder='Dzieci w wieku do 21 lat']") WebElement dependentsQuantityListbox;
    @FindBy(xpath = "//md-select[@placeholder='Wykształcenie']") WebElement educationListbox;
    @FindBy(xpath = "//md-select[@placeholder='Wykonywany zawód']") WebElement occupationTypeListbox;
    @FindBy(xpath = "//input[@ng-model='user.existPmt']") WebElement currentDebtField;
    @FindBy(xpath = "//md-select[@placeholder='Osoba kontaktowa']") WebElement contactPersonListbox;
    @FindBy(xpath = "//input[@ng-model='contactPersonPhone']") WebElement contactPersonPhoneField;
    @FindBy(xpath = "//md-select[@placeholder='Nieruchomość, w której zamieszkujesz']") WebElement propertyOwnListbox;
    @FindBy(xpath = "//input[@ng-model='user.postalCode']") WebElement postalCodeField;
    @FindBy(xpath = "//md-select[@placeholder='Województwo']") WebElement livRegionListbox;
    @FindBy(xpath = "//input[@ng-model='user.city']") WebElement livCityField;
    @FindBy(xpath = "//input[@ng-model='user.street']") WebElement livStreetField;
    @FindBy(xpath = "//input[@ng-model='user.building']") WebElement livBuildingField;
    @FindBy(xpath = "//input[@ng-model='user.flat']") WebElement livApartmentField;
    @FindBy(xpath = "//md-select[@placeholder='Zatrudnienia']") WebElement empType1Listbox;
    @FindBy(xpath = "//md-select[@placeholder='Okres pracy w podanego pracodawcy']") WebElement workExpirience1Listbox;
    @FindBy(xpath = "//input[@ng-model='job.income']") WebElement netIncome1Field;
    @FindBy(xpath = "//input[@ng-model='job.employer']") WebElement empName1Field;
    @FindBy(xpath = "//md-select[@placeholder='Województwo firmy']") WebElement empRegion1Listbox;
    @FindBy(xpath = "//input[@ng-model='job.phone']") WebElement empPhone11Field;
    @FindBy(xpath = "//md-select[@placeholder='Termin ukończenia umowy']") WebElement tempContrExpTime1Listbox;
    @FindBy(xpath = "//button[@type='submit']") WebElement submitButton;

    public AboutMePage(WebDriver driver) {
        super(driver);
        initPageElements();
    }

    private void initPageElements() {
        aboutMeForm = new Form(findWithXPath("//form[@name='workForm']"));
    }


    PdlOfferPage submitAboutMePageWithAcceptableData() {
        aboutMeForm.set(peselField, pesel)
                .set(socialNumberField, socialNumber)
                .set(bankAccountField, bankAccount)
                .set(emailField, email)
                .selectFromListBoxByValue(maritalStatusListbox, MaritalStatuses.Married.getValue())
                .selectFromListBoxByValue(dependentsQuantityListbox, DependentsQuantities.None.getValue())
                .selectFromListBoxByValue(educationListbox, Education.SecondaryEducation.getValue())
                .selectFromListBoxByValue(occupationTypeListbox, OccupationTypes.ItSpecialist.getValue())
                .set(currentDebtField, "0")
                .selectFromListBoxByValue(contactPersonListbox, ContactPersons.WifeOrHusband.getValue())
                .set(contactPersonPhoneField, contactPersonPhone)
                .selectFromListBoxByValue(propertyOwnListbox, PropertyOwn.HomeOwner.getValue())
                .set(postalCodeField, postalCode)
                .selectFromListBoxByValue(livRegionListbox, Regions.PodkarpackieWojewództwo.getValue())
                .set(livCityField, testString)
                .set(livStreetField, testString)
                .set(livBuildingField, testString)
                .set(livApartmentField, testString)
                .selectFromListBoxByValue(empType1Listbox, EmploymentTypes.FullTimeEmployed.getValue())
                .selectFromListBoxByValue(workExpirience1Listbox, TermsOfWorkExperience.From1To2Years.getValue())
                .set(netIncome1Field, "3500")
                .set(empName1Field, testString)
                .selectFromListBoxByValue(empRegion1Listbox, Regions.PodkarpackieWojewództwo.getValue())
                .set(empPhone11Field, empPhoneField)
                .submit(submitButton);
        waitForAngularRequestsToFinish();

        return new PdlOfferPage(driver);
    }

    public void cleanInstWormCache() throws SQLException {
        if (instWormCacheDAO.instWormCacheForPdlPresent(name, pesel, lastName, bankAccount)) {
            instWormCacheDAO.deleteInstWormCache(name, pesel, lastName, bankAccount);
        }
    }

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
        Single("Single"), Married("Single"), Widowed("Widowed"), Divorced("Divorced"), LivingWithPartner("Living with Partner");
        private final String value;

        MaritalStatuses(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }
}
