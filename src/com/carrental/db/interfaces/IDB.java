package com.carrental.db.interfaces;

import java.sql.Connection;

public interface IDB {
    Connection getConnection();
    void close();
}
