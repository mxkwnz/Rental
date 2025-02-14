package com.carrental.controller;

import com.carrental.services.CarService;
import com.carrental.models.VehicleCategory;
import com.carrental.models.Car;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CarController {
    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
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

        System.out.println("Select Car Category:");
        for (int i = 0; i < VehicleCategory.values().length; i++) {
            System.out.println((i + 1) + ". " + VehicleCategory.values()[i]);
        }
        System.out.print("Enter your choice: ");
        int categoryChoice = scanner.nextInt();
        scanner.nextLine();

        if (categoryChoice < 1 || categoryChoice > VehicleCategory.values().length) {
            System.out.println("Invalid category choice.");
            return;
        }

        VehicleCategory category = VehicleCategory.values()[categoryChoice - 1];

        carService.addNewCar(brand, model, price, available, category);
        System.out.println("Car added successfully.");
    }

    public void removeCar(Scanner scanner) throws SQLException {
        System.out.println("Available Cars:");
        List<Car> allCars = carService.getAllCars();
        if (allCars.isEmpty()) {
            System.out.println("No cars available.");
        } else {
            allCars.forEach(System.out::println);
            System.out.print("Enter Car ID to remove: ");
            int carId = scanner.nextInt();
            scanner.nextLine();

            carService.removeCar(carId);
            System.out.println("Car successfully removed.");
        }
    }

    public void viewAllCars() throws SQLException {
        System.out.println("Viewing All Cars:");
        List<Car> allCars = carService.getAllCars();
        if (allCars.isEmpty()) {
            System.out.println("No cars available.");
        } else {
            for (Car car : allCars) {
                System.out.println(car);
            }
        }
    }

    public void displayAvailableCars() throws SQLException {
        List<Car> availableCars = carService.getAvailableCars();
        if (availableCars.isEmpty()) {
            System.out.println("No available cars.");
        } else {
            for (Car car : availableCars) {
                System.out.println(car);
            }
        }
    }

    public void manageCarStatus(Scanner scanner) throws SQLException {
        System.out.println("Managing Car Status...");
        System.out.print("Enter Car ID to change status: ");
        int carId = scanner.nextInt();
        scanner.nextLine();

        Car car = carService.getCarById(carId);
        if (car == null) {
            System.out.println("Car not found.");
            return;
        }

        System.out.println("Current status of the car: " + (car.isAvailable() ? "Available" : "Unavailable"));
        System.out.print("Change status to (1: Available, 2: Unavailable): ");
        int statusChoice = scanner.nextInt();
        scanner.nextLine();

        boolean newStatus = statusChoice == 1;

        carService.updateCarStatus(carId, newStatus);
        System.out.println("Car status updated successfully.");
    }
}