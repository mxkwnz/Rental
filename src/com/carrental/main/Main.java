package com.carrental.main;

import com.carrental.services.*;
import com.carrental.interfaces.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        ICarService carService = new CarService();
        IUserService userService = new UserService();
        IRentalService rentalService = new RentalService();

        carService.showAvailableCars();

        System.out.print("Введите ID автомобиля для аренды: ");
        int carId = scanner.nextInt();
        System.out.print("Введите ID пользователя (или 0 для создания нового пользователя): ");
        int userId = scanner.nextInt();
        System.out.print("Введите дату начала аренды (YYYY-MM-DD): ");
        String startDateStr = scanner.next();
        System.out.print("Введите дату окончания аренды (YYYY-MM-DD): ");
        String endDateStr = scanner.next();

        Date startDate = Date.valueOf(startDateStr);
        Date endDate = Date.valueOf(endDateStr);

        rentalService.rentCar(carId, userId, startDate, endDate);
    }
}