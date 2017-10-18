package com.ekassaauto.database.dao.aui;

import com.ekassaauto.database.entities.aui.ScheduleEntity;
import com.ekassaauto.database.entities.aui.UserCredential;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.util.List;

/**
 * Created by user on 15.06.2017.
 */
public class UserCredentialsDAO {
    private EntityManager entityManager;

    public UserCredentialsDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Boolean getStateOfMarketingDistributionByPhone(String phone) {
        TypedQuery<UserCredential> query = entityManager.createQuery("SELECT user FROM UserCredential user where user.phone =:phone",
                UserCredential.class);
        query.setParameter("phone", phone);
        UserCredential userCredential = query.getSingleResult();
        return userCredential.getPlainUserEntity().getMarketingDistribution();
    }

    public List<UserCredential> getUserByPhone(String phone) throws SQLException {
        Query query = entityManager.createQuery("SELECT user FROM UserCredential user where user.phone =:phone");
        query.setParameter("phone", phone);
        return query.getResultList();
    }

    public void deleteUserByPhone(String regPhone) {
        entityManager.getTransaction().begin();
        TypedQuery<UserCredential> query = entityManager.createQuery("SELECT user FROM UserCredential user where user.phone = :regPhone",
                UserCredential.class);
        query.setParameter("regPhone", regPhone);
        List<UserCredential> userCredentialList = query.getResultList();
//        ScheduleEntity scheduleEntity = entityManager.createQuery("select s from ScheduleEntity s where s.id = 782607", ScheduleEntity.class).getSingleResult();
//        entityManager.remove(scheduleEntity);
//
//        entityManager.getTransaction().commit();
//        ScheduleEntity scheduleEntity = userCredentialList.get(0).getPlainUserEntity().getSuperDealEntities().get(0).getDealEntities().get(1).getScheduleEntities().get(0);
//        entityManager.remove(scheduleEntity);
        userCredentialList.forEach(userCredential -> entityManager.remove(userCredential));
//        System.out.println(userCredential);
        entityManager.getTransaction().commit();
//        entityManager.flush();
    }

    public List<UserCredential> getUserByEmail(String email) {
        Query query = entityManager.createQuery("select users from UserCredential users inner join users.plainUserEntity pue where pue.email = :email",
                UserCredential.class);
        query.setParameter("email", email);
        return (List<UserCredential>) query.getResultList();
    }

    public void deleteUserByEmail(String email) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("select uc from UserCredential uc inner join uc.plainUserEntity pue where pue.email = :email",
                UserCredential.class);
        query.setParameter("email", email);
        List<UserCredential> resultList = query.getResultList();
        resultList.forEach(entityManager::remove);
        entityManager.getTransaction().commit();
    }
}
