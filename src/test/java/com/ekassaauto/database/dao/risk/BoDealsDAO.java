package com.ekassaauto.database.dao.risk;

import com.ekassaauto.database.entities.risk.BoDealsEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by user on 29.09.2017.
 */
public class BoDealsDAO {
    private EntityManager entityManager;

    public BoDealsDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void printId(String pesel) {
        TypedQuery<BoDealsEntity> query = entityManager.createNamedQuery("getBoDealsByPesel", BoDealsEntity.class);
        query.setParameter("pesel", pesel);
        List<BoDealsEntity> boDealsEntities = query.getResultList();
        System.out.println(boDealsEntities.get(0).getId());
    }
}
