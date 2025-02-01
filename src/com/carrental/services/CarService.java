package com.carrental.services;

import com.carrental.db.DatabaseConnection;
import com.carrental.models.Car;
import com.carrental.interfaces.ICarService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarService implements ICarService {

    @Override
    public List<Car> getAvailableCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM Cars";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Car car = new Car(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("model"),
                        rs.getBoolean("availability"),
                        rs.getDouble("price_per_day")
                );
                cars.add(car);
            }
        }
        return cars;
    }

    @Override
    public void addNewCar(String name, String model, double pricePerDay) throws SQLException {
        String query = "INSERT INTO Cars (name, model, availability, price_per_day) VALUES (?, ?, TRUE, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, model);
            stmt.setDouble(3, pricePerDay);
            stmt.executeUpdate();
        }
    }

    @Override
    public void removeCar(int carId) throws SQLException {
        String query = "DELETE FROM Cars WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, carId);
            stmt.executeUpdate();
        }
    }

    @Override
    public Car getCarById(int carId) throws SQLException {
        String query = "SELECT * FROM Cars WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, carId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Car(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("model"),
                        rs.getBoolean("availability"),
                        rs.getDouble("price_per_day")
                );
            } else {
                throw new SQLException("Car not found.");
            }
        }
    }

    @Override
    public boolean isCarAvailable(int carId) throws SQLException {
        String query = "SELECT availability FROM Cars WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, carId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("availability");
            } else {
                return false;
            }
        }
    }
}
