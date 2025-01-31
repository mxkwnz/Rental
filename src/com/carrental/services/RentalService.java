package com.carrental.services;

import com.carrental.db.DatabaseConnection;
import com.carrental.interfaces.IRentalService;
import java.sql.*;
import java.util.Scanner;

public class RentalService implements IRentalService {

    public void addRentalDays(int rentalId, Date startDate, Date endDate) throws SQLException {
        String insertRentalDaysQuery = "INSERT INTO RentalDays (rental_id, rental_date) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertRentalDaysQuery)) {

            long diffDays = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
            for (int i = 0; i <= diffDays; i++) {
                Date rentalDay = new Date(startDate.getTime() + (i * (1000 * 60 * 60 * 24)));
                stmt.setInt(1, rentalId);
                stmt.setDate(2, rentalDay);stmt.executeUpdate();
            }
            System.out.println("Дни аренды успешно добавлены.");
        }
    }

    @Override
    public void rentCar(int carId, int userId, Date startDate, Date endDate) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();

        if (userId == 0 || !userService.userExists(userId)) {
            System.out.println("Пользователь с ID " + userId + " не найден.");
            System.out.print("Хотите создать нового пользователя? (yes/no): ");
            String response = scanner.nextLine().toLowerCase();
            if (response.equals("yes")) {
                userId = getUserInputId(scanner);
                userService.addNewUser(userId);
            } else {
                System.out.println("Операция аренды отменена.");
                return;
            }
        }

        if (isCarOccupied(carId, startDate, endDate)) {
            System.out.println("Машина уже занята в выбранные даты. Пожалуйста, выберите другой день.");
            return;
        }

        String priceQuery = "SELECT price_per_day FROM Cars WHERE id = ?";
        double pricePerDay = 0;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement priceStmt = conn.prepareStatement(priceQuery)) {
            priceStmt.setInt(1, carId);
            ResultSet priceRs = priceStmt.executeQuery();
            if (priceRs.next()) {
                pricePerDay = priceRs.getDouble("price_per_day");
            }

            long diffDays = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
            double totalPrice = pricePerDay * diffDays;

            String rentQuery = "INSERT INTO Rentals (car_id, user_id, start_date, end_date, total_price) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement rentStmt = conn.prepareStatement(rentQuery, Statement.RETURN_GENERATED_KEYS)) {
                rentStmt.setInt(1, carId);
                rentStmt.setInt(2, userId);
                rentStmt.setDate(3, startDate);
                rentStmt.setDate(4, endDate);
                rentStmt.setDouble(5, totalPrice);
                rentStmt.executeUpdate();

                ResultSet generatedKeys = rentStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int rentalId = generatedKeys.getInt(1);
                    addRentalDays(rentalId, startDate, endDate); // Добавляем дни аренды в таблицу RentalDays
                }

                System.out.println("Аренда успешно оформлена! Общая стоимость аренды: " + totalPrice + " KZT");
            }
        }
    }
    private static int getUserInputId(Scanner scanner) {
        int userId = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Введите ID нового пользователя: ");
            String userInput = scanner.nextLine();
            try {
                userId = Integer.parseInt(userInput); // Преобразуем строку в число
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода! Пожалуйста, введите корректный ID.");
            }
        }
        return userId;
    }

    private boolean isCarOccupied(int carId, Date startDate, Date endDate) throws SQLException {
        String checkQuery = "SELECT COUNT(*) FROM Rentals WHERE car_id = ? AND ((start_date <= ? AND end_date >= ?) OR (start_date <= ? AND end_date >= ?))";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(checkQuery)) {
            stmt.setInt(1, carId);
            stmt.setDate(2, endDate);
            stmt.setDate(3, startDate);
            stmt.setDate(4, startDate);
            stmt.setDate(5, endDate);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        }
        return false;
    }
}