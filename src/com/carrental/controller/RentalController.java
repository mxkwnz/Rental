package com.carrental.controller;

import com.carrental.services.RentalService;
import com.carrental.services.UserService;
import com.carrental.services.CarService;
import java.sql.SQLException;
import java.util.Scanner;

public class RentalController {
    private RentalService rentalService;
    private UserService userService;
    private CarService carService;

    public RentalController(RentalService rentalService, UserService userService, CarService carService) {
        this.rentalService = rentalService;
        this.userService = userService;
        this.carService = carService;
    }

    public void startRentalProcess(Scanner scanner) throws SQLException {
        System.out.print("Enter Car ID to rent: ");
        int carId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Start Date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        System.out.print("Enter End Date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();

        rentalService.rentCar(carId, userId, startDate, endDate);
        System.out.println("Car rental process started successfully.");
    }

    public void manageRentals(Scanner scanner) throws SQLException {
        while (true) {
            System.out.println("\n===== Rental Management =====");
            System.out.println("1. View Rentals");
            System.out.println("2. Approve/Reject Rental");
            System.out.println("3. View Rented Cars");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> viewRentals();
                case 2 -> approveOrRejectRentals(scanner);
                case 3 -> viewRentedCars();
                case 4 -> {
                    System.out.println("Exiting Rental Management...");
                    return;
                }
                default -> System.out.println("❌ Invalid option.");
            }
        }
    }

    public void approveOrRejectRentals(Scanner scanner) throws SQLException {
        System.out.print("Enter Rental ID to approve/reject: ");
        int rentalId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Approve (true) or Reject (false)?: ");
        boolean approve = scanner.nextBoolean();
        scanner.nextLine();
        rentalService.updateRentalStatus(rentalId, approve);
        System.out.println("Rental decision recorded successfully.");
    }

    public void viewRentedCars() throws SQLException {
        System.out.println("Currently Rented Cars:");
        rentalService.getRentedCars();
    }

    public void viewRentals() throws SQLException {
        System.out.println("Showing all rentals...");
        rentalService.getAllRentals();
    }
}
