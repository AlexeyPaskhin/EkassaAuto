package database.dao;

import database.DBUtils;
import database.PersistenceManager;
import database.entities.UserCredential;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 15.06.2017.
 */
public class UserCredentialsDAO {
    private EntityManager entityManager;

    public UserCredentialsDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<UserCredential> getUserByPhone(String phone) throws SQLException {
        Query query = entityManager.createQuery("SELECT user FROM UserCredential user where user.phone =:phone");
        query.setParameter("phone",phone);
        return query.getResultList();
    }

    public void deleteUserByPhone(String regPhone) {
        entityManager.getTransaction().begin();
        TypedQuery<UserCredential> query = entityManager.createQuery("SELECT user FROM UserCredential user where user.phone = :regPhone",UserCredential.class);
        query.setParameter("regPhone", regPhone);
        UserCredential userCredential = query.getSingleResult();
//        userCredential.getPhone();
//        System.out.println(userCredential);
        entityManager.remove(userCredential);
        entityManager.getTransaction().commit();
//        entityManager.flush();
    }

    public List<UserCredential> getUserByEmail(String email) {
        Query query = entityManager.createQuery("select users from UserCredential users inner join users.plainUserEntity pue where pue.email = :email", UserCredential.class);
        query.setParameter("email", email);
        List<UserCredential> resultList = query.getResultList();
        return resultList;
    }

    public void deleteUserByEmail(String email) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("select uc from UserCredential uc inner join uc.plainUserEntity pue where pue.email = :email", UserCredential.class);
        query.setParameter("email", email);
        List<UserCredential> resultList = query.getResultList();
        resultList.forEach(entityManager::remove);
        entityManager.getTransaction().commit();
    }
}
