package com.ekassaauto.database.entities.aui;

import javax.persistence.*;
import java.util.List;

/**
 * Created by user on 15.06.2017.
 */
@Entity
@Table(name = "plainusers")
public class PlainUserEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String employer;
    @Column(name = "employerphone")
    private String employerPhone;
    @Column(name = "employerregion")
    private String employerRegion;
    @Column(name = "employmenttype")
    private String employmentType;
    @Column(name = "firstname")
    private String firstName;
    private Long income;
    private String lastName;
    @Column(name = "livapartment")
    private String livApartment;
    @Column(name = "livbuilding")
    private String livBuilding;
    @Column(name = "livcity")
    private String livСity;
    @Column(name = "livpostcode")
    private String postalCode;
    @Column(name = "livregion")
    private String livRegion;
    @Column(name = "livstreet")
    private String livStreet;
    @Column(name = "maritalstatus")
    private String maritalStatus;
    @Column(name = "propertyown")
    private String propertyOwn;
    private String account;
    private Long children;
    private String education;
    private String occupationtype;
    private String pesel;
    @Column(name = "socialnumber")
    private String socialNumber;
    @Column(name = "workexperience")
    private Long workExperience;
    @Column(name = "ismarketingdistribution")
    private Boolean isMarketingDistribution;
    @Column(name = "firsttimeentered")
    private Boolean firstTimeEntered;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userprofileid")
    private List<SuperDealEntity> superDealEntities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getEmployerPhone() {
        return employerPhone;
    }

    public void setEmployerPhone(String employerPhone) {
        this.employerPhone = employerPhone;
    }

    public String getEmployerRegion() {
        return employerRegion;
    }

    public void setEmployerRegion(String employerRegion) {
        this.employerRegion = employerRegion;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getIncome() {
        return income;
    }

    public void setIncome(Long income) {
        this.income = income;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLivApartment() {
        return livApartment;
    }

    public void setLivApartment(String livApartment) {
        this.livApartment = livApartment;
    }

    public String getLivBuilding() {
        return livBuilding;
    }

    public void setLivBuilding(String livBuilding) {
        this.livBuilding = livBuilding;
    }

    public String getLivСity() {
        return livСity;
    }

    public void setLivСity(String livСity) {
        this.livСity = livСity;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLivRegion() {
        return livRegion;
    }

    public void setLivRegion(String livRegion) {
        this.livRegion = livRegion;
    }

    public String getLivStreet() {
        return livStreet;
    }

    public void setLivStreet(String livStreet) {
        this.livStreet = livStreet;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPropertyOwn() {
        return propertyOwn;
    }

    public void setPropertyOwn(String propertyOwn) {
        this.propertyOwn = propertyOwn;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getChildren() {
        return children;
    }

    public void setChildren(Long children) {
        this.children = children;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getOccupationtype() {
        return occupationtype;
    }

    public void setOccupationtype(String occupationtype) {
        this.occupationtype = occupationtype;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getSocialNumber() {
        return socialNumber;
    }

    public void setSocialNumber(String socialNumber) {
        this.socialNumber = socialNumber;
    }

    public Long getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(Long workExperience) {
        this.workExperience = workExperience;
    }

    public Boolean getMarketingDistribution() {
        return isMarketingDistribution;
    }

    public Boolean getFirstTimeEntered() {
        return firstTimeEntered;
    }

    public void setFirstTimeEntered(Boolean firstTimeEntered) {
        this.firstTimeEntered = firstTimeEntered;
    }

    public void setMarketingDistribution(Boolean marketingDistribution) {
        isMarketingDistribution = marketingDistribution;
    }

    public List<SuperDealEntity> getSuperDealEntities() {
        return superDealEntities;
    }

    public void setSuperDealEntities(List<SuperDealEntity> superDealEntities) {
        this.superDealEntities = superDealEntities;
    }

    @Override
    public String toString() {
        return "PlainUserEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
