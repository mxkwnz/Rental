package com.carrental.db.interfaces;
import java.sql.Connection;
import java.sql.SQLException;

public interface IDB {
    Connection getConnection() throws SQLException;
    void close() throws SQLException;
}
