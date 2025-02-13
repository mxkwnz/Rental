package com.carrental.services;

import com.carrental.db.interfaces.IDB;
import com.carrental.models.Car;
import com.carrental.models.VehicleCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarService {
    private IDB db;

    public CarService(IDB db) {
        this.db = db;
    }

    public void addNewCar(String brand, String model, double price, boolean available, VehicleCategory category) throws SQLException {
        String query = "INSERT INTO cars (brand, model, rate_per_day, available, category) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, brand);
            stmt.setString(2, model);
            stmt.setDouble(3, price);
            stmt.setBoolean(4, available);
            stmt.setString(5, category.name());
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
    public List<Car> getAllCars() throws SQLException {
        String query = "SELECT * FROM cars";
        List<Car> cars = new ArrayList<>();
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                cars.add(new Car(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getBoolean("available"),
                        rs.getDouble("rate_per_day"),
                        VehicleCategory.valueOf(rs.getString("category"))
                ));
            }
        }
        return cars;
    }

    public List<Car> getAvailableCars() throws SQLException {
        String query = "SELECT * FROM cars WHERE available = TRUE";
        List<Car> availableCars = new ArrayList<>();
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                availableCars.add(new Car(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getBoolean("available"),
                        rs.getDouble("rate_per_day"),
                        VehicleCategory.valueOf(rs.getString("category"))
                ));
            }
        }
        return availableCars;
    }

    public List<Car> getCarsByCategory(VehicleCategory category) throws SQLException {
        String query = "SELECT * FROM cars WHERE category = ? AND available = TRUE";
        List<Car> cars = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, category.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cars.add(new Car(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getBoolean("available"),
                        rs.getDouble("rate_per_day"),
                        VehicleCategory.valueOf(rs.getString("category"))
                ));
            }
        }
        return cars;
    }

    public Car getCarById(int carId) throws SQLException {
        String query = "SELECT * FROM cars WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, carId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Car(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getBoolean("available"),
                        rs.getDouble("rate_per_day"),
                        VehicleCategory.valueOf(rs.getString("category"))
                );
            } else {
                return null;
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
