package com.carrental.main;

import com.carrental.controller.CarController;
import com.carrental.controller.UserController;
import com.carrental.controller.RentalController;
import com.carrental.services.CarService;
import com.carrental.services.UserService;
import com.carrental.services.RentalService;
import com.carrental.db.PostgreDB;
import com.carrental.menu.AdminMenu;
import com.carrental.menu.ManagerMenu;
import com.carrental.menu.CustomerMenu;
import com.carrental.models.Password;
import com.carrental.models.User;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            PostgreDB db = new PostgreDB("localhost", "postgres", "postgres", "E00244631");
            CarService carService = new CarService(db);
            UserService userService = new UserService(db);
            RentalService rentalService = new RentalService(db);

            CarController carController = new CarController(carService);
            UserController userController = new UserController(userService);
            RentalController rentalController = new RentalController(rentalService, carService);

            AdminMenu adminMenu = new AdminMenu(userController, carController, rentalController);
            ManagerMenu managerMenu = new ManagerMenu(rentalController, carController, userController);
            CustomerMenu customerMenu = new CustomerMenu(rentalController, carController);

            Scanner scanner = new Scanner(System.in);

            System.out.println("Select your role: ");
            System.out.println("1. Admin");
            System.out.println("2. Manager");
            System.out.println("3. Customer");

            int roleChoice = scanner.nextInt();
            scanner.nextLine();

            String role = null;
            switch (roleChoice) {
                case 1:
                    role = "ADMIN";
                    break;
                case 2:
                    role = "MANAGER";
                    break;
                case 3:
                    role = "CUSTOMER";
                    break;
                default:
                    System.out.println("Invalid choice. Exiting...");
                    return;
            }

            System.out.print("Enter your ID: ");
            int userId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter your Password: ");
            String passwordString = scanner.nextLine();

            Password password = new Password(passwordString);

            User user = userController.authenticateUser(userId, passwordString, role);

            if (user != null) {
                System.out.println("Login successful!");

                while (true) {
                    switch (role.toUpperCase()) {
                        case "ADMIN":
                            adminMenu.displayMenu();
                            int adminChoice = scanner.nextInt();
                            adminMenu.handleChoice(adminChoice);
                            break;
                        case "MANAGER":
                            managerMenu.displayMenu();
                            int managerChoice = scanner.nextInt();
                            managerMenu.handleChoice(managerChoice);
                            break;
                        case "CUSTOMER":
                            customerMenu.displayMenu();
                            int customerChoice = scanner.nextInt();
                            customerMenu.handleChoice(customerChoice);
                            break;
                        default:
                            System.out.println("Unknown role.");
                            break;
                    }

                    System.out.print("");
                    String answer = scanner.nextLine();
                    if (answer.equalsIgnoreCase("no")) {
                        System.out.println("Logging out...");
                        break;
                    }
                }
            } else {
                System.out.println("Invalid ID or Password. Exiting...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
