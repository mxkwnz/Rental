package com.carrental.main;

import com.carrental.controller.CarController;
import com.carrental.controller.UserController;
import com.carrental.controller.RentalController;
import com.carrental.services.CarService;
import com.carrental.services.UserService;
import com.carrental.services.RentalService;
import com.carrental.db.PostgreDB;

import java.util.Scanner;
import java.sql.SQLException;

public class MyAppApplication {
    private CarController carController;
    private UserController userController;
    private RentalController rentalController;

    public MyAppApplication(CarController carController, UserController userController, RentalController rentalController) {
        this.carController = carController;
        this.userController = userController;
        this.rentalController = rentalController;
    }

    public void run() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== Main Menu =====");
            System.out.println("1. Add New Car");
            System.out.println("2. Remove Car");
            System.out.println("3. Rent a Car");
            System.out.println("4. Add New User");
            System.out.println("5. Remove User");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                carController.addNewCar(scanner);
            } else if (choice == 2) {
                carController.removeCar(scanner);
            } else if (choice == 3) {
                rentalController.startRentalProcess(scanner);
            } else if (choice == 4) {
                userController.addUser(scanner, "MANAGER");
            } else if (choice == 5) {
                userController.removeUser(scanner, "MANAGER");
            } else if (choice == 6) {
                break;
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        PostgreDB db = new PostgreDB("localhost", "rental_db", "user", "password");
        CarService carService = new CarService(db);
        UserService userService = new UserService(db);
        RentalService rentalService = new RentalService(db);

        CarController carController = new CarController(carService);
        UserController userController = new UserController(userService);
        RentalController rentalController = new RentalController(rentalService, userService, carService);

        MyAppApplication app = new MyAppApplication(carController, userController, rentalController);
        app.run();
    }
}
