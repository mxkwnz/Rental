package com.carrental.services;

import com.carrental.db.DatabaseConnection;
import com.carrental.interfaces.ICarService;
import com.carrental.models.Car;
import java.sql.*;

public class CarService implements ICarService {
    @Override
    public void showAvailableCars() throws SQLException {
        String query = "SELECT * FROM Cars WHERE availability = TRUE";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Доступные автомобили для аренды:");
            while (rs.next()) {
                Car car = new Car(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("model"),
                        rs.getBoolean("availability"),
                        rs.getDouble("price_per_day")
                );
                System.out.println("ID: " + car.getId() + ", Название: " + car.getName() +
                        ", Модель: " + car.getModel() + ", Цена в день: " + car.getPricePerDay() + " KZT");
            }
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
}