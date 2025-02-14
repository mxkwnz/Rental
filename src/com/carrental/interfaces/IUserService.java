package com.carrental.interfaces;

import com.carrental.models.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserService {
    List<User> getAllUsers() throws SQLException;
    boolean userExists(int userId) throws SQLException;

    void addNewUser(String name, String email, String phone) throws SQLException;
    void removeUser(int userId) throws SQLException;
    boolean hasRole(int userId, String role) throws SQLException;
}
