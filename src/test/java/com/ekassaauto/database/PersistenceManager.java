package com.ekassaauto.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by user on 15.06.2017.
 */
public class PersistenceManager {
    private EntityManagerFactory emFactory;

    public PersistenceManager() {
        // "jpa-example" was the value of the name attribute of the
        // persistence-unit element.
        emFactory = Persistence.createEntityManagerFactory("jpa-aui");
    }

    public EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }

    public void closeEntityManager() {
        emFactory.close();
    }
}
