package com.carrental.interfaces;

import java.sql.SQLException;

public interface IUserService {
    boolean userExists(int userId) throws SQLException;
    void addNewUser(int userId) throws SQLException;
}