package com.carrental.controller;

import com.carrental.interfaces.ICarService;
import com.carrental.models.Car;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CarController {

    private ICarService carService;

    public CarController(ICarService carService) {
        this.carService = carService;
    }

    public void displayAvailableCars() throws SQLException {
        List<Car> cars = carService.getAvailableCars();
        System.out.println("Available Cars:");
        for (Car car : cars) {
            boolean isAvailable = carService.isCarAvailable(car.getId());
            System.out.println("Car ID: " + car.getId() + ", Name: " + car.getName() +
                    ", Model: " + car.getModel() + ", Price per day: " + car.getPricePerDay() + " KZT, Available: " + (isAvailable ? "Yes" : "No"));
        }
    }

    public void addNewCar(Scanner scanner) throws SQLException {
        System.out.print("Enter the car's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the car's model: ");
        String model = scanner.nextLine();
        System.out.print("Enter the car's price per day: ");
        double pricePerDay = scanner.nextDouble();

        carService.addNewCar(name, model, pricePerDay);
    }

    public void removeCar(Scanner scanner) throws SQLException {
        System.out.print("Enter the Car ID to remove: ");
        int carId = scanner.nextInt();
        carService.removeCar(carId);
    }
}
