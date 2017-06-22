package database.dao;

import database.DBUtils;
import database.PersistenceManager;
import database.entities.UserCredential;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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
}
