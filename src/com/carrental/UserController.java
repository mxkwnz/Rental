package com.carrental.controller;

import com.carrental.interfaces.IUserService;
import com.carrental.models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserController {

    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    public void displayUsers() throws SQLException {
        List<User> users = userService.getAllUsers();
        System.out.println("Users:");
        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail() + ", Phone: " + user.getPhone());
        }
    }

    public void addNewUser(Scanner scanner) throws SQLException {
        System.out.print("Enter the user's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the user's email: ");
        String email = scanner.nextLine();
        System.out.print("Enter the user's phone: ");
        String phone = scanner.nextLine();

        userService.addNewUser(name, email, phone);
    }

    public void removeUser(Scanner scanner) throws SQLException {
        System.out.print("Enter the User ID to remove: ");
        int userId = scanner.nextInt();
        userService.removeUser(userId);
    }
}
