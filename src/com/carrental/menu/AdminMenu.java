package com.carrental.menu;

import com.carrental.controller.CarController;
import com.carrental.controller.UserController;
import com.carrental.controller.RentalController;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminMenu implements Menu {
    private final UserController userController;
    private final CarController carController;
    private final RentalController rentalController;

    public AdminMenu(UserController userController, CarController carController, RentalController rentalController) {
        this.userController = userController;
        this.carController = carController;
        this.rentalController = rentalController;
    }

    @Override
    public void displayMenu() {
        System.out.println("\n===== ADMIN MENU =====");
        System.out.println("1. Add Manager");
        System.out.println("2. Remove Manager");
        System.out.println("3. View Managers");
        System.out.println("4. View Customers");
        System.out.println("5. Add Car");
        System.out.println("6. Remove Car");
        System.out.println("7. Manage Rentals");
        System.out.println("8. View All Cars");
        System.out.println("0. Exit");
    }


    @Override
    public void handleChoice(int choice) {
        try {
            switch (choice) {
                case 1 -> userController.addUser(new Scanner(System.in), "MANAGER");
                case 2 -> userController.removeUser(new Scanner(System.in), "MANAGER");
                case 3 -> userController.displayManagers();
                case 4 -> userController.displayCustomers();
                case 5 -> carController.addNewCar(new Scanner(System.in));
                case 6 -> carController.removeCar(new Scanner(System.in));
                case 7 -> rentalController.manageRentals(new Scanner(System.in));
                case 8 -> carController.viewAllCars();
                case 0 -> System.out.println("Exiting Admin Menu...");
                default -> System.out.println("Invalid choice, please try again.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
