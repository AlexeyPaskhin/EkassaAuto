package com.ekassaauto.database.entities;

import javax.persistence.*;

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
    @Column(name = "firstname")
    private String firstName;
    private String lastName;
    private String account;
    private String pesel;
    @Column(name = "ismarketingdistribution")
    private Boolean isMarketingDistribution;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public Boolean getMarketingDistribution() {
        return isMarketingDistribution;
    }

    public void setMarketingDistribution(Boolean marketingDistribution) {
        isMarketingDistribution = marketingDistribution;
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
