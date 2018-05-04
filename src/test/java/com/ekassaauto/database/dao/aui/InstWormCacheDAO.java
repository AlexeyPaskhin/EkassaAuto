package com.ekassaauto.database.dao.aui;

import com.ekassaauto.database.entities.aui.InstWormCacheEntity;
import org.joda.time.DateTime;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by user on 15.08.2017.
 */
public class InstWormCacheDAO {
    private EntityManager entityManager;

    public InstWormCacheDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void printDate(Long id) {
        TypedQuery<InstWormCacheEntity> query = entityManager.createNamedQuery("getInstWormCache", InstWormCacheEntity.class);
        query.setParameter("id", id);
        InstWormCacheEntity instWormCacheEntity = query.getSingleResult();
        DateTime cacheDate = new DateTime(instWormCacheEntity.getCacheDate());
        System.out.println(cacheDate);
    }

    public boolean instWormCacheForPdlPresent(String firstName, String pesel, String secondName, String accountNumber) throws SQLException {
        List<InstWormCacheEntity> instWormCacheEntityList = getInstWormCacheEntities(firstName, pesel, secondName, accountNumber);
        return instWormCacheEntityList.size() != 0;
    }

    public List<InstWormCacheEntity> getInstWormCacheEntities(String firstName, String pesel, String secondName, String accountNumber) throws SQLException {
        TypedQuery<InstWormCacheEntity> query = entityManager.createNamedQuery("getParticularInstWormCache", InstWormCacheEntity.class);
        query.setParameter("firstName", firstName);
        query.setParameter("pesel", pesel);
        query.setParameter("secondName", secondName);
        query.setParameter("accountNumber", accountNumber);
        return query.getResultList();
    }

    public void deleteInstWormCache(String firstName, String pesel, String secondName, String accountNumber) throws SQLException {
        if (instWormCacheForPdlPresent(firstName, pesel, secondName, accountNumber)) {
            List<InstWormCacheEntity> instWormCacheEntities = getInstWormCacheEntities(firstName, pesel, secondName, accountNumber);

            entityManager.getTransaction().begin();

            instWormCacheEntities
                    .stream()
                    .flatMap(instWormCache -> instWormCache.getScoringEntities().stream())
                    .forEach(scoringEntity -> entityManager.remove(scoringEntity));

            instWormCacheEntities.stream()
                    .flatMap(instWormCacheEntity -> instWormCacheEntity.getBankReportIncomeEntities().stream())
                    .forEach(bankReportIncomeEntity -> entityManager.remove(bankReportIncomeEntity));

            instWormCacheEntities.forEach(instWormCache -> entityManager.remove(instWormCache));

            entityManager.getTransaction().commit();
        }
    }

    public boolean instWormCacheIsSuccessful(String firstName, String pesel, String secondName, String accountNumber) throws SQLException {
        List<InstWormCacheEntity> instWormCacheEntityList = getInstWormCacheEntities(firstName, pesel, secondName, accountNumber);
        if (instWormCacheForPdlPresent(firstName, pesel, secondName, accountNumber)) {
            InstWormCacheEntity lastInstWormCache = instWormCacheEntityList.get(instWormCacheEntityList.size() - 1);  //getting the last entry
            if (lastInstWormCache.getResult().equalsIgnoreCase("SUCCESS")) {
                return true;
            } else if (lastInstWormCache.getResult().equalsIgnoreCase("DECLINE")) {
                return false;
            } else throw new AssertionError("There is no correct result for this existing instantor cache");
        } else throw new SQLException("There is no bank account verification cache for this phone number");
    }
}