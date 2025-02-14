package com.carrental.main;

import com.carrental.services.UserService;
import com.carrental.models.User;
import com.carrental.db.PostgreDB;
import java.util.Scanner;

public class CarRentalApplication {

    private UserService userService;

    public CarRentalApplication(UserService userService) {
        this.userService = userService;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Password: ");
        String password = scanner.nextLine();


        try {
            User user = userService.authenticateUser(userId, password);

            if (user != null) {
                System.out.println("Welcome, " + user.getName() + "!");
            } else {
                System.out.println("Authentication failed.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred during authentication: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        PostgreDB dbConnection = new PostgreDB("host", "database", "user", "password"); // Adjust according to your DB setup
        UserService userService = new UserService(dbConnection);
        CarRentalApplication app = new CarRentalApplication(userService);
        app.run();
    }
}
