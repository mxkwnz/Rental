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
    public static void main(String[] args) {
        try {
            PostgreDB db = new PostgreDB("localhost", "postgres1", "postgres", "taktalif12");

            CarService carService = new CarService(db);
            UserService userService = new UserService(db);
            RentalService rentalService = new RentalService(db);

            CarController carController = new CarController(carService);
            UserController userController = new UserController(userService);
            RentalController rentalController = new RentalController(rentalService, userService, carService);

            Scanner scanner = new Scanner(System.in);

            String role = selectUserRole(scanner);
            if (role == null) {
                System.out.println("Exiting system.");
                return;
            }

            User loggedInUser = authenticateUser(scanner, userService, role);
            if (loggedInUser == null) {
                System.out.println("Error: Invalid ID or password.");
                return;
            }

            switch (loggedInUser.getRole().toUpperCase()) {
                case "ADMIN" -> adminMenu(scanner, userController, carController, rentalController);
                case "MANAGER" -> managerMenu(scanner, rentalController, carController, userController);
                case "CUSTOMER" -> customerMenu(scanner, rentalController, carController);
                default -> System.out.println("Error: Unknown role.");
            }
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    private static String selectUserRole(Scanner scanner) {
        while (true) {
            System.out.println("\n===== SELECT USER ROLE =====");
            System.out.println("1. Admin");
            System.out.println("2. Manager");
            System.out.println("3. Customer");
            System.out.println("4. Exit");
            System.out.print("Choose your role: ");
            int roleChoice = scanner.nextInt();
            scanner.nextLine();

            switch (roleChoice) {
                case 1 -> { return "ADMIN"; }
                case 2 -> { return "MANAGER"; }
                case 3 -> { return "CUSTOMER"; }
                case 4 -> { return null; }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static User authenticateUser(Scanner scanner, UserService userService, String role) throws SQLException {
        System.out.print("Enter your ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = userService.authenticateUser(userId, password);
        return (user != null && user.getRole().equalsIgnoreCase(role)) ? user : null;
    }

    private static void adminMenu(Scanner scanner, UserController userController, CarController carController, RentalController rentalController) {
        while (true) {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Add Manager");
            System.out.println("2. Remove Manager");
            System.out.println("3. View Managers");
            System.out.println("4. View Customers");
            System.out.println("5. Add Car");
            System.out.println("6. Remove Car");
            System.out.println("7. Manage Rentals");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> safeRun(() -> userController.addUser(scanner, "MANAGER"));
                case 2 -> safeRun(() -> userController.removeUser(scanner, "MANAGER"));
                case 3 -> safeRun(userController::displayManagers);
                case 4 -> safeRun(userController::displayCustomers);
                case 5 -> safeRun(() -> carController.addNewCar(scanner));
                case 6 -> safeRun(() -> carController.removeCar(scanner));
                case 7 -> safeRun(() -> rentalController.manageRentals(scanner));
                case 8 -> {
                    System.out.println("Exiting admin menu.");
                    return;
                }
                default -> System.out.println("Invalid input.");
            }
        }
    }

    private static void managerMenu(Scanner scanner, RentalController rentalController, CarController carController, UserController userController) {
        while (true) {
            System.out.println("\n===== MANAGER MENU =====");
            System.out.println("1. Approve/Reject Rentals");
            System.out.println("2. View Rented Cars");
            System.out.println("3. Manage Car Status");
            System.out.println("4. View Customers");
            System.out.println("5. Add Customer");
            System.out.println("6. Remove Customer");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> safeRun(() -> rentalController.approveOrRejectRentals(scanner));
                case 2 -> safeRun(rentalController::viewRentedCars);
                case 3 -> safeRun(() -> carController.manageCarStatus(scanner));
                case 4 -> safeRun(userController::displayCustomers);
                case 5 -> safeRun(() -> userController.addUser(scanner, "CUSTOMER"));
                case 6 -> safeRun(() -> userController.removeUser(scanner, "CUSTOMER"));
                case 7 -> {
                    System.out.println("Exiting manager menu.");
                    return;
                }
                default -> System.out.println("Invalid input.");
            }
        }
    }

    private static void customerMenu(Scanner scanner, RentalController rentalController, CarController carController) {
        while (true) {
            System.out.println("\n===== CUSTOMER MENU =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. View Available Cars");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> safeRun(() -> rentalController.startRentalProcess(scanner));
                case 2 -> safeRun(carController::displayAvailableCars);
                case 3 -> {
                    System.out.println("Exiting customer menu.");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void safeRun(SqlRunnable runnable) {
        try {
            runnable.run();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    @FunctionalInterface
    interface SqlRunnable {
        void run() throws SQLException;
    }
}
