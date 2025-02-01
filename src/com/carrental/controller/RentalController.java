package com.carrental.controller;

import com.carrental.interfaces.IRentalService;
import com.carrental.interfaces.IUserService;
import com.carrental.interfaces.ICarService;

import java.sql.SQLException;
import java.util.Scanner;

public class RentalController {

    private IRentalService rentalService;
    private IUserService userService;
    private ICarService carService;

    public RentalController(IRentalService rentalService, IUserService userService, ICarService carService) {
        this.rentalService = rentalService;
        this.userService = userService;
        this.carService = carService;
    }

    public void startRentalProcess(int carId, int userId, String startDate, String endDate) throws SQLException {
        if (!userService.userExists(userId)) {
            System.out.println("Пользователь с ID " + userId + " не существует.");
            System.out.print("Хотите добавить этого пользователя? (да/нет): ");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine().toLowerCase();
            if (response.equals("да")) {
                addNewUser(scanner);
                rentalService.rentCar(carId, userId, startDate, endDate);
            } else {
                System.out.println("Операция аренды отменена.");
            }
        } else {
            if (rentalService.isCarOccupied(carId, startDate, endDate)) {
                System.out.println("Эта машина уже занята в выбранные даты. Пожалуйста, выберите другую машину или даты.");
            } else {
                rentalService.rentCar(carId, userId, startDate, endDate);
                printRentalInvoice(userId);
            }
        }
    }

    public void addNewUser(Scanner scanner) throws SQLException {
        System.out.print("Введите имя пользователя: ");
        String name = scanner.nextLine();
        System.out.print("Введите email пользователя: ");
        String email = scanner.nextLine();
        System.out.print("Введите телефон пользователя: ");
        String phone = scanner.nextLine();

        userService.addNewUser(name, email, phone);
    }

    public void printRentalInvoice(int userId) throws SQLException {
        rentalService.printRentalInvoice(userId);
    }

    public void removeRental(int rentalId) throws SQLException {
        rentalService.removeRental(rentalId);
    }

    public void removeRentalDays(Scanner scanner) throws SQLException {
        System.out.print("Введите ID аренды для удаления дней: ");
        int rentalId = scanner.nextInt();
        System.out.print("Введите дату начала (YYYY-MM-DD): ");
        String startDate = scanner.next();
        System.out.print("Введите дату окончания (YYYY-MM-DD): ");
        String endDate = scanner.next();

        rentalService.removeRentalDaysByPeriod(rentalId, startDate, endDate);
    }

    public void updateRental(Scanner scanner) throws SQLException {
        System.out.print("Введите ID аренды для обновления: ");
        int rentalId = scanner.nextInt();
        System.out.print("Введите новую дату начала (YYYY-MM-DD): ");
        String newStartDate = scanner.next();
        System.out.print("Введите новую дату окончания (YYYY-MM-DD): ");
        String newEndDate = scanner.next();

        rentalService.updateRental(rentalId, newStartDate, newEndDate);
    }
}
