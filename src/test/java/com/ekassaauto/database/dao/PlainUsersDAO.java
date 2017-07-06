package com.ekassaauto.database.dao;

import com.ekassaauto.database.entities.PlainUserEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Created by user on 26.06.2017.
 */
public class PlainUsersDAO {
    private EntityManager entityManager;

    public PlainUsersDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public String getRegisteredEmail() {
        TypedQuery<PlainUserEntity> query = entityManager.createQuery("SELECT user FROM PlainUserEntity user WHERE user.email LIKE '%_@_%.__%'",
                PlainUserEntity.class);
        query.setMaxResults(1);
        PlainUserEntity plainUserEntity = query.getSingleResult();
        return plainUserEntity.getEmail();
    }
}
