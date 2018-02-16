package com.ekassaauto.database.entities.aui;

import javax.persistence.*;
import java.util.List;

/**
 * Created by user on 15.06.2017.
 */
@Entity
@Table(name = "userscredentials")
public class UserCredential {
    @Id
    @GeneratedValue
    private Long id;
    private String password;
    private String phone;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "plain_user_id",nullable = false)
    private PlainUserEntity plainUserEntity;

    @OneToMany(mappedBy = "userCredentials")
//    @JoinColumn(name = "id")
    private List<ScoringEntity> scoringEntities;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public PlainUserEntity getPlainUserEntity() {
        return plainUserEntity;
    }

    public void setPlainUserEntity(PlainUserEntity plainUserEntity) {
        this.plainUserEntity = plainUserEntity;
    }

    public List<ScoringEntity> getScoringEntities() {
        return scoringEntities;
    }

    public void setScoringEntities(List<ScoringEntity> scoringEntities) {
        this.scoringEntities = scoringEntities;
    }

    @Override
    public String toString() {
        return "UserCredential{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                '}';
    }
}
