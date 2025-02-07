package com.carrental.services;

import com.carrental.db.interfaces.IDB;
import java.sql.*;

public class RentalService {
    private IDB db;

    public RentalService(IDB db) {
        this.db = db;
    }

    public void rentCar(int carId, int userId, String startDate, String endDate) throws SQLException {
        String query = "INSERT INTO Rentals (car_id, user_id, start_date, end_date, status) VALUES (?, ?, ?, ?, 'PENDING')";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, carId);
            stmt.setInt(2, userId);
            stmt.setDate(3, java.sql.Date.valueOf(startDate));
            stmt.setDate(4, java.sql.Date.valueOf(endDate));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int rentalId = rs.getInt(1);
                    System.out.println("Car rental process started successfully.");
                    printReceipt(rentalId);
                } else {
                    System.err.println("Error: Unable to retrieve rental ID.");
                }
            }
        }
    }

    private void printReceipt(int rentalId) throws SQLException {
        String query = "SELECT r.id AS rental_id, u.name AS user_name, c.model AS car_model, c.rate_per_day, " +
                "r.start_date, r.end_date " +
                "FROM Rentals r " +
                "JOIN Users u ON r.user_id = u.id " +
                "JOIN Cars c ON r.car_id = c.id " +
                "WHERE r.id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, rentalId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String userName = rs.getString("user_name");
                    String carModel = rs.getString("car_model");
                    double ratePerDay = rs.getDouble("rate_per_day");
                    Date startDate = rs.getDate("start_date");
                    Date endDate = rs.getDate("end_date");

                    long days = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
                    double totalCost = days * ratePerDay;

                    System.out.println("========== RENTAL RECEIPT ==========");
                    System.out.printf("Rental ID: %d\n", rentalId);
                    System.out.printf("Customer Name: %s\n", userName);
                    System.out.printf("Car Model: %s\n", carModel);
                    System.out.printf("Rate per Day: %.2f\n", ratePerDay);
                    System.out.printf("Start Date: %s\n", startDate);
                    System.out.printf("End Date: %s\n", endDate);
                    System.out.printf("Total Days: %d\n", days);
                    System.out.printf("Total Cost: %.2f\n", totalCost);
                    System.out.println("====================================");
                } else {
                    System.err.println("Error: Receipt data not found.");
                }
            }
        }
    }

    // Метод для обновления статуса аренды
    public void updateRentalStatus(int rentalId, boolean approve) throws SQLException {
        String query = "UPDATE Rentals SET status = ? WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, approve ? "APPROVED" : "REJECTED");
            stmt.setInt(2, rentalId);
            stmt.executeUpdate();
        }
    }

    public void getAllRentals() throws SQLException {
        String query = "SELECT * FROM Rentals";
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("List of all rentals:");
            while (rs.next()) {
                System.out.printf("Rental ID: %d, Car ID: %d, User ID: %d, Start: %s, End: %s, Status: %s\n",
                        rs.getInt("id"), rs.getInt("car_id"), rs.getInt("user_id"),
                        rs.getDate("start_date"), rs.getDate("end_date"),
                        rs.getString("status"));
            }
        }
    }

    public void getRentedCars() throws SQLException {
        String query = "SELECT * FROM Rentals WHERE status = 'APPROVED'";
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Currently Rented Cars:");
            while (rs.next()) {
                System.out.printf("Rental ID: %d, Car ID: %d, User ID: %d, Start: %s, End: %s\n",
                        rs.getInt("id"), rs.getInt("car_id"), rs.getInt("user_id"),
                        rs.getDate("start_date"), rs.getDate("end_date"));
            }
        }
    }
}
