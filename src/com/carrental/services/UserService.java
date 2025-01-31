package com.carrental.services;

import com.carrental.db.DatabaseConnection;
import com.carrental.interfaces.IUserService;
import java.sql.*;
import java.util.Scanner;

public class UserService implements IUserService {
    public boolean userExists(int userId) throws SQLException {
        String query = "SELECT id FROM Users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public void addNewUser(int userId) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите имя нового пользователя: ");
        String name = scanner.nextLine();
        System.out.print("Введите email нового пользователя: ");
        String email = scanner.nextLine();
        System.out.print("Введите телефон нового пользователя: ");
        String phone = scanner.nextLine();

        String insertUserQuery = "INSERT INTO Users (id, name, email, phone) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(insertUserQuery)) {
            stmt.setInt(1, userId);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.executeUpdate();
            System.out.println("Новый пользователь с ID " + userId + " был успешно добавлен в базу данных.");
        }
    }
}