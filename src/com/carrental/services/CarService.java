package com.carrental.services;

import com.carrental.db.interfaces.IDB;
import java.sql.*;

public class CarService {
    private IDB db;

    public CarService(IDB db) {
        this.db = db;
    }

    public void addNewCar(String brand, String model, double price, boolean available) throws SQLException {
        String query = "INSERT INTO cars (brand, model, rate_per_day, available) VALUES (?, ?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, brand);
            stmt.setString(2, model);
            stmt.setDouble(3, price);
            stmt.setBoolean(4, available);
            stmt.executeUpdate();
        }
    }

    public void removeCar(int carId) throws SQLException {
        String query = "DELETE FROM cars WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, carId);
            stmt.executeUpdate();
        }
    }

    public void getAllCars() throws SQLException {
        String query = "SELECT * FROM cars";
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.printf("Car ID: %d, Brand: %s, Model: %s, Price: %.2f, Available: %b\n",
                        rs.getInt("id"), rs.getString("brand"), rs.getString("model"),
                        rs.getDouble("rate_per_day"), rs.getBoolean("available"));
            }
        }
    }

    public void getAvailableCars() throws SQLException {
        String query = "SELECT * FROM cars WHERE available = TRUE";
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.printf("Car ID: %d, Brand: %s, Model: %s, Price: %.2f\n",
                        rs.getInt("id"), rs.getString("brand"), rs.getString("model"),
                        rs.getDouble("rate_per_day"));
            }
        }
    }

    public void updateCarStatus(int carId, boolean available) throws SQLException {
        String query = "UPDATE cars SET available = ? WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, available);
            stmt.setInt(2, carId);
            stmt.executeUpdate();
        }
    }
}
