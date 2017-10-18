package com.ekassaauto.database.entities.aui;

import javax.persistence.*;
import java.util.List;

/**
 * Created by user on 02.10.2017.
 */
@Entity
@Table(name = "deal")
public class DealEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "super_deal_id")
    private Long superDealId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "deal_id")
    private List<ScheduleEntity> scheduleEntities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSuperDealId() {
        return superDealId;
    }

    public void setSuperDealId(Long superDealId) {
        this.superDealId = superDealId;
    }

    public List<ScheduleEntity> getScheduleEntities() {
        return scheduleEntities;
    }

    public void setScheduleEntities(List<ScheduleEntity> scheduleEntities) {
        this.scheduleEntities = scheduleEntities;
    }
}
