package com.carrental.main;

import com.carrental.controller.CarController;
import com.carrental.controller.UserController;
import com.carrental.controller.RentalController;

import java.sql.SQLException;
import java.util.Scanner;

public class MyAppApplication {

    private CarController carController;
    private UserController userController;
    private RentalController rentalController;
    private Scanner scanner = new Scanner(System.in);

    public MyAppApplication(CarController carController, UserController userController, RentalController rentalController) {
        this.carController = carController;
        this.userController = userController;
        this.rentalController = rentalController;
    }

    public void run() throws SQLException {
        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Показать доступные машины");
            System.out.println("2. Показать пользователей");
            System.out.println("3. Арендовать машину");
            System.out.println("4. Добавить новую машину");
            System.out.println("5. Удалить машину");
            System.out.println("6. Добавить нового пользователя");
            System.out.println("7. Удалить пользователя");
            System.out.println("8. Удалить дни аренды");
            System.out.println("9. Обновить аренду");
            System.out.println("10. Выйти");
            System.out.print("Выберите опцию: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                carController.displayAvailableCars();
            } else if (choice == 2) {
                userController.displayUsers();
            } else if (choice == 3) {
                System.out.print("Введите ID машины для аренды: ");
                int carId = scanner.nextInt();
                System.out.print("Введите ID пользователя: ");
                int userId = scanner.nextInt();
                System.out.print("Введите дату начала аренды (YYYY-MM-DD): ");
                String startDate = scanner.next();
                System.out.print("Введите дату окончания аренды (YYYY-MM-DD): ");
                String endDate = scanner.next();

                rentalController.startRentalProcess(carId, userId, startDate, endDate);
            } else if (choice == 4) {
                carController.addNewCar(scanner);
            } else if (choice == 5) {
                carController.removeCar(scanner);
            } else if (choice == 6) {
                userController.addNewUser(scanner);
            } else if (choice == 7) {
                userController.removeUser(scanner);
            } else if (choice == 8) {
                rentalController.removeRentalDays(scanner);
            } else if (choice == 9) {
                rentalController.updateRental(scanner);
            } else if (choice == 10) {
                break;
            }
        }
    }
}
