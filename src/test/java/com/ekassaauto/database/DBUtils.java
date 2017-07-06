package com.ekassaauto.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by user on 15.06.2017.
 */
public class DBUtils {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5678/aui";
        String USER = "wildfly";
        String PASS = "NH#y28Nh";
        return DriverManager.getConnection(url, USER, PASS);
    }
}
