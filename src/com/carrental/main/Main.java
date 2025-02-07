package com.carrental.main;

import com.carrental.db.PostgreDB;
import com.carrental.controller.CarController;
import com.carrental.controller.UserController;
import com.carrental.controller.RentalController;
import com.carrental.models.User;
import com.carrental.services.CarService;
import com.carrental.services.UserService;
import com.carrental.services.RentalService;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        PostgreDB db = new PostgreDB("localhost", "postgres", "postgres", "E00244631");

        CarService carService = new CarService(db);
        UserService userService = new UserService(db);
        RentalService rentalService = new RentalService(db);

        CarController carController = new CarController(carService);
        UserController userController = new UserController(userService);
        RentalController rentalController = new RentalController(rentalService, userService, carService);

        Scanner scanner = new Scanner(System.in);

        User loggedInUser = authenticateUser(scanner, userService);
        if (loggedInUser == null) {
            System.out.println("Error: Invalid ID or password.");
            return;
        }

        switch (loggedInUser.getRole().toUpperCase()) {
            case "ADMIN" -> adminMenu(scanner, userController, carController, rentalController);
            case "MANAGER" -> managerMenu(scanner, rentalController, carController);
            case "CUSTOMER" -> customerMenu(scanner, rentalController, carController);
            default -> System.out.println("Error: Unknown role.");
        }
    }

    private static User authenticateUser(Scanner scanner, UserService userService) throws SQLException {
        System.out.print("Enter your ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        return userService.authenticateUser(userId, password);
    }

    private static void adminMenu(Scanner scanner, UserController userController, CarController carController, RentalController rentalController) throws SQLException {
        while (true) {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Manage Users");
            System.out.println("2. Add Car");
            System.out.println("3. Remove Car");
            System.out.println("4. Manage Rentals");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> userController.displayUsers();
                case 2 -> carController.addNewCar(scanner);
                case 3 -> carController.removeCar(scanner);
                case 4 -> rentalController.manageRentals(scanner);
                case 5 -> {
                    System.out.println("Exiting admin menu.");
                    return;
                }
                default -> System.out.println("Invalid input.");
            }
        }
    }

    private static void managerMenu(Scanner scanner, RentalController rentalController, CarController carController) throws SQLException {
        while (true) {
            System.out.println("\n===== MANAGER MENU =====");
            System.out.println("1. Approve or Reject Rental");
            System.out.println("2. View Rented Cars");
            System.out.println("3. Update Car Status");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> rentalController.approveOrRejectRentals(scanner);
                case 2 -> rentalController.viewRentedCars();
                case 3 -> carController.manageCarStatus(scanner);
                case 4 -> {
                    System.out.println("Exiting manager menu.");
                    return;
                }
                default -> System.out.println("Invalid input.");
            }
        }
    }

    private static void customerMenu(Scanner scanner, RentalController rentalController, CarController carController) throws SQLException {
        while (true) {
            System.out.println("\n===== CUSTOMER MENU =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. View Available Cars");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> rentalController.startRentalProcess(scanner);
                case 2 -> carController.displayAvailableCars();
                case 3 -> {
                    System.out.println("Exiting customer menu.");
                    return;
                }
                default -> System.out.println("Invalid input.");
            }
        }
    }
}

