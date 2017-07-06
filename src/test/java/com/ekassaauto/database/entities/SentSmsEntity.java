package com.ekassaauto.database.entities;

import javax.persistence.*;

/**
 * Created by user on 05.07.2017.
 */
@Entity
@Table(name = "sentsms")
public class SentSmsEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String phone;
    private String smscode;

    @Override
    public String toString() {
        return "SentSmsEntity{" +
                "phone='" + phone + '\'' +
                ", smscode=" + smscode +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSmscode() {
        return smscode;
    }

    public void setSmscode(String smscode) {
        this.smscode = smscode;
    }
}
