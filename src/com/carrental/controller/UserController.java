package com.carrental.controller;

import com.carrental.models.User;
import com.carrental.services.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void addUser(Scanner scanner, String role) throws SQLException {
        System.out.print("Enter User Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User newUser = new User(0, name, email, phone, password, role.toUpperCase());
        userService.addUser(newUser);
        System.out.println(role + " successfully added.");
    }

    public void removeUser(Scanner scanner, String role) throws SQLException {
        System.out.print("Enter " + role + " ID to remove: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        userService.removeUser(userId);
        System.out.println(role + " successfully removed.");
    }

    public void displayUsers() throws SQLException {
        System.out.println("All Users:");
        userService.getAllUsers();
    }

    public void displayCustomers() throws SQLException {
        System.out.println("All Customers:");
        List<User> customers = userService.getUsersByRole("CUSTOMER");
        customers.forEach(System.out::println);
    }

    public void displayManagers() throws SQLException {
        System.out.println("All Managers:");
        List<User> managers = userService.getUsersByRole("MANAGER");
        managers.forEach(System.out::println);
    }
}
