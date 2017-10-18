package com.ekassaauto.database.entities.aui;

import javax.persistence.*;
import java.util.List;

/**
 * Created by user on 02.10.2017.
 */
@Entity
@Table(name = "schedule_day")
public class ScheduleDayEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "schedule_id")
    private Long scheduleId;
    private Long day;

    @ManyToOne
    @JoinColumn(name = "schedule_id", insertable = false, updatable = false)
    private ScheduleEntity scheduleEntity;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "scheduledaymap_id", nullable = false)
    private List<ScheduleScheduleDayEntity> scheduleScheduleDayEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public List<ScheduleScheduleDayEntity> getScheduleScheduleDayEntity() {
        return scheduleScheduleDayEntity;
    }

    public void setScheduleScheduleDayEntity(List<ScheduleScheduleDayEntity> scheduleScheduleDayEntity) {
        this.scheduleScheduleDayEntity = scheduleScheduleDayEntity;
    }
}
