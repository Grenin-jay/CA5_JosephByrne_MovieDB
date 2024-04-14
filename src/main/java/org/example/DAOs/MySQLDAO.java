package org.example.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Main author: Joseph Byrne
 * Other contributors: Ema Eiliakas
 *
 */

public class MySQLDAO {
    private static final String URL = "jdbc:mysql://localhost/";
    private String dbname = "ca5_joseph_byrne";
    private String username = "root";
    private String password = "";

    /**
     * Main author: Joseph Byrne
     *
     */
    public Connection getConnection() {

        try {

            Connection conn = DriverManager.getConnection
                    (URL + dbname,username, password);

            return conn;
        }

        catch (SQLException e) {

            System.out.println("Unable to connect to database: " + e.getMessage());

            return null;
        }
    }
}