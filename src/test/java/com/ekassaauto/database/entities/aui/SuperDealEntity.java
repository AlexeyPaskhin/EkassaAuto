package com.ekassaauto.database.entities.aui;

import javax.persistence.*;
import java.util.List;

/**
 * Created by user on 02.10.2017.
 */
@Entity
@Table(name = "super_deal")
public class SuperDealEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "userprofileid")
    private Long userProfileId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "super_deal_id")
    private List<DealEntity> dealEntities;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }

    public List<DealEntity> getDealEntities() {
        return dealEntities;
    }

    public void setDealEntities(List<DealEntity> dealEntities) {
        this.dealEntities = dealEntities;
    }
}
