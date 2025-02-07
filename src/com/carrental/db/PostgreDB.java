package com.carrental.db;

import com.carrental.db.interfaces.IDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreDB implements IDB {
    private String host;
    private String database;
    private String user;
    private String password;
    private Connection connection;

    public PostgreDB() {
        this("localhost", "postgres", "postgres", "E00244631");
    }

    public PostgreDB(String host, String database, String user, String password) {
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://" + host + "/" + database, user, password
            );
        }
        return connection;
    }

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
