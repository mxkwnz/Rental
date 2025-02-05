package com.carrental.services;

import com.carrental.db.interfaces.IDB;
import com.carrental.interfaces.IRentalService;

import java.sql.*;

public class RentalService implements IRentalService {
    private IDB db;

    public RentalService(IDB db) {
        this.db = db;
    }

    @Override
    public void rentCar(int carId, int userId, String startDate, String endDate) throws SQLException {
        if (isCarOccupied(carId, startDate, endDate)) {
            System.out.println("Машина уже занята в выбранный период. Пожалуйста, выберите другую машину или даты.");
            return;
        }

        String priceQuery = "SELECT price_per_day FROM Cars WHERE id = ?";
        double pricePerDay = 0;
        try (Connection conn = db.getConnection();
             PreparedStatement priceStmt = conn.prepareStatement(priceQuery)) {
            priceStmt.setInt(1, carId);
            ResultSet priceRs = priceStmt.executeQuery();
            if (priceRs.next()) {
                pricePerDay = priceRs.getDouble("price_per_day");
            }

            long diffDays = (Date.valueOf(endDate).getTime() - Date.valueOf(startDate).getTime()) / (1000 * 60 * 60 * 24);
            double totalPrice = pricePerDay * diffDays;

            String rentQuery = "INSERT INTO Rentals (car_id, user_id, start_date, end_date, total_price) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement rentStmt = conn.prepareStatement(rentQuery, Statement.RETURN_GENERATED_KEYS)) {
                rentStmt.setInt(1, carId);
                rentStmt.setInt(2, userId);
                rentStmt.setDate(3, Date.valueOf(startDate));
                rentStmt.setDate(4, Date.valueOf(endDate));
                rentStmt.setDouble(5, totalPrice);
                rentStmt.executeUpdate();

                ResultSet generatedKeys = rentStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int rentalId = generatedKeys.getInt(1);
                    System.out.println("Аренда успешно оформлена с ID аренды: " + rentalId);
                    addRentalDays(rentalId, startDate, endDate);
                    printRentalInvoice(rentalId);
                }
            }
        }
    }

    public boolean isCarOccupied(int carId, String startDate, String endDate) throws SQLException {
        String query = "SELECT COUNT(*) FROM Rentals WHERE car_id = ? AND ((start_date <= ? AND end_date >= ?) OR (start_date <= ? AND end_date >= ?))";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, carId);
            stmt.setDate(2, Date.valueOf(endDate));
            stmt.setDate(3, Date.valueOf(startDate));
            stmt.setDate(4, Date.valueOf(startDate));
            stmt.setDate(5, Date.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        }
        return false;
    }

    public void addRentalDays(int rentalId, String startDate, String endDate) throws SQLException {
        String insertRentalDaysQuery = "INSERT INTO RentalDays (rental_id, rental_date) VALUES (?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertRentalDaysQuery)) {

            long diffDays = (Date.valueOf(endDate).getTime() - Date.valueOf(startDate).getTime()) / (1000 * 60 * 60 * 24);
            for (int i = 0; i <= diffDays; i++) {
                Date rentalDay = new Date(Date.valueOf(startDate).getTime() + (i * (1000 * 60 * 60 * 24)));
                stmt.setInt(1, rentalId);
                stmt.setDate(2, rentalDay);
                stmt.executeUpdate();
            }
            System.out.println("Дни аренды успешно добавлены.");
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
        String query = "SELECT * FROM Rentals WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, rentalId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int carId = rs.getInt("car_id");
                int userId = rs.getInt("user_id");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                double totalPrice = rs.getDouble("total_price");

                String carQuery = "SELECT name FROM Cars WHERE id = ?";
                String userQuery = "SELECT name FROM Users WHERE id = ?";
                String carName = "", userName = "";

                try (PreparedStatement carStmt = conn.prepareStatement(carQuery);
                     PreparedStatement userStmt = conn.prepareStatement(userQuery)) {

                    carStmt.setInt(1, carId);
                    userStmt.setInt(1, userId);

                    ResultSet carRs = carStmt.executeQuery();
                    ResultSet userRs = userStmt.executeQuery();

                    if (carRs.next()) {
                        carName = carRs.getString("name");
                    }
                    if (userRs.next()) {
                        userName = userRs.getString("name");
                    }
                }

                System.out.println("Чек аренды:");
                System.out.println("Машина: " + carName);
                System.out.println("Пользователь: " + userName);
                System.out.println("Дата начала: " + startDate);
                System.out.println("Дата окончания: " + endDate);
                System.out.println("Общая стоимость: " + totalPrice + " KZT");
            }
        }
    }

    @Override
    public void removeRentalDaysByPeriod(int rentalId, String startDate, String endDate) throws SQLException {
        String query = "DELETE FROM RentalDays WHERE rental_id = ? AND rental_date BETWEEN ? AND ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, rentalId);
            stmt.setDate(2, Date.valueOf(startDate));
            stmt.setDate(3, Date.valueOf(endDate));
            stmt.executeUpdate();
            System.out.println("Дни аренды удалены.");
        }
    }

    @Override
    public void updateRental(int rentalId, String newStartDate, String newEndDate) throws SQLException {
        String updateQuery = "UPDATE Rentals SET start_date = ?, end_date = ? WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setDate(1, Date.valueOf(newStartDate));
            stmt.setDate(2, Date.valueOf(newEndDate));
            stmt.setInt(3, rentalId);
            stmt.executeUpdate();
            System.out.println("Аренда обновлена.");

            removeRentalDaysByPeriod(rentalId, newStartDate, newEndDate);
            addRentalDays(rentalId, newStartDate, newEndDate);
        }
    }
}
