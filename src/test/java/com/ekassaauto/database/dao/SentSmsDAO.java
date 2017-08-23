package com.ekassaauto.database.dao;

import com.ekassaauto.database.entities.InstWormCacheEntity;
import com.ekassaauto.database.entities.SentSmsEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by user on 05.07.2017.
 */
public class SentSmsDAO {
    private EntityManager entityManager;

    public SentSmsDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<SentSmsEntity> getSmsCodeEntryByPhone(String phone) {
        Query query = entityManager.createQuery("select s from SentSmsEntity s where s.phone = :phone");
        query.setParameter("phone",phone);
        return query.getResultList();
    }

    public String getSmsCodeByPhone(String phone) {
        TypedQuery<SentSmsEntity> query = entityManager.createQuery("select s from SentSmsEntity s where s.phone = :phone", SentSmsEntity.class);
        query.setParameter("phone",phone);
        query.setMaxResults(1);
        SentSmsEntity sentSmsEntity = query.getSingleResult();
//        TypedQuery<InstWormCacheEntity> query2 = entityManager.createNamedQuery("getInstWormCache", InstWormCacheEntity.class);
//        query2.setParameter("id",1);
        return sentSmsEntity.getSmscode();
    }
}
