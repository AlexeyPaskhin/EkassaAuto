package com.ekassaauto.database.entities.aui;

import javax.persistence.*;

/**
 * Created by user on 23.08.2017.
 */
@Entity
@Table(name = "cpa_shadow_client_informations")
@NamedQueries({
        @NamedQuery(
                name = "getExistingUsersCpaEntitiesWithoutRejectAndWithSkipPersonalData",
                query = "select cscie from CpaShadowClientInformationsEntity cscie, UserCredential user where cscie.phone=user.phone and cscie.riskPolicyReject=false" +
                        " and cscie.skipPersonalData=true" +
                        " and cscie.activePdl=false" +
                        " order by cscie.id desc "
        )  ,
        @NamedQuery(
                name = "getCpaClientById",
                query = "select cscie from CpaShadowClientInformationsEntity cscie where id = :id"
        )
})
public class CpaShadowClientInformationsEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "skip_personal_data")
    private boolean skipPersonalData;
    private String phone;
    @Column(name = "auto_login")
    private boolean autoLogin;
    private String email;
    @Column(name = "activepdl")
    private boolean activePdl;
    private boolean enable;
    @Column(name = "paid_customer")
    private boolean paidCustomer;
    @Column(name = "risk_policy_reject")
    private boolean riskPolicyReject;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSkipPersonalData() {
        return skipPersonalData;
    }

    public void setSkipPersonalData(boolean skipPersonalData) {
        this.skipPersonalData = skipPersonalData;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivePdl() {
        return activePdl;
    }

    public void setActivePdl(boolean activePdl) {
        this.activePdl = activePdl;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isPaidCustomer() {
        return paidCustomer;
    }

    public void setPaidCustomer(boolean paidCustomer) {
        this.paidCustomer = paidCustomer;
    }

    public boolean isRiskPolicyReject() {
        return riskPolicyReject;
    }

    public void setRiskPolicyReject(boolean riskPolicyReject) {
        this.riskPolicyReject = riskPolicyReject;
    }
}
