package com.ekassaauto.database;

import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by user on 15.06.2017.
 */
public class PersistenceManager {
    private EntityManagerFactory auiEmFactory;
    private EntityManagerFactory riskEmFactory;

    public PersistenceManager() {
        // "jpa-example" was the value of the name attribute of the
        // persistence-unit element.
        auiEmFactory = Persistence.createEntityManagerFactory("jpa-aui");
        riskEmFactory = Persistence.createEntityManagerFactory("jpa-risk");
    }

    public EntityManager getAuiEntityManager() {
        return auiEmFactory.createEntityManager();
    }
    public EntityManager getRiskEntityManager() {
        return riskEmFactory.createEntityManager();
    }

    public void closeAuiEntityManager() {
        auiEmFactory.close();
    }
    public void closeRiskEntityManager() {
        riskEmFactory.close();
    }
}
