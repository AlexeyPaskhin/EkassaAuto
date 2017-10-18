package com.ekassaauto.database.entities.aui;

import javax.persistence.*;

/**
 * Created by user on 02.10.2017.
 */
@Entity
@Table(name = "schedule_schedule_day")
public class ScheduleScheduleDayEntity {
    @Id
    @GeneratedValue
    @Column(name = "schedule_id")
    private Long scheduleId;
//    @Column(name = "scheduledaymap_id")
//    private Long scheduledaymapId;

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

//    public Long getScheduledaymapId() {
//        return scheduledaymapId;
//    }
//
//    public void setScheduledaymapId(Long scheduledaymapId) {
//        this.scheduledaymapId = scheduledaymapId;
//    }
}
