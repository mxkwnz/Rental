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
        System.out.println("Доступные автомобили:");
        for (Car car : cars) {
            boolean isAvailable = carService.isCarAvailable(car.getId());
            System.out.println("ID машины: " + car.getId() + ", Название: " + car.getName() +
                    ", Модель: " + car.getModel() + ", Цена в день: " + car.getPricePerDay() + " KZT, Доступность: " + (isAvailable ? "Да" : "Нет"));
        }
    }

    public void addNewCar(Scanner scanner) throws SQLException {
        System.out.print("Введите название машины: ");
        String name = scanner.nextLine();
        System.out.print("Введите модель машины: ");
        String model = scanner.nextLine();
        System.out.print("Введите цену за день аренды: ");
        double pricePerDay = scanner.nextDouble();

        carService.addNewCar(name, model, pricePerDay);
    }

    public void removeCar(Scanner scanner) throws SQLException {
        System.out.print("Введите ID машины для удаления: ");
        int carId = scanner.nextInt();
        carService.removeCar(carId);
    }
}