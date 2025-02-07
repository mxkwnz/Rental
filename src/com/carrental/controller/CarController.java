package com.carrental.controller;

import com.carrental.services.CarService;
import java.sql.SQLException;
import java.util.Scanner;

public class CarController {
    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    public void displayAvailableCars() throws SQLException {
        System.out.println("Available Cars:");
        carService.getAvailableCars();
    }

    public void addNewCar(Scanner scanner) throws SQLException {
        System.out.print("Enter Car Brand: ");
        String brand = scanner.nextLine();
        System.out.print("Enter Car Model: ");
        String model = scanner.nextLine();
        System.out.print("Enter Price Per Day: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Availability (true/false): ");
        boolean available = scanner.nextBoolean();
        scanner.nextLine();

        carService.addNewCar(brand, model, price, available);
        System.out.println("Car added successfully.");
    }

    public void removeCar(Scanner scanner) throws SQLException {
        System.out.print("Enter Car ID to remove: ");
        int carId = scanner.nextInt();
        scanner.nextLine();
        carService.removeCar(carId);
        System.out.println("Car successfully removed.");
    }

    public void manageCarStatus(Scanner scanner) throws SQLException {
        System.out.print("Enter Car ID to update status: ");
        int carId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Is the car available? (true/false): ");
        boolean available = scanner.nextBoolean();
        scanner.nextLine();

        carService.updateCarStatus(carId, available);
        System.out.println("Car status updated successfully.");
    }
}
