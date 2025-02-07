package com.carrental.controller;

import com.carrental.models.User;
import com.carrental.services.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    public void addNewUser(Scanner scanner) throws SQLException {
        System.out.print("Enter User Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Role (ADMIN, MANAGER, CUSTOMER): ");
        String role = scanner.nextLine().toUpperCase();

        User newUser = new User(0, name, email, phone, password, role);
        userService.addUser(newUser);
        System.out.println("User successfully added.");
    }

    public void removeUser(Scanner scanner) throws SQLException {
        System.out.print("Enter User ID to remove: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        userService.removeUser(userId);
        System.out.println("User successfully removed.");
    }

    public void displayUsers() throws SQLException {
        System.out.println("All Users:");
        userService.getAllUsers();
    }
}
