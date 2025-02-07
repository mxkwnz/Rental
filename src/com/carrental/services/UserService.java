package com.carrental.services;

import com.carrental.db.interfaces.IDB;
import com.carrental.interfaces.IUserService;
import com.carrental.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService {
    private IDB db;

    public UserService(IDB db) {
        this.db = db;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";  // Example query to get all users
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public boolean userExists(int userId) throws SQLException {
        String query = "SELECT COUNT(*) FROM Users WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    @Override
    public void addNewUser(String name, String email, String phone) throws SQLException {
        String query = "INSERT INTO Users (name, email, phone) VALUES (?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.executeUpdate();
        }
    }

    @Override
    public void removeUser(int userId) throws SQLException {
        String query = "DELETE FROM Users WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    @Override
    public boolean hasRole(int userId, String role) throws SQLException {
        String query = "SELECT COUNT(*) FROM UserRoles WHERE user_id = ? AND role = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, role);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }
}
