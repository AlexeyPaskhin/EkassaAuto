package com.ekassaauto.database.entities.aui;

import javax.persistence.*;

/**
 * Created by user on 25.04.2018.
 */
@Entity
@Table(name = "employment")
public class EmploymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "plain_user_id")
    private PlainUserEntity plainUserEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlainUserEntity getPlainUserEntity() {
        return plainUserEntity;
    }

    public void setPlainUserEntity(PlainUserEntity plainUserEntity) {
        this.plainUserEntity = plainUserEntity;
    }
}
