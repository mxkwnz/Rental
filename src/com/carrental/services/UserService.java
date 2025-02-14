package com.carrental.services;

import com.carrental.db.interfaces.IDB;
import com.carrental.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private IDB db;

    public UserService(IDB db) {
        this.db = db;
    }

    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO users (name, email, phone, password, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole());
            stmt.executeUpdate();

            System.out.println("User successfully added.");
        } catch (SQLException e) {
            System.err.println("Error while adding user: " + e.getMessage());
            throw e;
        }
    }

    public void addManager(String name, String email) throws SQLException {
        String query = "INSERT INTO users (name, email, role) VALUES (?, ?, 'MANAGER')";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.executeUpdate();

            System.out.println("Manager successfully added.");
        } catch (SQLException e) {
            System.err.println("Error while adding manager: " + e.getMessage());
            throw e;
        }
    }

    public void removeUser(int userId) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User successfully removed.");
            } else {
                System.out.println("User with ID " + userId + " not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error while removing user: " + e.getMessage());
            throw e;
        }
    }

    public void removeManager(int managerId) throws SQLException {
        String query = "DELETE FROM users WHERE id = ? AND role = 'MANAGER'";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, managerId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Manager successfully removed.");
            } else {
                System.out.println("Manager with ID " + managerId + " not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error while removing manager: " + e.getMessage());
            throw e;
        }
    }

    public User authenticateUser(int userId, String password) throws SQLException {
        String query = "SELECT id, name, email, phone, password, role FROM users WHERE id = ? AND password = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            } else {
                System.out.println("Invalid login credentials.");
            }
        } catch (SQLException e) {
            System.err.println("Error during user authentication: " + e.getMessage());
            throw e;
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException {
        String query = "SELECT id, name, email, phone, role FROM users";
        List<User> users = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        null,
                        rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving the list of users: " + e.getMessage());
            throw e;
        }
        return users;
    }

    public List<User> getUsersByRole(String role) throws SQLException {
        String query = "SELECT id, name, email, phone, role FROM users WHERE role = ?";
        List<User> users = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        null,
                        rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving users by role: " + e.getMessage());
            throw e;
        }
        return users;
    }

    public void viewManagers() throws SQLException {
        String query = "SELECT id, name, email FROM users WHERE role = 'MANAGER'";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.printf("ID: %d, Name: %s, Email: %s%n", rs.getInt("id"), rs.getString("name"), rs.getString("email"));
            }
        }
    }


    public void viewCustomers() throws SQLException {
        String query = "SELECT id, name, email FROM users WHERE role = 'CUSTOMER'";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.printf("ID: %d, Name: %s, Email: %s%n", rs.getInt("id"), rs.getString("name"), rs.getString("email"));
            }
        }
    }
}
