package com.ekassaauto.database.entities.aui;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by user on 16.08.2017.
 */
@Entity
@Table(name = "inst_report")
public class InstReportEntity {
    @Id
    @GeneratedValue
    private Long id;
}
