package com.ekassaauto.database.dao.aui;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by user on 28.09.2017.
 */
public class CpaClientCasheDAO {
    private EntityManager entityManager;

    public CpaClientCasheDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void deleteAllCpaCache() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createNamedQuery("deleteAllCache");
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }
}
