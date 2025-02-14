package com.carrental.menu;

import com.carrental.controller.RentalController;
import com.carrental.controller.CarController;

import java.sql.SQLException;
import java.util.Scanner;

public class CustomerMenu {
    private RentalController rentalController;
    private CarController carController;

    public CustomerMenu(RentalController rentalController, CarController carController) {
        this.rentalController = rentalController;
        this.carController = carController;
    }

    public void displayMenu() {
        System.out.println("Customer Menu:");
        System.out.println("1. Start rental process");
        System.out.println("2. View available cars");
        System.out.println("0. Exit");
    }


    public void handleChoice(int choice) throws SQLException {
        switch (choice) {
            case 1:
                rentalController.startRentalProcess(new Scanner(System.in));
                break;
            case 2:
                carController.displayAvailableCars();
                break;
            case 0:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
}
