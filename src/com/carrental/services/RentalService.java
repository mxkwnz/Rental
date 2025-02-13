package com.carrental.services;

import com.carrental.db.PostgreDB;
import com.carrental.models.Rental;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RentalService {
    private PostgreDB db;

    public RentalService(PostgreDB db) {
        this.db = db;
    }

    public void rentCar(int carId, int userId, String startDate, String endDate) throws SQLException {
        String query = "INSERT INTO rentals (car_id, user_id, start_date, end_date, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, carId);
            statement.setInt(2, userId);
            statement.setString(3, startDate);
            statement.setString(4, endDate);
            statement.setString(5, "Pending");

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Car rented successfully.");
            } else {
                System.out.println("Failed to rent the car.");
            }
        }
    }

    public List<Rental> getAllRentals() throws SQLException {
        List<Rental> rentals = new ArrayList<>();
        String query = "SELECT * FROM rentals";

        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Rental rental = new Rental(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("car_id"),
                        resultSet.getString("start_date"),
                        resultSet.getString("end_date"),
                        resultSet.getString("status")
                );
                rentals.add(rental);
            }
        }
        return rentals;
    }

    public void updateRentalStatus(int rentalId, boolean approve) throws SQLException {
        String status = approve ? "Approved" : "Rejected";
        String query = "UPDATE rentals SET status = ? WHERE id = ?";

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, status);
            statement.setInt(2, rentalId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Rental status updated to " + status);
            } else {
                System.out.println("Failed to update rental status.");
            }
        }
    }

    public void manageRentals(Scanner scanner) throws SQLException {
        List<Rental> rentals = getAllRentals();

        if (rentals.isEmpty()) {
            System.out.println("No rentals available to manage.");
            return;
        }

        System.out.println("Managing rentals...");
        System.out.println("\nList of Rentals:");

        for (int i = 0; i < rentals.size(); i++) {
            Rental rental = rentals.get(i);
            System.out.println((i + 1) + ". Rental ID: " + rental.getRentalId() +
                    ", Car ID: " + rental.getCarId() +
                    ", User ID: " + rental.getUserId() +
                    ", Start Date: " + rental.getStartDate() +
                    ", End Date: " + rental.getEndDate() +
                    ", Status: " + rental.getStatus());
        }

        System.out.print("Enter rental ID to approve/reject (or 0 to return): ");
        int rentalId = scanner.nextInt();
        scanner.nextLine();

        if (rentalId == 0) {
            System.out.println("Returning to menu...");
            return;
        }

        Rental selectedRental = null;
        for (Rental rental : rentals) {
            if (rental.getRentalId() == rentalId) {
                selectedRental = rental;
                break;
            }
        }

        if (selectedRental == null) {
            System.out.println("Invalid rental ID. Returning to menu...");
            return;
        }

        System.out.print("Approve this rental? (yes/no): ");
        String decision = scanner.nextLine();
        boolean approve = decision.equalsIgnoreCase("yes");

        updateRentalStatus(rentalId, approve);
        System.out.println("Rental status updated to " + (approve ? "Approved" : "Rejected"));
    }

    public void getRentedCars() throws SQLException {
        String query = "SELECT * FROM rentals WHERE status = 'Approved'";

        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int rentalId = resultSet.getInt("id");
                int carId = resultSet.getInt("car_id");
                int userId = resultSet.getInt("user_id");
                String startDate = resultSet.getString("start_date");
                String endDate = resultSet.getString("end_date");

                System.out.println("Rental ID: " + rentalId + ", Car ID: " + carId + ", User ID: " + userId +
                        ", Start Date: " + startDate + ", End Date: " + endDate);
            }
        }
    }
}
