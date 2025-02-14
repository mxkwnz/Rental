package com.carrental.menu;

import com.carrental.controller.CarController;
import com.carrental.controller.RentalController;
import com.carrental.controller.UserController;
import java.sql.SQLException;
import java.util.Scanner;

public class ManagerMenu implements Menu {
    private final RentalController rentalController;
    private final CarController carController;
    private final UserController userController;

    public ManagerMenu(RentalController rentalController, CarController carController, UserController userController) {
        this.rentalController = rentalController;
        this.carController = carController;
        this.userController = userController;
    }


    @Override
    public void displayMenu() {
        System.out.println("\n===== MANAGER MENU =====");
        System.out.println("1. Approve/Reject Rentals");
        System.out.println("2. View Rented Cars");
        System.out.println("3. Manage Car Status");
        System.out.println("4. View Customers");
        System.out.println("5. Add Customer");
        System.out.println("6. Remove Customer");
        System.out.println("7. View All Cars");
        System.out.println("0. Exit");
    }


    @Override
    public void handleChoice(int choice) {
        try {
            switch (choice) {
                case 1 -> rentalController.manageRentals(new Scanner(System.in));  // Исправлено
                case 2 -> rentalController.viewRentedCars();  // Исправлено
                case 3 -> carController.manageCarStatus(new Scanner(System.in));
                case 4 -> userController.displayCustomers();
                case 5 -> userController.addUser(new Scanner(System.in), "CUSTOMER");
                case 6 -> userController.removeUser(new Scanner(System.in), "CUSTOMER");
                case 7 -> carController.viewAllCars();
                case 0 -> System.out.println("Exiting Manager Menu...");
                default -> System.out.println("Invalid choice, please try again.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
