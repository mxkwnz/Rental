package com.carrental.services;

import com.carrental.db.interfaces.IDB;
import com.carrental.interfaces.IRentalService;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RentalService implements IRentalService {
    private IDB db;

    public RentalService(IDB db) {
        this.db = db;
    }

    @Override
    public void rentCar(int carId, int userId, String startDate, String endDate) throws SQLException {
        String query = "INSERT INTO Rentals (car_id, user_id, start_date, end_date, total_price) VALUES (?, ?, ?, ?, ?)";

        // Convert Strings to java.sql.Date
        java.sql.Date sqlStartDate = convertToDate(startDate);
        java.sql.Date sqlEndDate = convertToDate(endDate);

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, carId);
            stmt.setInt(2, userId);
            stmt.setDate(3, sqlStartDate);
            stmt.setDate(4, sqlEndDate);

            // Calculate total price based on car rental days
            double totalPrice = calculateTotalPrice(carId, sqlStartDate, sqlEndDate, conn);
            stmt.setDouble(5, totalPrice);

            stmt.executeUpdate();
        }
    }

    @Override
    public void removeRental(int rentalId) throws SQLException {
        String query = "DELETE FROM Rentals WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, rentalId);
            stmt.executeUpdate();
        }
    }

    @Override
    public void printRentalInvoice(int rentalId) throws SQLException {
        String query = "SELECT r.id AS rental_id, c.name AS car_name, c.model AS car_model, " +
                "u.name AS user_name, u.email AS user_email, r.start_date, r.end_date, r.total_price " +
                "FROM Rentals r " +
                "JOIN Cars c ON r.car_id = c.id " +
                "JOIN Users u ON r.user_id = u.id " +
                "WHERE r.id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, rentalId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Чек аренды:");
                System.out.println("Машина: " + rs.getString("car_name") + " (" + rs.getString("car_model") + ")");
                System.out.println("Клиент: " + rs.getString("user_name") + " - " + rs.getString("user_email"));
                System.out.println("Дата начала: " + rs.getDate("start_date"));
                System.out.println("Дата окончания: " + rs.getDate("end_date"));
                System.out.println("Общая стоимость: " + rs.getDouble("total_price") + " KZT");
            }
        }
    }

    @Override
    public boolean isCarOccupied(int carId, String startDate, String endDate) throws SQLException {
        String query = "SELECT COUNT(*) FROM Rentals WHERE car_id = ? AND " +
                "((start_date BETWEEN ? AND ?) OR (end_date BETWEEN ? AND ?))";

        java.sql.Date sqlStartDate = convertToDate(startDate);
        java.sql.Date sqlEndDate = convertToDate(endDate);

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, carId);
            stmt.setDate(2, sqlStartDate);
            stmt.setDate(3, sqlEndDate);
            stmt.setDate(4, sqlStartDate);
            stmt.setDate(5, sqlEndDate);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    @Override
    public void removeRentalDaysByPeriod(int rentalId, String startDate, String endDate) throws SQLException {
        String query = "UPDATE Rentals SET end_date = ? WHERE id = ? AND start_date = ?";
        java.sql.Date sqlStartDate = convertToDate(startDate);
        java.sql.Date sqlEndDate = convertToDate(endDate);

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, sqlEndDate);
            stmt.setInt(2, rentalId);
            stmt.setDate(3, sqlStartDate);
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateRental(int rentalId, String newStartDate, String newEndDate) throws SQLException {
        String query = "UPDATE Rentals SET start_date = ?, end_date = ? WHERE id = ?";
        java.sql.Date sqlNewStartDate = convertToDate(newStartDate);
        java.sql.Date sqlNewEndDate = convertToDate(newEndDate);

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, sqlNewStartDate);
            stmt.setDate(2, sqlNewEndDate);
            stmt.setInt(3, rentalId);
            stmt.executeUpdate();
        }
    }

    // Helper method to convert String to java.sql.Date
    private java.sql.Date convertToDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = sdf.parse(dateStr);
            return new java.sql.Date(parsedDate.getTime());
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format! Use yyyy-MM-dd", e);
        }
    }

    // Helper method to calculate total price based on car rental days
    private double calculateTotalPrice(int carId, java.sql.Date startDate, java.sql.Date endDate, Connection conn) throws SQLException {
        String priceQuery = "SELECT price_per_day FROM Cars WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(priceQuery)) {
            stmt.setInt(1, carId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double pricePerDay = rs.getDouble("price_per_day");

                // Calculate rental duration in days
                long diffInMillis = endDate.getTime() - startDate.getTime();
                int rentalDays = (int) (diffInMillis / (1000 * 60 * 60 * 24));

                return rentalDays * pricePerDay;
            }
        }
        return 0;
    }
}
