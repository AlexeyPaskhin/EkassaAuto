package com.ekassaauto.PageObjects;

import com.ekassaauto.database.entities.aui.InstWormCacheEntity;
import com.ekassaauto.database.entities.aui.PlainUserEntity;

import javax.persistence.*;

/**
 * Created by user on 25.04.2018.
 */
@Entity
@Table(name = "bank_report_income")
public class BankReportIncomeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "inst_worm_cache_id")
    private InstWormCacheEntity instWormCacheEntity;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InstWormCacheEntity getInstWormCacheEntity() {
        return instWormCacheEntity;
    }

    public void setInstWormCacheEntity(InstWormCacheEntity instWormCacheEntity) {
        this.instWormCacheEntity = instWormCacheEntity;
    }
}
