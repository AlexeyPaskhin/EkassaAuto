package com.ekassaauto.database.entities.aui;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 02.10.2017.
 */
@Entity
@Table(name = "schedule")
public class ScheduleEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "deal_id")
    private Long dealId;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "scheduleEntity")
//    @MapKeyColumn(name="day")
//    private Map<Long,ScheduleDayEntity> scheduleDayEntities;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id")
    private List<ScheduleDayEntity> scheduleDayEntities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDealId() {
        return dealId;
    }

    public void setDealId(Long dealId) {
        this.dealId = dealId;
    }

//    public Map<Long, ScheduleDayEntity> getScheduleDayEntities() {
//        return scheduleDayEntities;
//    }
//
//    public void setScheduleDayEntities(Map<Long, ScheduleDayEntity> scheduleDayEntities) {
//        this.scheduleDayEntities = scheduleDayEntities;
//    }


    public List<ScheduleDayEntity> getScheduleDayEntities() {
        return scheduleDayEntities;
    }

    public void setScheduleDayEntities(List<ScheduleDayEntity> scheduleDayEntities) {
        this.scheduleDayEntities = scheduleDayEntities;
    }
}
